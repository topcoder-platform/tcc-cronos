/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.resource.Resource;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test case for <code>RegistrationResultImpl</code> class.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class RegistrationResultImplTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>RegistrationResultImpl</code>.
     * </p>
     */
    private RegistrationResultImpl result;

    /**
     * <p>
     * Represents the array of errors.
     * </p>
     */
    private String[] errors;

    /**
     * <p>
     * Represents previous registration resource.
     * </p>
     */
    private Resource preOne;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        errors = new String[] {"Error"};
        preOne = new Resource(1);
        result = new RegistrationResultImpl(false, errors, null);
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
        this.preOne = null;
        this.result = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultCtorAccuracy() throws Exception {
        assertNotNull("Should not be null.", new RegistrationResultImpl());
    }

    /**
     * <p>
     * Test for constructor with full parameters.
     * </p>
     * <p>
     * Tests it against successful=true AND errors not empty, Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithTrueAndErrorsExist() throws Exception {
        try {
            new RegistrationResultImpl(true, errors, null);
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
     * Tests it against successful=false AND previousRegistraion is not null, Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorWithFalseAndPreRegExist() throws Exception {
        try {
            new RegistrationResultImpl(false, errors, preOne);
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
     * Tests it for accuracy, non-null instance should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("'result' should not be null.", result);

        assertFalse("False should be returned.", result.isSuccessful());

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
    public void testIsSuccessfulAccuracy() throws Exception {
        assertFalse("False should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>setSuccessful(successful)</code> method.
     * </p>
     * <p>
     * Tests it with successful=false, just should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetSuccessfulWithFalse() throws Exception {
        result = new RegistrationResultImpl(true, null, preOne);
        assertTrue("True should be returned.", result.isSuccessful());
        result.setSuccessful(false);
        assertFalse("False should be returned.", result.isSuccessful());
    }

    /**
     * <p>
     * Test for <code>setSuccessful(successful)</code> method.
     * </p>
     * <p>
     * Tests it with successful=true, Expects IAE.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetSuccessfulWithTrue() throws Exception {
        assertFalse("False should be returned.", result.isSuccessful());
        assertEquals("There should be one error message.", 1, result.getErrors().length);

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
     * Tests it against successful=true AND error messages exist. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsWithTrueAndErrorExist() throws Exception {
        result = new RegistrationResultImpl(true, null, null);
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
     * Tests it against errors array containing null elements. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsWithErrorHasNullEle() throws Exception {
        try {
            result.setErrors(new String[] {null});
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
     * Tests it against errors array containing empty elements. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsWithErrorHasEmptyEle() throws Exception {
        try {
            result.setErrors(new String[] {"error", "   "});
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
     * Tests it for accuracy, errors message should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetErrorsAccuracy() throws Exception {
        result.setErrors(null);
        result.setErrors(errors);

        assertEquals("There should be one error message.", 1, result.getErrors().length);
    }

    /**
     * <p>
     * Test for <code>getPreviouseRegistration()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, null should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetPreviouseRegistrationAccuracy1() throws Exception {
        assertNull("Null should be returned.", result.getPreviousRegistration());
    }

    /**
     * <p>
     * Test for <code>setPreviouseRegistration(resource)</code> method.
     * </p>
     * <p>
     * Tests it with successful=false AND non-null resource. Expects
     * <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testSetPreviouseRegistrationFailure() throws Exception {
        try {
            result.setPreviousRegistration(preOne);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>setPreviouseRegistration(resource)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, previous registration should be set successfully.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void setPreviouseRegistrationAccuracy() throws Exception {
        result.setSuccessful(true);

        assertNull("Null should be returned.", result.getPreviousRegistration());
        result.setPreviousRegistration(preOne);
        assertEquals("Previous registration mismatched.", preOne, result.getPreviousRegistration());
    }
}
