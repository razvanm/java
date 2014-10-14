package io.veyron.veyron.veyron.runtimes.google;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.veyron.veyron.veyron.runtimes.google.android.RedirectStderr;
import io.veyron.veyron.veyron.runtimes.google.naming.Namespace;
import io.veyron.veyron.veyron.runtimes.google.security.PrivateID;
import io.veyron.veyron.veyron.runtimes.google.security.PublicIDStore;
import io.veyron.veyron.veyron.runtimes.google.security.Signer;
import io.veyron.veyron.veyron.runtimes.google.security.Util;
import io.veyron.veyron.veyron2.OptionDefs;
import io.veyron.veyron.veyron2.Options;
import io.veyron.veyron.veyron2.ipc.Dispatcher;
import io.veyron.veyron.veyron2.ipc.VeyronException;
import io.veyron.veyron.veyron2.security.CryptoUtil;
import io.veyron.veyron.veyron2.security.Label;
import io.veyron.veyron.veyron2.security.PublicID;
import io.veyron.veyron.veyron2.security.wire.ChainPublicID;
import io.veyron.veyron.veyron2.vdl.Any;
import io.veyron.veyron.veyron2.vdl.JSONUtil;

import java.io.EOFException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.util.Date;

/**
 * Runtime is an implementation of Veyron Runtime that calls to native Go code for most of its
 * functionalities.
 */
public class Runtime implements io.veyron.veyron.veyron2.Runtime {
	private static Runtime globalRuntime = null;

	private static native long nativeInit(Options opts);
	private static native long nativeNewRuntime(Options opts) throws VeyronException;

	/**
	 * Returns the initialized global instance of the Runtime.
	 *
	 * @param  ctx  android context.
	 * @param  opts runtime options.
	 * @return      a pre-initialized runtime instance.
	 */
	public static synchronized Runtime init(android.content.Context ctx, Options opts) {
		if (Runtime.globalRuntime == null) {
			if (opts == null) {
				opts = new Options();
			}
			try {
				setupRuntimeOptions(ctx, opts);
				final io.veyron.veyron.veyron2.security.PrivateID privateID =
					(io.veyron.veyron.veyron2.security.PrivateID)opts.get(OptionDefs.RUNTIME_ID);
				Runtime.globalRuntime = new Runtime(nativeInit(opts), privateID);
			} catch (VeyronException e) {
				throw new RuntimeException(
					"Couldn't initialize global Veyron Runtime instance: " + e.getMessage());
			}
		}
		return Runtime.globalRuntime;
	}

	/**
	 * Returns the pre-initialized global Runtime instance.  Returns <code>null</code> if init()
	 * hasn't already been invoked.
	 *
	 * @return a pre-initialized runtime instance.
	 */
	public static synchronized Runtime defaultRuntime() {
		return Runtime.globalRuntime;
	}

	/**
	 * Creates and initializes a new Runtime instance.
	 *
	 * @param  ctx  android context.
	 * @param  opts runtime options.
	 * @return      a new runtime instance.
	 */
	public static synchronized Runtime newRuntime(android.content.Context ctx, Options opts) {
		if (opts == null) {
			opts = new Options();
		}
		try {
			setupRuntimeOptions(ctx, opts);
			final io.veyron.veyron.veyron2.security.PrivateID privateID =
				(io.veyron.veyron.veyron2.security.PrivateID)opts.get(OptionDefs.RUNTIME_ID);
			return new Runtime(nativeNewRuntime(opts), privateID);
		} catch (VeyronException e) {
			throw new RuntimeException("Couldn't create Veyron Runtime: " + e.getMessage());
		}
	}

	private static void setupRuntimeOptions(android.content.Context ctx, Options opts)
		throws VeyronException {
		// If the PrivateID option isn't specified, generate a new PrivateID.  Note that we
		// choose to generate keys inside Java (instead of native code) because we can
		// conveniently store them inside Android KeyStore.
		if (!opts.has(OptionDefs.RUNTIME_ID) || opts.get(OptionDefs.RUNTIME_ID) == null) {
			// Check if the private key has already been generated for this package.
			// (NOTE: Android package names are unique.)
			KeyStore.PrivateKeyEntry keyEntry =
				CryptoUtil.getKeyStorePrivateKey(ctx.getPackageName());
			if (keyEntry == null) {
				// Generate a new private key.
				keyEntry = CryptoUtil.genKeyStorePrivateKey(ctx, ctx.getPackageName());
			}
			final Signer signer = new Signer(
				keyEntry.getPrivateKey(), (ECPublicKey)keyEntry.getCertificate().getPublicKey());
			final PrivateID id = PrivateID.create(ctx.getPackageName(), signer);
			opts.set(OptionDefs.RUNTIME_ID, id);
		}
	}

