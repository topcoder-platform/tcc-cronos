/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for BatchOperationException.
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
public class BatchOperationExceptionTests extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * An exception instance used for testing.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

    /**
     * <p>
     * The Throwable array used for testing..
     * </p>
     */
    private Throwable[] causes = new Throwable[] {CAUSE_EXCEPTION};

    /**
     * <p>
     * The BatchOperationException instance for testing.
     * </p>
     */
    private BatchOperationException batchException;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        batchException = new BatchOperationException(ERROR_MESSAGE, causes);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        batchException = null;
    }

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(BatchOperationExceptionTests.class);
    }

    /**
     * <p>
     * Tests ctor BatchOperationException#BatchOperationException(String, Throwable[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created BatchOperationException instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new BatchOperationException instance.", batchException);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE,
            batchException.getMessage());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies BatchOperationException subclasses DataAccessException.
     * </p>
     */
    public void testBatchOperationExceptionInheritance() {
        assertTrue("BatchOperationException does not subclass DataAccessException.",
            batchException instanceof DataAccessException);
    }

    /**
     * <p>
     * Tests BatchOperationException#getCauses() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BatchOperationException#getCauses() is correct.
     * </p>
     */
    public void testGetCauses() {
        causes = this.batchException.getCauses();
        assertEquals("Failed to get the causes correctly.", CAUSE_EXCEPTION, causes[0]);
    }
}