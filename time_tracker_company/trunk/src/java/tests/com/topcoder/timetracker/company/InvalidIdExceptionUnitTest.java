/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.util.errorhandling.BaseRuntimeException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>InvalidIdException</code>.
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
public class InvalidIdExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The invalid id used for testing.
     * </p>
     */
    private static final long INVALID = -1;

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testInvalidIdException1() {
        InvalidIdException ce = new InvalidIdException(ERROR_MESSAGE, INVALID);

        assertNotNull("Unable to instantiate InvalidIdException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
        assertEquals("Invalid id is not properly set.", INVALID, ce.getInvalidId());
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
    public void testInvalidIdException2() {
        Exception cause = new Exception();
        InvalidIdException ce = new InvalidIdException(ERROR_MESSAGE, INVALID, cause);

        assertNotNull("Unable to instantiate InvalidIdException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
        assertEquals("Invalid id is not properly set.", INVALID, ce.getInvalidId());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidIdException subclasses BaseRuntimeException.
     * </p>
     */
    public void testInvalidIdExceptionInheritance1() {
        assertTrue("InvalidIdException does not subclass BaseRuntimeException.",
            new InvalidIdException(ERROR_MESSAGE, INVALID) instanceof BaseRuntimeException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidIdException subclasses BaseRuntimeException.
     * </p>
     */
    public void testInvalidIdExceptionInheritance2() {
        assertTrue("InvalidIdException does not subclass BaseRuntimeException.",
            new InvalidIdException(ERROR_MESSAGE, INVALID, new Exception()) instanceof BaseRuntimeException);
    }
}
