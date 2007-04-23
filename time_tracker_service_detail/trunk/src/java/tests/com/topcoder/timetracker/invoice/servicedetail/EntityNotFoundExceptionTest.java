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
public class EntityNotFoundExceptionTest extends TestCase {

    /** ID used in the unit test. */
    private long id = 43;

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(EntityNotFoundExceptionTest.class);
    }

    /**
     * <p>
     * Creation test.
     * </p>
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testEntityNotFoundException1() {
        final EntityNotFoundException ex = new EntityNotFoundException(id);

        assertNotNull("Unable to instantiate EntityNotFoundException.", ex);
        assertTrue("Error message is not properly propagated to super class.", ex.getMessage().indexOf("43") > 0);
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
        EntityNotFoundException ex = new EntityNotFoundException(id, "testMessage");

        assertNotNull("Unable to instantiate EntityNotFoundException.", ex);
        assertEquals("Cause is not properly propagated to super class.", "testMessage", ex.getMessage());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>EntityNotFoundException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testEntityNotFoundExceptionInheritance1() {
        assertTrue("BatchExecutionException does not subclass DataAccessException.", new EntityNotFoundException(
            id) instanceof DataAccessException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>EntityNotFoundException</code> subclasses <code>DataAccessException</code>.
     * </p>
     */
    public void testEntityNotFoundExceptionInheritance2() {
        assertTrue("BatchExecutionException does not subclass DataAccessException.", new EntityNotFoundException(
            id, "testMessage") instanceof DataAccessException);
    }

    /**
     * Test <code>getIds</code> for accuracy. Condition: normal. Expect: normal.
     */
    public void testGetId() {
        EntityNotFoundException exception = new EntityNotFoundException(id);
        assertEquals("The returned value is not as expected", 43, exception.getId());
    }
}
