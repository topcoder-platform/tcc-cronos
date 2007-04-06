/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.timetracker.user.ConfigurationException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for ConfigurationException.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ConfigurationExceptionAccuracyTests extends TestCase {

    /**
     * <p>
     * An exception instance used to create the ConfigurationException.
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
        return new TestSuite(ConfigurationExceptionAccuracyTests.class);
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
    public void testConfigurationException1() {
        ConfigurationException exception = new ConfigurationException("message");
        assertNotNull("Unable to instantiate ConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", "message", exception.getMessage());
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
    public void testConfigurationException2() {
        ConfigurationException exception = new ConfigurationException("message", CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate ConfigurationException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf("message") >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

}