	private static void encodeLocalIDOption(Options opts) throws VeyronException {
		if (opts == null || !opts.has(OptionDefs.LOCAL_ID)) {
			return;
		}
		// Encode the id.
		final PublicID id = opts.get(OptionDefs.LOCAL_ID, PublicID.class);
		if (id == null) {
			opts.remove(OptionDefs.LOCAL_ID);
			return;
		}
		final ChainPublicID[] chains = id.encode();
		final String[] encodedChains = Util.encodeChains(chains);
		opts.set(OptionDefs.LOCAL_ID, encodedChains);
	}

	static {
		System.loadLibrary("jniwrapper");
		System.loadLibrary("veyronjni");
		RedirectStderr.Start();
	}

	private final long nativePtr;
	private Client client;
	private final io.veyron.veyron.veyron2.security.PrivateID privateID;  // non-null.
	private io.veyron.veyron.veyron2.security.PublicIDStore publicIDStore;

	private native long nativeNewClient(long nativePtr, Options opts) throws VeyronException;
	private native long nativeNewServer(long nativePtr, Options opts) throws VeyronException;
	private native long nativeGetClient(long nativePtr);
	private native long nativeNewContext(long nativePtr);
	private native long nativeGetPublicIDStore(long nativePtr);
	private native long nativeGetNamespace(long nativePtr);
	private native void nativeFinalize(long nativePtr);

	private Runtime(long nativePtr, io.veyron.veyron.veyron2.security.PrivateID privateID) {
		this.nativePtr = nativePtr;
		this.privateID = privateID;
	}
	@Override
	public io.veyron.veyron.veyron2.ipc.Client newClient() throws VeyronException {
		return newClient(null);
	}
	@Override
	public io.veyron.veyron.veyron2.ipc.Client newClient(Options opts) throws VeyronException {
		encodeLocalIDOption(opts);
		final long nativeClientPtr = nativeNewClient(this.nativePtr, opts);
		return new Client(nativeClientPtr);
	}
	@Override
	public io.veyron.veyron.veyron2.ipc.Server newServer() throws VeyronException {
		return newServer(null);
	}
	@Override
	public io.veyron.veyron.veyron2.ipc.Server newServer(Options opts) throws VeyronException {
		encodeLocalIDOption(opts);
		final long nativeServerPtr = nativeNewServer(this.nativePtr, opts);
		return new Server(nativeServerPtr);
	}
	@Override
	public synchronized io.veyron.veyron.veyron2.ipc.Client getClient() {
		if (this.client == null) {
			final long nativeClientPtr = nativeGetClient(this.nativePtr);
			this.client = new Client(nativeClientPtr);
		}
		return this.client;
	}
	@Override
	public io.veyron.veyron.veyron2.ipc.Context newContext() {
		final long nativeContextPtr = nativeNewContext(this.nativePtr);
		return new Context(nativeContextPtr);
	}
	@Override
	public io.veyron.veyron.veyron2.security.PrivateID newIdentity(String name) throws VeyronException {
		try {
			// Generate a new private key, stored in the clear in the app's memory.
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
			final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(256, random);
			final KeyPair keyPair = keyGen.generateKeyPair();
			final Signer signer = new Signer(keyPair.getPrivate(), (ECPublicKey)keyPair.getPublic());
			return PrivateID.create(name, signer);
		} catch (NoSuchAlgorithmException e) {
			throw new VeyronException("ECDSA algorithm not supported: " + e.getMessage());
		}
	}
	@Override
	public io.veyron.veyron.veyron2.security.PrivateID getIdentity() {
		return this.privateID;
	}
	@Override
	public io.veyron.veyron.veyron2.security.PublicIDStore getPublicIDStore() {
		if (this.publicIDStore == null) {
			this.publicIDStore = new PublicIDStore(nativeGetPublicIDStore(this.nativePtr));
		}
		return this.publicIDStore;
	}
	@Override
	public io.veyron.veyron.veyron2.naming.Namespace getNamespace() {
		return new Namespace(nativeGetNamespace(this.nativePtr));
	}
	@Override
	protected void finalize() {
		nativeFinalize(this.nativePtr);
	}

