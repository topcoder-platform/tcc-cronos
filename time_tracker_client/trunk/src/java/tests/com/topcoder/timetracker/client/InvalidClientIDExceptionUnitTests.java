/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>InvalidClientIDException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InvalidClientIDExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>InvalidClientIDException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testInvalidClientIDExceptionAccuracy1()
        throws Exception {
        InvalidClientIDException e = new InvalidClientIDException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>InvalidClientIDException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testInvalidClientIDExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        InvalidClientIDException e = new InvalidClientIDException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
