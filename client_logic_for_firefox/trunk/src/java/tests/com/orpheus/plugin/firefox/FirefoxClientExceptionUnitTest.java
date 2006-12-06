/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>FirefoxClientException</code>.
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
public class FirefoxClientExceptionUnitTest extends TestCase {
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
    public void testFirefoxClientException1() {
        FirefoxClientException ce = new FirefoxClientException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate FirefoxClientException.", ce);
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
    public void testFirefoxClientException2() {
        Exception cause = new Exception();
        FirefoxClientException ce = new FirefoxClientException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate FirefoxClientException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxClientException subclasses BaseException.
     * </p>
     */
    public void testFirefoxClientExceptionInheritance1() {
        assertTrue("FirefoxClientException does not subclass BaseException.",
            new FirefoxClientException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies FirefoxClientException subclasses BaseException.
     * </p>
     */
    public void testFirefoxClientExceptionInheritance2() {
        assertTrue("FirefoxClientException does not subclass BaseException.",
            new FirefoxClientException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}
