/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DuplicateEntityException.
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
public class DuplicateEntityExceptionTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The DuplicateEntityException instance for testing.
     * </p>
     */
    private DuplicateEntityException entityException;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        entityException = new DuplicateEntityException(8, ERROR_MESSAGE);
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
        return new TestSuite(DuplicateEntityExceptionTests.class);
    }

    /**
     * <p>
     * Tests ctor DuplicateEntityException#DuplicateEntityException(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DuplicateEntityException instance should not be null.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create a new DuplicateEntityException instance.", new DuplicateEntityException(5));
    }

    /**
     * <p>
     * Tests ctor DuplicateEntityException#DuplicateEntityException(long, String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DuplicateEntityException instance should not be null.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create a new DuplicateEntityException instance.", this.entityException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DuplicateEntityException subclasses Exception.
     * </p>
     */
    public void testDuplicateEntityExceptionInheritance1() {
        assertTrue("DuplicateEntityException does not subclass Exception.", entityException instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DuplicateEntityException subclasses Exception.
     * </p>
     */
    public void testDuplicateEntityExceptionInheritance2() {
        DuplicateEntityException exception = new DuplicateEntityException(8);
        assertTrue("DuplicateEntityException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Tests DuplicateEntityException#getId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DuplicateEntityException#getId() is correct.
     * </p>
     */
    public void testGetId() {
        assertEquals("Failed to get the id correctly.", 8, this.entityException.getId());
    }
}