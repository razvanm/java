// This file was auto-generated by the veyron vdl tool.
// Source: syncgroup.vdl
package io.veyron.store.veyron.services.syncgroup;

/**
 * type SyncGroupInfo struct{Name string;Config veyron.io/store/veyron/services/syncgroup.SyncGroupConfig struct{Desc string;PathPatterns []string;Options map[string]any;ACL veyron2/security.ACL struct{In map[veyron2/security.BlessingPattern string]veyron2/security.LabelSet uint32;NotIn map[string]veyron2/security.LabelSet};MountTables []string};RootOID veyron.io/store/veyron2/storage.ID [16]byte;ETag string;SGOID veyron.io/store/veyron/services/syncgroup.ID [16]byte;Joiners map[veyron.io/store/veyron/services/syncgroup.NameIdentity struct{Name string;Identity string}]veyron.io/store/veyron/services/syncgroup.JoinerMetaData struct{SyncPriority int32}} 
 * A SyncGroupInfo is the conceptual state of a SyncGroup object.
 **/
public final class SyncGroupInfo implements android.os.Parcelable, java.io.Serializable {
    static final long serialVersionUID = 0L;

    
    
      @com.google.gson.annotations.SerializedName("Name")
      private java.lang.String name;
    
      @com.google.gson.annotations.SerializedName("Config")
      private io.veyron.store.veyron.services.syncgroup.SyncGroupConfig config;
    
      @com.google.gson.annotations.SerializedName("RootOID")
      private io.veyron.store.veyron2.storage.ID rootOID;
    
      @com.google.gson.annotations.SerializedName("ETag")
      private java.lang.String eTag;
    
      @com.google.gson.annotations.SerializedName("SGOID")
      private io.veyron.store.veyron.services.syncgroup.ID sGOID;
    
      @com.google.gson.annotations.SerializedName("Joiners")
      private java.util.Map<io.veyron.store.veyron.services.syncgroup.NameIdentity, io.veyron.store.veyron.services.syncgroup.JoinerMetaData> joiners;
    

    
    public SyncGroupInfo(final java.lang.String name, final io.veyron.store.veyron.services.syncgroup.SyncGroupConfig config, final io.veyron.store.veyron2.storage.ID rootOID, final java.lang.String eTag, final io.veyron.store.veyron.services.syncgroup.ID sGOID, final java.util.Map<io.veyron.store.veyron.services.syncgroup.NameIdentity, io.veyron.store.veyron.services.syncgroup.JoinerMetaData> joiners) {
        
            this.name = name;
        
            this.config = config;
        
            this.rootOID = rootOID;
        
            this.eTag = eTag;
        
            this.sGOID = sGOID;
        
            this.joiners = joiners;
        
    }

    
    
    public java.lang.String getName() {
        return this.name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public io.veyron.store.veyron.services.syncgroup.SyncGroupConfig getConfig() {
        return this.config;
    }
    public void setConfig(io.veyron.store.veyron.services.syncgroup.SyncGroupConfig config) {
        this.config = config;
    }
    
    public io.veyron.store.veyron2.storage.ID getRootOID() {
        return this.rootOID;
    }
    public void setRootOID(io.veyron.store.veyron2.storage.ID rootOID) {
        this.rootOID = rootOID;
    }
    
    public java.lang.String getETag() {
        return this.eTag;
    }
    public void setETag(java.lang.String eTag) {
        this.eTag = eTag;
    }
    
    public io.veyron.store.veyron.services.syncgroup.ID getSGOID() {
        return this.sGOID;
    }
    public void setSGOID(io.veyron.store.veyron.services.syncgroup.ID sGOID) {
        this.sGOID = sGOID;
    }
    
    public java.util.Map<io.veyron.store.veyron.services.syncgroup.NameIdentity, io.veyron.store.veyron.services.syncgroup.JoinerMetaData> getJoiners() {
        return this.joiners;
    }
    public void setJoiners(java.util.Map<io.veyron.store.veyron.services.syncgroup.NameIdentity, io.veyron.store.veyron.services.syncgroup.JoinerMetaData> joiners) {
        this.joiners = joiners;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final SyncGroupInfo other = (SyncGroupInfo)obj;

        
        
        
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
         
         
        
        
        
        if (this.config == null) {
            if (other.config != null) {
                return false;
            }
        } else if (!this.config.equals(other.config)) {
            return false;
        }
         
         
        
        
        
        if (this.rootOID == null) {
            if (other.rootOID != null) {
                return false;
            }
        } else if (!this.rootOID.equals(other.rootOID)) {
            return false;
        }
         
         
        
        
        
        if (this.eTag == null) {
            if (other.eTag != null) {
                return false;
            }
        } else if (!this.eTag.equals(other.eTag)) {
            return false;
        }
         
         
        
        
        
        if (this.sGOID == null) {
            if (other.sGOID != null) {
                return false;
            }
        } else if (!this.sGOID.equals(other.sGOID)) {
            return false;
        }
         
         
        
        
        
        if (this.joiners == null) {
            if (other.joiners != null) {
                return false;
            }
        } else if (!this.joiners.equals(other.joiners)) {
            return false;
        }
         
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (name == null ? 0 : name.hashCode());
        
        result = prime * result + (config == null ? 0 : config.hashCode());
        
        result = prime * result + (rootOID == null ? 0 : rootOID.hashCode());
        
        result = prime * result + (eTag == null ? 0 : eTag.hashCode());
        
        result = prime * result + (sGOID == null ? 0 : sGOID.hashCode());
        
        result = prime * result + (joiners == null ? 0 : joiners.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, name);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, config);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, rootOID);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, eTag);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, sGOID);
    	
    		com.veyron2.vdl.ParcelUtil.writeValue(out, joiners);
    	
    }
	public static final android.os.Parcelable.Creator<SyncGroupInfo> CREATOR
		= new android.os.Parcelable.Creator<SyncGroupInfo>() {
		@Override
		public SyncGroupInfo createFromParcel(android.os.Parcel in) {
			return new SyncGroupInfo(in);
		}
		@Override
		public SyncGroupInfo[] newArray(int size) {
			return new SyncGroupInfo[size];
		}
	};
	private SyncGroupInfo(android.os.Parcel in) {
		
			this.name = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.name);
		
			this.config = (io.veyron.store.veyron.services.syncgroup.SyncGroupConfig) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.config);
		
			this.rootOID = (io.veyron.store.veyron2.storage.ID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.rootOID);
		
			this.eTag = (java.lang.String) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.eTag);
		
			this.sGOID = (io.veyron.store.veyron.services.syncgroup.ID) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.sGOID);
		
			this.joiners = (java.util.Map<io.veyron.store.veyron.services.syncgroup.NameIdentity, io.veyron.store.veyron.services.syncgroup.JoinerMetaData>) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), this.joiners);
		
	}
}