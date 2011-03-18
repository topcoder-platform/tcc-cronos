/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.servlet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>Helper</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends TestCase {
    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Set up environment.
     * </p>
     */
    public void setUp() {
    }

    /**
     * <p>
     * Accuracy test for checkNull method.
     * </p>
     */
    public void testCheckNull() {
        try {
            Helper.checkNull(null, "null object");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for checkString method.
     * </p>
     */
    public void testCheckString() {
        try {
            Helper.checkString(" ", "trim'd empty string");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for checkString method.
     * </p>
     */
    public void testCheckStringWithNull() {
        try {
            Helper.checkString(null, "null string");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for checkString method.
     * </p>
     */
    public void testCheckStringWithValidString() {
        try {
            Helper.checkString("string", "valid string");

            // success
        } catch (IllegalArgumentException e) {
            fail("No exception should be thrown");
        }
    }

    /**
     * <p>
     * Accuracy test for checkState method.
     * </p>
     */
    public void testCheckStateAccuracy() {
        try {
            Helper.checkState("string_value", "paramName");

            // success
        } catch (IllegalStateException e) {
            fail("No exception should be thrown");
        }
    }

    /**
     * <p>
     * Failure test for checkState method.
     * </p>
     * <p>
     * String is null, ISE should be thrown.
     * </p>
     */
    public void testCheckStateFailure1() {
        try {
            Helper.checkState(null, "paramName");

            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for checkState method.
     * </p>
     * <p>
     * String is empty, ISE should be thrown.
     * </p>
     */
    public void testCheckStateFailure2() {
        try {
            Helper.checkState(" ", "paramName");

            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Destroy the environment.
     * </p>
     */
    protected void tearDown() {
    }
}
