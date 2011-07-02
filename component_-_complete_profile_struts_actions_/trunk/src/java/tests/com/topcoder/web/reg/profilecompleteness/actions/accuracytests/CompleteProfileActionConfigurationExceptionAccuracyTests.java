/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.accuracytests;

import com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileActionConfigurationException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for CompleteProfileActionConfigurationException.
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
public class CompleteProfileActionConfigurationExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the CompleteProfileActionConfigurationException.
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
        return new TestSuite(CompleteProfileActionConfigurationExceptionAccuracyTests.class);
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
    public void testCompleteProfileActionConfigurationException1() {
        CompleteProfileActionConfigurationException exception = new CompleteProfileActionConfigurationException(
            ERROR_MESSAGE);
        assertNotNull("Unable to instantiate CompleteProfileActionConfigurationException.", exception);
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
    public void testCompleteProfileActionConfigurationException2() {
        CompleteProfileActionConfigurationException exception = new CompleteProfileActionConfigurationException(
            ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate CompleteProfileActionConfigurationException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies CompleteProfileActionConfigurationException subclasses Exception.
     * </p>
     */
    public void testCompleteProfileActionConfigurationExceptionInheritance1() {
        CompleteProfileActionConfigurationException exception = new CompleteProfileActionConfigurationException(
            ERROR_MESSAGE);
        assertTrue("CompleteProfileActionConfigurationException does not subclass Exception.",
            exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies CompleteProfileActionConfigurationException subclasses Exception.
     * </p>
     */
    public void testCompleteProfileActionConfigurationExceptionInheritance2() {
        CompleteProfileActionConfigurationException exception = new CompleteProfileActionConfigurationException(
            ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("CompleteProfileActionConfigurationException does not subclass Exception.",
            exception instanceof Exception);
    }
}