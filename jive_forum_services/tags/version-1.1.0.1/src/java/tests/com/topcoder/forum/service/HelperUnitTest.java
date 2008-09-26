/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * Unit tests for <code>Helper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(HelperUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>checkNull(Object, String)</code>
     * </p>
     *
     * <p>
     * Target: Verify that <code>checkNull(Object, String)</code> is correct.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        Helper.checkNull("test", "name");
    }

    /**
     * <p>
     * Failure test for <code>checkNull(Object, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: the obj to be checked is null.<br>
     * Expected: <code>IllegalArgumentException</code>.
     * </p>
     */
    public void testCheckNull_Failure() {
        try {
            Helper.checkNull(null, "null");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkString(String, String)</code>.
     * </p>
     * <p>
     * If the string is non-null and non-empty, it should process successfully.
     * </p>
     */
    public void testCheckString() {
        Helper.checkString("non-null", "non-null");
    }

    /**
     * <p>
     * Failure test for <code>checkString(String, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the string is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckString_Failure1() {
        try {
            Helper.checkString(null, "null");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>checkString(String, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the string is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckString_Failure2() {
        try {
            Helper.checkString("", "null");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>checkString(String, String)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the string is full of blank space,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckString_Failure3() {
        try {
            Helper.checkString("   ", "null");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkId(long, String)</code>. If the id is above 0,
     * it should process successfully.
     * </p>
     */
    public void testCheckId() {
        Helper.checkId(1, "id");

        Helper.checkId(1000, "id");
    }

    /**
     * <p>
     * Failure test for <code>checkId(long, String)</code>. If the id isn't above 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckId_Failure() {
        try {
            Helper.checkId(0, "id");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>checkId(long, String)</code>. If the id isn't above 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckId_Failure1() {
        try {
            Helper.checkId(-2, "id");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkOptionId(long, String)</code>. If the id is above 0,
     * it should process successfully.
     * </p>
     */
    public void testCheckOptionId() {
        Helper.checkOptionId(1, "id");

        Helper.checkOptionId(1000, "id");
    }

    /**
     * <p>
     * Accuracy test for <code>checkOptionId(long, String)</code>. If the id is -1,
     * it should process successfully.
     * </p>
     */
    public void testCheckOptionId1() {
        Helper.checkOptionId(-1, "id");
    }

    /**
     * <p>
     * Failure test for <code>checkId(long, String)</code>. If the id isn't above 0,
     * and neither -1, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckOptionId_Failure() {
        try {
            Helper.checkOptionId(-100, "id");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>checkId(long, String)</code>. If the id isn't above 0,
     * and neither -1, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckOptionId_Failure1() {
        try {
            Helper.checkOptionId(0, "id");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>checkId(long, String)</code>. If the id isn't above 0,
     * and neither -1, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckOptionId_Failure2() {
        try {
            Helper.checkOptionId(-3, "id");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
}
