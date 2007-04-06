/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import junit.framework.TestCase;


/**
 * UnitTest for <code>NotificationPersistenceException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class NotificationPersistenceExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>NotificationPersistenceException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testNotificationPersistenceExceptionAccuracy1()
        throws Exception {
        NotificationPersistenceException e = new NotificationPersistenceException("msg");
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
        NotificationPersistenceException e = new NotificationPersistenceException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
