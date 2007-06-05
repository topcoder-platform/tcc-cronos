/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import com.topcoder.registration.service.impl.RemovalResultImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests for RemovalResultImpl.
 *
 * @author liulike
 * @version 1.0
 */
public class RemovalResultImplFailureTest extends TestCase {

    /**
     * The RemovalResultImpl instance used in test.
     */
    private RemovalResultImpl instance;

    /**
     * The error array used in test.
     */
    private String[] errors;

    /**
     * Set up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        errors = new String[2];
        errors[0] = "1";
        errors[1] = "2";
        instance = new RemovalResultImpl(false, errors);
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        errors = null;
        instance = null;
    }

    /**
     * Failure tests for RemovalResultImpl(boolean, String[]), it tests the case that errors is a non-empty
     * array but successful is true, and the IllegalArgumentException is expected.
     *
     */
    public void testRemovalResultImplBooleanStringArray_invalid1() {
        try {
            instance = new RemovalResultImpl(true, errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RemovalResultImpl(boolean, String[]), it tests the case that errors contains null
     * elements in the array, and the IllegalArgumentException is expected.
     *
     */
    public void testRemovalResultImplBooleanStringArray_invalid2() {
        try {
            errors = new String[1];
            instance = new RemovalResultImpl(true, errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RemovalResultImpl(boolean, String[]), it tests the case that errors contains empty
     * elements in the array, and the IllegalArgumentException is expected.
     *
     */
    public void testRemovalResultImplBooleanStringArray_invalid3() {
        try {
            errors = new String[1];
            errors[0] = "";
            instance = new RemovalResultImpl(true, errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that errors is a non-empty array but
     * successful is true, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid1() {
        instance = new RemovalResultImpl(true, null);
        try {
            instance.setErrors(errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that errors contains null elements in the
     * array, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid2() {
        try {
            errors = new String[1];
            instance.setErrors(errors);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setErrors(String[]), it tests the case that errors contains null elements in the
     * array, and the IllegalArgumentException is expected.
     *
     */
    public void testSetErrors_invalid3() {
        try {
            errors = new String[1];
            errors[0] = "";
            instance.setErrors(errors);
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
        return new TestSuite(RemovalResultImplFailureTest.class);
    }

}
