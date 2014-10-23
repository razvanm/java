package io.veyron.veyron.veyron2.security;

import io.veyron.veyron.veyron2.ipc.VeyronException;

import java.security.interfaces.ECPublicKey;

class BlessingRootsImpl implements BlessingRoots {
	private final long nativePtr;

	private native void nativeAdd(long nativePtr, ECPublicKey root, BlessingPattern pattern)
		throws VeyronException;
	private native void nativeRecognized(long nativePtr, ECPublicKey root, String blessing)
		throws VeyronException;
	private native String nativeDebugString(long nativePtr);
	private native void nativeFinalize(long nativePtr);

	private BlessingRootsImpl(long nativePtr) {
		this.nativePtr = nativePtr;
	}

	@Override
	public void add(ECPublicKey root, BlessingPattern pattern) throws VeyronException {
		nativeAdd(this.nativePtr, root, pattern);
	}
	@Override
	public void recognized(ECPublicKey root, String blessing) throws VeyronException {
		nativeRecognized(this.nativePtr, root, blessing);
	}
	@Override
	public String debugString() {
		return nativeDebugString(this.nativePtr);
	}
	@Override
	public void finalize() {
		nativeFinalize(this.nativePtr);
	}
}