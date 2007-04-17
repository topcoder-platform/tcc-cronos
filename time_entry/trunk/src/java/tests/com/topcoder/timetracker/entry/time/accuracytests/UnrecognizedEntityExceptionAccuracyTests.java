/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityExceptionTests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for UnrecognizedEntityException.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class UnrecognizedEntityExceptionAccuracyTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The UnrecognizedEntityException instance for testing.
     * </p>
     */
    private UnrecognizedEntityException entityException;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        entityException = new UnrecognizedEntityException(8, ERROR_MESSAGE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        entityException = null;
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UnrecognizedEntityExceptionTests.class);
    }

    /**
     * <p>
     * Tests ctor UnrecognizedEntityException#UnrecognizedEntityException(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UnrecognizedEntityException instance should not be null.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create a new UnrecognizedEntityException instance.",
            new UnrecognizedEntityException(5));
    }

    /**
     * <p>
     * Tests ctor UnrecognizedEntityException#UnrecognizedEntityException(long, String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created UnrecognizedEntityException instance should not be null.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create a new UnrecognizedEntityException instance.", this.entityException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UnrecognizedEntityException subclasses Exception.
     * </p>
     */
    public void testUnrecognizedEntityExceptionInheritance1() {
        UnrecognizedEntityException exception = new UnrecognizedEntityException(5);
        assertTrue("UnrecognizedEntityException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies UnrecognizedEntityException subclasses Exception.
     * </p>
     */
    public void testUnrecognizedEntityExceptionInheritance2() {
        assertTrue("UnrecognizedEntityException does not subclass Exception.", entityException instanceof Exception);
    }

    /**
     * <p>
     * Tests UnrecognizedEntityException#getId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies UnrecognizedEntityException#getId() is correct.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get the id correctly.", 8, this.entityException.getId());
    }
}