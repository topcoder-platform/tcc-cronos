/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for PersistenceException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class PersistenceExceptionTest extends TestCase {
    /**
     * Test ctor PersistenceException(String) with string message and test whether it can be obtained later.
     */
    public void testPersistenceExceptionString() {
        String msg = "msg";
        PersistenceException e = new PersistenceException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test PersistenceException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testPersistenceExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        PersistenceException e = new PersistenceException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
