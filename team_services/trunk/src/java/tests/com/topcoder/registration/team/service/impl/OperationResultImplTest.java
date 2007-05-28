/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.impl;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>OperationResultImpl</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OperationResultImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>OperationResultImpl</code> class.
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
     * Test for constructor with no parameters.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("'result' should not be null.", result);
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it with successful=true, and errors array not empty, expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithTrueAndErrorsExist() throws Exception {
        try {
            new OperationResultImpl(true, new String[] {"error"});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it with successful=false, and errors array has null elements, expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithTrueAndErrorsHasNull() throws Exception {
        try {
            new OperationResultImpl(false, new String[] {null, "error"});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it with successful=false, and errors array has empty elements, expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithTrueAndErrorsHasEmpty() throws Exception {
        try {
            new OperationResultImpl(false, new String[] {"   ", "error"});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
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
     * Test for <code>isSuccessful()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, true should be returned.
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
     * Test for <code>setSuccessful(successful)</code> method.
     * </p>
     * <p>
     * Tests it with successful=true, but errors exist, expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetSuccessfulWithTrueAndErrorsExist() throws Exception {
        result = new OperationResultImpl(false, new String[] {"error"});
        try {
            result.setSuccessful(true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setSuccessful(successful)</code> method.
     * </p>
     * <p>
     * Tests it with successful=true, no errors exist, successful should be set to true.
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
     * Test for <code>getErrors()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array of errors should be returned.
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
     * Test for <code>setErrors(errors)</code> method.
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
     * Test for <code>setErrors(errors)</code> method.
     * </p>
     * <p>
     * Tests it with successful=true and errors exist, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void tesSetErrorsWithTrue() throws Exception {
        try {
            result.setErrors(new String[] {"error"});
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
