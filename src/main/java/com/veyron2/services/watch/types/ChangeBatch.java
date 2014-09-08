// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package com.veyron2.services.watch.types;

/**
 * type ChangeBatch struct{Changes []veyron2/services/watch/types.Change struct{Name string;State int32;Value any;ResumeMarker veyron2/services/watch/types.ResumeMarker []byte;Continued bool}} 
 * ChangeBatch is a batch of Change messages.
 **/
public final class ChangeBatch implements android.os.Parcelable {
    
    
      private java.util.ArrayList<com.veyron2.services.watch.types.Change> changes;
    

    
    public ChangeBatch(final java.util.ArrayList<com.veyron2.services.watch.types.Change> changes) {
        
            this.changes = changes;
        
    }

    
    
    public java.util.ArrayList<com.veyron2.services.watch.types.Change> getChanges() {
        return this.changes;
    }
    public void setChanges(java.util.ArrayList<com.veyron2.services.watch.types.Change> changes) {
        this.changes = changes;
    }
    

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final ChangeBatch other = (ChangeBatch)obj;

        
        
        if (this.changes == null) {
            if (other.changes != null) {
                return false;
            }
        } else if (!this.changes.equals(other.changes)) {
            return false;
        }
         
         
        return true;
    }
    @Override
    public int hashCode() {
        int result = 1;
        final int prime = 31;
        
        result = prime * result + (changes == null ? 0 : changes.hashCode());
        
        return result;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
    	
    		for (com.veyron2.services.watch.types.Change listElem : this.changes) {
out.writeParcelable(listElem, flags);
}

    	
    }
	public static final android.os.Parcelable.Creator<ChangeBatch> CREATOR
		= new android.os.Parcelable.Creator<ChangeBatch>() {
		@Override
		public ChangeBatch createFromParcel(android.os.Parcel in) {
			return new ChangeBatch(in);
		}
		@Override
		public ChangeBatch[] newArray(int size) {
			return new ChangeBatch[size];
		}
	};
	private ChangeBatch(android.os.Parcel in) {
		
			{
// Reading list this.changes.
final int listSize = in.readInt();
this.changes = new java.util.ArrayList<com.veyron2.services.watch.types.Change>(listSize);
for (int listIdx = 0; listIdx < listSize; ++listIdx) {
com.veyron2.services.watch.types.Change listVal;
listVal = (com.veyron2.services.watch.types.Change) in.readParcelable(com.veyron2.services.watch.types.Change.class.getClassLoader());
this.changes.add(listVal);
}
}

		
	}
}