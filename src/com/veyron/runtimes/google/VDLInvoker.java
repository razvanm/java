
package com.veyron.runtimes.google;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.vdl.VeyronService;

/**
 * VDLInvoker is a helper class that uses reflection to invoke VDL interface
 * methods for objects that implements those interfaces. It is required that the
 * provided objects implement exactly one VDL interface.
 */
public final class VDLInvoker {
    // A cache of ClassInfo objects, aiming to reduce the cost of expensive
    // reflection operations.
    private static Map<Class<?>, ClassInfo> serviceWrapperClasses = new HashMap<Class<?>, ClassInfo>();

    private final static class ServiceMethod {
        private final Object wrappedService;
        private final Method method;

        public ServiceMethod(Object wrappedService, Method method) {
            this.wrappedService = wrappedService;
            this.method = method;
        }

        public Object invoke(Object... args) throws IllegalAccessException,
        IllegalArgumentException, InvocationTargetException {
            return method.invoke(wrappedService, args);
        }
    }

    private final Map<String, ServiceMethod> invokableMethods = new HashMap<String, ServiceMethod>();

    private final Gson gson = new Gson();
    private final Class<?> serviceClass; // Only used to make exception messages

    // more clear.

    /**
     * Creates a new invoker for the given object.
     * 
     * @param obj service object we're invoking methods on
     * @returns Invoker new VDL invoker instance
     * @throws IllegalArgumentException if the provided object is invalid
     *             (either null or doesn't implement exactly one VDL interface)
     */
    // TODO(bprosnitz) We need to throw better exception types in the final
    // release.
    public VDLInvoker(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException("Can't create VDLInvoker with a null object.");
        }
        this.serviceClass = obj.getClass();

