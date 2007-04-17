/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.DataAccessException;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for DataAccessException.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DataAccessExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used to create the DataAccessException.
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
        return new TestSuite(DataAccessExceptionAccuracyTests.class);
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
    public void testDataAccessException1() {
        DataAccessException exception = new DataAccessException(ERROR_MESSAGE);
        assertNotNull("Unable to instantiate DataAccessException.", exception);
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
    public void testDataAccessException2() {
        DataAccessException exception = new DataAccessException(ERROR_MESSAGE, CAUSE_EXCEPTION);

        assertNotNull("Unable to instantiate DataAccessException.", exception);
        assertTrue("The error message should match", exception.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, exception.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DataAccessException subclasses Exception.
     * </p>
     */
    public void testDataAccessExceptionInheritance1() {
        DataAccessException exception = new DataAccessException(ERROR_MESSAGE);
        assertTrue("DataAccessException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DataAccessException subclasses Exception.
     * </p>
     */
    public void testDataAccessExceptionInheritance2() {
        DataAccessException exception = new DataAccessException(ERROR_MESSAGE, CAUSE_EXCEPTION);
        assertTrue("DataAccessException does not subclass Exception.", exception instanceof Exception);
    }
}