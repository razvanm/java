// This file was auto-generated by the veyron vdl tool.
// Source: node.vdl
package com.veyron2.services.mgmt.node;

import com.veyron2.Options;
import com.veyron2.ipc.Context;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.mgmt.build.BinaryDescription;

/**
 * Node can be used to manage a node. The idea is that this interace
 * will be invoked using an object name that identifies the node.
**/
public interface Node extends Application { 
	// Describe generates a description of the node.
	public Description describe(Context context) throws VeyronException;
	public Description describe(Context context, Options veyronOpts) throws VeyronException;
	// IsRunnable checks if the node can execute the given binary.
	public boolean isRunnable(Context context, BinaryDescription binary) throws VeyronException;
	public boolean isRunnable(Context context, BinaryDescription binary, Options veyronOpts) throws VeyronException;
	// Reset resets the node. If the deadline is non-zero and the node
// in question is still running after the given deadline expired,
// reset of the node is enforced.
//
// TODO(jsimsa): Switch deadline to time.Duration when built-in types
// are implemented.
	public void reset(Context context, long deadline) throws VeyronException;
	public void reset(Context context, long deadline, Options veyronOpts) throws VeyronException;
}
