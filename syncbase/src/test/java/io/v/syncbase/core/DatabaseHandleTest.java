// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.v.syncbase.core.TestConstants.anyDbPermissions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DatabaseHandleTest {
    @Before
    public void setUp() {
        System.loadLibrary("syncbase");
    }

    @Test
    public void listCollections() {
        Id dbId = new Id("idp:a:angrybirds", "core_list_db");
        try {
            Database db = Service.database(dbId);
            db.create(anyDbPermissions());
            BatchDatabase batchDb = db.beginBatch(null);
            List<Id> collections = batchDb.listCollections();
            assertNotNull(collections);
            assertEquals(0, collections.size());
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void getResumeMarker() {
        Id dbId = new Id("idp:a:angrybirds", "core_get_resume_marker");
        try {
            Database db = Service.database(dbId);
            db.create(anyDbPermissions());
            BatchDatabase batchDb = db.beginBatch(null);
            byte[] marker = batchDb.getResumeMarker();
            assertNotNull(marker);
            assertTrue(marker.length > 0);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }
}
