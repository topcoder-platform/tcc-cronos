/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>FirefoxExtensionConfigurationException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionConfigurationExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testFirefoxExtensionConfigurationException1() {
        FirefoxExtensionConfigurationException ce = new FirefoxExtensionConfigurationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate FirefoxExtensionConfigurationException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
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
    public void testFirefoxExtensionConfigurationException2() {
        Exception cause = new Exception();
        FirefoxExtensionConfigurationException ce = new FirefoxExtensionConfigurationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate FirefoxExtensionConfigurationException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxExtensionConfigurationException subclasses FirefoxClientException.
     * </p>
     */
    public void testFirefoxExtensionConfigurationExceptionInheritance1() {
        assertTrue("FirefoxExtensionConfigurationException does not subclass FirefoxClientException.",
            new FirefoxExtensionConfigurationException(ERROR_MESSAGE) instanceof FirefoxClientException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxExtensionConfigurationException subclasses FirefoxClientException.
     * </p>
     */
    public void testFirefoxExtensionConfigurationExceptionInheritance2() {
        assertTrue("FirefoxExtensionConfigurationException does not subclass FirefoxClientException.",
            new FirefoxExtensionConfigurationException(ERROR_MESSAGE, new Exception())
            instanceof FirefoxClientException);
    }
}
