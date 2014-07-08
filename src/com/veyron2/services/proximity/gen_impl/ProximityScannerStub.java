// This file was auto-generated by the veyron vdl tool.
// Source(s):  proximity.vdl
package com.veyron2.services.proximity.gen_impl;

import com.veyron2.services.proximity.Device;
import com.veyron2.services.proximity.Proximity;
import com.veyron2.services.proximity.ProximityAnnouncer;
import com.veyron2.services.proximity.ProximityAnnouncerFactory;
import com.veyron2.services.proximity.ProximityAnnouncerService;
import com.veyron2.services.proximity.ProximityFactory;
import com.veyron2.services.proximity.ProximityScanner;
import com.veyron2.services.proximity.ProximityScannerFactory;
import com.veyron2.services.proximity.ProximityScannerService;
import com.veyron2.services.proximity.ProximityService;

/* Client stub for interface: ProximityScanner. */
public final class ProximityScannerStub implements ProximityScanner {
	private static final java.lang.String vdlIfacePathOpt = "com.veyron2.services.proximity.ProximityScanner";
	private final com.veyron2.ipc.Client client;
	private final java.lang.String name;

	public ProximityScannerStub(com.veyron2.ipc.Client client, java.lang.String name) {
		this.client = client;
		this.name = name;
	}
	// Methods from interface ProximityScanner.
	@Override
	public java.util.ArrayList<Device> nearbyDevices(com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
		return nearbyDevices(context, null);
	}
	@Override
	public java.util.ArrayList<Device> nearbyDevices(com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
		// Prepare input arguments.
		final java.lang.Object[] inArgs = new java.lang.Object[]{  };

		// Add VDL path option.
		// NOTE(spetrovic): this option is temporary and will be removed soon after we switch
		// Java to encoding/decoding from vom.Value objects.
		if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
		if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
			veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ProximityScannerStub.vdlIfacePathOpt);
		}

		// Start the call.
		final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.name, "NearbyDevices", inArgs, veyronOpts);

		// Prepare output argument and finish the call.
			final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{ new com.google.common.reflect.TypeToken<java.util.ArrayList<Device>>() {} };
			return (java.util.ArrayList<Device>)call.finish(resultTypes)[0];

	}
}
