/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.accuracytests;

import com.topcoder.timetracker.notification.NotificationSendingException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for NotificationSendingException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class NotificationSendingExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the NotificationSendingException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(NotificationSendingExceptionAccuracyTests.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testNotificationSendingException1() {
        NotificationSendingException exception = new NotificationSendingException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate NotificationSendingException.", exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testNotificationSendingException2() {
        NotificationSendingException exception = new NotificationSendingException(ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate NotificationSendingException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies NotificationSendingException subclasses Exception.
     * </p>
     */
    public void testNotificationSendingExceptionInheritance1() {
        NotificationSendingException exception = new NotificationSendingException(ERROR_MESSAGE);
        assertTrue("NotificationSendingException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies NotificationSendingException subclasses Exception.
     * </p>
     */
    public void testNotificationSendingExceptionInheritance2() {
        NotificationSendingException exception = new NotificationSendingException(ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("NotificationSendingException does not subclass Exception.", exception instanceof Exception);
    }
}