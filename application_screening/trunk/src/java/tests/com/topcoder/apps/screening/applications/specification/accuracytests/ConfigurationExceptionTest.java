/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ConfigurationExceptionTest.java
 */
package com.topcoder.apps.screening.applications.specification.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.ConfigurationException;

/**
 * Set of tests for testing ConfigurationException class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {

    private String message = "message";

    private Exception cause = new Exception();

    /**
     * Creates a test suite for the tests in this test case.
     * 
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }

    /**
     * Accuracy test method for ConfigurationException(Throwable).
     */
    public final void testConfigurationExceptionString() {
        ConfigurationException exception = new ConfigurationException(message);
        // checking that message for correctly retrieved message
        assertTrue(exception.getMessage().indexOf(message) != -1);
    }

    /**
     * Accuracy test method for ConfigurationException(String, Throwable).
     */
    public final void testConfigurationExceptionStringThrowable() {
        ConfigurationException exception = new ConfigurationException(message,
                cause);
        // testing for equal messages
        assertTrue(exception.getMessage().indexOf(message) != -1);
        // testing for cause
        assertEquals(cause, exception.getCause());
    }
}