/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.InvalidClientPropertyException;

/**
 * <p>
 * Unit test cases for InvalidClientPropertyException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class InvalidClientPropertyExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the InvalidClientPropertyException.
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
        return new TestSuite(InvalidClientPropertyExceptionAccuracyTests.class);
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
    public void testInvalidClientPropertyException1() {
        InvalidClientPropertyException exception = new InvalidClientPropertyException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate InvalidClientPropertyException.", exception);
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
    public void testInvalidClientPropertyException2() {
        InvalidClientPropertyException exception = new InvalidClientPropertyException(ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate InvalidClientPropertyException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidClientPropertyException subclasses Exception.
     * </p>
     */
    public void testInvalidClientPropertyExceptionInheritance1() {
        InvalidClientPropertyException exception = new InvalidClientPropertyException(ERROR_MESSAGE);
        assertTrue("InvalidClientPropertyException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidClientPropertyException subclasses Exception.
     * </p>
     */
    public void testInvalidClientPropertyExceptionInheritance2() {
        InvalidClientPropertyException exception = new InvalidClientPropertyException(ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("InvalidClientPropertyException does not subclass Exception.", exception instanceof Exception);
    }
}