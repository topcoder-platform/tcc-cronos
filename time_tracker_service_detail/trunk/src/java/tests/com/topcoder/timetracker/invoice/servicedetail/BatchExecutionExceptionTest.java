/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test cases for <code>InvalidDataException</code>.
 * </p>
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error
 * message and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class BatchExecutionExceptionTest extends TestCase {

    /** IDs used in the unit test. */
    private long[] ids = new long[] {1, 2, 3};

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(BatchExecutionExceptionTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testBatchExecutionException1() {
        final BatchExecutionException ex = new BatchExecutionException(ids);

        assertNotNull("Unable to instantiate BatchExecutionException.", ex);
        assertTrue("Error message is not properly propagated to super class.", ex.getMessage().indexOf("1") > 0
            && ex.getMessage().indexOf("2") > 0 && ex.getMessage().indexOf("3") > 0);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testBatchExecutionException2() {
        Exception cause = new Exception();
        BatchExecutionException ex = new BatchExecutionException(ids, cause);

        assertNotNull("Unable to instantiate BatchExecutionException.", ex);
        assertEquals("Cause is not properly propagated to super class.", cause, ex.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>BatchExecutionException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testBatchExecutionExceptionInheritance1() {
        assertTrue("BatchExecutionException does not subclass DataAccessException.", new BatchExecutionException(
            ids) instanceof DataAccessException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>BatchExecutionException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testBatchExecutionExceptionInheritance2() {
        assertTrue("BatchExecutionException does not subclass DataAccessException.", new BatchExecutionException(
            ids, new Exception()) instanceof DataAccessException);
    }

    /**
     * Test <code>getIds</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetIds() {
        BatchExecutionException exception = new BatchExecutionException(ids, new Exception());
        assertEquals("The returned value is not as expected", 1, exception.getIds()[0]);
        assertEquals("The returned value is not as expected", 2, exception.getIds()[1]);
        assertEquals("The returned value is not as expected", 3, exception.getIds()[2]);
    }
}
