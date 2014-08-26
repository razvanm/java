
// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron.services.store.raw;

/**
 * type Version uint64 
 * Version identifies the value in the store for a key at some point in time.
 * The version is a numeric identifier that is globally unique within the space
 * of a single ID, meaning that if two stores contain an entry with the same ID
 * and version, then the entries represent the same thing, at the same point in
 * time (as agreed upon by the two stores).
 **/
public final class Version {
    private long value;

    public Version(long value) {
        this.value = value;
    }
    public long getValue() { return this.value; }

    public void setValue(long value) { this.value = value; }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        final com.veyron.services.store.raw.Version other = (com.veyron.services.store.raw.Version)obj;
        
        return this.value == other.value;
        
    }
    @Override
    public int hashCode() {
        return java.lang.Long.valueOf(value).hashCode();
    }
}