	private static class Server implements io.veyron.veyron.veyron2.ipc.Server {
		private final long nativePtr;

		private native String nativeListen(long nativePtr, String protocol, String address)
			throws VeyronException;
		private native void nativeServe(long nativePtr, String name, Dispatcher dispatcher)
			throws VeyronException;
		private native String[] nativeGetPublishedNames(long nativePtr) throws VeyronException;
		private native void nativeStop(long nativePtr) throws VeyronException;
		private native void nativeFinalize(long nativePtr);

		Server(long nativePtr) {
			this.nativePtr = nativePtr;
		}
		// Implement io.veyron.veyron.veyron2.ipc.Server.
		@Override
		public String listen(String protocol, String address) throws VeyronException {
			return nativeListen(this.nativePtr, protocol, address);
		}
		@Override
		public void serve(String name, Dispatcher dispatcher) throws VeyronException {
			nativeServe(this.nativePtr, name, dispatcher);
		}
		@Override
		public String[] getPublishedNames() throws VeyronException {
			return nativeGetPublishedNames(this.nativePtr);
		}
		@Override
		public void stop() throws VeyronException {
			nativeStop(this.nativePtr);
		}
		// Implement java.lang.Object.
		@Override
		protected void finalize() {
			nativeFinalize(this.nativePtr);
		}
	}

	private static class Client implements io.veyron.veyron.veyron2.ipc.Client {
		private final long nativePtr;
		// TODO(bprosnitz) Ensure gson is thread safe.
		private final Gson gson;

		private native long nativeStartCall(long nativePtr,
			io.veyron.veyron.veyron2.ipc.Context context, String name, String method, String[] args,
			Options opts) throws VeyronException;
		private native void nativeClose(long nativePtr);
		private native void nativeFinalize(long nativePtr);

		Client(long nativePtr) {
			this.nativePtr = nativePtr;
			this.gson = JSONUtil.getGsonBuilder().create();
		}
		// Implement io.veyron.veyron.veyron2.ipc.Client.
		@Override
		public Call startCall(io.veyron.veyron.veyron2.ipc.Context context,
			String name, String method, Object[] args) throws VeyronException {
			return startCall(context, name, method, args, null);
		}
		@Override
		public Call startCall(io.veyron.veyron.veyron2.ipc.Context context,
			String name, String method, Object[] args, Options opts) throws VeyronException {
			if (method == "") {
				throw new VeyronException("Empty method name invoked on object %s", name);
			}

			// Encode all input arguments to JSON.
			final String[] jsonArgs = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				jsonArgs[i] = this.gson.toJson(args[i]);
			}

