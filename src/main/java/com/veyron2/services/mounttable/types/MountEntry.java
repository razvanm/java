// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package com.veyron2.services.mounttable.types;

/**
 * type MountEntry struct{Name string;Servers []veyron2/services/mounttable/types.MountedServer struct{Server string;TTL uint32}} 
 * MountEntry represents a given name mounted in the mounttable.
 **/
public final class MountEntry {
    
    
      private java.lang.String name;
    
      private java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer> servers;
    

    
    public MountEntry(final java.lang.String name, final java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer> servers) {
        
            this.name = name;
        
            this.servers = servers;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer> getServers() {
        return this.servers;
    }
    public void setServers(java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer> servers) {
        this.servers = servers;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final MountEntry other = (MountEntry)obj;

        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
        
        
        if (this.servers == null) {
            if (other.servers != null) {
                return false;
            }
        } else if (!this.servers.equals(other.servers)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (servers == null ? 0 : servers.hashCode());
        
        return result;
    }
}