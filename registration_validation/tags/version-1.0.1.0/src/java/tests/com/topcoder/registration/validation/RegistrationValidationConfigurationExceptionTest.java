/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.service.RegistrationValidationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for RegistrationValidationConfigurationException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can
 * be instantiated with the error message and cause properly propagated, and
 * that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegistrationValidationConfigurationExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the
     * RegistrationValidationConfigurationException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testRegistrationValidationConfigurationException1() {
        RegistrationValidationConfigurationException exception = new RegistrationValidationConfigurationException(
                ERROR_MESSAGE);
        assertNotNull(
                "Unable to instantiate RegistrationValidationConfigurationException.",
                exception);
        assertEquals(
                "Error message is not properly propagated to super class.",
                ERROR_MESSAGE, exception.getMessage());
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
    public void testRegistrationValidationConfigurationException2() {
        RegistrationValidationConfigurationException exception = new RegistrationValidationConfigurationException(
                ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull(
                "Unable to instantiate RegistrationValidationConfigurationException.",
                exception);
        assertTrue("The error message should match", exception.getMessage()
                .indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.",
                CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies RegistrationValidationConfigurationException subclasses
     * BaseException.
     * </p>
     */
    public void testRegistrationValidationConfigurationExceptionInheritance1() {
        RegistrationValidationConfigurationException exception = new RegistrationValidationConfigurationException(
                ERROR_MESSAGE);
        assertTrue(
                "RegistrationValidationConfigurationException does not subclass Exception.",
                exception instanceof RegistrationValidationException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies RegistrationValidationConfigurationException subclasses
     * RegistrationValidationException.
     * </p>
     */
    public void testRegistrationValidationConfigurationExceptionInheritance2() {
        RegistrationValidationConfigurationException exception = new RegistrationValidationConfigurationException(
                ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue(
                "RegistrationValidationConfigurationException does not subclass Exception.",
                exception instanceof RegistrationValidationException);
    }
}
