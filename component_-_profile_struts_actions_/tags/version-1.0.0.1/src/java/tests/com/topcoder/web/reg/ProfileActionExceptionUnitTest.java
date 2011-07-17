/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import junit.framework.TestCase;

/**
 * <p>
 * This class contains Unit tests for ProfileActionException.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProfileActionExceptionUnitTest extends TestCase {

    /**
     * <p>
     * Represents ProfileActionException instance for testing.
     * </p>
     */
    private ProfileActionException exception;

    /**
     * <p>
     * Represents error message for testing.
     * </p>
     */
    private String message;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     */
    public void setUp() {
        message = "just for testing";
        exception = new ProfileActionException();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     */
    public void tearDown() {
        exception = null;
        message = null;
    }

    /**
     * <p>
     * Tests exception default constructor.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor() {
        exception = new ProfileActionException(message);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
    }

    /**
     * <p>
     * Tests exception constructor with passed message.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_Message() {
        exception = new ProfileActionException(message);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
    }

    /**
     * <p>
     * Tests exception constructor with passed cause.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_Cause() {
        Throwable cause = new NullPointerException(message);
        exception = new ProfileActionException(cause);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Cause should be set successfully.", cause, exception.getCause());
    }

    /**
     * <p>
     * Tests exception constructor with passed message and cause.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_MessageCause() {
        Throwable cause = new NullPointerException(message);
        exception = new ProfileActionException(message, cause);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
        assertEquals("Cause should be set successfully.", cause, exception.getCause());
    }
}
