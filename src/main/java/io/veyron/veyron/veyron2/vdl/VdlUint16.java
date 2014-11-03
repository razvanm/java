package io.veyron.veyron.veyron2.vdl;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;

/**
 * VdlUint16 is a representation of a VDL uint16.
 */
public class VdlUint16 extends VdlValue implements Parcelable, TypeAdapterFactory {
    private final short value;

    public VdlUint16(short value) {
        super(Types.UINT16);
        this.value = value;
    }

    public short getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final VdlUint16 other = (VdlUint16) obj;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(value);
    }

    public static final Creator<VdlUint16> CREATOR = new Creator<VdlUint16>() {
        @Override
        public VdlUint16 createFromParcel(Parcel in) {
            return new VdlUint16(in);
        }

        @Override
        public VdlUint16[] newArray(int size) {
            return new VdlUint16[size];
        }
    };

    private VdlUint16(Parcel in) {
        this((short) in.readInt());
    }

    public VdlUint16() {
        this((short) 0);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.equals(new TypeToken<VdlUint16>() {})) {
            return null;
        }
        final TypeAdapter<Short> delegate = gson.getAdapter(new TypeToken<Short>() {});
        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, ((VdlUint16) value).getValue());
            }

            @SuppressWarnings("unchecked")
            @Override
            public T read(JsonReader in) throws IOException {
                return (T) new VdlUint16(delegate.read(in));
            }
        };
    }
}