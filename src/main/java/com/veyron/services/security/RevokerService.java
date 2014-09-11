// This file was auto-generated by the veyron vdl tool.
// Source: revoker.vdl
package com.veyron.services.security;

/**
 * Revoker is the interface for preventing discharges from being issued. The
 * dicharger ensures that no discharges will be issued for caveats that
 * have been explicitly revoked using this interface. To prevent discharge
 * stealing caveats just have to be unique; the exact structure is not relevant
 * to the client or the verifier. To make Revoker's job easy, each caveat
 * contains a SHA256 hash of its revocation token. To revoke a caveat C and
 * have it added to the discharger's blacklist, one simply needs to call
 * Revoke(x) with an x s.t.  SHA256(x) = C. All caveats for which this has not
 * been revoked will get discharges, irrespective of who created them. This
 * means that the existence of a valid discharge does not imply that a
 * corresponding caveat exists, and even if it does, it may not be meant for
 * use with this revocation service. Just looking at discharges is meaningless,
 * a valid (Caveat, Discharge) pair is what can be relied on for
 * authentication. Not keeping track of non-revoked caveats enables
 * performance improvements on the Discharger side.
 */

@com.veyron2.vdl.VeyronService(
	serviceWrapper = com.veyron.services.security.gen_impl.RevokerServiceWrapper.class,
	vdlPathName = "veyron/services/security/RevokerService"
)
public interface RevokerService  {

    
    // Revoke ensures that iff a nil is returned, all discharge requests to the
// caveat with nonce sha256(caveatPreimage) are going to be denied.

    public void revoke(final com.veyron2.ipc.ServerContext context, final com.veyron.services.security.RevocationToken caveatPreimage) throws com.veyron2.ipc.VeyronException;

}
