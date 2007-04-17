/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectEmailDAOException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailDAOExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectEmailDAOException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectEmailDAOExceptionAccuracy1()
        throws Exception {
        RejectEmailDAOException e = new RejectEmailDAOException("msg");
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
        RejectEmailDAOException e = new RejectEmailDAOException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
