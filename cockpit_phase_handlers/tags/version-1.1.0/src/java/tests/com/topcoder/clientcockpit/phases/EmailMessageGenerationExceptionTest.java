/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the functionality of <code>{@link EmailMessageGenerationException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EmailMessageGenerationExceptionTest extends TestCase {

    /**
     * <p>
     * Accuracy test of
     * <code>{@link EmailMessageGenerationException#
     * EmailMessageGenerationException(String message)}</code> constructor.
     * Creates an instance and get its attributes for test.
     * </p>
     */
    public void testEmailMessageGenerationException_accuracy_1() {
        String msg = "msg";
        EmailMessageGenerationException e = new EmailMessageGenerationException(msg);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
    }

    /**
     * <p>
     * Accuracy test of
     * <code>{@link EmailMessageGenerationException#
     * EmailMessageGenerationException(String message, Throwable cause)}</code>
     * constructor. Creates an instance and get its attributes for test.
     * </p>
     */
    public void testEmailMessageGenerationException_accuracy_2() {
        String msg = "msg";
        Throwable t = new RuntimeException();
        EmailMessageGenerationException e = new EmailMessageGenerationException(msg, t);
        assertTrue("msg should be set and obtained properly", e.getMessage().indexOf(msg) == 0);
        assertEquals("throwable should be set and obtained properly", t, e.getCause());
    }
}
