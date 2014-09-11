// This file was auto-generated by the veyron vdl tool.
// Source(s):  repository.vdl
package com.veyron.services.mgmt.repository.gen_impl;

/* Client stub for interface: Profile. */
public final class ProfileStub implements com.veyron.services.mgmt.repository.Profile {
    private static final java.lang.String vdlIfacePathOpt = "veyron/services/mgmt/repository/Profile";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final com.veyron2.services.mgmt.repository.gen_impl.ProfileStub profileStub;
    

    public ProfileStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.profileStub = new com.veyron2.services.mgmt.repository.gen_impl.ProfileStub(client, veyronName);
         
    }

    // Methods from interface Profile.


    
    public com.veyron.services.mgmt.profile.Specification specification(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return specification(context, null);
    }
    
    public com.veyron.services.mgmt.profile.Specification specification(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ProfileStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "specification", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<com.veyron.services.mgmt.profile.Specification>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (com.veyron.services.mgmt.profile.Specification)results[0];
         

         

        
    }

    
    public void put(final com.veyron2.ipc.Context context, final com.veyron.services.mgmt.profile.Specification Specification) throws com.veyron2.ipc.VeyronException {
         put(context, Specification, null);
    }
    
    public void put(final com.veyron2.ipc.Context context, final com.veyron.services.mgmt.profile.Specification Specification, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ProfileStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Specification };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "put", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void remove(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
         remove(context, null);
    }
    
    public void remove(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ProfileStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "remove", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }




    @Override
    public java.lang.String description(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.profileStub.description(context);
    }
    @Override
    public java.lang.String description(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.profileStub.description(context, veyronOpts);
    }

    @Override
    public java.lang.String label(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.profileStub.label(context);
    }
    @Override
    public java.lang.String label(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.profileStub.label(context, veyronOpts);
    }


}
