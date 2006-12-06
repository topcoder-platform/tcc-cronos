/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>FirefoxExtensionPersistenceException</code>.
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
public class FirefoxExtensionPersistenceExceptionUnitTest extends TestCase {
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
    public void testFirefoxExtensionPersistenceException1() {
        FirefoxExtensionPersistenceException ce = new FirefoxExtensionPersistenceException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate FirefoxExtensionPersistenceException.", ce);
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
    public void testFirefoxExtensionPersistenceException2() {
        Exception cause = new Exception();
        FirefoxExtensionPersistenceException ce = new FirefoxExtensionPersistenceException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate FirefoxExtensionPersistenceException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxExtensionPersistenceException subclasses FirefoxClientException.
     * </p>
     */
    public void testFirefoxExtensionPersistenceExceptionInheritance1() {
        assertTrue("FirefoxExtensionPersistenceException does not subclass FirefoxClientException.",
            new FirefoxExtensionPersistenceException(ERROR_MESSAGE) instanceof FirefoxClientException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxExtensionPersistenceException subclasses FirefoxClientException.
     * </p>
     */
    public void testFirefoxExtensionPersistenceExceptionInheritance2() {
        assertTrue("FirefoxExtensionPersistenceException does not subclass FirefoxClientException.",
            new FirefoxExtensionPersistenceException(ERROR_MESSAGE, new Exception()) instanceof FirefoxClientException);
    }
}
