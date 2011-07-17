/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This class contains Unit tests for ProfileCompletenessRetrievalException.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProfileCompletenessRetrievalExceptionUnitTest extends TestCase {

    /**
     * <p>
     * Represents ProfileCompletenessRetrievalException instance for testing.
     * </p>
     */
    private ProfileCompletenessRetrievalException exception;

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
        exception = new ProfileCompletenessRetrievalException(message);
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
     * Tests exception constructor with passed message.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_Message() {
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
    }

    /**
     * <p>
     * Tests exception constructor with passed message and data.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_MessageData() {
        ExceptionData data = new ExceptionData();
        exception = new ProfileCompletenessRetrievalException(message, data);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
    }

    /**
     * <p>
     * Tests exception constructor with passed message, cause and data.
     * </p>
     * <p>
     * Exception should be created successfully.
     * </p>
     */
    public void testCtor_MessageDataCause() {
        ExceptionData data = new ExceptionData();
        Throwable cause = new NullPointerException(message);
        exception = new ProfileCompletenessRetrievalException(message, cause, data);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
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
        exception = new ProfileCompletenessRetrievalException(message, cause);
        assertNotNull("Exception should be created successfully.", exception);
        assertEquals("Message should be set successfully.", message, exception.getMessage());
        assertEquals("Cause should be set successfully.", cause, exception.getCause());
    }
}
