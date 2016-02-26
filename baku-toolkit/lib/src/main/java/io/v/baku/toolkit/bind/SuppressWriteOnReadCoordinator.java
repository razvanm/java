// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.baku.toolkit.bind;

import android.view.View;
import android.widget.TextView;

import io.v.baku.toolkit.BakuActivityTrait;
import io.v.rx.syncbase.SingleWatchEvent;
import lombok.RequiredArgsConstructor;
import rx.Observable;
import rx.Subscription;

/**
 * This coordinator suppresses the write binding when caused by an update from the read binding.
 * Android {@link android.widget.EditText} fires text update events whether changes originate from
 * the user or from code. If we don't suppress writes while responding to Syncbase changes with
 * Android widgets, we easily end up in update loops.
 *
 * To operate correctly, this coordinator must occur single-threaded with the widget binding layer.
 *
 * This coordinator is required (and injected if missing) in the coordinator chain for
 * {@linkplain io.v.baku.toolkit.bind.SyncbaseBinding.Builder#bindTo(TextView) two-way
 * <code>TextView</code> bindings}.
 *
 * ## Usage
 *The following example shows how you would use this coordinator explicitly while creating a custom
 * binding within a {@link io.v.baku.toolkit.BakuActivityTrait}:
 *
 * ```java
 * {@link BakuActivityTrait#binder() binder()}.{@link
 *         io.v.baku.toolkit.bind.SyncbaseBinding.Builder#key(java.lang.String) key}("foo")
 *         .{@link io.v.baku.toolkit.bind.SyncbaseBinding.Builder#coordinators(CoordinatorChain[])
 *         coordinators}({@link
 *         SuppressWriteOnReadCoordinator#SuppressWriteOnReadCoordinator(TwoWayBinding)
 *         SuppressWriteOnReadCoordinator::new})
 *         .{@link io.v.baku.toolkit.bind.SyncbaseBinding.Builder#bindTo(View) bindTo}(myView);
 * ```
 */
@RequiredArgsConstructor
public class SuppressWriteOnReadCoordinator<T> implements TwoWayBinding<T> {
    private final TwoWayBinding<T> mChild;

    private boolean mSuppressWrites;

    @Override
    public Observable<SingleWatchEvent<T>> downlink() {
        final Observable<SingleWatchEvent<T>> childDownlink = mChild.downlink();
        return Observable.create(s -> s.add(childDownlink.subscribe(x -> {
            mSuppressWrites = true;
            s.onNext(x);
            mSuppressWrites = false;
        }, s::onError, s::onCompleted)));
    }

    @Override
    public Subscription uplink(Observable<T> rxData) {
        return mChild.uplink(rxData.filter(x -> !mSuppressWrites));
    }
}
