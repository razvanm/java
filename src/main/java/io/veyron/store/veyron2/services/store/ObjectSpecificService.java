// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.store;


@com.veyron2.vdl.VeyronService(
	serviceWrapper = io.veyron.store.veyron2.services.store.gen_impl.ObjectSpecificServiceWrapper.class,
	vdlPathName = "veyron.io/store/veyron2/services/store/ObjectSpecificService"
)
public interface ObjectSpecificService  {

    
    // Get returns the value for the Object.  The value returned is from the
// most recent mutation of the entry in the Transaction, or from the
// Transaction's snapshot if there is no mutation.

    public io.veyron.store.veyron2.storage.Entry get(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Put modifies the value of the Object.

    public io.veyron.store.veyron2.storage.Stat put(final com.veyron2.ipc.ServerContext context, final com.veyron2.vdl.Any V) throws com.veyron2.ipc.VeyronException;

}
