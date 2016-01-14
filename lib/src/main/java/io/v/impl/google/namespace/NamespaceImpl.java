// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.impl.google.namespace;

import com.google.common.util.concurrent.ListenableFuture;

import io.v.impl.google.ListenableFutureCallback;
import io.v.v23.InputChannel;
import io.v.v23.rpc.Callback;
import org.joda.time.Duration;

import java.util.List;
import java.util.Map;

import io.v.v23.Options;
import io.v.v23.context.VContext;
import io.v.v23.namespace.Namespace;
import io.v.v23.naming.GlobReply;
import io.v.v23.naming.MountEntry;
import io.v.v23.security.access.Permissions;
import io.v.v23.verror.VException;

/**
 * An implementation of {@link Namespace} that calls to native code for most of its
 * functionalities.
 */
public class NamespaceImpl implements Namespace {
    private final long nativePtr;

    private static native InputChannel<GlobReply> nativeGlob(
            long nativePtr, VContext context, String pattern, Options options) throws VException;
    private static native void nativeMount(long nativePtr, VContext context, String name,
                                           String server, Duration ttl, Options options,
                                           Callback<Void> callback);
    private static native void nativeUnmount(long nativePtr, VContext context, String name,
                                             String server, Options options,
                                             Callback<Void> callback);
    private static native void nativeDelete(long nativePtr, VContext context, String name,
                                            boolean deleteSubtree, Options options,
                                            Callback<Void> callback);
    private static native void nativeResolveToMountTable(long nativePtr, VContext context,
                                                         String name, Options options,
                                                         Callback<MountEntry> callback);
    private static native void nativeResolve(long nativePtr, VContext context, String name,
                                             Options options, Callback<MountEntry> callback);
    private static native boolean nativeFlushCacheEntry(long nativePtr, VContext context,
                                                        String name);
    private static native void nativeSetRoots(long nativePtr, List<String> roots) throws VException;
    private static native void nativeSetPermissions(long nativePtr, VContext context, String name,
                                                    Permissions permissions, String version,
                                                    Options options, Callback<Void> callback);
    private static native void nativeGetPermissions(long nativePtr, VContext context, String name,
                                                    Options options,
                                                    Callback<Map<String, Permissions>> callback);
    private native void nativeFinalize(long nativePtr);

    private NamespaceImpl(long nativePtr) {
        this.nativePtr = nativePtr;
    }

    @Override
    public ListenableFuture<Void> mount(VContext ctx, String name, String server,
                                        Duration ttl) {
        return mount(ctx, name, server, ttl, null);
    }

    @Override
    public ListenableFuture<Void> mount(VContext ctx, String name, String server, Duration ttl,
                                        Options options) {
        ListenableFutureCallback<Void> callback = new ListenableFutureCallback<>();
        nativeMount(nativePtr, ctx, name, server, ttl, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public ListenableFuture<Void> unmount(VContext ctx, String name, String server) {
        return unmount(ctx, name, server, null);
    }

    @Override
    public ListenableFuture<Void> unmount(VContext ctx, String name, String server,
                                          Options options) {
        ListenableFutureCallback<Void> callback = new ListenableFutureCallback<>();
        nativeUnmount(nativePtr, ctx, name, server, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public ListenableFuture<Void> delete(VContext ctx, String name, boolean deleteSubtree) {
        return delete(ctx, name, deleteSubtree, null);
    }

    @Override
    public ListenableFuture<Void> delete(VContext ctx, String name, boolean deleteSubtree,
                                         Options options) {
        ListenableFutureCallback<Void> callback = new ListenableFutureCallback<>();
        nativeDelete(nativePtr, ctx, name, deleteSubtree, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public ListenableFuture<MountEntry> resolve(VContext ctx, String name) {
        return resolve(ctx, name, null);
    }

    @Override
    public ListenableFuture<MountEntry> resolve(VContext ctx, String name, Options options) {
        ListenableFutureCallback<MountEntry> callback = new ListenableFutureCallback<>();
        nativeResolve(nativePtr, ctx, name, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public ListenableFuture<MountEntry> resolveToMountTable(VContext ctx, String name) {
        return resolveToMountTable(ctx, name, null);
    }

    @Override
    public ListenableFuture<MountEntry> resolveToMountTable(VContext ctx, String name,
                                                            Options options) {
        ListenableFutureCallback<MountEntry> callback = new ListenableFutureCallback<>();
        nativeResolveToMountTable(nativePtr, ctx, name, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public boolean flushCacheEntry(VContext ctx, String name) {
        return nativeFlushCacheEntry(nativePtr, ctx, name);
    }

    @Override
    public InputChannel<GlobReply> glob(VContext ctx, String pattern) {
        return glob(ctx, pattern, null);
    }

    @Override
    public InputChannel<GlobReply> glob(VContext ctx, String pattern, Options options) {
        try {
            return nativeGlob(nativePtr, ctx, pattern, options);
        } catch (VException e) {
            throw new RuntimeException("Couldn't create glob InputChannel.", e);
        }
    }

    @Override
    public void setRoots(List<String> roots) throws VException {
        nativeSetRoots(nativePtr, roots);
    }

    @Override
    public ListenableFuture<Void> setPermissions(VContext ctx, String name,
                                                 Permissions permissions, String version) {
        return setPermissions(ctx, name, permissions, version, null);
    }

    @Override
    public ListenableFuture<Void> setPermissions(VContext ctx, String name,
                                                 Permissions permissions, String version,
                                                 Options options) {
        ListenableFutureCallback<Void> callback = new ListenableFutureCallback<>();
        nativeSetPermissions(nativePtr, ctx, name, permissions, version, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public ListenableFuture<Map<String, Permissions>> getPermissions(VContext ctx,
                                                                     String name) {
        return getPermissions(ctx, name, null);
    }

    @Override
    public ListenableFuture<Map<String, Permissions>> getPermissions(VContext ctx, String name,
                                                                     Options options) {
        final ListenableFutureCallback<Map<String, Permissions>> callback = new ListenableFutureCallback<>();
        nativeGetPermissions(nativePtr, ctx, name, options, callback);
        return callback.getFuture(ctx);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        return this.nativePtr == ((NamespaceImpl) other).nativePtr;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(this.nativePtr).hashCode();
    }

    @Override
    protected void finalize() {
        nativeFinalize(this.nativePtr);
    }
}
