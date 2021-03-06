// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.v23.syncbase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.v.impl.google.naming.NamingUtil;
import io.v.impl.google.services.syncbase.SyncbaseServer;
import io.v.v23.InputChannel;
import io.v.v23.InputChannels;
import io.v.v23.V;
import io.v.v23.context.VContext;
import io.v.v23.naming.Endpoint;
import io.v.v23.rpc.ListenSpec;
import io.v.v23.rpc.Server;
import io.v.v23.security.BlessingPattern;
import io.v.v23.security.Blessings;
import io.v.v23.security.Caveat;
import io.v.v23.security.VPrincipal;
import io.v.v23.security.VSecurity;
import io.v.v23.security.access.AccessList;
import io.v.v23.security.access.Constants;
import io.v.v23.security.access.Permissions;
import io.v.v23.services.syncbase.BatchOptions;
import io.v.v23.services.syncbase.BlobRef;
import io.v.v23.services.syncbase.CollectionRowPattern;
import io.v.v23.services.syncbase.Id;
import io.v.v23.services.syncbase.KeyValue;
import io.v.v23.services.syncbase.ReadOnlyBatchException;
import io.v.v23.services.syncbase.SyncgroupMemberInfo;
import io.v.v23.services.syncbase.SyncgroupSpec;
import io.v.v23.services.watch.ResumeMarker;
import io.v.v23.syncbase.util.Util;
import io.v.v23.vdl.VdlAny;
import io.v.v23.verror.CanceledException;
import io.v.v23.verror.NoExistException;
import io.v.v23.verror.VException;
import io.v.v23.vom.VomUtil;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.google.common.truth.Truth.assertThat;
import static io.v.v23.VFutures.sync;

/**
 * Client-server syncbase tests.
 */
public class SyncbaseTest extends TestCase {
    private static final Id DB_ID = new Id("jroot:o:coffee", "db");
    private static final String COLLECTION_NAME = "collection";
    private static final Id COLLECTION_ID = new Id("jroot:o:coffee:bean", COLLECTION_NAME);
    private static final String ROW_NAME = "row/a#%b";  // symbols are okay
    private static final String ROW_NAME2 = "row/a#%c";  // symbols are okay
    private static final String ROW_NAME3 = "row/a#%d";  // symbols are okay

    private VContext rootCtx;
    private VContext ctx;
    private Permissions allowAllDb;
    private Permissions allowAllCx;
    private Permissions allowAllSg;
    private Endpoint serverEndpoint;

    @Override
    protected void setUp() throws Exception {
        rootCtx = V.init();
        VPrincipal rootPrincipal = VSecurity.newPrincipal();
        Blessings blessings = rootPrincipal.blessSelf("jroot");
        rootPrincipal.blessingStore().setDefaultBlessings(blessings);
        rootPrincipal.blessingStore().set(blessings, new BlessingPattern("..."));
        rootPrincipal.roots().add(rootPrincipal.publicKey(), new BlessingPattern("jroot"));
        rootCtx = V.withPrincipal(rootCtx, rootPrincipal);
        // Note, the client should bless the server. This doesn't currently happen, but it doesn't
        // affect any of the Java tests. A workaround used in Go tests is manually blessing the
        // server with the same extension as the client, or adding the server blessing to all ACLs.
        VContext serverCtx = forkContext(rootCtx, "r:server");
        serverCtx = V.withListenSpec(serverCtx, V.getListenSpec(serverCtx).withAddress(
                new ListenSpec.Address("tcp", "localhost:0")));
        AccessList acl = new AccessList(
                ImmutableList.of(
                    new BlessingPattern("jroot:o:coffee:bean"),
                    new BlessingPattern("jroot:u")),
                ImmutableList.<String>of());
        allowAllDb = new Permissions(ImmutableMap.of(
                Constants.RESOLVE.getValue(), acl,
                Constants.READ.getValue(), acl,
                Constants.WRITE.getValue(), acl,
                Constants.ADMIN.getValue(), acl));
        allowAllCx = new Permissions(ImmutableMap.of(
                Constants.READ.getValue(), acl,
                Constants.WRITE.getValue(), acl,
                Constants.ADMIN.getValue(), acl));
        allowAllSg = new Permissions(ImmutableMap.of(
                Constants.READ.getValue(), acl,
                Constants.ADMIN.getValue(), acl));
        String tmpDir = Files.createTempDir().getAbsolutePath();
        serverCtx = SyncbaseServer.withNewServer(serverCtx, new SyncbaseServer.Params()
                .withPermissions(allowAllDb)
                .withStorageRootDir(tmpDir));
        Server server = V.getServer(serverCtx);
        assertThat(server).isNotNull();
        Endpoint[] endpoints = server.getStatus().getEndpoints();
        assertThat(endpoints).isNotEmpty();
        serverEndpoint = endpoints[0];
        ctx = forkContext(rootCtx, "o:coffee:bean:phone");
    }

