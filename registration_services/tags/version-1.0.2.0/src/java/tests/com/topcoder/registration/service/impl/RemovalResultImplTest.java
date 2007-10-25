/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>RemovalResultImpl</code>.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class RemovalResultImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RemovalResultImpl</code>.
     * </p>
     */
    private RemovalResultImpl result;

    /**
     * <p>
     * Represents the array of errors.
     * </p>
     */
    private String[] errors;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        errors = new String[] {"Error"};
        result = new RemovalResultImpl(false, errors);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        this.errors = null;
        this.result = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests for accuracy, non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultCtorAccuracy() throws Exception {
        assertNotNull("Should not be null.", new RemovalResultImpl());
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it with successful=true AND error messages exist.Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithTrueAndErrorMsgExist() throws Exception {
        try {
            new RemovalResultImpl(true, errors);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>isSuccessful()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, false should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testIsSuccessfull() throws Exception {
        assertFalse("False should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>setSuccessful(successful)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with successful=true, Expects IAE.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetSuccessfulTrue() throws Exception {
        assertFalse("False should be returned.", result.isSuccessful());
        assertEquals("There should be one message.", 1, result.getErrors().length);

        try {
            result.setSuccessful(true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }

    }

    /**
     * <p>
     * Test for <code>getErrors()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing one error message should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetErrorsAccuracy() throws Exception {
        assertEquals("There should be one error message.", 1, result.getErrors().length);
    }

    /**
     * <p>
     * Test for <code>setErrors(errors)</code> method.
     * </p>
     * <p>
     * Tests it with successful=true and errors exist. Expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsWithTrueAndErrorExist() throws Exception {
        result = new RemovalResultImpl(true, null);
        try {
            result.setErrors(errors);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setErrors(errors)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, errors should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsAccuracy() throws Exception {
        assertEquals("There should be one message.", 1, result.getErrors().length);
        result.setErrors(null);
        assertEquals("There should be no message now.", 0, result.getErrors().length);
        result.setErrors(errors);
        assertEquals("There should be one message.", 1, result.getErrors().length);
    }

}
