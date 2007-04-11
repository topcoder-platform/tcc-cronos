/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import junit.framework.TestCase;


/**
 * Test case for RateConfigurationException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RateConfigurationExceptionTest extends TestCase {
    /**
     * Test ctor RateConfigurationException(String) with string message and test whether it can be obtained
     * later.
     */
    public void testRateConfigurationExceptionString() {
        String msg = "msg";
        RateConfigurationException e = new RateConfigurationException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test RateConfigurationException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testRateConfigurationExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        RateConfigurationException e = new RateConfigurationException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