    @Override
    protected void tearDown() throws Exception {
        rootCtx.cancel();
    }

    public void testService() throws Exception {
        SyncbaseService service = createService();
        assertThat(service.fullName()).isEqualTo(serverEndpoint.name());
        assertThat(sync(service.listDatabases(ctx))).isEmpty();
    }


    public void testDatabase() throws Exception {
        SyncbaseService service = createService();
        assertThat(service).isNotNull();
        Database db = service.getDatabase(DB_ID, null);
        assertThat(db).isNotNull();
        assertThat(db.id()).isEqualTo(DB_ID);
        assertThat(db.fullName()).isEqualTo(
                NamingUtil.join(serverEndpoint.name(), Util.encodeId(DB_ID)));
        assertThat(sync(db.exists(ctx))).isFalse();
        assertThat(sync(service.listDatabases(ctx))).isEmpty();
        sync(db.create(ctx, allowAllDb));
        assertThat(sync(db.exists(ctx))).isTrue();
        assertThat(sync(service.listDatabases(ctx))).containsExactly(db.id());
        assertThat(sync(db.listCollections(ctx))).isEmpty();
        sync(db.destroy(ctx));
        assertThat(sync(db.exists(ctx))).isFalse();
        assertThat(sync(service.listDatabases(ctx))).isEmpty();
    }

    public void testCollection() throws Exception {
        Database db = createDatabase(createService());
        assertThat(db).isNotNull();
        Collection collection = db.getCollection(COLLECTION_ID);
        assertThat(collection).isNotNull();
        assertThat(collection.id()).isEqualTo(COLLECTION_ID);
        assertThat(collection.fullName()).isEqualTo(
                NamingUtil.join(serverEndpoint.name(), Util.encodeId(DB_ID),
                        Util.encodeId(COLLECTION_ID)));
        assertThat(sync(collection.exists(ctx))).isFalse();
        assertThat(sync(db.listCollections(ctx))).isEmpty();
        sync(collection.create(ctx, allowAllCx));
        assertThat(sync(collection.exists(ctx))).isTrue();
        assertThat(sync(db.listCollections(ctx))).containsExactly(COLLECTION_ID);

        assertThat(sync(collection.getRow("row1").exists(ctx))).isFalse();
        sync(collection.put(ctx, "row1", "value1"));
        assertThat(sync(collection.getRow("row1").exists(ctx))).isTrue();
        assertThat(sync(collection.get(ctx, "row1", String.class))).isEqualTo("value1");
        sync(collection.delete(ctx, "row1"));
        assertThat(sync(collection.getRow("row1").exists(ctx))).isFalse();
        sync(collection.put(ctx, "row1", "value1"));
        sync(collection.put(ctx, "row2", "value2"));
        assertThat(sync(collection.getRow("row1").exists(ctx))).isTrue();
        assertThat(sync(collection.getRow("row2").exists(ctx))).isTrue();
        assertThat(sync(collection.get(ctx, "row1", String.class))).isEqualTo("value1");
        assertThat(sync(collection.get(ctx, "row2", String.class))).isEqualTo("value2");
        assertThat(sync(InputChannels.asList(
                collection.scan(ctx, RowRange.range("row1", "row3"))))).containsExactly(
                new KeyValue("row1", (VdlAny) VomUtil.decode(VomUtil.encode("value1", String.class), VdlAny.class)),
                new KeyValue("row2", (VdlAny) VomUtil.decode(VomUtil.encode("value2", String.class), VdlAny.class)));
        sync(collection.deleteRange(ctx, RowRange.range("row1", "row3")));
        assertThat(sync(collection.getRow("row1").exists(ctx))).isFalse();
        assertThat(sync(collection.getRow("row2").exists(ctx))).isFalse();

        sync(collection.destroy(ctx));
        assertThat(sync(collection.exists(ctx))).isFalse();
        assertThat(sync(db.listCollections(ctx))).isEmpty();
    }

    public static class MyTestClass {
        String foo;
        Integer bar;
        List<String> baz;

        public MyTestClass() {
            foo = null;
            bar = null;
            baz = Lists.newArrayList();
        }

        public MyTestClass(String inFoo, Integer inBar, String ... inBaz) {
            foo = null;
            bar = null;
            baz = Lists.newArrayList();
            for (String value : inBaz) {
                baz.add(value);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof MyTestClass)) {
                return false;
            }

            MyTestClass that = (MyTestClass) o;

            if (foo != null ? !foo.equals(that.foo) : that.foo != null) {
                return false;
            }
            if (bar != null ? !bar.equals(that.bar) : that.bar != null) {
                return false;
            }
            return baz.equals(that.baz);
        }

