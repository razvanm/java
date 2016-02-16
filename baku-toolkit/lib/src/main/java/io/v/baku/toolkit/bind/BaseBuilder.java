// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.baku.toolkit.bind;


import android.app.Activity;

import io.v.baku.toolkit.BakuActivityTrait;
import io.v.baku.toolkit.ErrorReporters;
import io.v.baku.toolkit.VAndroidContextTrait;
import io.v.rx.syncbase.RxTable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class BaseBuilder<T extends BaseBuilder<T>> {
    @SuppressWarnings("unchecked")
    protected final T mSelf = (T)this;

    protected Activity mActivity;
    protected RxTable mRxTable;
    protected CompositeSubscription mSubscriptionParent;
    protected Subscription mLastSubscription;
    protected Action1<Throwable> mOnError;

    public T activity(final Activity activity) {
        mActivity = activity;
        return mSelf;
    }

    public T rxTable(final RxTable rxTable) {
        mRxTable = rxTable;
        return mSelf;
    }

    /**
     * Sets the following properties from the given {@link BakuActivityTrait}:
     * <ul>
     *     <li>{@link #activity(Activity)}</li>
     *     <li>{@link #rxTable(RxTable)}</li>
     *     <li>{@link #subscriptionParent(CompositeSubscription)}</li>
     *     <li>{@link #onError(Action1)}</li>
     * </ul>
     */
    public T activity(final BakuActivityTrait<?> trait) {
        return activity(trait.getVAndroidContextTrait().getAndroidContext())
                .rxTable(trait.getSyncbaseTable())
                .subscriptionParent(trait.getSubscriptions())
                .onError(trait::onSyncError);
    }

    /**
     * Sets the following properties from the given {@link VAndroidContextTrait}:
     * <ul>
     *     <li>{@link #activity(Activity)}</li>
     *     <li>{@link #onError(Action1)}</li>
     * </ul>
     */
    public T activity(final VAndroidContextTrait<? extends Activity> trait) {
        return activity(trait.getAndroidContext())
                .onError(ErrorReporters.getDefaultSyncErrorReporter(trait));
    }

    public T subscriptionParent(final CompositeSubscription subscriptionParent) {
        mSubscriptionParent = subscriptionParent;
        return mSelf;
    }

    protected Subscription subscribe(final Subscription subscription) {
        if (mSubscriptionParent == null) {
            mSubscriptionParent = new CompositeSubscription();
        }
        mSubscriptionParent.add(subscription);
        mLastSubscription = subscription;
        return subscription;
    }

    /**
     * @return the current subscription parent.
     */
    public CompositeSubscription getAllBindings() {
        return mSubscriptionParent;
    }

    public Subscription getLastBinding() {
        return mLastSubscription;
    }

    public T onError(final Action1<Throwable> onError) {
        mOnError = onError;
        return mSelf;
    }
}
