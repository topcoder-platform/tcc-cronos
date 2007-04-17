/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import junit.framework.TestCase;


/**
 * UnitTest for <code>ClientPersistenceException</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ClientPersistenceExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>ClientPersistenceException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientPersistenceExceptionAccuracy1()
        throws Exception {
        ClientPersistenceException e = new ClientPersistenceException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>ClientPersistenceException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testClientPersistenceExceptionAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        ClientPersistenceException e = new ClientPersistenceException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
