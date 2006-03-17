/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.ConfigurationException;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Accuracy test for class ConfigurationException.
 * </p>
 * @author fuyun
 * @version 1.0
 */
public class ConfigurationExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * A string with detail message used for test.
     * </p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>
     * A throwable cause used for test.
     * </p>
     */
    private static final Exception CAUSE = new Exception();

    /**
     * <p>
     * Test for ConfigurationException(String message).
     * </p>
     */
    public void testConfigurationExceptionString() {
        // construct ConfigurationException with detail message.
        ConfigurationException ce = new ConfigurationException(DETAIL_MESSAGE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE, ce.getMessage());

        // Verify that the ConfigurationException is subclass of BaseException
        assertTrue("ConfigurationException should be subclass of BaseException",
                ce instanceof BaseException);
        assertFalse(false);
    }

    /**
     * <p>
     * Test for ConfigurationException(String message, Throwable cause).
     * </p>
     */
    public void testConfigurationExceptionStringThrowable() {
        // construct ConfigurationException with detail message.
        ConfigurationException ce = new ConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that the detail message is correctly handled
        assertEquals("Error message should be identical.", DETAIL_MESSAGE + ", caused by null", ce
                .getMessage());

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, ce.getCause());
    }

    /**
     * <p>
     * Test for ConfigurationException(Throwable cause).
     * </p>
     */
    public void testConfigurationExceptionThrowable() {
        // construct ConfigurationException with detail message.
        ConfigurationException ce = new ConfigurationException(CAUSE);

        // Verify that there is a cause
        assertEquals("Cause should be identical", CAUSE, ce.getCause());
    }
}
