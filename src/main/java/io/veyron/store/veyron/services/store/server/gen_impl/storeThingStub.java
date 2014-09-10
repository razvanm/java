// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package io.veyron.store.veyron.services.store.server.gen_impl;

/* Client stub for interface: storeThing. */
public final class storeThingStub implements io.veyron.store.veyron.services.store.server.storeThing {
    private static final java.lang.String vdlIfacePathOpt = "io.veyron.store.veyron.services.store.server.storeThing";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.SyncGroupStub syncGroupStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.DirSpecificStub dirSpecificStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.ObjectSpecificStub objectSpecificStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.StatableStub statableStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.TransactableStub transactableStub;
    
    
    private final com.veyron2.services.mounttable.gen_impl.GlobbableStub globbableStub;
    
    
    private final com.veyron2.services.watch.gen_impl.GlobWatcherStub globWatcherStub;
    
    
    private final io.veyron.store.veyron2.services.watch.gen_impl.QueryWatcherStub queryWatcherStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.DirOrObjectStub dirOrObjectStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.TransactionStub transactionStub;
    

    public storeThingStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.syncGroupStub = new io.veyron.store.veyron2.services.store.gen_impl.SyncGroupStub(client, veyronName);
         
        this.dirSpecificStub = new io.veyron.store.veyron2.services.store.gen_impl.DirSpecificStub(client, veyronName);
         
        this.objectSpecificStub = new io.veyron.store.veyron2.services.store.gen_impl.ObjectSpecificStub(client, veyronName);
         
        this.statableStub = new io.veyron.store.veyron2.services.store.gen_impl.StatableStub(client, veyronName);
         
        this.transactableStub = new io.veyron.store.veyron2.services.store.gen_impl.TransactableStub(client, veyronName);
         
        this.globbableStub = new com.veyron2.services.mounttable.gen_impl.GlobbableStub(client, veyronName);
         
        this.globWatcherStub = new com.veyron2.services.watch.gen_impl.GlobWatcherStub(client, veyronName);
         
        this.queryWatcherStub = new io.veyron.store.veyron2.services.watch.gen_impl.QueryWatcherStub(client, veyronName);
         
        this.dirOrObjectStub = new io.veyron.store.veyron2.services.store.gen_impl.DirOrObjectStub(client, veyronName);
         
