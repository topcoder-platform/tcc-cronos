/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>InvalidClientPropertyException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InvalidClientPropertyExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>InvalidClientPropertyException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testInvalidClientPropertyExceptionAccuracy1()
        throws Exception {
        InvalidClientPropertyException e = new InvalidClientPropertyException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>InvalidClientPropertyException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testInvalidClientPropertyExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        InvalidClientPropertyException e = new InvalidClientPropertyException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
