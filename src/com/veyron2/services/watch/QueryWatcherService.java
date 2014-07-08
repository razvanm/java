// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron2.services.watch;

import com.veyron2.ipc.ServerContext;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.watch.gen_impl.QueryWatcherServiceWrapper;
import com.veyron2.vdl.Stream;
import com.veyron2.vdl.VeyronService;

/**
 * QueryWatcher allows a client to receive updates for changes to objects
 * that match a query.  See the package comments for details.
**/
@VeyronService(serviceWrapper=QueryWatcherServiceWrapper.class)
public interface QueryWatcherService { 
	// WatchQuery returns a stream of changes that satisy a query.
	public void watchQuery(ServerContext context, QueryRequest req, Stream<ChangeBatch,Void> stream) throws VeyronException;
}
