// This file was auto-generated by the veyron vdl tool.
// Source(s):  debug.vdl
package com.veyron.services.mgmt.debug.gen_impl;

/* Client stub for interface: Debug. */
public final class DebugStub implements com.veyron.services.mgmt.debug.Debug {
    private static final java.lang.String vdlIfacePathOpt = "veyron/services/mgmt/debug/Debug";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final com.veyron2.services.mgmt.logreader.gen_impl.LogFileStub logFileStub;
    
    
    private final com.veyron2.services.mounttable.gen_impl.GlobbableStub globbableStub;
    
    
    private final com.veyron2.services.watch.gen_impl.GlobWatcherStub globWatcherStub;
    
    
    private final com.veyron2.services.mgmt.stats.gen_impl.StatsStub statsStub;
    

    public DebugStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.logFileStub = new com.veyron2.services.mgmt.logreader.gen_impl.LogFileStub(client, veyronName);
         
        this.globbableStub = new com.veyron2.services.mounttable.gen_impl.GlobbableStub(client, veyronName);
         
        this.globWatcherStub = new com.veyron2.services.watch.gen_impl.GlobWatcherStub(client, veyronName);
         
        this.statsStub = new com.veyron2.services.mgmt.stats.gen_impl.StatsStub(client, veyronName);
         
    }

    // Methods from interface Debug.





    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mgmt.logreader.types.LogEntry, java.lang.Long> readLog(final com.veyron2.ipc.Context context, final long StartPos, final int NumEntries, final boolean Follow) throws com.veyron2.ipc.VeyronException {
        
        return this.logFileStub.readLog(context, StartPos, NumEntries, Follow);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mgmt.logreader.types.LogEntry, java.lang.Long> readLog(final com.veyron2.ipc.Context context, final long StartPos, final int NumEntries, final boolean Follow, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.logFileStub.readLog(context, StartPos, NumEntries, Follow, veyronOpts);
    }

    @Override
    public long size(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.logFileStub.size(context);
    }
    @Override
    public long size(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.logFileStub.size(context, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.Any value(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.statsStub.value(context);
    }
    @Override
    public com.veyron2.vdl.Any value(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.statsStub.value(context, veyronOpts);
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
