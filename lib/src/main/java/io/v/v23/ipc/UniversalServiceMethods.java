package io.v.v23.ipc;

/**
 * UniversalServiceMethods defines the set of methods that are implemented on all services.
 */
public interface UniversalServiceMethods {
    /**
     * Returns a description of the service.
     *
     * @param  context         client context for the call.
     * @return                 description of the service.
     * @throws VException      if the description couldn't be fetched.
     */
    // TODO(spetrovic): Re-enable once we can import the new Signature classes.
    //public ServiceSignature getSignature(VContext context) throws VException;


    /**
     * Returns a description of the service.
     *
     * @param  context         client context for the call.
     * @param  opts            call options.
     * @return                 description of the service.
     * @throws VException      if the description couldn't be fetched.
     */
    // TODO(spetrovic): Re-enable once we can import the new Signature classes.
    //public ServiceSignature getSignature(VContext context, Options opts) throws VException;
}