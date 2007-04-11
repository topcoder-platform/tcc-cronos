/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.accuracytests;

import junit.framework.TestCase;

import com.cronos.im.persistence.ConfigurationException;

/**
 * <p>
 * Accuracy test for <code>{@link ConfigurationExceptionAccuracyTests}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Tests accuracy of <code>ConfigurationException(String)</code> constructor. The detail error message should be
     * correct.
     */
    public void testConfigurationExceptionStringAccuracy() {
        // Construct ConfigurationException with a detail message
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests accuracy of <code>ConfigurationException(String, Throwable)</code> constructor. The detail error message
     * and the original cause of error should be correct.
     */
    public void testConfigurationExceptionStringThrowableAccuracy() {
        // Construct ConfigurationException with a detail message and a cause
        ConfigurationException exception = new ConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }
}
