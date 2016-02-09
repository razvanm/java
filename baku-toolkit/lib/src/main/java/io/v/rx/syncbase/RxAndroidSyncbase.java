// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.rx.syncbase;

import android.content.Context;

import io.v.baku.toolkit.VAndroidContextTrait;
import io.v.debug.SyncbaseAndroidClient;
import io.v.v23.context.VContext;
import io.v.v23.rpc.Server;
import io.v.v23.security.Blessings;
import io.v.v23.syncbase.SyncbaseService;
import lombok.experimental.Accessors;
import rx.Observable;

/**
 * Models a binding to a Syncbase Android service as an {@code Observable} of
 * {@link SyncbaseService}s. The binding will be asynchronously made and then potentially
 * periodically lost and regained, so modeling further operations as subscriptions works well.
 */
@Accessors(prefix = "m")
public class RxAndroidSyncbase extends RxSyncbase implements AutoCloseable {
    private final SyncbaseAndroidClient mClient;

    public Observable<Server> getRxServer() {
        return mClient.getRxServer();
    }

    @Override
    public Observable<SyncbaseService> getRxClient() {
        return mClient.getRxClient();
    }

    public RxAndroidSyncbase(final VContext vContext, final SyncbaseAndroidClient client) {
        super(vContext);
        mClient = client;
    }

    public RxAndroidSyncbase(final Context androidContext, final VContext ctx,
                             final Observable<Blessings> rxBlessings) {
        this(ctx, new SyncbaseAndroidClient(androidContext, rxBlessings));
    }

    public RxAndroidSyncbase(final VAndroidContextTrait trait) {
        this(trait.getAndroidContext(), trait.getVContext(),
                trait.getBlessingsProvider().getRxBlessings());
    }

    @Override
    public void close() {
        mClient.close();
    }
}