        List<Object> serviceWrappers = wrapService(obj);
        for (Object wrapper : serviceWrappers) {
            final Class<?> c = wrapper.getClass();
            ClassInfo cInfo;
            synchronized (VDLInvoker.this) {
                cInfo = VDLInvoker.serviceWrapperClasses.get(c);
            }
            if (cInfo == null) {
                cInfo = new ClassInfo(c);

                // Note that multiple threads might decide to create a new
                // ClassInfo and insert it
                // into the cache, but that's just wasted work and not a race
                // condition.
                synchronized (VDLInvoker.this) {
                    VDLInvoker.serviceWrapperClasses.put(c, cInfo);
                }
            }

            for (Entry<String, Method> m : cInfo.getMethods()) {
                invokableMethods.put(m.getKey(), new ServiceMethod(wrapper, m.getValue()));
            }
        }
    }

    /**
     * Iterate through the veyron services an object implements and generates
     * service wrappers for each.
     * 
     * @param srv The service object
     * @return A list of service wrappers
     * @throws IllegalArgumentException If the input service is invalid.
     */
    private List<Object> wrapService(Object srv) throws IllegalArgumentException {
        Class<?> klass = srv.getClass();
        List<Object> stubs = new ArrayList<Object>();
        for (Class<?> iface : klass.getInterfaces()) {
            VeyronService vs = iface.getAnnotation(VeyronService.class);
            // There should only be one constructor.
            if (vs.serviceWrapper().getConstructors().length != 1) {
                throw new RuntimeException(
                        "Expected ServiceWrapper to only have a single constructor");
            }
            Constructor<?> constructor = vs.serviceWrapper().getConstructors()[0];

            try {
                stubs.add(constructor.newInstance(srv));
            } catch (InstantiationException e) {
                throw new RuntimeException("Invalid constructor. Problem instanciating.", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Invalid constructor. Illegal access.", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Invalid constructor. Problem invoking.", e);
            }
        }
        if (stubs.size() == 0) {
            throw new IllegalArgumentException(
                    "Object does not implement a valid generated service interface.");
        }
        return stubs;
    }

    /**
     * InvokeReply stores the replies for the {@link #invoke} method. The
     * replies are JSON-encoded. In addition to replies, this class also stores
     * application error, if any.
     */
    public class InvokeReply {
        public String[] results; // can be null, e.g., if an error occurred.
        public boolean hasApplicationError = false;
        public String errorID;
        public String errorMsg;
    }

    /**
     * Converts the provided arguments from JSON and invokes the given method
     * using reflection. JSON-encodes the reply. Application errors are returned
     * along with the reply, while any other encountered errors are thrown as
     * exceptions.
     * 
     * @param method name of the method to be invoked
     * @param call in-flight call information
     * @param inArgs JSON encoded arguments to the method
     * @return InvokeReply JSON-encoded invocation reply and application errors
     * @throws IllegalArgumentException if invalid arguments are passed
     * @throws IllegalAccessException if a runtime access error occurs
     * @throws InvocationTargetException
     */
    public InvokeReply invoke(String method, ServerCall call, String[] inArgs) throws
    IllegalArgumentException, IllegalAccessException {
        final ServiceMethod m = this.invokableMethods.get(method);
        if (m == null) {
            throw new IllegalArgumentException(String.format(
                    "Couldn't find method %s in class %s",
                    method, this.serviceClass.getCanonicalName()));
        }

        // Decode JSON arguments.
        final Object[] args = this.prepareArgs(m, call, inArgs);

        // Invoke the method and process results.
        final InvokeReply reply = new InvokeReply();
        try {
            final Object result = m.invoke(args);
            reply.results = this.prepareResults(m, result);
        } catch (InvocationTargetException e) { // The underlying method threw
            // an exception.
            VeyronException ve;
            if ((e.getCause() instanceof VeyronException)) {
                ve = (VeyronException) e.getTargetException();
            } else {
                // Dump the stack trace locally.
                e.getTargetException().printStackTrace();

                ve = new VeyronException(
                        String.format(
                                "Remote invocations of java methods may only throw VeyronException, but call to %s threw %s",
                                method, e.getTargetException().getClass()));
            }
            reply.hasApplicationError = true;
            reply.errorID = ve.getID();
            reply.errorMsg = ve.getMessage();
        }

        return reply;
    }

    private Object[] prepareArgs(ServiceMethod m, ServerCall call, String[] inArgs)
            throws JsonSyntaxException {
        final Class<?>[] inTypes = m.method.getParameterTypes();
        assert inArgs.length == inTypes.length;

        // The first argument is always context, so we add it.
        final int argsLength = inArgs.length + 1;
        final Object[] ret = new Object[argsLength];
        ret[0] = call;
        for (int i = 0; i < inArgs.length; i++) {
            ret[i + 1] = this.gson.fromJson(inArgs[i], inTypes[i]);
        }
        return ret;
    }

    private String[] prepareResults(ServiceMethod m, Object result)
            throws IllegalArgumentException, IllegalAccessException {
        if (m.method.getReturnType().getDeclaringClass() == m.method.getDeclaringClass()) {
            // The return type was declared in the service definition, so this
            // method has multiple out args.
            final Field[] fields = m.method.getReturnType().getFields();
            final String[] reply = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                reply[i] = this.gson.toJson(fields[i].get(result));
            }
            return reply;
        }
        final String[] reply = new String[1];
        reply[0] = this.gson.toJson(result);
        return reply;
    }

    private static class ClassInfo {
        final Map<String, Method> methods = new HashMap<String, Method>();

        ClassInfo(Class<?> c) throws IllegalArgumentException {
            final Method[] methodList = c.getDeclaredMethods();
            for (int i = 0; i < methodList.length; i++) {
                final Method method = methodList[i];
                Method oldval = null;
                try {
                    oldval = this.methods.put(method.getName(), method);
                } catch (IllegalArgumentException e) {
                } // method not an VDL method.
                if (oldval != null) {
                    throw new IllegalArgumentException("Overloading of method " + method.getName()
                            + " not allowed on service wrapper");
                }
            }
        }

        Set<Entry<String, Method>> getMethods() {
            return this.methods.entrySet();
        }
    }
}
