// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.store;


public interface DirOrObject extends io.veyron.store.veyron2.services.store.Statable, io.veyron.store.veyron2.services.store.Transactable, com.veyron2.services.mounttable.Globbable, com.veyron2.services.watch.GlobWatcher, io.veyron.store.veyron2.services.watch.QueryWatcher {

    
    

    
    // Remove removes this Dir (and all its children, recursively) or Object.

    public void remove(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public void remove(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

    
    

    
    // Query returns the sequence of elements that satisfy the query.

    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q) throws com.veyron2.ipc.VeyronException;
    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

}
