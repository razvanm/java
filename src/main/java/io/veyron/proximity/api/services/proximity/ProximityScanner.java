// This file was auto-generated by the veyron vdl tool.
// Source: proximity.vdl
package io.veyron.proximity.api.services.proximity;


public interface ProximityScanner  {

    
    

    
    // NearbyDevices returns the most up-to-date list of nearby devices,
// sorted in increasing distance order.

    public java.util.List<io.veyron.proximity.api.services.proximity.Device> nearbyDevices(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public java.util.List<io.veyron.proximity.api.services.proximity.Device> nearbyDevices(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

}
