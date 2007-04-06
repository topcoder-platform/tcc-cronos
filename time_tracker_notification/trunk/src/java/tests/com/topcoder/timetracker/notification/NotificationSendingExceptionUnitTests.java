/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import junit.framework.TestCase;


/**
 * UnitTest for <code>NotificationSendingException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationSendingExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>NotificationSendingException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testNotificationSendingExceptionAccuracy1()
        throws Exception {
        NotificationSendingException e = new NotificationSendingException("msg");
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
        NotificationSendingException e = new NotificationSendingException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
