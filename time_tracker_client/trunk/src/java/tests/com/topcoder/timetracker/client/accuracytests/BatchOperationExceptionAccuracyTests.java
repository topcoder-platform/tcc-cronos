/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.BatchOperationException;

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
     * An exception instance used to create the BatchOperationException.
     * </p>
     */
    private static final Exception CAUSE_EXCEPTION = new NullPointerException();

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
        batchException = new BatchOperationException(ERROR_MESSAGE, CAUSE_EXCEPTION, new boolean[] {true});
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
        return new TestSuite(BatchOperationExceptionAccuracyTests.class);
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
    public void testBatchOperationException1() {
        BatchOperationException exception = new BatchOperationException(ERROR_MESSAGE, new boolean[] {true});
        assertNotNull("Unable to instantiate BatchOperationException.", exception);
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
    public void testBatchOperationException2() {
        assertNotNull("Unable to instantiate BatchOperationException.", batchException);
        assertTrue("The error message should match", batchException.getMessage().indexOf(ERROR_MESSAGE) >= 0);
        assertEquals("Cause is not properly propagated to super class.", CAUSE_EXCEPTION, batchException.getCause());
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
    public void testBatchOperationExceptionInheritance1() {
        BatchOperationException exception = new BatchOperationException(ERROR_MESSAGE, new boolean[] {true});
        assertTrue("BatchOperationException does not subclass Exception.", exception instanceof Exception);
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
    public void testBatchOperationExceptionInheritance2() {
        assertTrue("BatchOperationException does not subclass Exception.", batchException instanceof Exception);
    }

    /**
     * <p>
     * Tests BatchOperationException#getResult() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BatchOperationException#getResult() is correct.
     * </p>
     */
    public void testgetResult() {
        assertTrue("Failed to get the result correctly.", this.batchException.getResult()[0]);
    }
}