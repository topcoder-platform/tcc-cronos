/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test case of {@link ConfigurationException}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";
    /**
     * <p>
     * The exception data used for testing.
     * </p>
     */
    private static final ExceptionData EXCEPTIONDATA = new ExceptionData();
    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Test method for {@link ConfigurationException#ConfigurationException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testConfigurationExceptionString() {
        ConfigurationException exception = new ConfigurationException(MESSAGE);
        assertNotNull("Unable to instantiate ConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for {@link ConfigurationException#ConfigurationException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testConfigurationExceptionStringThrowable() {
        ConfigurationException exception = new ConfigurationException(MESSAGE, CAUSE);
        assertNotNull("Unable to instantiate ConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Test method for {@link ConfigurationException#ConfigurationException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testConfigurationExceptionStringExceptionData() {
        ConfigurationException exception = new ConfigurationException(MESSAGE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Test method for {@link ConfigurationException#ConfigurationException()}.
     * It verifies the exception is created successfully.
     * </p>
     */
    public void testConfigurationExceptionStringCauseExceptionData() {
        ConfigurationException exception = new ConfigurationException(MESSAGE, CAUSE, EXCEPTIONDATA);
        assertNotNull("Unable to instantiate ConfigurationException", exception);
        assertSame("message should the same.", MESSAGE, exception.getMessage());
        assertSame("cause should the same.", CAUSE, exception.getCause());
    }
}
