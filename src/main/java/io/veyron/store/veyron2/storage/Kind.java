
// This file was auto-generated by the veyron vdl tool.
// Source: types.vdl
package io.veyron.store.veyron2.storage;

/**
 * type Kind int16 
 * Kind makes it possible to tell the difference between Dirs and Objects.
 **/
public final class Kind implements android.os.Parcelable, java.io.Serializable, com.google.gson.TypeAdapterFactory {
    private short value;

    public Kind(short value) {
        this.value = value;
    }
    public short getValue() { return this.value; }

    public void setValue(short value) { this.value = value; }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final io.veyron.store.veyron2.storage.Kind other = (io.veyron.store.veyron2.storage.Kind)obj;
        
        
        return this.value == other.value;
         
         
    }
    @Override
    public int hashCode() {
        return (int)value;
    }
    @Override
    public int describeContents() {
    	return 0;
    }
    @Override
    public void writeToParcel(android.os.Parcel out, int flags) {
   		com.veyron2.vdl.ParcelUtil.writeValue(out, value);
    }
	public static final android.os.Parcelable.Creator<Kind> CREATOR
		= new android.os.Parcelable.Creator<Kind>() {
		@Override
		public Kind createFromParcel(android.os.Parcel in) {
			return new Kind(in);
		}
		@Override
		public Kind[] newArray(int size) {
			return new Kind[size];
		}
	};
	private Kind(android.os.Parcel in) {
		value = (short) com.veyron2.vdl.ParcelUtil.readValue(in, getClass().getClassLoader(), value);
	}

	public Kind() {}

	@Override
	public <T> com.google.gson.TypeAdapter<T> create(com.google.gson.Gson gson, com.google.gson.reflect.TypeToken<T> type) {
		if (!type.equals(new com.google.gson.reflect.TypeToken<Kind>(){})) {
			return null;
		}
		final com.google.gson.TypeAdapter<java.lang.Short> delegate = gson.getAdapter(new com.google.gson.reflect.TypeToken<java.lang.Short>() {});
		return new com.google.gson.TypeAdapter<T>() {
			@Override
			public void write(com.google.gson.stream.JsonWriter out, T value) throws java.io.IOException {
				delegate.write(out, ((Kind) value).getValue());
			}
			@Override
			public T read(com.google.gson.stream.JsonReader in) throws java.io.IOException {
				return (T) new Kind(delegate.read(in));
			}
		};
	}
}