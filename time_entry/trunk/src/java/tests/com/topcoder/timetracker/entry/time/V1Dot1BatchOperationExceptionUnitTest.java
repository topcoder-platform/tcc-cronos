/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;


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
 * @version 2.0
 */
public class V1Dot1BatchOperationExceptionUnitTest extends TestCase {
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
    public void testBatchOperationException1() {
        BatchOperationException ce = new BatchOperationException(ERROR_MESSAGE);

        assertNotNull("Unable to instantiate BatchOperationException.", ce);
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
    public void testBatchOperationException2() {
        Throwable cause = new Exception();
        BatchOperationException ce = new BatchOperationException(ERROR_MESSAGE, cause);

        assertNotNull("Unable to instantiate BatchOperationException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies BatchOperationException subclasses DAOActionException.
     * </p>
     */
    public void testBatchOperationExceptionInheritance() {
        assertTrue("BatchOperationException does not subclass DAOActionException.",
            new BatchOperationException(ERROR_MESSAGE) instanceof DAOActionException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies BatchOperationException subclasses DAOActionException.
     * </p>
     */
    public void testBatchOperationExceptionInheritance2() {
        assertTrue("BatchOperationException does not subclass DAOActionException.",
            new BatchOperationException(ERROR_MESSAGE, new Exception()) instanceof DAOActionException);
    }
}
