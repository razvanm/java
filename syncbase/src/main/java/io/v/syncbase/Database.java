// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.v.v23.VFutures;
import io.v.v23.syncbase.DatabaseCore;
import io.v.v23.verror.VException;

public class Database implements DatabaseHandle {
    private final io.v.v23.syncbase.Database mDbImpl;

    protected Database(io.v.v23.syncbase.Database dbImpl) {
        mDbImpl = dbImpl;
    }

    public Id getId() {
        return new Id(mDbImpl.id());
    }

    public Collection collection(String name, CollectionOptions opts) {
        // TODO(sadovsky): If !opts.withoutSyncgroup, create syncgroup and update userdata syncgroup.
        return new Collection(mDbImpl.getCollection(new io.v.v23.services.syncbase.Id(Syncbase.getPersonalBlessingString(), name)), true);
    }

    protected static Collection getCollectionImpl(DatabaseCore vDb, Id id) {
        // TODO(sadovsky): Consider throwing an exception if the collection does not already exist.
        return new Collection(vDb.getCollection(id.toVId()), false);
    }

    public Collection getCollection(Id id) {
        return getCollectionImpl(mDbImpl, id);
    }

    // Exposed as a static function so that the implementation can be shared between Database and
    // BatchDatabase.
    protected static Iterator<Collection> getCollectionsImpl(DatabaseCore vDb) {
        List<io.v.v23.services.syncbase.Id> vIds;
        try {
            vIds = VFutures.sync(vDb.listCollections(Syncbase.getVContext()));
        } catch (VException e) {
            throw new RuntimeException("listCollections failed", e);
        }
        ArrayList<Collection> cxs = new ArrayList<>(vIds.size());
        for (io.v.v23.services.syncbase.Id vId : vIds) {
            cxs.add(new Collection(vDb.getCollection(vId), false));
        }
        return cxs.iterator();
    }

    public Iterator<Collection> getCollections() {
        return getCollectionsImpl(mDbImpl);
    }

    class SyncgroupOptions {
        // TODO(sadovsky): Fill this in.
    }

    // FOR ADVANCED USERS. Creates syncgroup and adds it to the user's "userdata" collection, as
    // needed. Idempotent.
    public Syncgroup syncgroup(String name, Collection[] collections, SyncgroupOptions opts) {
        throw new RuntimeException("Not implemented");
    }

    public Syncgroup getSyncgroup(Id id) {
        throw new RuntimeException("Not implemented");
    }

    public Iterator<Syncgroup> getSyncgroups() {
        throw new RuntimeException("Not implemented");
    }

    public class AddSyncgroupInviteHandlerOptions {
        // TODO(sadovsky): Fill this in.
    }

    // Notifies 'h' of any existing syncgroup invites, and of all subsequent new invites.
    public void addSyncgroupInviteHandler(SyncgroupInviteHandler h, AddSyncgroupInviteHandlerOptions opts) {
        throw new RuntimeException("Not implemented");
    }

    public void removeSyncgroupInviteHandler(SyncgroupInviteHandler h) {
        throw new RuntimeException("Not implemented");
    }

    public void removeSyncgroupInviteHandlers() {
        throw new RuntimeException("Not implemented");
    }

    // Joins syncgroup and adds it to the user's "userdata" collection, as needed.
    // TODO(sadovsky): Should we call this "joinSyncgroup"? Also, should we add "accept" and
    // "ignore" methods to the SyncgroupInvite class, or should we treat it as a POJO?
    // TODO(sadovsky): Make this method async.
    public Syncgroup acceptSyncgroupInvite(SyncgroupInvite invite) {
        throw new RuntimeException("Not implemented");
    }

    // Records that the user has ignored this invite, such that it's never surfaced again.
    // Note: This will be one of the last things we implement.
    public void ignoreSyncgroupInvite(SyncgroupInvite invite) {
        throw new RuntimeException("Not implemented");
    }

    public BatchDatabase beginBatch() {
        throw new RuntimeException("Not implemented");
    }

    public class AddWatchChangeHandlerOptions {
        // TODO(sadovsky): Fill this in.
    }

    // Notifies 'h' of initial state, and of all subsequent changes to this database. Options struct
    // includes resume marker.
    // Note: Eventually we'll add a watch variant that takes a query, where the query can be
    // constructed using some sort of query builder API.
    public void addWatchChangeHandler(WatchChangeHandler h, AddWatchChangeHandlerOptions opts) {
        throw new RuntimeException("Not implemented");
    }

    public void removeWatchChangeHandler(WatchChangeHandler h) {
        throw new RuntimeException("Not implemented");
    }

    public void removeAllWatchChangeHandlers() {
        throw new RuntimeException("Not implemented");
    }
}
