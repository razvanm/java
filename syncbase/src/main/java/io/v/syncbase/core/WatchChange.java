// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.syncbase.core;

public class WatchChange {
    public enum EntityType { ROOT, COLLECTION, ROW }
    public enum ChangeType { PUT, DELETE }

    public EntityType entityType;
    public Id collection;
    public String row;
    public ChangeType changeType;
    public byte[] value;
    public byte[] resumeMarker;
    public boolean fromSync;
    public boolean continued;
}