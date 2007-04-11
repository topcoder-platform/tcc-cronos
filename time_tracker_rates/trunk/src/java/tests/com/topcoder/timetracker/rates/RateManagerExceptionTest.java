/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import junit.framework.TestCase;


/**
 * Test case for RateManagerException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RateManagerExceptionTest extends TestCase {
    /**
     * Test ctor RateManagerException(String) with string message and test whether it can be obtained later.
     */
    public void testRateManagerExceptionString() {
        String msg = "msg";
        RateManagerException e = new RateManagerException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test RateManagerException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testRateManagerExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        RateManagerException e = new RateManagerException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
