// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.v.v23.VFutures;
import io.v.v23.services.syncbase.SyncgroupMemberInfo;
import io.v.v23.services.syncbase.SyncgroupSpec;
import io.v.v23.verror.ExistException;
import io.v.v23.verror.VException;

public class Syncgroup {
    private final Database mDatabase;
    private final Id mId;
    private final io.v.v23.syncbase.Syncgroup mVSyncgroup;

    protected void createIfMissing(List<Collection> collections) {
        ArrayList<io.v.v23.services.syncbase.Id> cxVIds = new ArrayList<>(collections.size());
        for (Collection cx : collections) {
            cxVIds.add(cx.getId().toVId());
        }
        SyncgroupSpec spec = new SyncgroupSpec(
                "", Syncbase.CLOUD_NAME, Syncbase.defaultPerms(), cxVIds,
                ImmutableList.of(Syncbase.MOUNT_POINT), false);
        SyncgroupMemberInfo info = new SyncgroupMemberInfo();
        // TODO(sadovsky): Still have no idea how to set sync priority.
        info.setSyncPriority((byte) 3);
        try {
            VFutures.sync(mVSyncgroup.create(Syncbase.getVContext(), spec, info));
        } catch (ExistException e) {
            // Syncgroup already exists.
            // TODO(sadovsky): Verify that the existing syncgroup has the specified configuration,
            // e.g. the specified collections?
        } catch (VException e) {
            throw new RuntimeException("Failed to create collection", e);
        }
    }

    // Note, we take 'id' because io.v.v23.syncbase.Syncgroup is missing a 'getId' method.
    protected Syncgroup(io.v.v23.syncbase.Syncgroup vSyncgroup, Database database, Id id) {
        mVSyncgroup = vSyncgroup;
        mDatabase = database;
        mId = id;
    }

    public Id getId() {
        return mId;
    }

    public AccessList getAccessList() {
        Map<String, SyncgroupSpec> versionedSpec;
        try {
            versionedSpec = VFutures.sync(mVSyncgroup.getSpec(Syncbase.getVContext()));
        } catch (VException e) {
            throw new RuntimeException("getSpec failed", e);
        }
        return new AccessList(versionedSpec.values().iterator().next().getPerms());
    }

    public static class UpdateAccessListOptions {
        public boolean syncgroupOnly;
    }

    // The following methods update the AccessList for the syncgroup and its associated collections.
    // Setting opts.syncgroupOnly makes it so these methods only update the AccessList for the
    // syncgroup.
    public void addUsers(List<User> users, AccessList.AccessLevel level, UpdateAccessListOptions opts) {
        AccessList delta = new AccessList();
        for (User u : users) {
            delta.users.put(u.getId(), level);
        }
        updateAccessList(delta, opts);
    }

    public void addUsers(List<User> users, AccessList.AccessLevel level) {
        addUsers(users, level, new UpdateAccessListOptions());
    }

    public void addUser(User user, AccessList.AccessLevel level) {
        addUsers(Collections.singletonList(user), level);
    }

    public void removeUsers(List<User> users, UpdateAccessListOptions opts) {
        AccessList delta = new AccessList();
        for (User u : users) {
            delta.users.put(u.getId(), null);
        }
        updateAccessList(delta, opts);
    }

    public void removeUsers(List<User> users) {
        removeUsers(users, new UpdateAccessListOptions());
    }

    public void removeUser(User user) {
        removeUsers(Collections.singletonList(user));
    }

    // Applies 'delta' to the AccessList. Note, NULL enum means "remove".
    public void updateAccessList(final AccessList delta, UpdateAccessListOptions opts) {
        // TODO(sadovsky): Make it so SyncgroupSpec can be updated as part of a batch?
        Map<String, SyncgroupSpec> versionedSpec;
        try {
            versionedSpec = VFutures.sync(mVSyncgroup.getSpec(Syncbase.getVContext()));
        } catch (VException e) {
            throw new RuntimeException("getSpec failed", e);
        }
        String version = versionedSpec.keySet().iterator().next();
        SyncgroupSpec spec = versionedSpec.values().iterator().next();
        AccessList.applyDelta(spec.getPerms(), delta);
        try {
            VFutures.sync(mVSyncgroup.setSpec(Syncbase.getVContext(), spec, version));
        } catch (VException e) {
            throw new RuntimeException("setSpec failed", e);
        }
        final List<io.v.v23.services.syncbase.Id> cxVIds = spec.getCollections();
        mDatabase.runInBatch(new Database.BatchOperation() {
            @Override
            public void run(BatchDatabase db) {
                for (io.v.v23.services.syncbase.Id vId : cxVIds) {
                    db.getCollection(new Id(vId)).updateAccessList(delta);
                }
            }
        }, new Database.BatchOptions());
    }
}
