/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>InsufficientDataException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InsufficientDataExceptionUnitTest extends TestCase {
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
    public void testInsufficientDataException1() {
        InsufficientDataException ce = new InsufficientDataException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate InsufficientDataException.", ce);
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
    public void testInsufficientDataException2() {
        Exception cause = new Exception();
        InsufficientDataException ce = new InsufficientDataException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate InsufficientDataException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InsufficientDataException subclasses BaseException.
     * </p>
     */
    public void testInsufficientDataExceptionInheritance1() {
        assertTrue("InsufficientDataException does not subclass BaseException.",
            new InsufficientDataException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InsufficientDataException subclasses BaseException.
     * </p>
     */
    public void testInsufficientDataExceptionInheritance2() {
        assertTrue("InsufficientDataException does not subclass BaseException.",
            new InsufficientDataException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}
