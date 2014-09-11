// This file was auto-generated by the veyron vdl tool.
// Source: boxes.vdl
package io.veyron.examples.boxes;

/**
 * BoxSignalling allows peers to rendezvous with each other
 */

@com.veyron2.vdl.VeyronService(
	serviceWrapper = io.veyron.examples.boxes.gen_impl.BoxSignallingServiceWrapper.class,
	vdlPathName = "veyron.io/examples/boxes/BoxSignallingService"
)
public interface BoxSignallingService  {

    
    // Add endpoint information to the signalling server.

    public void add(final com.veyron2.ipc.ServerContext context, final java.lang.String Endpoint) throws com.veyron2.ipc.VeyronException;

    
    // Get endpoint information about a peer.

    public java.lang.String get(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

}