        this.transactionStub = new io.veyron.store.veyron2.services.store.gen_impl.TransactionStub(client, veyronName);
         
    }

    // Methods from interface storeThing.





    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q) throws com.veyron2.ipc.VeyronException {
        
        return this.dirOrObjectStub.query(context, Q);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.dirOrObjectStub.query(context, Q, veyronOpts);
    }

    @Override
    public void remove(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
         this.dirOrObjectStub.remove(context);
    }
    @Override
    public void remove(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.dirOrObjectStub.remove(context, veyronOpts);
    }

    @Override
    public java.util.List<java.lang.String> getSyncGroupNames(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.dirSpecificStub.getSyncGroupNames(context);
    }
    @Override
    public java.util.List<java.lang.String> getSyncGroupNames(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.dirSpecificStub.getSyncGroupNames(context, veyronOpts);
    }

    @Override
    public void make(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
         this.dirSpecificStub.make(context);
    }
    @Override
    public void make(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.dirSpecificStub.make(context, veyronOpts);
    }

    @Override
    public io.veyron.store.veyron2.storage.Entry get(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.objectSpecificStub.get(context);
    }
    @Override
    public io.veyron.store.veyron2.storage.Entry get(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.objectSpecificStub.get(context, veyronOpts);
    }

    @Override
    public io.veyron.store.veyron2.storage.Stat put(final com.veyron2.ipc.Context context, final com.veyron2.vdl.Any V) throws com.veyron2.ipc.VeyronException {
        
        return this.objectSpecificStub.put(context, V);
    }
    @Override
    public io.veyron.store.veyron2.storage.Stat put(final com.veyron2.ipc.Context context, final com.veyron2.vdl.Any V, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.objectSpecificStub.put(context, V, veyronOpts);
    }

    @Override
    public boolean exists(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.statableStub.exists(context);
    }
    @Override
    public boolean exists(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.statableStub.exists(context, veyronOpts);
    }

    @Override
    public io.veyron.store.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.statableStub.stat(context);
    }
    @Override
    public io.veyron.store.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.statableStub.stat(context, veyronOpts);
    }

    @Override
    public void createSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.createSyncGroup(context, name, config);
    }
    @Override
    public void createSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.createSyncGroup(context, name, config, veyronOpts);
    }

    @Override
    public void destroySyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.destroySyncGroup(context, name);
    }
    @Override
    public void destroySyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.destroySyncGroup(context, name, veyronOpts);
    }

    @Override
    public void ejectFromSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, final java.lang.String member) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.ejectFromSyncGroup(context, name, member);
    }
    @Override
    public void ejectFromSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, final java.lang.String member, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.ejectFromSyncGroup(context, name, member, veyronOpts);
    }

    @Override
    public java.util.List<java.lang.String> getMembersOfSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
        return this.syncGroupStub.getMembersOfSyncGroup(context, name);
    }
    @Override
    public java.util.List<java.lang.String> getMembersOfSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.syncGroupStub.getMembersOfSyncGroup(context, name, veyronOpts);
    }

    @Override
    public io.veyron.store.veyron2.services.store.SyncGroup.GetSyncGroupConfigOut getSyncGroupConfig(final com.veyron2.ipc.Context context, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
        return this.syncGroupStub.getSyncGroupConfig(context, name);
    }
    @Override
    public io.veyron.store.veyron2.services.store.SyncGroup.GetSyncGroupConfigOut getSyncGroupConfig(final com.veyron2.ipc.Context context, final java.lang.String name, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.syncGroupStub.getSyncGroupConfig(context, name, veyronOpts);
    }

    @Override
    public void joinSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.joinSyncGroup(context, name);
    }
    @Override
    public void joinSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.joinSyncGroup(context, name, veyronOpts);
    }

    @Override
    public void leaveSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.leaveSyncGroup(context, name);
    }
    @Override
    public void leaveSyncGroup(final com.veyron2.ipc.Context context, final java.lang.String name, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.leaveSyncGroup(context, name, veyronOpts);
    }

    @Override
    public void setSyncGroupConfig(final com.veyron2.ipc.Context context, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config, final java.lang.String eTag) throws com.veyron2.ipc.VeyronException {
        
         this.syncGroupStub.setSyncGroupConfig(context, name, config, eTag);
    }
    @Override
    public void setSyncGroupConfig(final com.veyron2.ipc.Context context, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config, final java.lang.String eTag, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupStub.setSyncGroupConfig(context, name, config, eTag, veyronOpts);
    }

    @Override
    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options) throws com.veyron2.ipc.VeyronException {
        
        return this.transactableStub.newTransaction(context, Options);
    }
    @Override
    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.transactableStub.newTransaction(context, Options, veyronOpts);
    }

    @Override
    public void abort(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
         this.transactionStub.abort(context);
    }
    @Override
    public void abort(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.transactionStub.abort(context, veyronOpts);
    }

    @Override
    public void commit(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
         this.transactionStub.commit(context);
    }
    @Override
    public void commit(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.transactionStub.commit(context, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchQuery(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.services.watch.types.QueryRequest Req) throws com.veyron2.ipc.VeyronException {
        
        return this.queryWatcherStub.watchQuery(context, Req);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchQuery(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.services.watch.types.QueryRequest Req, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.queryWatcherStub.watchQuery(context, Req, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern) throws com.veyron2.ipc.VeyronException {
        
        return this.globbableStub.glob(context, pattern);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.globbableStub.glob(context, pattern, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchGlob(final com.veyron2.ipc.Context context, final com.veyron2.services.watch.types.GlobRequest Req) throws com.veyron2.ipc.VeyronException {
        
        return this.globWatcherStub.watchGlob(context, Req);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchGlob(final com.veyron2.ipc.Context context, final com.veyron2.services.watch.types.GlobRequest Req, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.globWatcherStub.watchGlob(context, Req, veyronOpts);
    }


}
