// This file was auto-generated by the veyron vdl tool.
// Source: repository.vdl
package com.veyron2.services.mgmt.repository;

import com.veyron2.Options;
import com.veyron2.ipc.Context;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.mgmt.application.Envelope;
import java.util.ArrayList;

/**
 * Application provides access to application envelopes. An
 * application envelope is identified by an application name and an
 * application version, which are specified through the object name,
 * and a profile name, which is specified using a method argument.
 *
 * Example:
 * /apps/search/v1.Match([]string{"base", "media"})
 *   returns an application envelope that can be used for downloading
 *   and executing the "search" application, version "v1", runnable
 *   on either the "base" or "media" profile.
**/
public interface Application { 
	// Match checks if any of the given profiles contains an application
// envelope for the given application version (specified through the
// object name suffix) and if so, returns this envelope. If multiple
// profile matches are possible, the method returns the first
// matching profile, respecting the order of the input argument.
	public Envelope match(Context context, ArrayList<String> profiles) throws VeyronException;
	public Envelope match(Context context, ArrayList<String> profiles, Options veyronOpts) throws VeyronException;
}
