// This file was auto-generated by the veyron vdl tool.
// Source(s):  syncgroup.vdl
package io.veyron.store.veyron.services.syncgroup.gen_impl;

public final class SyncGroupServerServiceWrapper {

    private final io.veyron.store.veyron.services.syncgroup.SyncGroupServerService service;



    
    private final com.veyron2.services.security.access.gen_impl.ObjectServiceWrapper objectWrapper;
    

    public SyncGroupServerServiceWrapper(final io.veyron.store.veyron.services.syncgroup.SyncGroupServerService service) {
        this.service = service;
        
        
        this.objectWrapper = new com.veyron2.services.security.access.gen_impl.ObjectServiceWrapper(service);
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("create".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(4), 
            };
        }
        
        if ("destroy".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(8), 
            };
        }
        
        if ("eject".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(8), 
            };
        }
        
        if ("get".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(2), 
            };
        }
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("join".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(4), 
            };
        }
        
        if ("leave".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("setConfig".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(8), 
            };
        }
        
        
        try {
            return this.objectWrapper.getMethodTags(call, method);
        } catch (com.veyron2.ipc.VeyronException e) {}  // method not found.
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public io.veyron.store.veyron.services.syncgroup.SyncGroupInfo create(final com.veyron2.ipc.ServerCall call, final io.veyron.store.veyron.services.syncgroup.SyncGroupConfig createArgs, final io.veyron.store.veyron2.storage.ID rootOID, final io.veyron.store.veyron.services.syncgroup.NameIdentity joiner, final io.veyron.store.veyron.services.syncgroup.JoinerMetaData metaData) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.create( call , createArgs, rootOID, joiner, metaData  );
    }

    public io.veyron.store.veyron.services.syncgroup.SyncGroupInfo join(final com.veyron2.ipc.ServerCall call, final io.veyron.store.veyron.services.syncgroup.NameIdentity joiner, final io.veyron.store.veyron.services.syncgroup.JoinerMetaData metaData) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.join( call , joiner, metaData  );
    }

    public void leave(final com.veyron2.ipc.ServerCall call, final io.veyron.store.veyron.services.syncgroup.NameIdentity name) throws com.veyron2.ipc.VeyronException {
         
         this.service.leave( call , name  );
    }

    public void eject(final com.veyron2.ipc.ServerCall call, final io.veyron.store.veyron.services.syncgroup.NameIdentity name) throws com.veyron2.ipc.VeyronException {
         
         this.service.eject( call , name  );
    }

    public void destroy(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.destroy( call   );
    }

    public io.veyron.store.veyron.services.syncgroup.SyncGroupInfo get(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.get( call   );
    }

    public void setConfig(final com.veyron2.ipc.ServerCall call, final io.veyron.store.veyron.services.syncgroup.SyncGroupConfig config, final java.lang.String eTag) throws com.veyron2.ipc.VeyronException {
         
         this.service.setConfig( call , config, eTag  );
    }




    public com.veyron2.services.security.access.Object.GetACLOut getACL(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
        
        return  this.objectWrapper.getACL(call);
    }

    public void setACL(final com.veyron2.ipc.ServerCall call, final com.veyron2.security.ACL acl, final java.lang.String etag) throws com.veyron2.ipc.VeyronException {
        
          this.objectWrapper.setACL(call, acl, etag);
    }
 

}
