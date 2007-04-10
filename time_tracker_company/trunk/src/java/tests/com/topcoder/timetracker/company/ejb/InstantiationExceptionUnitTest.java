/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Unit test cases for <code>InstantiationException</code>.
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
public class InstantiationExceptionUnitTest extends TestCase {
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
    public void testInstantiationException1() {
        InstantiationException ce = new InstantiationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate InstantiationException.", ce);
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
    public void testInstantiationException2() {
        Exception cause = new Exception();
        InstantiationException ce = new InstantiationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate InstantiationException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InstantiationException subclasses BaseException.
     * </p>
     */
    public void testInstantiationExceptionInheritance1() {
        assertTrue("InstantiationException does not subclass BaseException.",
            new InstantiationException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InstantiationException subclasses BaseException.
     * </p>
     */
    public void testInstantiationExceptionInheritance2() {
        assertTrue("InstantiationException does not subclass BaseException.",
            new InstantiationException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}

