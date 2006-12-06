/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import com.orpheus.plugin.firefox.FirefoxClientException;
import com.orpheus.plugin.firefox.FirefoxExtensionConfigurationException;
import com.orpheus.plugin.firefox.FirefoxExtensionPersistenceException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the functionality of custom exceptions. All consturctors are tested.
 * 
 * @author visualage
 * @version 1.0
 */
public class ExceptionTestCase extends TestCase {
    /**
     * Represents a throwable cause.
     */
    private static final Exception CAUSE = new Exception();

    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Aggragates all tests in this class.
     * 
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ExceptionTestCase.class);
    }

    /**
     * Tests the accuracy of <code>FirefoxClientException(String)</code>. The detail error message should be correct.
     */
    public void testFirefoxClientExceptionStringAccuracy() {
        // Construct FirefoxClientException with a detail message
        FirefoxClientException exception = new FirefoxClientException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests the accuracy of <code>FirefoxClientException(String, Throwable)</code>. The detail error message and the
     * original cause of error should be correct.
     */
    public void testFirefoxClientExceptionStringThrowableAccuracy() {
        // Construct FirefoxClientException with a detail message and a cause
        FirefoxClientException exception = new FirefoxClientException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * Tests the accuracy of <code>FirefoxExtensionConfigurationException(String)</code>. The detail error message
     * should be correct.
     */
    public void testFirefoxExtensionConfigurationExceptionStringAccuracy() {
        // Construct FirefoxExtensionConfigurationException with a detail message
        FirefoxExtensionConfigurationException exception = new FirefoxExtensionConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests the accuracy of <code>FirefoxExtensionConfigurationException(String, Throwable)</code>. The detail error
     * message and the original cause of error should be correct.
     */
    public void testFirefoxExtensionConfigurationExceptionStringThrowableAccuracy() {
        // Construct FirefoxExtensionConfigurationException with a detail message and a cause
        FirefoxExtensionConfigurationException exception = new FirefoxExtensionConfigurationException(DETAIL_MESSAGE,
            CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }

    /**
     * Tests the accuracy of <code>FirefoxExtensionPersistenceException(String)</code>. The detail error message
     * should be correct.
     */
    public void testFirefoxExtensionPersistenceExceptionStringAccuracy() {
        // Construct FirefoxExtensionPersistenceException with a detail message
        FirefoxExtensionPersistenceException exception = new FirefoxExtensionPersistenceException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * Tests the accuracy of <code>FirefoxExtensionPersistenceException(String, Throwable)</code>. The detail error
     * message and the original cause of error should be correct.
     */
    public void testFirefoxExtensionPersistenceExceptionStringThrowableAccuracy() {
        // Construct FirefoxExtensionPersistenceException with a detail message and a cause
        FirefoxExtensionPersistenceException exception = new FirefoxExtensionPersistenceException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE + ", caused by null",
            exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertTrue("Cause should be identical.", CAUSE == exception.getCause());
    }
}
