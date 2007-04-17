/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.RejectEmailNotFoundException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectEmailNotFoundException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailNotFoundExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectEmailNotFoundException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectEmailNotFoundExceptionAccuracy1()
        throws Exception {
        RejectEmailNotFoundException e = new RejectEmailNotFoundException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>ChatSessionPersistenceException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testNotificationConfigurationAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        RejectEmailNotFoundException e = new RejectEmailNotFoundException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