			// Invoke native method.
			// Make sure that the method name starts with an uppercase character.
			method = Character.toUpperCase(method.charAt(0)) + method.substring(1);
			final long nativeCallPtr = nativeStartCall(
				this.nativePtr, context, name, method, jsonArgs, opts);
			return new ClientCall(nativeCallPtr);
		}
		@Override
		public void close() {
			nativeClose(this.nativePtr);
		}
		// Implement java.lang.Object.
		@Override
		protected void finalize() {
			nativeFinalize(this.nativePtr);
		}
	}

	private static class Context implements io.veyron.veyron.veyron2.ipc.Context {
		private final long nativePtr;

		private native void nativeFinalize(long nativePtr);

		Context(long nativePtr) {
			this.nativePtr = nativePtr;
		}
		// Implement java.lang.Object.
		@Override
		protected void finalize() {
			nativeFinalize(this.nativePtr);
		}
	}

	private static class Stream implements io.veyron.veyron.veyron2.ipc.Stream {
		private final long nativeStreamPtr;
		private final Gson gson;

		private native void nativeSend(long nativeStreamPtr, String item) throws VeyronException;
		private native String nativeRecv(long nativeStreamPtr) throws EOFException, VeyronException;

		Stream(long nativeStreamPtr) {
			this.nativeStreamPtr = nativeStreamPtr;
			this.gson = JSONUtil.getGsonBuilder().create();
		}
		@Override
		public void send(Object item) throws VeyronException {
			nativeSend(nativeStreamPtr, this.gson.toJson(item));
		}

		@Override
		public Object recv(TypeToken<?> type) throws EOFException, VeyronException {
			final String result = nativeRecv(nativeStreamPtr);
			try {
				return this.gson.fromJson(result, type.getType());
			} catch (JsonSyntaxException e) {
				throw new VeyronException(String.format(
					"Error decoding result %s from JSON: %s", result, e.getMessage()));
			}
		}
	}

	private static class ClientCall extends Stream implements io.veyron.veyron.veyron2.ipc.Client.Call {
		private final long nativePtr;
		private final Gson gson;

		private native String[] nativeFinish(long nativePtr) throws VeyronException;
		private native void nativeCancel(long nativePtr);
		private native void nativeFinalize(long nativePtr);

		ClientCall(long nativePtr) {
			super(nativePtr);
			this.nativePtr = nativePtr;
			this.gson = JSONUtil.getGsonBuilder().create();
		}

		@Override
		public void closeSend() throws VeyronException {
			// TODO(spetrovic): implement this.
		}
		@Override
		public Object[] finish(TypeToken<?>[] types) throws VeyronException {
			// Call native method.
			final String[] jsonResults = nativeFinish(this.nativePtr);
			if (jsonResults.length != types.length) {
				throw new VeyronException(String.format(
					"Mismatch in number of results, want %s, have %s",
					types.length, jsonResults.length));
			}

			// JSON-decode results and return.
			final Object[] ret = new Object[types.length];
			for (int i = 0; i < types.length; i++) {
				final TypeToken<?> type = types[i];
				final String jsonResult = jsonResults[i];
				if (type.equals(new TypeToken<Any>(){})) {  // Any type.
					ret[i] = new Any(jsonResult);
					continue;
				}
				try {
					ret[i] = this.gson.fromJson(jsonResult, type.getType());
				} catch (JsonSyntaxException e) {
					throw new VeyronException(String.format(
						"Error decoding JSON result %s into type %s: %s",
						jsonResult, e.getMessage()));
				}
			}
			return ret;
		}
		@Override
		public void cancel() {
			nativeCancel(this.nativePtr);
		}
		@Override
		protected void finalize() {
			nativeFinalize(this.nativePtr);
		}
	}

	@SuppressWarnings("unused")
	private static class ServerCall extends Stream implements io.veyron.veyron.veyron2.ipc.ServerCall {
		private final long nativePtr;
		private final io.veyron.veyron.veyron.runtimes.google.security.Context context;

		public native long nativeBlessing(long nativePtr);
		private native long nativeDeadline(long nativePtr);
		private native boolean nativeClosed(long nativePtr);

		public ServerCall(long nativePtr) {
			super(nativePtr);
			this.nativePtr = nativePtr;
			this.context = new io.veyron.veyron.veyron.runtimes.google.security.Context(this.nativePtr);
		}
		// Implements io.veyron.veyron.veyron2.ipc.ServerContext.
		@Override
		public io.veyron.veyron.veyron2.security.PublicID blessing() {
			return new io.veyron.veyron.veyron.runtimes.google.security.PublicID(nativeBlessing(this.nativePtr));
		}
		@Override
		public Date deadline() {
			return new Date(nativeDeadline(this.nativePtr));
		}
		@Override
		public boolean closed() {
			return nativeClosed(this.nativePtr);
		}
		// Implements io.veyron.veyron.veyron2.security.Context.
		@Override
		public String method() {
			return this.context.method();
		}
		@Override
		public String name() {
			return this.context.name();
		}
		@Override
		public String suffix() {
			return this.context.suffix();
		}
		@Override
		public Label label() {
			return this.context.label();
		}
		@Override
		public PublicID localID() {
			return this.context.localID();
		}
		@Override
		public PublicID remoteID() {
			return this.context.remoteID();
		}
		@Override
		public String localEndpoint() {
			return this.context.localEndpoint();
		}
		@Override
		public String remoteEndpoint() {
			return this.context.remoteEndpoint();
		}
	}
}