        @Override
        public int hashCode() {
            int result = foo != null ? foo.hashCode() : 0;
            result = 31 * result + (bar != null ? bar.hashCode() : 0);
            result = 31 * result + baz.hashCode();
            return result;
        }
    }

    public void testRow() throws Exception {
        Collection collection = createCollection(createDatabase(createService()));
        Row row = collection.getRow(ROW_NAME);
        assertThat(row).isNotNull();
        assertThat(row.key()).isEqualTo(ROW_NAME);
        assertThat(row.fullName()).isEqualTo(
                NamingUtil.join(serverEndpoint.name(), Util.encodeId(DB_ID),
                        Util.encodeId(COLLECTION_ID), Util.encode(ROW_NAME)));
        assertThat(sync(row.exists(ctx))).isFalse();
        sync(row.put(ctx, "value"));
        assertThat(sync(row.exists(ctx))).isTrue();
        assertThat(sync(row.get(ctx, String.class))).isEqualTo("value");
        assertThat(sync(collection.get(ctx, ROW_NAME, String.class))).isEqualTo("value");
        sync(row.delete(ctx));
        assertThat(sync(row.exists(ctx))).isFalse();

        // String
        sync(collection.put(ctx, ROW_NAME, "value"));
        assertThat(sync(row.exists(ctx))).isTrue();
        assertThat(sync(row.get(ctx, String.class))).isEqualTo("value");
        assertThat(sync(collection.get(ctx, ROW_NAME, String.class))).isEqualTo("value");

        // Integer
        sync(collection.put(ctx, ROW_NAME3, 42));
        assertThat(sync(collection.get(ctx, ROW_NAME3, Integer.class))).isEqualTo(42);

        // Java POJO, MyTestClass
        MyTestClass mtc = new MyTestClass("hello", 58501, "a", "b", "c");
        sync(collection.put(ctx, ROW_NAME2, mtc));
        assertThat(sync(collection.get(ctx, ROW_NAME2, MyTestClass.class))).isEqualTo(mtc);

        // VDL-generated class, Caveat
        Random random = new Random();
        byte[] idBytes = new byte[16];
        random.nextBytes(idBytes);
        Caveat caveat = new Caveat(new io.v.v23.uniqueid.Id(idBytes), "voms".getBytes());
        sync(collection.put(ctx, ROW_NAME2, caveat));
        assertThat(sync(collection.get(ctx, ROW_NAME2, Caveat.class))).isEqualTo(caveat);
    }

    public void testDatabaseExec() throws Exception {
        Database db = createDatabase(createService());
        Collection collection = createCollection(db);
        Foo foo = new Foo(4, "f");
        Bar bar = new Bar(0.5f, "b");
        Baz baz = new Baz("John Doe", true);

        sync(collection.put(ctx, "foo", foo));
        sync(collection.put(ctx, "bar", bar));
        sync(collection.put(ctx, "baz", baz));

        {
            DatabaseCore.QueryResults results = sync(db.exec(ctx,
                    "select k, v.Name from " + COLLECTION_NAME + " where Type(v) like \"%Baz\""));
            assertThat(results.columnNames()).containsExactly("k", "v.Name");
            assertThat(sync(InputChannels.asList(results))).containsExactly(ImmutableList.of(
                    new VdlAny(String.class, "baz"), new VdlAny(String.class, baz.name)));
        }
        {
            DatabaseCore.QueryResults results =
                    sync(db.exec(ctx, "select k, v from " + COLLECTION_NAME));
            assertThat(results.columnNames()).containsExactly("k", "v");
            assertThat(sync(InputChannels.asList(results))).containsExactly(
                    ImmutableList.of(new VdlAny(String.class, "bar"), new VdlAny(Bar.class, bar)),
                    ImmutableList.of(new VdlAny(String.class, "baz"), new VdlAny(Baz.class, baz)),
                    ImmutableList.of(new VdlAny(String.class, "foo"), new VdlAny(Foo.class, foo))
            );
        }
        {
            DatabaseCore.QueryResults results = sync(db.exec(ctx,
                    "select k, v from " + COLLECTION_NAME + " where k = ? or v.I = ?",
                    Arrays.<Object>asList("baz", 4),
                    Arrays.<Type>asList(String.class, int.class)));
            assertThat(results.columnNames()).containsExactly("k", "v");
            assertThat(sync(InputChannels.asList(results))).containsExactly(
                    ImmutableList.of(new VdlAny(String.class, "baz"), new VdlAny(Baz.class, baz)),
                    ImmutableList.of(new VdlAny(String.class, "foo"), new VdlAny(Foo.class, foo))
            );
        }
    }

    public void testDatabaseWatch() throws Exception {
        Database db = createDatabase(createService());
        Collection collection = createCollection(db);
        Foo foo = new Foo(4, "f");
        Bar bar = new Bar(0.5f, "b");
        Baz baz = new Baz("John Doe", true);
        ResumeMarker marker = sync(db.getResumeMarker(ctx));

        sync(collection.put(ctx, "foo", foo));
        sync(collection.put(ctx, "bar", bar));
        sync(collection.put(ctx, "ba%", baz));
        sync(collection.getRow("ba%").delete(ctx));

        ImmutableList<WatchChange> expectedChanges = ImmutableList.of(
                new WatchChange(COLLECTION_ID, "foo", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false),
                new WatchChange(COLLECTION_ID, "ba%", ChangeType.PUT_CHANGE,
                        new VdlAny(Baz.class, baz), null, false, false),
                new WatchChange(COLLECTION_ID, "ba%", ChangeType.DELETE_CHANGE,
                        new VdlAny(), null, false, false));

        VContext ctxC = ctx.withCancel();
        Iterator<WatchChange> it = InputChannels.asIterable(
                // Watch two prefixes in the same call. The second filter matches only keys with
                // the literal prefix 'ba%' - the '%' is not interpreted as a wildcard, so "bar"
                // is not matched.
                db.watch(ctxC, marker, ImmutableList.of(
                    Util.rowPrefixPattern(COLLECTION_ID, "foo"),
                    Util.rowPrefixPattern(COLLECTION_ID, "ba%"))))
                .iterator();
        checkWatch(it, expectedChanges);
        ctxC.cancel();
    }

    public void testDatabaseWatchWithInitialState() throws Exception {
        Database db = createDatabase(createService());
        Collection collection = createCollection(db);
        Foo foo = new Foo(4, "f");
        Bar bar = new Bar(0.5f, "b");
        Baz baz = new Baz("John Doe", true);

        sync(collection.put(ctx, "foo", foo));
        sync(collection.put(ctx, "barfoo", foo));
        sync(collection.put(ctx, "bar", bar));

        final VContext ctxC = ctx.withCancel();
        Iterator<WatchChange> it = InputChannels.asIterable(
                db.watch(ctxC, ImmutableList.of(Util.rowPrefixPattern(COLLECTION_ID, "b"))))
                .iterator();

        ImmutableList<WatchChange> expectedInitialChanges = ImmutableList.of(
                new WatchChange(COLLECTION_ID, "bar", ChangeType.PUT_CHANGE,
                        new VdlAny(Bar.class, bar), null, false, true),
                new WatchChange(COLLECTION_ID, "barfoo", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false));
        checkWatch(it, expectedInitialChanges);

        sync(collection.put(ctx, "baz", baz));
        sync(collection.getRow("baz").delete(ctx));

        ImmutableList<WatchChange> expectedChanges = ImmutableList.of(
                new WatchChange(COLLECTION_ID, "baz", ChangeType.PUT_CHANGE,
                        new VdlAny(Baz.class, baz), null, false, false),
                new WatchChange(COLLECTION_ID, "baz", ChangeType.DELETE_CHANGE,
                        new VdlAny(), null, false, false));
        checkWatch(it, expectedChanges);
        ctxC.cancel();
    }

    public void testDatabaseWatchWithContextCancel() throws Exception {
        final VContext ctxC = ctx.withCancel();
        Database db = createDatabase(createService());
        createCollection(db);

        InputChannel<WatchChange> channel = db.watch(
                ctxC, sync(db.getResumeMarker(ctx)),
                ImmutableList.of(Util.rowPrefixPattern(COLLECTION_ID, "b")));
        final SettableFuture<Void> wait = SettableFuture.create();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ctxC.cancel();
                wait.set(null);
            }
        }).start();
        wait.get(3, TimeUnit.SECONDS);
        try {
            sync(InputChannels.asList(channel));
        } catch (CanceledException e) {
            // OK
        }
    }

    public void testDatabaseWatchFilters() throws Exception {
        Database db = createDatabase(createService());

        // Create and populate two collections.
        Collection cxAFoo = db.getCollection(new Id("jroot:u:alice", "foo"));
        sync(cxAFoo.create(forkContext(rootCtx, "u:alice"), allowAllCx));
        Collection cxBFoo = db.getCollection(new Id("jroot:u:bob", "foobar"));
        sync(cxBFoo.create(forkContext(rootCtx, "u:bob"), allowAllCx));
        Collection collection = createCollection(db);
        Foo foo = new Foo(42, "LUE");

        sync(cxAFoo.put(ctx, "abc", foo));
        sync(cxAFoo.put(ctx, "abcd", foo));
        sync(cxAFoo.put(ctx, "bcd", foo));
        sync(cxAFoo.put(ctx, "bc_", foo));

        sync(cxBFoo.put(ctx, "abc", foo));
        sync(cxBFoo.put(ctx, "bcd", foo));
        sync(cxBFoo.put(ctx, "bc_", foo));
        sync(cxBFoo.put(ctx, "bc_e", foo));

        final VContext ctxC = ctx.withCancel();
        Iterator<WatchChange> it1 = InputChannels.asIterable(
                db.watch(ctxC, ImmutableList.of(
                        new CollectionRowPattern("jroot:u:%", "foo", "abc%"),
                        new CollectionRowPattern("jroot:u:%", "foo%",
                                "%" + Util.escapePattern("c_")))))
                .iterator();
        Iterator<WatchChange> it2 = InputChannels.asIterable(
                db.watch(ctxC, ImmutableList.of(
                        Util.rowPrefixPattern(new Id("jroot:u:bob", "foobar"), "bc_"),
                        new CollectionRowPattern("%:alice", "%", "%bcd"))))
                .iterator();

        ImmutableList<WatchChange> expectedInitialChanges1 = ImmutableList.of(
                new WatchChange(cxAFoo.id(), "abc", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxAFoo.id(), "abcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxAFoo.id(), "bc_", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxBFoo.id(), "bc_", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false));
        checkWatch(it1, expectedInitialChanges1);

        ImmutableList<WatchChange> expectedInitialChanges2 = ImmutableList.of(
                new WatchChange(cxAFoo.id(), "abcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxAFoo.id(), "bcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxBFoo.id(), "bc_", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, true),
                new WatchChange(cxBFoo.id(), "bc_e", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false));
        checkWatch(it2, expectedInitialChanges2);

        // More changes to existing collections.
        sync(cxAFoo.put(ctx, "abcd", foo));
        sync(cxBFoo.getRow("bc_").delete(ctx));

        // Create and populate another collection.
        Collection cxAFoobar = db.getCollection(new Id("jroot:u:alice", "foobar"));
        sync(cxAFoobar.create(forkContext(rootCtx, "u:alice"), allowAllCx));
        sync(cxAFoobar.put(ctx, "abcd", foo));
        sync(cxAFoobar.put(ctx, "abc_", foo));

        ImmutableList<WatchChange> expectedChanges1 = ImmutableList.of(
                new WatchChange(cxAFoo.id(), "abcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false),
                new WatchChange(cxBFoo.id(), "bc_", ChangeType.DELETE_CHANGE,
                        new VdlAny(), null, false, false),
                new WatchChange(cxAFoobar.id(), "abc_", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false));
        checkWatch(it1, expectedChanges1);

        ImmutableList<WatchChange> expectedChanges2 = ImmutableList.of(
                new WatchChange(cxAFoo.id(), "abcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false),
                new WatchChange(cxBFoo.id(), "bc_", ChangeType.DELETE_CHANGE,
                        new VdlAny(), null, false, false),
                new WatchChange(cxAFoobar.id(), "abcd", ChangeType.PUT_CHANGE,
                        new VdlAny(Foo.class, foo), null, false, false));
        checkWatch(it2, expectedChanges2);

        ctxC.cancel();
    }

    public void testBatch() throws Exception {
        Database db = createDatabase(createService());
        Collection collection = createCollection(db);
        assertThat(sync(InputChannels.asList(collection.scan(ctx, RowRange.prefix(""))))).isEmpty();

        BatchDatabase batchFoo = sync(db.beginBatch(ctx, null));
        Collection batchFooCollection = batchFoo.getCollection(COLLECTION_ID);
        assertThat(sync(batchFooCollection.exists(ctx))).isTrue();
        sync(batchFooCollection.put(ctx, ROW_NAME, "foo"));
        // Assert that value is visible inside the batch but not outside.
        assertThat(sync(batchFooCollection.get(ctx, ROW_NAME, String.class))).isEqualTo("foo");
        assertThat(sync(collection.getRow(ROW_NAME).exists(ctx))).isFalse();

        BatchDatabase batchBar = sync(db.beginBatch(ctx, null));
        Collection batchBarCollection = batchBar.getCollection(COLLECTION_ID);
        assertThat(sync(batchBarCollection.exists(ctx))).isTrue();
        sync(batchBarCollection.put(ctx, ROW_NAME, "foo"));
        // Assert that value is visible inside the batch but not outside.
        assertThat(sync(batchBarCollection.get(ctx, ROW_NAME, String.class))).isEqualTo("foo");
        assertThat(sync(collection.getRow(ROW_NAME).exists(ctx))).isFalse();

        sync(batchFoo.commit(ctx));
        // Assert that the value is visible outside the batch.
        assertThat(sync(collection.get(ctx, ROW_NAME, String.class))).isEqualTo("foo");

        try {
            sync(batchBar.commit(ctx));
            fail("Expected batchBar.commit() to fail");
        } catch (VException e) {
            // ok
        }
    }

    public void testRunInBatch() throws Exception {
        final Database d = createDatabase(createService());
        Collection collection = createCollection(d);

        sync(Batch.runInBatch(ctx, d, new BatchOptions(), new Batch.BatchOperation() {
            private int retries = 0;

            @Override
            public ListenableFuture<Void> run(BatchDatabase b) {
                ++retries;
                String fooKey = String.format("foo-%d", retries);
                String barKey = String.format("bar-%d", retries);
                try {
                    // Read foo. It does not exist.
                    try {
                        sync(b.getCollection(COLLECTION_ID).get(ctx, fooKey, String.class));
                        throw new VException("Expected b.get() to fail with NoExistException");
                    } catch (NoExistException e) {
                        // ok
                    }
                    // If we need to fail the commit, write to foo in a separate concurrent batch.
                    if (retries < 2) {
                        sync(d.getCollection(COLLECTION_ID).put(ctx, fooKey, "foo"));
                    }
                    // Write to bar.
                    sync(b.getCollection(COLLECTION_ID).put(ctx, barKey, "bar"));
                } catch (VException e) {
                    return Futures.immediateFailedFuture(e);
                }
                return Futures.immediateFuture(null);
            }
        }));

        // First try failed (wrote foo), second succeeded (bar commit succeeded).
        assertThat(sync(InputChannels.asList(collection.scan(ctx, RowRange.prefix("")))))
                .containsExactly(
                        new KeyValue("bar-2", (VdlAny) VomUtil.decode(VomUtil.encode("bar", String.class), VdlAny.class)),
                        new KeyValue("foo-1", (VdlAny) VomUtil.decode(VomUtil.encode("foo", String.class), VdlAny.class)));
    }

    public void testRunInBatchReadOnly() throws Exception {
        final Database d = createDatabase(createService());
        Collection collection = createCollection(d);
        sync(collection.put(ctx, "foo", "foo"));

        sync(Batch.runInBatch(ctx, d, new BatchOptions("", true), new Batch.BatchOperation() {
            @Override
            public ListenableFuture<Void> run(BatchDatabase b) {
                try {
                    // Read foo.
                    Object before = sync(b.getCollection(COLLECTION_ID).get(ctx, "foo", String.class));
                    // Write to foo in a separate concurrent batch. It should not cause a retry
                    // since readonly batches are not committed.
                    sync(d.getCollection(COLLECTION_ID).put(ctx, "foo", "oof"));
                    // Read foo again. Batch should not see the changed value.
                    Object after = sync(b.getCollection(COLLECTION_ID).get(ctx, "foo", String.class));
                    if (!before.equals(after)) {
                        throw new VException("batch should not see concurrently changed value");
                    }
                    // Try writing to bar. This should fail since the batch is readonly.
                    try {
                        sync(b.getCollection(COLLECTION_ID).put(ctx, "bar", "bar"));
                        throw new VException("Expected b.put() to fail with ReadOnlyBatchException");
                    } catch (ReadOnlyBatchException e) {
                        // ok
                    }
                } catch (VException e) {
                    return Futures.immediateFailedFuture(e);
                }
                return Futures.immediateFuture(null);
            }
        }));

        // Single uncommitted iteration.
        assertThat(sync(InputChannels.asList(collection.scan(ctx, RowRange.prefix("")))))
                .containsExactly(
                        new KeyValue("foo", (VdlAny) VomUtil.decode(VomUtil.encode("oof", String.class), VdlAny.class)));
    }

    public void testSyncgroup() throws Exception {
        Database db = createDatabase(createService());
        Collection collection = createCollection(db);
        Id syncgroupId = new Id(COLLECTION_ID.getBlessing(), "test");

        // "A" creates the group.
        SyncgroupSpec spec = new SyncgroupSpec("test", "", allowAllSg,
                ImmutableList.of(COLLECTION_ID),
                ImmutableList.<String>of(), false);
        SyncgroupMemberInfo memberInfo = new SyncgroupMemberInfo();
        memberInfo.setSyncPriority((byte) 1);
        Syncgroup group = db.getSyncgroup(syncgroupId);
        {
            sync(group.create(ctx, spec, memberInfo));
            assertThat(sync(db.listSyncgroups(ctx))).containsExactly(syncgroupId);
            assertThat(sync(group.getSpec(ctx)).values()).containsExactly(spec);
            assertThat(sync(group.getMembers(ctx)).values()).containsExactly(memberInfo);
            assertThat(sync(group.join(ctx, serverEndpoint.name(), null, memberInfo))).isEqualTo(spec);
        }
        // TODO(spetrovic): test leave() and destroy().

        SyncgroupSpec specRMW = new SyncgroupSpec("testRMW", "", allowAllSg,
                ImmutableList.of(COLLECTION_ID),
                ImmutableList.<String>of(), false);
        assertThat(sync(group.getSpec(ctx)).keySet()).isNotEmpty();
        String version = sync(group.getSpec(ctx)).keySet().iterator().next();
        sync(group.setSpec(ctx, specRMW, version));
        assertThat(sync(group.getSpec(ctx)).values()).containsExactly(specRMW);

        SyncgroupSpec specOverwrite = new SyncgroupSpec("testOverwrite", "", allowAllSg,
                ImmutableList.of(COLLECTION_ID),
                ImmutableList.<String>of(), false);
        sync(group.setSpec(ctx, specOverwrite, ""));
        assertThat(sync(group.getSpec(ctx)).values()).containsExactly(specOverwrite);
    }

    // TODO(spetrovic): Test Database.enforceSchema().

    public void testBlobSmall() throws Exception {
        byte[] data = new byte[]{1, 2, 3, 4, 5};
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        OutputStream out = writer.stream(ctx);
        out.write(data);
        out.close();
        assertThat(sync(writer.size(ctx))).isEqualTo(data.length);
        sync(writer.commit(ctx));
        BlobRef ref = writer.getRef();

        BlobReader reader = db.readBlob(ctx, ref);
        byte[] actual = new byte[data.length];
        ByteStreams.readFully(reader.stream(ctx, 0), actual);
        assertThat(actual).isEqualTo(data);
    }

    public void testBlobLarge() throws Exception {
        byte[] data = new byte[1 << 17];
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) (i & 0xFF);
        }
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        OutputStream out = writer.stream(ctx);
        out.write(data);
        out.close();
        assertThat(sync(writer.size(ctx))).isEqualTo(data.length);
        sync(writer.commit(ctx));
        BlobRef ref = writer.getRef();

        BlobReader reader = db.readBlob(ctx, ref);
        byte[] actual = new byte[data.length];
        ByteStreams.readFully(reader.stream(ctx, 0), actual);
        assertThat(actual).isEqualTo(data);
    }

    public void testBlobWriteResume() throws Exception {
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        BlobRef ref = writer.getRef();
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        {
            // Write, part 1.
            OutputStream out = writer.stream(ctx);
            out.write(data, 0, data.length / 2);
            out.close();
            assertThat(sync(writer.size(ctx))).isEqualTo(data.length / 2);
        }
        {
            // Write, part 2.
            writer = sync(db.writeBlob(ctx, ref));
            assertThat(sync(writer.size(ctx))).isEqualTo(5);
            OutputStream out = writer.stream(ctx);
            out.write(data, data.length / 2, data.length / 2);
            out.close();
            assertThat(sync(writer.size(ctx))).isEqualTo(data.length);
            sync(writer.commit(ctx));
        }
        // Read.
        BlobReader reader = db.readBlob(ctx, ref);
        byte[] actual = new byte[data.length];
        ByteStreams.readFully(reader.stream(ctx, 0), actual);
        assertThat(actual).isEqualTo(data);
    }

    public void testBlobWriteCommitted() throws Exception {
        byte[] data = new byte[]{1, 2, 3, 4, 5};
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        BlobRef ref = writer.getRef();
        OutputStream out = writer.stream(ctx);
        out.write(data);
        out.close();
        assertThat(sync(writer.size(ctx))).isEqualTo(data.length);
        sync(writer.commit(ctx));

        try {
            out = writer.stream(ctx);
            out.write(data);
            out.close();
            fail("write of a committed blob should fail");
        } catch (Exception e) {
            // OK
        }
        try {
            sync(writer.commit(ctx));
            fail("commit of a committed blob should fail");
        } catch (VException e) {
            // OK
        }

        BlobReader reader = db.readBlob(ctx, ref);
        byte[] actual = new byte[data.length];
        ByteStreams.readFully(reader.stream(ctx, 0), actual);
        assertThat(actual).isEqualTo(data);
    }

    public void testBlobWriteCancelable() throws Exception {
        Database db = createDatabase(createService());
        VContext ctxC = ctx.withCancel();
        BlobWriter writer = sync(db.writeBlob(ctxC, null));
        BlobRef ref = writer.getRef();
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Write 1st chunk.
        OutputStream out = writer.stream(ctxC);
        out.write(data, 0, data.length / 2);
        ctxC.cancel();
        // Write 2nd chunk.
        try {
            out.write(data, data.length / 2, data.length / 2);
            out.close();
            fail("write on a canceled stream should fail");
        } catch (IOException e) {
            // OK
        }
    }

    public void testBlobReadUncommitted() throws Exception {
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        BlobRef ref = writer.getRef();
        byte[] data = new byte[]{1, 2, 3, 4, 5};
        OutputStream out = writer.stream(ctx);
        out.write(data, 0, data.length);
        out.close();

        BlobReader reader = db.readBlob(ctx, ref);
        try {
            byte[] actual = new byte[data.length];
            ByteStreams.readFully(reader.stream(ctx, 0), actual);
            fail("read of an uncommitted blob should fail");
        } catch (IOException e) {
            // OK
        }
        try {
            sync(reader.prefetch(ctx, 0).recv());
        } catch (VException e) {
            // OK
        }
    }

    public void testBlobReadPrefetch() throws Exception {
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        BlobRef ref = writer.getRef();
        byte[] data = new byte[]{1, 2, 3, 4, 5};
        OutputStream out = writer.stream(ctx);
        out.write(data, 0, data.length);
        out.close();
        sync(writer.commit(ctx));

        // Prefetch
        BlobReader reader = db.readBlob(ctx, ref);
        sync(InputChannels.asDone(reader.prefetch(ctx, 0)));
        // Read
        byte[] actual = new byte[data.length];
        ByteStreams.readFully(reader.stream(ctx, 0), actual);
        assertThat(actual).isEqualTo(data);
    }

    public void testBlobReadClosedStream() throws Exception {
        byte[] data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Database db = createDatabase(createService());
        BlobWriter writer = sync(db.writeBlob(ctx, null));
        OutputStream out = writer.stream(ctx);
        out.write(data);
        out.close();
        assertThat(sync(writer.size(ctx))).isEqualTo(data.length);
        sync(writer.commit(ctx));
        BlobRef ref = writer.getRef();

        BlobReader reader = db.readBlob(ctx, ref);
        byte[] actual = new byte[data.length / 2];
        InputStream in = reader.stream(ctx, 0);
        // Read 1st chunk.
        ByteStreams.readFully(in, actual);
        assertThat(actual).isEqualTo(new byte[]{1, 2, 3, 4, 5});
        // Close the input stream.
        in.close();
        // Read 2nd chunk.
        try {
            ByteStreams.readFully(in, actual);
            fail("read of a closed stream should fail");
        } catch (IOException e) {
            // OK
        }
    }


    private VContext forkContext(VContext rootCtx, String extension) throws VException {
        VPrincipal rootPrincipal = V.getPrincipal(rootCtx);
        VPrincipal principal = VSecurity.newPrincipal();
        Blessings blessings = rootPrincipal.bless(principal.publicKey(),
                rootPrincipal.blessingStore().defaultBlessings(), extension,
                VSecurity.newUnconstrainedUseCaveat());
        principal.blessingStore().setDefaultBlessings(blessings);
        principal.blessingStore().set(blessings, new BlessingPattern("..."));
        principal.roots().add(rootPrincipal.publicKey(), new BlessingPattern("jroot"));
        return V.withPrincipal(rootCtx, principal);
    }

    private SyncbaseService createService() throws Exception {
        return Syncbase.newService(serverEndpoint.name());
    }


    private Database createDatabase(SyncbaseService service) throws Exception {
        Database db = service.getDatabase(DB_ID, null);
        sync(db.create(ctx, allowAllDb));
        return db;
    }

    private Collection createCollection(Database db) throws Exception {
        Collection collection = db.getCollection(COLLECTION_ID);
        sync(collection.create(ctx, allowAllCx));
        return collection;
    }

    private void checkWatch(Iterator<WatchChange> it,
                            List<WatchChange> expectedChanges) throws Exception {
        for (WatchChange expected : expectedChanges) {
            assertThat(it.hasNext()).isTrue();
            WatchChange actual = it.next();
            assertThat(actual.getCollectionId()).isEqualTo(expected.getCollectionId());
            assertThat(actual.getRowName()).isEqualTo(expected.getRowName());
            assertThat(actual.getChangeType()).isEqualTo(expected.getChangeType());
            assertThat(actual.getValue()).isEqualTo(expected.getValue());
            assertThat(actual.isFromSync()).isEqualTo(expected.isFromSync());
            assertThat(actual.isContinued()).isEqualTo(expected.isContinued());
        }
    }

    private static class Foo implements Serializable {
        private int i;
        private String s;

        public Foo() {
            this.i = 0;
            this.s = "";
        }

        public Foo(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Foo foo = (Foo) o;

            if (i != foo.i) return false;
            return !(s != null ? !s.equals(foo.s) : foo.s != null);

        }
    }

    private static class Bar implements Serializable {
        private float f;
        private String s;

        public Bar() {
            this.f = 0f;
            this.s = "";
        }

        public Bar(float f, String s) {
            this.f = f;
            this.s = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Bar bar = (Bar) o;

            if (Float.compare(bar.f, f) != 0) return false;
            return !(s != null ? !s.equals(bar.s) : bar.s != null);

        }
    }

    private static class Baz implements Serializable {
        private String name;
        private boolean active;

        public Baz() {
            this.name = "";
            this.active = false;
        }

        public Baz(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Baz baz = (Baz) o;

            if (active != baz.active) return false;
            return !(name != null ? !name.equals(baz.name) : baz.name != null);

        }
    }
}
