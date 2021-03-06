// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.debug;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.joda.time.Duration;

import io.v.v23.rpc.Server;
import io.v.v23.security.Blessings;
import io.v.v23.syncbase.Syncbase;
import io.v.v23.syncbase.SyncbaseService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.Value;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Syncbase Vanadium client for the {@link SyncbaseAndroidService} bound service, wrapped in an
 * {@link Observable}.
 */
@Accessors(prefix = "m")
public class SyncbaseAndroidClient implements AutoCloseable {
    public static class BindException extends Exception {
        public BindException(final String message) {
            super(message);
        }
    }

    @Value
    public static class ServerClient {
        Server mServer;
        SyncbaseService mClient;
    }

    @RequiredArgsConstructor
    private static class SyncbaseServiceConnection implements ServiceConnection {
        private final Observer<ServerClient> mObserver;
        private Subscription lastSubscription;

        @Override
        public void onServiceConnected(final ComponentName componentName,
                                       final IBinder iBinder) {
            final SyncbaseAndroidService.Binder binder = (SyncbaseAndroidService.Binder) iBinder;
            lastSubscription = binder.getObservable()
                    .map(b -> new ServerClient(b.getServer(), Syncbase.newService(b.getEndpoint())))
                    .subscribe(mObserver);
        }

        @Override
        public void onServiceDisconnected(final ComponentName componentName) {
            lastSubscription.unsubscribe();
            mObserver.onNext(null);
        }
    }

    private final Context mAndroidContext;
    private final Observable<ServerClient> mObservable;
    private final SyncbaseServiceConnection mConnection;
    private boolean mBound;

    public Observable<Server> getRxServer() {
        return mObservable.map(ServerClient::getServer);
    }

    public Observable<SyncbaseService> getRxClient() {
        return mObservable.map(ServerClient::getClient);
    }

    @Synchronized
    private void startService(final Context androidContext, final Intent intent) {
        if (androidContext.startService(intent) == null) {
            mConnection.mObserver.onError(
                    new BindException("Could not locate Syncbase Android service"));
        }
        mBound = androidContext.bindService(
                intent, mConnection, Context.BIND_AUTO_CREATE);
        if (!mBound) {
            mConnection.mObserver.onError(
                    new BindException("Failed to bind to Syncbase Android service"));
        }
    }

    /**
     * @deprecated Use {@link
     *  #SyncbaseAndroidClient(Context, Observable, SyncbaseAndroidService.Options)}
     */
    public SyncbaseAndroidClient(final Context androidContext,
                                 final Observable<Blessings> rxBlessings,
                                 final boolean cleanStart, final Duration keepAlive) {
        this(androidContext, rxBlessings, new SyncbaseAndroidService.Options()
                .withCleanStart(cleanStart)
                .withKeepAlive(keepAlive));
    }

    /**
     * Starts/connects to {@link SyncbaseAndroidService} and returns an {@code Observable} which
     * receives an {@code onNext} whenever the Android service client connects. Subscriptions share
     * a connection and initially receive the active instance if still connected.
     *
     * @param rxBlessings an optional observable of blessings. If provided, the Syncbase service
     *                    will not be started until blessings are available.
     *                    TODO(rosswang): this should either handle blessings changes or not care.
     */
    public SyncbaseAndroidClient(final Context androidContext,
                                 final @Nullable Observable<Blessings> rxBlessings,
                                 final @Nullable SyncbaseAndroidService.Options options) {
        mAndroidContext = androidContext;

        /*
        SyncbaseAndroidService is included in the io.v.baku.toolkit manifest, but since Android
        libraries are linked statically, we'll need to resolve it via the end application package
        (through androidContext).
         */
        final Intent intent = new Intent(androidContext, SyncbaseAndroidService.class);
        if (options != null) {
            intent.putExtra(SyncbaseAndroidService.EXTRA_OPTIONS, options);
        }

        final ReplaySubject<ServerClient> rpl = ReplaySubject.createWithSize(1);
        mConnection = new SyncbaseServiceConnection(rpl);

        if (rxBlessings == null) {
            startService(androidContext, intent);
        } else {
            rxBlessings.take(1)
                    .subscribe(s -> {
                        startService(androidContext, intent);
                    }, rpl::onError);
        }

        mObservable = rpl.filter(s -> s != null);
    }

    public SyncbaseAndroidClient(final Context androidContext,
                                 final Observable<Blessings> rxBlessings) {
        this(androidContext, rxBlessings, null);
    }

    @Override
    @Synchronized
    public void close() {
        if (mBound) {
            mAndroidContext.unbindService(mConnection);
            mBound = false;
        }
    }
}
