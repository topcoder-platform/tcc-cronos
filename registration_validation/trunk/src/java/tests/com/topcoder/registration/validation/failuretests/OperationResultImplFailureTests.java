/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.OperationResultImpl;
/**
 * Failure tests for OperationResultImpl class.
 * @author slion
 * @version 1.0
 */
public class OperationResultImplFailureTests extends TestCase {
    /**
     * Represents the OperationResultImpl instance for testing.
     */
    private OperationResultImpl impl = null;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        impl = new OperationResultImpl();
    }

    /**
     * Teardown the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Tests OperationResultImpl(boolean successful, String[] errors) method with invalid errors,
     * IllegalArgumentException should be thrown.
     */
    public void testOperationResultImpl_InvalidErrors() {
        try {
            new OperationResultImpl(true, new String[]{"test"});
            fail("testOperationResultImpl_InvalidErrors is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOperationResultImpl_InvalidErrors.");
        }
    }

    /**
     * Tests OperationResultImpl(boolean successful, String[] errors) method that errors
     * contains null element,
     * IllegalArgumentException should be thrown.
     */
    public void testOperationResultImpl_NullError() {
        try {
            new OperationResultImpl(false, new String[]{null});
            fail("testOperationResultImpl_NullError is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOperationResultImpl_NullError.");
        }
    }

    /**
     * Tests OperationResultImpl(boolean successful, String[] errors) method that errors
     * contains empty error,
     * IllegalArgumentException should be thrown.
     */
    public void testOperationResultImpl_EmptyError() {
        try {
            new OperationResultImpl(false, new String[]{" "});
            fail("testOperationResultImpl_NullError is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOperationResultImpl_NullError.");
        }
    }

    /**
     * Tests setErrors(String[] errors) method with invalid String[] errors,
     * IllegalArgumentException should be thrown.
     */
    public void testSetErrors_InvalidErrors() {
        try {
            impl.setErrors(new String[]{"test"});
            fail("testSetErrors_InvalidErrors is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetErrors_InvalidErrors.");
        }
    }

    /**
     * Tests setErrors(String[] errors) method when errors contains null element,
     * IllegalArgumentException should be thrown.
     */
    public void testSetErrors_NullError() {
        try {
            impl.setSuccessful(false);
            impl.setErrors(new String[]{null});
            fail("testSetErrors_NullError is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetErrors_NullError.");
        }
    }

    /**
     * Tests setErrors(String[] errors) method when errors contains empty element,
     * IllegalArgumentException should be thrown.
     */
    public void testSetErrors_EmptyError() {
        try {
            impl.setSuccessful(false);
            impl.setErrors(new String[]{"  "});
            fail("testSetErrors_EmptyError is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetErrors_EmptyError.");
        }
    }

    /**
     * Tests setSuccessful(boolean successful) method in failure,
     * IllegalArgumentException should be thrown.
     */
    public void testSetSuccessful_HasErrors() {
        try {
            impl.setSuccessful(false);
            impl.setErrors(new String[]{"test"});
            impl.setSuccessful(true);
            fail("testSetSuccessful_HasErrors is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetSuccessful_HasErrors.");
        }
    }
}