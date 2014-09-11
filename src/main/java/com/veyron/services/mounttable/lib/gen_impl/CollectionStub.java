// This file was auto-generated by the veyron vdl tool.
// Source(s):  collection_test.vdl
package com.veyron.services.mounttable.lib.gen_impl;

/* Client stub for interface: Collection. */
public final class CollectionStub implements com.veyron.services.mounttable.lib.Collection {
    private static final java.lang.String vdlIfacePathOpt = "veyron/services/mounttable/lib/Collection";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public CollectionStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Collection.


    
    public void export(final com.veyron2.ipc.Context context, final java.lang.String Val, final boolean Overwrite) throws com.veyron2.ipc.VeyronException {
         export(context, Val, Overwrite, null);
    }
    
    public void export(final com.veyron2.ipc.Context context, final java.lang.String Val, final boolean Overwrite, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, CollectionStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Val, Overwrite };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "export", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public byte[] lookup(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return lookup(context, null);
    }
    
    public byte[] lookup(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, CollectionStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "lookup", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<byte[]>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (byte[])results[0];
         

         

        
    }





}
