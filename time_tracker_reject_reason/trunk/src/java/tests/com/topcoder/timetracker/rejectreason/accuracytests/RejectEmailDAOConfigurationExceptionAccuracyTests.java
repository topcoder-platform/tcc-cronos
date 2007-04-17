/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOConfigurationException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectEmailDAOConfigurationException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailDAOConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectEmailDAOConfigurationException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectEmailDAOConfigurationExceptionAccuracy1()
        throws Exception {
        RejectEmailDAOConfigurationException e = new RejectEmailDAOConfigurationException("msg");
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
        RejectEmailDAOConfigurationException e = new RejectEmailDAOConfigurationException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
