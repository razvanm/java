// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static io.v.syncbase.core.TestConstants.anyCollectionPermissions;
import static io.v.syncbase.core.TestConstants.anyDbPermissions;
import static org.junit.Assert.fail;

public class BatchDatabaseTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        System.loadLibrary("syncbase");
        io.v.syncbase.internal.Service.Init(folder.newFolder().getAbsolutePath());
    }

    @After
    public void tearDown() throws Exception {
        io.v.syncbase.internal.Service.Shutdown();
    }

    @Test
    public void commitAndAbort() {
        Id dbId = new Id("idp:a:angrybirds", "core_abort_db");
        Id collectionId = new Id("...", "collection");
        try {
            Database db = Service.database(dbId);
            db.create(anyDbPermissions());
            BatchDatabase batchDb = db.beginBatch(null);
            Collection collection = batchDb.collection(collectionId);
            collection.create(anyCollectionPermissions());
            batchDb.abort();
            batchDb = db.beginBatch(null);
            collection = batchDb.collection(collectionId);
            // This should work because we abort the previous batch.
            collection.create(anyCollectionPermissions());
            batchDb.commit();
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }
}