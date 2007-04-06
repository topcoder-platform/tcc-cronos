/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.send;

import junit.framework.TestCase;


/**
 * UnitTest for <code>MessageBodyGeneratorException</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class MessageBodyGeneratorExceptionUnitTests extends TestCase {
    /**
     * Accuracy test of <code>MessageBodyGeneratorException(String msg)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testMessageBodyGeneratorExceptionAccuracy1()
        throws Exception {
        MessageBodyGeneratorException e = new MessageBodyGeneratorException("msg");
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
        MessageBodyGeneratorException e = new MessageBodyGeneratorException("msg", cause);
        assertEquals("cause is incorrect.", cause, e.getCause());
        assertEquals("message is incorrect.", "msg, caused by cause", e.getMessage());
    }
}
