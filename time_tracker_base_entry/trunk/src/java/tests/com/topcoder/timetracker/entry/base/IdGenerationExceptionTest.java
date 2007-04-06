/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import junit.framework.TestCase;


/**
 * Test case for IdGenerationException.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class IdGenerationExceptionTest extends TestCase {
    /**
     * Test ctor IdGenerationException(String) with string message and test whether it can be obtained later.
     */
    public void testIdGenerationExceptionString() {
        String msg = "msg";
        IdGenerationException e = new IdGenerationException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * Test IdGenerationException(String, Throwable) with msg and cause, and obtain them later.
     */
    public void testIdGenerationExceptionStringThrowable() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        IdGenerationException e = new IdGenerationException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
