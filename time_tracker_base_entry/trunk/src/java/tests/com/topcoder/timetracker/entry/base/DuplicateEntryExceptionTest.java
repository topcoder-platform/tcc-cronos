/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for DuplicateEntryException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DuplicateEntryExceptionTest extends TestCase {
    /**
     * Tests {@link DuplicateEntryException#DuplicateEntryException(String)} with given message, msg should be
     * obtained later, id should be not set.
     */
    public void testDuplicateEntryExceptionString() {
        String msg = "msg";
        DuplicateEntryException e = new DuplicateEntryException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("entityId should be not set", -1, e.getEntityId());
    }

    /**
     * Tests {@link DuplicateEntryException#DuplicateEntryException(String, long)} with given message and id, msg
     * and id should be obtained later.
     */
    public void testDuplicateEntryExceptionStringLong() {
        String msg = "msg";
        long id = 123;
        DuplicateEntryException e = new DuplicateEntryException(msg, id);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("entityId should be", id, e.getEntityId());
    }

    /**
     * Tests {@link DuplicateEntryException#DuplicateEntryException(String, Throwable)} with given message and
     * cause, msg and cause should be obtained later, id should be not set.
     */
    public void testDuplicateEntryExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        DuplicateEntryException e = new DuplicateEntryException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
        assertEquals("entityId should be not set", -1, e.getEntityId());
    }

    /**
     * Tests {@link DuplicateEntryException#DuplicateEntryException(String, Throwable, long)} with given
     * message,cause and id. msg,cause and id should be obtained later.
     */
    public void testDuplicateEntryExceptionStringThrowableLong() {
        String msg = "msg";
        long id = 123;
        Throwable t = new RuntimeException();
        DuplicateEntryException e = new DuplicateEntryException(msg, t, id);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
        assertEquals("entityId should be", id, e.getEntityId());
    }

    /**
     * Tests {@link DuplicateEntryException#getEntityId()}. Id should be obtained by this method.
     */
    public void testGetEntityId() {
        String msg = "msg";
        long id = 123;
        Throwable t = new RuntimeException();
        DuplicateEntryException e = new DuplicateEntryException(msg, t, id);
        assertEquals("entityId should be", id, e.getEntityId());
    }
}
