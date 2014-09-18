// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.store;

/**
 * Transactable provides the NewTransaction method.
 */

@com.veyron2.vdl.VeyronService(
	serviceWrapper = io.veyron.store.veyron2.services.store.gen_impl.TransactableServiceWrapper.class,
	vdlPathName = "veyron.io/store/veyron2/services/store/TransactableService"
)
public interface TransactableService  {

    
    // NewTransaction creates a transaction with the given options.  It returns
// the name of the transaction relative to the receiver's name.  The client
// must rebind to this new name to work with the receiver and its descendants
// as part of the transaction.

    public java.lang.String newTransaction(final com.veyron2.ipc.ServerContext context, final java.util.List<com.veyron2.vdl.Any> Options) throws com.veyron2.ipc.VeyronException;

}