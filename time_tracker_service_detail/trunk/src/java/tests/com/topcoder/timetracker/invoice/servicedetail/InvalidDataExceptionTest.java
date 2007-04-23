/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for <code>InvalidDataException</code>.
 * </p>
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error
 * message and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class InvalidDataExceptionTest extends TestCase {

    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(InvalidDataExceptionTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testInvalidDataException1() {
        final InvalidDataException ex = new InvalidDataException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate InvalidDataException.", ex);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ex
            .getMessage());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testInvalidDataException2() {
        Exception cause = new Exception();
        InvalidDataException ex = new InvalidDataException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate InvalidDataException.", ex);
        assertEquals("Cause is not properly propagated to super class.", cause, ex.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>InvalidDataException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testInvalidDataExceptionInheritance1() {
        assertTrue("InvalidDataException does not subclass DataAccessException.", new InvalidDataException(
            ERROR_MESSAGE) instanceof DataAccessException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>InvalidDataException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testInvalidDataExceptionInheritance2() {
        assertTrue("InvalidDataException does not subclass DataAccessException.", new InvalidDataException(
            ERROR_MESSAGE, new Exception()) instanceof DataAccessException);
    }
}
