// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron.services.store.server;

/**
 * Named 'storeThing' instead of 'thing' so that the struct in thing.go can be
 * named 'thing'.
 */

@com.veyron2.vdl.VeyronService(
	serviceWrapper = io.veyron.store.veyron.services.store.server.gen_impl.storeThingServiceWrapper.class,
	vdlPathName = "veyron.io/store/veyron/services/store/server/storeThingService"
)
public interface storeThingService extends io.veyron.store.veyron2.services.store.DirSpecificService, io.veyron.store.veyron2.services.store.ObjectSpecificService, io.veyron.store.veyron2.services.store.DirOrObjectService, io.veyron.store.veyron2.services.store.TransactionService {

}
