/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOConfigurationException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectReasonDAOConfigurationException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectReasonDAOConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectReasonDAOConfigurationException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectReasonDAOConfigurationExceptionAccuracy1()
        throws Exception {
        RejectReasonDAOConfigurationException e = new RejectReasonDAOConfigurationException("msg");
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
        RejectReasonDAOConfigurationException e = new RejectReasonDAOConfigurationException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
