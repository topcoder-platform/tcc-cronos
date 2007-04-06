/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for EntryManagerException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntryManagerExceptionTest extends TestCase {
    /**
     * Test ctor EntryManagerException(String) with string message and test whether it can be obtained later.
     */
    public void testEntryManagerExceptionString() {
        String msg = "msg";
        EntryManagerException e = new EntryManagerException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test EntryManagerException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testEntryManagerExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        EntryManagerException e = new EntryManagerException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
