/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests.impl;

import junit.framework.TestCase;

import com.topcoder.registration.team.service.impl.OperationResultImpl;

/**
 * <p>
 * Unit tests for <code>OperationResultImpl</code> class.
 * </p>
 * @author 80x86
 * @version 1.0
 */
public class OperationResultImplUnitTests extends TestCase {

    /**
     * <p>
     * OperationResultImpl used in the unit tests.
     * </p>
     */
    private OperationResultImpl result;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new OperationResultImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        result = null;
    }

    /**
     * <p>
     * Accuracy test for constructor with no parameters.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1_Accuracy() throws Exception {
        assertNotNull("'result' should not be null.", result);
        assertEquals("should be true", true, result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for constructor with full parameters.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        result = new OperationResultImpl(true, null);
        assertNotNull("'result' should not be null.", result);
        assertTrue("True should be returned.", result.isSuccessful());
        assertEquals("There should be no errors.", 0, result.getErrors().length);
    }

    /**
     * <p>
     * Accuracy test for <code>isSuccessful()</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testIsSuccessful() throws Exception {
        result.setSuccessful(false);
        assertFalse("False should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>setSuccessful(successful)</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetSuccessfulAccuracy() throws Exception {
        result.setSuccessful(true);
        assertTrue("True should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Accuracy test for <code>getErrors()</code> method.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetErrors() throws Exception {
        String[] errors = result.getErrors();
        assertEquals("There should be no errors.", 0, errors.length);
        result = new OperationResultImpl(false, new String[] {"error"});
        errors = result.getErrors();
        assertEquals("There should be one error.", 1, errors.length);
    }

    /**
     * <p>
     * Accuracy test for <code>setErrors(errors)</code> method.
     * </p>
     * <p>
     * Tests it with null errors array, empty errors array should be set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsWithNull() throws Exception {
        result.setErrors(null);
        assertEquals("There should be no errors.", 0, result.getErrors().length);
    }

    /**
     * <p>
     * Accuracy test for <code>setErrors(errors)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with successful=false, errors array should be set.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsAccuracy() throws Exception {
        result.setSuccessful(false);
        result.setErrors(new String[] {"error"});
        assertEquals("There should be one error.", 1, result.getErrors().length);
    }
}
