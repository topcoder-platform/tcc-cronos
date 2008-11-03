/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the functionality of <code>{@link EmailSendingException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailSendingExceptionTest extends TestCase {

    /**
     * <p>
     * Accuracy test of
     * <code>{@link EmailSendingException#
     * EmailSendingException(String message)}</code> constructor.
     * Creates an instance and get its attributes for test.
     * </p>
     */
    public void testEmailSendingException_accuracy_1() {
        String msg = "msg";
        EmailSendingException e = new EmailSendingException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link EmailSendingException#
     * EmailSendingException(String message, Throwable cause)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testEmailSendingException_accuracy_2() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        EmailSendingException e = new EmailSendingException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
