// This file was auto-generated by the veyron vdl tool.
// Source(s):  base.vdl
package com.veyron2.vdl.test_base.gen_impl;

import com.veyron2.vdl.test_base.Args;
import com.veyron2.vdl.test_base.CompComp;
import com.veyron2.vdl.test_base.Composites;
import com.veyron2.vdl.test_base.NamedStruct;
import com.veyron2.vdl.test_base.NestedArgs;
import com.veyron2.vdl.test_base.Scalars;
import com.veyron2.vdl.test_base.ServiceA;
import com.veyron2.vdl.test_base.ServiceAFactory;
import com.veyron2.vdl.test_base.ServiceAService;
import com.veyron2.vdl.test_base.ServiceB;
import com.veyron2.vdl.test_base.ServiceBFactory;
import com.veyron2.vdl.test_base.ServiceBService;
import com.veyron2.vdl.test_base.VeyronConsts;

/* Client stub for interface: ServiceB. */
public final class ServiceBStub implements ServiceB {
	private static final java.lang.String vdlIfacePathOpt = "com.veyron2.vdl.test_base.ServiceB";
	private final com.veyron2.ipc.Client client;
	private final java.lang.String name;
	private final ServiceA serviceA;

	public ServiceBStub(com.veyron2.ipc.Client client, java.lang.String name) {
		this.client = client;
		this.name = name;
		this.serviceA = new com.veyron2.vdl.test_base.gen_impl.ServiceAStub(client, name);
	}
	// Methods from interface ServiceB.
	@Override
	public CompComp methodB1(com.veyron2.ipc.Context context, Scalars a, Composites b) throws com.veyron2.ipc.VeyronException {
		return methodB1(context, a, b, null);
	}
	@Override
	public CompComp methodB1(com.veyron2.ipc.Context context, Scalars a, Composites b, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Prepare input arguments.
		final java.lang.Object[] inArgs = new java.lang.Object[]{ a, b };

		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
		// Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ServiceBStub.vdlIfacePathOpt);
		}

		// Start the call.
		final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.name, "MethodB1", inArgs, veyronOpts);

		// Prepare output argument and finish the call.
			final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{ new com.google.common.reflect.TypeToken<CompComp>() {} };
			return (CompComp)call.finish(resultTypes)[0];

	}
	// Methods from sub-interface ServiceA.
	@Override
	public void methodA1(com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
		methodA1(context, null);
	}
	@Override
	public void methodA1(com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
	    // Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ServiceBStub.vdlIfacePathOpt);
		}
		this.serviceA.methodA1(context, veyronOpts);
	}
	@Override
	public java.lang.String methodA2(com.veyron2.ipc.Context context, int a, java.lang.String b) throws com.veyron2.ipc.VeyronException {
		return methodA2(context, a, b, null);
	}
	@Override
	public java.lang.String methodA2(com.veyron2.ipc.Context context, int a, java.lang.String b, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
	    // Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ServiceBStub.vdlIfacePathOpt);
		}
		return this.serviceA.methodA2(context, a, b, veyronOpts);
	}
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Void,Scalars,java.lang.String> methodA3(com.veyron2.ipc.Context context, int a) throws com.veyron2.ipc.VeyronException {
		return methodA3(context, a, null);
	}
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Void,Scalars,java.lang.String> methodA3(com.veyron2.ipc.Context context, int a, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
	    // Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ServiceBStub.vdlIfacePathOpt);
		}
		return this.serviceA.methodA3(context, a, veyronOpts);
	}
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Integer,java.lang.String,java.lang.Void> methodA4(com.veyron2.ipc.Context context, int a) throws com.veyron2.ipc.VeyronException {
		return methodA4(context, a, null);
	}
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Integer,java.lang.String,java.lang.Void> methodA4(com.veyron2.ipc.Context context, int a, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
	    // Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ServiceBStub.vdlIfacePathOpt);
		}
		return this.serviceA.methodA4(context, a, veyronOpts);
	}
}
