// This file was auto-generated by the veyron vdl tool.
// Source: vsync.vdl
package com.veyron.runtimes.google.vsync;

import com.veyron.runtimes.google.vsync.gen_impl.SyncServiceWrapper;
import com.veyron2.ipc.ServerContext;
import com.veyron2.ipc.VeyronException;
import com.veyron2.vdl.Stream;
import com.veyron2.vdl.VeyronService;
import java.util.HashMap;

/**
 * Sync allows a device to GetDeltas from another device.
**/
@VeyronService(serviceWrapper=SyncServiceWrapper.class)
public interface SyncService { 
	// GetDeltas returns a device's current generation vector and all the missing log records
// when compared to the incoming generation vector.
	public HashMap<String, Long> getDeltas(ServerContext context, HashMap<String, Long> in, String clientID, Stream<LogRec,Void> stream) throws VeyronException;
}
