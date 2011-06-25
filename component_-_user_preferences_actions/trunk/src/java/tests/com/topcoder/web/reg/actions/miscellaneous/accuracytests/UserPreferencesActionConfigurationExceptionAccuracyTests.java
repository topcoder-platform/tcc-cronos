/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionConfigurationException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for UserPreferencesActionConfigurationException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class UserPreferencesActionConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the UserPreferencesActionConfigurationException.
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
        return new TestSuite(UserPreferencesActionConfigurationExceptionAccuracyTests.class);
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
    public void testUserPreferencesActionConfigurationException1() {
        UserPreferencesActionConfigurationException exception = new UserPreferencesActionConfigurationException(
            ERROR_MESSAGE);
        assertNotNull("Unable to instantiate UserPreferencesActionConfigurationException.", exception);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, exception.getMessage());
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
    public void testUserPreferencesActionConfigurationException2() {
        UserPreferencesActionConfigurationException exception = new UserPreferencesActionConfigurationException(
            ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate UserPreferencesActionConfigurationException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UserPreferencesActionConfigurationException subclasses Exception.
     * </p>
     */
    public void testUserPreferencesActionConfigurationExceptionInheritance1() {
        UserPreferencesActionConfigurationException exception = new UserPreferencesActionConfigurationException(
            ERROR_MESSAGE);
        assertTrue("UserPreferencesActionConfigurationException does not subclass Exception.",
            exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UserPreferencesActionConfigurationException subclasses Exception.
     * </p>
     */
    public void testUserPreferencesActionConfigurationExceptionInheritance2() {
        UserPreferencesActionConfigurationException exception = new UserPreferencesActionConfigurationException(
            ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("UserPreferencesActionConfigurationException does not subclass Exception.",
            exception instanceof Exception);
    }
}