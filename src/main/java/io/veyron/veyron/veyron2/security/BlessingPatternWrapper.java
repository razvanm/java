package io.veyron.veyron.veyron2.security;

import io.veyron.veyron.veyron2.VeyronException;

public class BlessingPatternWrapper {
	private static final String TAG = "Veyron runtime";

	private static native BlessingPatternWrapper nativeWrap(BlessingPattern pattern)
		throws VeyronException;

	/**
	 * Wraps the provided blessing pattern.
	 *
	 * @param  pattern         the blessing pattern being wrapped.
	 * @return                 wrapped blessing pattern.
	 * @throws VeyronException if the blessing pattern couldn't be wrapped.
	 */
	public static BlessingPatternWrapper wrap(BlessingPattern pattern) throws VeyronException {
		return nativeWrap(pattern);
	}

	private native boolean nativeIsMatchedBy(long nativePtr, String[] blessings);
	private native boolean nativeIsValid(long nativePtr);
	private native BlessingPatternWrapper nativeMakeGlob(long nativePtr) throws VeyronException;
	private native void nativeFinalize(long nativePtr);

	private long nativePtr;
	private BlessingPattern pattern;

	private BlessingPatternWrapper(long nativePtr, BlessingPattern pattern) {
		this.nativePtr = nativePtr;
		this.pattern = pattern;
	}

	/**
	 * Returns {@code true} iff one of the presented blessings matches this pattern as per
	 * the rules described in documentation for the {@code BlessingPattern} type.
	 *
	 * @param  blessings blessings compared against this pattern.
	 * @return           true iff one of the presented blessings matches this pattern.
	 */
	public boolean isMatchedBy(String... blessings) {
		return nativeIsMatchedBy(this.nativePtr, blessings);
	}

	/**
	 * Returns {@code true} iff this pattern is well formed, i.e., does not contain any character
	 * sequences that will cause the BlessingPattern to never match any valid blessings.
	 *
	 * @return true iff the pattern is well formed.
	 */
	public boolean isValid() {
		return nativeIsValid(this.nativePtr);
	}

	/**
	 * Returns a pattern that matches all extensions of the blessings that are matched by this
	 * pattern.
	 *
	 * For example:
	 * <code>
	 *     final BlessingPatternWrapper delegates =
	 *             BlessingPatternWrapper.create(new BlessingPattern("alice")).makeGlob();
	 *     delegates.MatchedBy("alice");            // Returns true
	 *     delegates.MatchedBy("alice/friend/bob"); // Returns true
	 * </code>
	 *
	 * @return a pattern that matches all extensions of the blessings that are matched by this
	 *         pattern.
	 */
	public BlessingPatternWrapper makeGlob() {
		try {
			return nativeMakeGlob(this.nativePtr);
		} catch (VeyronException e) {
			android.util.Log.e(TAG, "Couldn't make glob: " + e.getMessage());
			return null;
		}
	}

	/*
	 * Returns the blessing pattern contained in the wrapper.
	 *
	 * @return the blessing pattern contained in the wrapper.
	 */
	public BlessingPattern getPattern() {
		return this.pattern;
	}

	@Override
	protected void finalize() {
		nativeFinalize(this.nativePtr);
	}
}