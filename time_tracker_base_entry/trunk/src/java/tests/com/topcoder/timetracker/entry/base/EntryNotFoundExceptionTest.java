/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for EntryNotFoundException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntryNotFoundExceptionTest extends TestCase {
    /**
     * Tests {@link EntryNotFoundException#EntryNotFoundException(String)} with given message, msg should be
     * obtained later, id should be not set.
     */
    public void testEntryNotFoundExceptionString() {
        String msg = "msg";
        EntryNotFoundException e = new EntryNotFoundException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("entityId should be not set", -1, e.getEntityId());
    }

    /**
     * Tests {@link EntryNotFoundException#EntryNotFoundException(String, long)} with given message and id, msg
     * and id should be obtained later.
     */
    public void testEntryNotFoundExceptionStringLong() {
        String msg = "msg";
        long id = 123;
        EntryNotFoundException e = new EntryNotFoundException(msg, id);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("entityId should be", id, e.getEntityId());
    }

    /**
     * Tests {@link EntryNotFoundException#EntryNotFoundException(String, Throwable)} with given message and
     * cause, msg and cause should be obtained later, id should be not set.
     */
    public void testEntryNotFoundExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        EntryNotFoundException e = new EntryNotFoundException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
        assertEquals("entityId should be not set", -1, e.getEntityId());
    }

    /**
     * Tests {@link EntryNotFoundException#EntryNotFoundException(String, Throwable, long)} with given
     * message,cause and id. msg,cause and id should be obtained later.
     */
    public void testEntryNotFoundExceptionStringThrowableLong() {
        String msg = "msg";
        long id = 123;
        Throwable t = new RuntimeException();
        EntryNotFoundException e = new EntryNotFoundException(msg, t, id);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
        assertEquals("entityId should be", id, e.getEntityId());
    }

    /**
     * Tests {@link EntryNotFoundException#getEntityId()}. Id should be obtained by this method.
     */
    public void testGetEntityId() {
        String msg = "msg";
        long id = 123;
        Throwable t = new RuntimeException();
        EntryNotFoundException e = new EntryNotFoundException(msg, t, id);
        assertEquals("entityId should be", id, e.getEntityId());
    }
}
