// This file was auto-generated by the veyron vdl tool.
// Source(s):  repository.vdl
package com.veyron2.services.mgmt.repository.gen_impl;

/* Client stub for interface: Application. */
public final class ApplicationStub implements com.veyron2.services.mgmt.repository.Application {
    private static final java.lang.String vdlIfacePathOpt = "veyron2/services/mgmt/repository/Application";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public ApplicationStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Application.


    
    public com.veyron2.services.mgmt.application.Envelope match(final com.veyron2.ipc.Context context, final java.util.List<java.lang.String> Profiles) throws com.veyron2.ipc.VeyronException {
        return match(context, Profiles, null);
    }
    
    public com.veyron2.services.mgmt.application.Envelope match(final com.veyron2.ipc.Context context, final java.util.List<java.lang.String> Profiles, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ApplicationStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Profiles };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "match", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<com.veyron2.services.mgmt.application.Envelope>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (com.veyron2.services.mgmt.application.Envelope)results[0];
         

         

        
    }





}
