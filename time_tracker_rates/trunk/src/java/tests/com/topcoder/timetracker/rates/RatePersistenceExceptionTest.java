/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import junit.framework.TestCase;


/**
 * Test case for RatePersistenceException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RatePersistenceExceptionTest extends TestCase {
    /**
     * Test ctor RatePersistenceException(String) with string message and test whether it can be obtained
     * later.
     */
    public void testRatePersistenceExceptionString() {
        String msg = "msg";
        RatePersistenceException e = new RatePersistenceException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test RatePersistenceException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testRatePersistenceExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        RatePersistenceException e = new RatePersistenceException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
