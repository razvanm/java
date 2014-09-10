// This file was auto-generated by the veyron vdl tool.
// Source(s):  proximity.vdl
package io.veyron.proximity.api.services.proximity.gen_impl;

public final class ProximityScannerServiceWrapper {

    private final io.veyron.proximity.api.services.proximity.ProximityScannerService service;




    public ProximityScannerServiceWrapper(final io.veyron.proximity.api.services.proximity.ProximityScannerService service) {
        this.service = service;
        
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("nearbyDevices".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(2), 
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public java.util.List<io.veyron.proximity.api.services.proximity.Device> nearbyDevices(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.nearbyDevices( call   );
    }



 

}
