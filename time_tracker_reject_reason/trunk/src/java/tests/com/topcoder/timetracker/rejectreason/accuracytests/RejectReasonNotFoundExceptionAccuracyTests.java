/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.timetracker.rejectreason.RejectReasonNotFoundException;

import junit.framework.TestCase;


/**
 * UnitTest for <code>RejectReasonNotFoundException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectReasonNotFoundExceptionAccuracyTests extends TestCase {
    /**
     * Accuracy test of <code>RejectReasonNotFoundException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRejectReasonNotFoundExceptionAccuracy1()
        throws Exception {
        RejectReasonNotFoundException e = new RejectReasonNotFoundException("msg");
        assertEquals("message is incorrect.", "msg", e.getMessage());
    }

    /**
     * Accuracy test of <code>RejectReasonNotFoundException(String msg, Throwable cause)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testNotificationConfigurationAccuracy2()
        throws Exception {
        Exception cause = new Exception("cause");
        RejectReasonNotFoundException e = new RejectReasonNotFoundException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
