/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.InvalidCompanyException;
import com.topcoder.timetracker.entry.time.InvalidCompanyExceptionTests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for InvalidCompanyException.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class InvalidCompanyExceptionAccuracyTests extends TestCase {
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
     * Tests InvalidCompanyException#getTimeEntryCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies InvalidCompanyException#getTimeEntryCompanyId() is correct.
     * </p>
     */
    public void testGetTimeEntryCompanyId() {
        assertEquals("Failed to get the time entry company id correctly.", 8,
            this.companyException.getTimeEntryCompanyId());
    }

    /**
     * <p>
     * Tests InvalidCompanyException#getOtherEntityCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies InvalidCompanyException#getOtherEntityCompanyId() is correct.
     * </p>
     */
    public void testGetOtherEntityCompanyId() {
        assertEquals("Failed to get the other entity company id correctly.", 10,
            this.companyException.getOtherEntityCompanyId());
    }
}