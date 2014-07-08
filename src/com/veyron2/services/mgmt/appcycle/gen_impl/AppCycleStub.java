// This file was auto-generated by the veyron vdl tool.
// Source(s):  appcycle.vdl
package com.veyron2.services.mgmt.appcycle.gen_impl;

import com.veyron2.services.mgmt.appcycle.AppCycle;
import com.veyron2.services.mgmt.appcycle.AppCycleFactory;
import com.veyron2.services.mgmt.appcycle.AppCycleService;
import com.veyron2.services.mgmt.appcycle.Task;

/* Client stub for interface: AppCycle. */
public final class AppCycleStub implements AppCycle {
	private static final java.lang.String vdlIfacePathOpt = "com.veyron2.services.mgmt.appcycle.AppCycle";
	private final com.veyron2.ipc.Client client;
	private final java.lang.String name;

	public AppCycleStub(com.veyron2.ipc.Client client, java.lang.String name) {
		this.client = client;
		this.name = name;
	}
	// Methods from interface AppCycle.
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Void,Task,java.lang.Void> stop(com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
		return stop(context, null);
	}
	@Override
	public com.veyron2.vdl.ClientStream<java.lang.Void,Task,java.lang.Void> stop(com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Prepare input arguments.
		final java.lang.Object[] inArgs = new java.lang.Object[]{  };

		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
		// Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, AppCycleStub.vdlIfacePathOpt);
		}

		// Start the call.
		final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.name, "Stop", inArgs, veyronOpts);

		return new com.veyron2.vdl.ClientStream<java.lang.Void, Task, java.lang.Void>() {
			@Override
			public void send(java.lang.Void item) throws com.veyron2.ipc.VeyronException {
				call.send(item);
			}
			@Override
			public Task recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
				final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken<Task>() {};
				final java.lang.Object result = call.recv(type);
				try {
					return (Task)result;
				} catch (java.lang.ClassCastException e) {
					throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
				}
			}
			@Override
			public java.lang.Void finish() throws com.veyron2.ipc.VeyronException {
				// Prepare output argument and finish the call.
					final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{  };
					call.finish(resultTypes);
					return null;

			}
		};
	}
	@Override
	public void forceStop(com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
		forceStop(context, null);
	}
	@Override
	public void forceStop(com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Prepare input arguments.
		final java.lang.Object[] inArgs = new java.lang.Object[]{  };

		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
		// Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, AppCycleStub.vdlIfacePathOpt);
		}

		// Start the call.
		final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.name, "ForceStop", inArgs, veyronOpts);

		// Prepare output argument and finish the call.
			final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{  };
			call.finish(resultTypes);

	}
}
