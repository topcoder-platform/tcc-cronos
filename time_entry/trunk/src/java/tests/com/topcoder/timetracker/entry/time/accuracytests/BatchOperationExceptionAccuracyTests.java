/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.BatchOperationExceptionTests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Accuracy test cases for BatchOperationException.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class BatchOperationExceptionAccuracyTests extends TestCase {
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
     * Verifies BatchOperationException subclasses Exception.
     * </p>
     */
    public void testBatchOperationExceptionInheritance() {
        assertTrue("BatchOperationException does not subclass Exception.", batchException instanceof Exception);
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