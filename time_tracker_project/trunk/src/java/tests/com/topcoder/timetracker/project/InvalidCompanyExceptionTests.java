/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for InvalidCompanyException.
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
public class InvalidCompanyExceptionTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The InvalidCompanyException instance for testing.
     * </p>
     */
    private InvalidCompanyException companyException;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        companyException = new InvalidCompanyException(8, 10, ERROR_MESSAGE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        companyException = null;
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(InvalidCompanyExceptionTests.class);
    }

    /**
     * <p>
     * Tests ctor InvalidCompanyException#InvalidCompanyException(long, long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created InvalidCompanyException instance should not be null.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create a new InvalidCompanyException instance.", new InvalidCompanyException(5, 8));
    }

    /**
     * <p>
     * Tests ctor InvalidCompanyException#InvalidCompanyException(long, long, String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created InvalidCompanyException instance should not be null.
     * </p>
     */
    public void testCtor2() {
        assertNotNull("Failed to create a new InvalidCompanyException instance.", this.companyException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidCompanyException subclasses Exception.
     * </p>
     */
    public void testInvalidCompanyExceptionInheritance1() {
        InvalidCompanyException exception = new InvalidCompanyException(5, 8);
        assertTrue("InvalidCompanyException does not subclass Exception.", exception instanceof Exception);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies InvalidCompanyException subclasses Exception.
     * </p>
     */
    public void testInvalidCompanyExceptionInheritance2() {
        assertTrue("InvalidCompanyException does not subclass Exception.", companyException instanceof Exception);
    }

    /**
     * <p>
     * Tests InvalidCompanyException#getFoundCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies InvalidCompanyException#getFoundCompanyId() is correct.
     * </p>
     */
    public void testGetFoundCompanyId() {
        assertEquals("Failed to get the found company id correctly.", 8, this.companyException.getFoundCompanyId());
    }

    /**
     * <p>
     * Tests InvalidCompanyException#getExpectedCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies InvalidCompanyException#getExpectedCompanyId() is correct.
     * </p>
     */
    public void testgetExpectedCompanyId() {
        assertEquals("Failed to get the expected company id correctly.", 10,
            this.companyException.getExpectedCompanyId());
    }
}