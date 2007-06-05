/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import com.topcoder.management.resource.Resource;
import com.topcoder.registration.service.impl.RegistrationResultImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for RegistrationResultImpl.
 *
 * @author liulike
 * @version 1.0
 */
public class RegistrationResultImplFailureTest extends TestCase {

    /**
     * The errors array used in test.
     */
    private String[] errors;

    /**
     * The Resource instance used in test.
     */
    private Resource previousRegistration;

    /**
     * The RegistrationResultImpl instance used in test.
     */
    private RegistrationResultImpl instance;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        errors = new String[2];
        errors[0] = "1";
        errors[1] = "2";

        previousRegistration = new Resource();

        instance = new RegistrationResultImpl(true, null, previousRegistration);
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        errors = null;
        previousRegistration = null;
        instance = null;
    }

    /**
     * Failure tests for RegistrationResultImpl(boolean, String[], Resource), it tests the case that if errors
     * is a non-empty array but successful is true, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationResultImplBooleanStringArrayResource_invalid1() {
        try {
            new RegistrationResultImpl(true, errors, previousRegistration);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationResultImpl(boolean, String[], Resource), it tests the case that errors
     * contains null elements in the array, ans the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationResultImplBooleanStringArrayResource_invalid2() {
        try {
            errors = new String[1];
            new RegistrationResultImpl(true, errors, previousRegistration);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationResultImpl(boolean, String[], Resource), it tests the case that errors
     * contains empty elements in the array, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationResultImplBooleanStringArrayResource_invalid3() {
        try {
            errors = new String[1];
            errors[0] = "";
            new RegistrationResultImpl(true, errors, previousRegistration);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationResultImpl(boolean, String[], Resource), it tests the case that
     * previousRegistration is not null but successful is false, and the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationResultImplBooleanStringArrayResource_invalid4() {
        try {
            new RegistrationResultImpl(false, errors, previousRegistration);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that if errors is a non-empty array but
     * successful is true, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid1() {
        try {
            instance.setErrors(errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that if errors contains null elements in the
     * array, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid2() {
        errors = new String[1];
        try {
            instance.setErrors(errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that if errors contains empty elements in the
     * array, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid3() {
        errors = new String[1];
        errors[0] = "";
        try {
            instance.setErrors(errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setPreviousRegistration(Resource), it tests the case that resource is not null but
     * successful is false, and the IllegalArgumentException is expected.
     *
     */
    public void testSetPreviousRegistration() {
        instance.setSuccessful(false);
        try {
            instance.setPreviousRegistration(previousRegistration);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all test cases.
     */
    public static Test suite() {
        return new TestSuite(RegistrationResultImplFailureTest.class);
    }

}
