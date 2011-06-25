/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests;

import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionExecutionException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for UserPreferencesActionExecutionException.
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
public class UserPreferencesActionExecutionExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the UserPreferencesActionExecutionException.
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
        return new TestSuite(UserPreferencesActionExecutionExceptionAccuracyTests.class);
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
    public void testUserPreferencesActionExecutionException1() {
        UserPreferencesActionExecutionException exception = new UserPreferencesActionExecutionException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate UserPreferencesActionExecutionException.", exception);
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
    public void testUserPreferencesActionExecutionException2() {
        UserPreferencesActionExecutionException exception = new UserPreferencesActionExecutionException(ERROR_MESSAGE,
            CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate UserPreferencesActionExecutionException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UserPreferencesActionExecutionException subclasses Exception.
     * </p>
     */
    public void testUserPreferencesActionExecutionExceptionInheritance1() {
        UserPreferencesActionExecutionException exception = new UserPreferencesActionExecutionException(ERROR_MESSAGE);
        assertTrue("UserPreferencesActionExecutionException does not subclass Exception.",
            exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UserPreferencesActionExecutionException subclasses Exception.
     * </p>
     */
    public void testUserPreferencesActionExecutionExceptionInheritance2() {
        UserPreferencesActionExecutionException exception = new UserPreferencesActionExecutionException(ERROR_MESSAGE,
            CAUSE_EXCEPTION);
        assertTrue("UserPreferencesActionExecutionException does not subclass Exception.",
            exception instanceof Exception);
    }
}