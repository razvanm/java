// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.watch;

/**
 * QueryWatcher allows a client to receive updates for changes to objects
 * that match a query.  See the package comments for details.
 */

@com.veyron2.vdl.VeyronService(
	serviceWrapper = io.veyron.store.veyron2.services.watch.gen_impl.QueryWatcherServiceWrapper.class,
	vdlPathName = "veyron.io/store/veyron2/services/watch/QueryWatcherService"
)
public interface QueryWatcherService  {

    
    // WatchQuery returns a stream of changes that satisy a query.

    public void watchQuery(final com.veyron2.ipc.ServerContext context, final io.veyron.store.veyron2.services.watch.types.QueryRequest Req, com.veyron2.vdl.Stream<java.lang.Void, com.veyron2.services.watch.types.Change> stream) throws com.veyron2.ipc.VeyronException;

}
