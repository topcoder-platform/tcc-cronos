/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import com.topcoder.registration.service.impl.RegistrationInfoImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure tests for RegistrationInfoImpl.
 *
 * @author liulike
 * @version 1.0
 */
public class RegistrationInfoImplFailureTest extends TestCase {

    /**
     * Failure tests for RegistrationInfoImpl(long, long, long). It tests the case that argument is negative,
     * the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationInfoImplLongLongLong_InvalidArgument1() {
        try {
            new RegistrationInfoImpl(-1, 1, 1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationInfoImpl(long, long, long). It tests the case that argument is negative,
     * the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationInfoImplLongLongLong_InvalidArgument2() {
        try {
            new RegistrationInfoImpl(1, -1, 1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for RegistrationInfoImpl(long, long, long). It tests the case that argument is negative,
     * the IllegalArgumentException is expected.
     *
     */
    public void testRegistrationInfoImplLongLongLong_InvalidArgument3() {
        try {
            new RegistrationInfoImpl(1, 1, -1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setProjectId(long), it tests the case that the parameter is negative, and the
     * IllegalArgumentException is expected.
     *
     */
    public void testSetProjectId_InvalidArgument() {
        try {
            new RegistrationInfoImpl(1, 1, 1).setProjectId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setUserId(long), it tests the case that the parameter is negative, and the
     * IllegalArgumentException is expected.
     *
     */
    public void testSetUserId_InvalidArgument() {
        try {
            new RegistrationInfoImpl(1, 1, 1).setUserId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Failure tests for setRoleId(long), it tests the case that the parameter is negative, and the
     * IllegalArgumentException is expected.
     *
     */
    public void testSetRoleId() {
        try {
            new RegistrationInfoImpl(1, 1, 1).setRoleId(-1);
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
        return new TestSuite(RegistrationInfoImplFailureTest.class);
    }
}
