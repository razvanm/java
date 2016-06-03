// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase.internal;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DatabaseTest {
    @Before
    public void setUp() {
        System.loadLibrary("syncbase");
    }

    @Test
    public void createDatabase() {
        Id dbId = new Id("idp:a:angrybirds", "create_db");
        String dbName = dbId.encode();

        // The instance is empty so creating of a database should succeed.
        try {
            Database.Create(dbName, null);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }

        // Creating the same database should raise an exception.
        boolean exceptionThrown = false;
        try {
            Database.Create(dbName, null);
        } catch (VError vError) {
            assertEquals("v.io/v23/verror.Exist", vError.id);
            assertNotNull(vError.message);
            assertNotNull(vError.stack);
            assertEquals(0, vError.actionCode);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void destroyDatabase() {
        Id dbId = new Id("idp:a:angrybirds", "destroy_db");
        String dbName = dbId.encode();
        try {
            // The instance is empty so creating of a database should succeed.
            Database.Create(dbName, null);
            Database.Destroy(dbName);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void existsDatabase() {
        Id dbId = new Id("idp:a:angrybirds", "exists_db");
        String dbName = dbId.encode();
        try {
            // We have not created the database yet so Exists should fail.
            assertFalse(Database.Exists(dbName));
            // The instance is empty so creating of a database should succeed.
            Database.Create(dbName, null);
            // Exists should succeed now.
            assertTrue(Database.Exists(dbName));
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void permissions() {
        Id dbId = new Id("idp:a:angrybirds", "permissions_db");
        String dbName = dbId.encode();
        try {
            Database.Create(dbName, null);
            VersionedPermissions versionedPermissions1 = Database.GetPermissions(dbName);
            assertNotNull(versionedPermissions1);
            assertTrue(versionedPermissions1.version.length() > 0);
            String json = new String(versionedPermissions1.permissions.json);
            assertTrue(json.contains("Admin"));

            Database.SetPermissions(dbName, versionedPermissions1);
            VersionedPermissions versionedPermissions2 = Database.GetPermissions(dbName);
            assertNotEquals(versionedPermissions1.version, versionedPermissions2.version);
            assertEquals(json, new String(versionedPermissions2.permissions.json));
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void abortDatabase() {
        Id dbId = new Id("idp:a:angrybirds", "abort_db");
        String dbName = dbId.encode();
        Id collectionId = new Id("idp:u:alice", "collection");
        String collectionName = Util.NamingJoin(Arrays.asList(dbName, collectionId.encode()));
        try {
            Database.Create(dbName, null);
            String batchHandle = Database.BeginBatch(dbName, null);
            Collection.Create(collectionName, batchHandle, null);
            Database.Abort(dbName, batchHandle);
            batchHandle = Database.BeginBatch(dbName, null);
            // This should work because we Abort the previous batch.
            Collection.Create(collectionName, batchHandle, null);
            Database.Commit(dbName, batchHandle);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void listCollections() {
        Id dbId = new Id("idp:a:angrybirds", "list_db");
        String dbName = dbId.encode();
        try {
            Database.Create(dbName, null);
            String batchHandle = Database.BeginBatch(dbName, null);
            assertNotNull(batchHandle);
            List<Id> collections = Database.ListCollections(dbName, batchHandle);
            assertNotNull(collections);
            assertEquals(0, collections.size());
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void getResumeMarker() {
        Id dbId = new Id("idp:a:angrybirds", "get_resume_marker");
        String dbName = dbId.encode();
        try {
            Database.Create(dbName, null);
            String batchHandle = Database.BeginBatch(dbName, null);
            byte[] marker = Database.GetResumeMarker(dbName, batchHandle);
            assertNotNull(marker);
            assertTrue(marker.length > 0);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void createSyncgroup() {
        Id dbId = new Id("idp:a:angrybirds", "create_syncgroups");
        String dbName = dbId.encode();
        Id sgId = new Id("idp:u:alice", "syncgroup");
        Id collectionId = new Id("idp:u:alice", "collection");
        String collectionName = Util.NamingJoin(Arrays.asList(dbName, collectionId.encode()));
        try {
            Database.Create(dbName, null);
            String batchHandle = Database.BeginBatch(dbId.encode(), null);
            Collection.Create(collectionName, batchHandle, null);
            Database.Commit(dbName, batchHandle);
            Database.SyncgroupSpec spec = new Database.SyncgroupSpec();
            spec.collections = Arrays.asList(collectionId);
            Database.SyncgroupMemberInfo info = new Database.SyncgroupMemberInfo();
            // TODO(razvanm): Pick some meaningful values.
            info.syncPriority = 1;
            info.blobDevType = 2;
            Database.CreateSyncgroup(dbName, sgId, spec, info);
            List<Id> syncgroups = Database.ListSyncgroups(dbName);
            assertEquals(1, syncgroups.size());
            Id actual = syncgroups.get(0);
            assertEquals(sgId.blessing, actual.blessing);
            assertEquals(sgId.name, actual.name);

            Database.VersionedSyncgroupSpec verSpec = Database.GetSyncgroupSpec(dbName, sgId);
            assertNotNull(verSpec.version);
            assertTrue(verSpec.version.length() > 0);
            assertNotNull(verSpec.syncgroupSpec);
            assertEquals(1, verSpec.syncgroupSpec.collections.size());
            actual = syncgroups.get(0);
            assertEquals(sgId.blessing, actual.blessing);
            assertEquals(sgId.name, actual.name);

            verSpec.syncgroupSpec.description = "Dummy description";
            Database.SetSyncgroupSpec(dbName, sgId, verSpec);
            assertEquals(verSpec.syncgroupSpec.description, Database.GetSyncgroupSpec(dbName, sgId).syncgroupSpec.description);

            Map<String, Database.SyncgroupMemberInfo> members = Database.GetSyncgroupMembers(dbName, sgId);
            assertNotNull(members);
            assertEquals(1, members.size());
            assertTrue(members.keySet().iterator().next().length() > 0);
            assertEquals(info.syncPriority, members.values().iterator().next().syncPriority);
            assertEquals(info.blobDevType, members.values().iterator().next().blobDevType);
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void listSyncgroups() {
        Id dbId = new Id("idp:a:angrybirds", "list_syncgroups");
        String dbName = dbId.encode();
        try {
            Database.Create(dbName, null);
            List<Id> syncgroups = Database.ListSyncgroups(dbName);
            assertNotNull(syncgroups);
            assertEquals(0, syncgroups.size());
        } catch (VError vError) {
            vError.printStackTrace();
            fail(vError.toString());
        }
    }

    @Test
    public void destroySyncgroup() {
        Id dbId = new Id("idp:a:angrybirds", "destroy_syncgroup");
        String dbName = dbId.encode();
        Id sgId = new Id("idp:u:alice", "syncgroup");
        // TODO(razvanm): We'll have to update this after the destroy lands.
        boolean exceptionThrown = false;
        try {
            Database.Create(dbName, null);
            Database.DestroySyncgroup(dbName, sgId);
        } catch (VError vError) {
            assertEquals("v.io/v23/verror.NotImplemented", vError.id);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void joinSyncgroup() {
        Id dbId = new Id("idp:a:angrybirds", "join_syncgroup");
        String dbName = dbId.encode();
        Id sgId = new Id("idp:u:alice", "syncgroup");
        boolean exceptionThrown = false;
        try {
            Database.SyncgroupSpec spec = Database.JoinSyncgroup(
                    dbName, "", new ArrayList<String>(), sgId, new Database.SyncgroupMemberInfo());
        } catch (VError vError) {
            assertEquals("v.io/v23/verror.NoExist", vError.id);
            assertNotNull(vError.message);
            assertNotNull(vError.stack);
            assertEquals(0, vError.actionCode);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void leaveSyncgroup() {
        Id dbId = new Id("idp:a:angrybirds", "leave_syncgroups");
        String dbName = dbId.encode();
        Id sgId = new Id("idp:u:alice", "syncgroup");
        boolean exceptionThrown = false;
        try {
            Database.Create(dbName, null);
            Database.LeaveSyncgroup(dbName, sgId);
        }  catch (VError vError) {
            assertEquals("v.io/v23/verror.NotImplemented", vError.id);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void ejectFromSyncgroup() {
        Id dbId = new Id("idp:a:angrybirds", "eject_from_syncgroup");
        String dbName = dbId.encode();
        Id sgId = new Id("idp:u:alice", "syncgroup");
        boolean exceptionThrown = false;
        try {
            Database.Create(dbName, null);
            Database.EjectFromSyncgroup(dbName, sgId, "");
        }  catch (VError vError) {
            assertEquals("v.io/v23/verror.NotImplemented", vError.id);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}
