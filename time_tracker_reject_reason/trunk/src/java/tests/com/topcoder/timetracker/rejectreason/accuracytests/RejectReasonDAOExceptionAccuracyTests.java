/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectReasonDAOException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectReasonDAOExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectReasonDAOException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectReasonDAOExceptionAccuracy1()
        throws Exception {
        RejectReasonDAOException e = new RejectReasonDAOException("msg");
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
        RejectReasonDAOException e = new RejectReasonDAOException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
