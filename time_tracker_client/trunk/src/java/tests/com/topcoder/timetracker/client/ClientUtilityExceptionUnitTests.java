/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>ClientUtilityException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ClientUtilityException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientUtilityExceptionAccuracy1()
        throws Exception {
        ClientUtilityException e = new ClientUtilityException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>ClientUtilityException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientUtilityExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        ClientUtilityException e = new ClientUtilityException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
