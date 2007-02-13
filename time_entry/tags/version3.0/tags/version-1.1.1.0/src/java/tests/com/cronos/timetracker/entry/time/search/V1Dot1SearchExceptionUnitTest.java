/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * Unit test cases for SearchException.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1SearchExceptionUnitTest extends TestCase {
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
    public void testSearchException1() {
        SearchException ce = new SearchException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate SearchException.", ce);
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
    public void testSearchException2() {
        Throwable cause = new Exception();
        SearchException ce = new SearchException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate SearchException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies SearchException subclasses BaseException.
     * </p>
     */
    public void testSearchExceptionInheritance() {
        assertTrue("SearchException does not subclass BaseException.",
            new SearchException(ERROR_MESSAGE) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies SearchException subclasses BaseException.
     * </p>
     */
    public void testSearchExceptionInheritance2() {
        assertTrue("SearchException does not subclass BaseException.",
            new SearchException(ERROR_MESSAGE, new Exception()) instanceof BaseException);
    }
}
