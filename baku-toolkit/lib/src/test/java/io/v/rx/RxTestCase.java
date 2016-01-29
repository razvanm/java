// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.rx;

import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import org.joda.time.Duration;
import org.junit.After;

import java.util.Iterator;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

import static org.junit.Assert.fail;

public abstract class RxTestCase {
    public static final long BLOCKING_DELAY_MS = 375;

    public static long verificationDelay(final Duration nominal) {
        return 2 * nominal.getMillis();
    }

    private final Multimap<Class<? extends Throwable>, Throwable> mErrors =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());


    public void catchAsync(final Throwable t) {
        mErrors.put(t.getClass(), t);
    }

    public void expect(final Class<? extends Throwable> type) {
        final Iterator<Throwable> iter = mErrors.get(type).iterator();
        if (!iter.hasNext()) {
            fail(type + " expected but not thrown");
        } else {
            iter.next();
            iter.remove();
        }
    }

    /**
     * Tests should call this where it make sense and to fail early if possible.
     */
    @After
    public void assertNoAsyncErrors() {
        if (!mErrors.isEmpty()) {
            fail(StreamSupport.stream(mErrors.values())
                    .map(Throwables::getStackTraceAsString)
                    .collect(Collectors.joining("\n")));
        }
    }
}
