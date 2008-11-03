/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>Helper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTests extends TestCase {
    /**
     * <p>
     * Accuracy test case for {@link Helper#checkNull(Object, String)}.The argument is not null so
     * <code>IllegalArgumentException</code> should not be thrown.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        try {
            Helper.checkNull("non-null", "non-null name");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Failure test case for {@link Helper#checkNull(Object, String)}.The argument is null so
     * <code>IllegalArgumentException</code> should be thrown.
     * </p>
     */
    public void testCheckNull_Failure() {
        try {
            Helper.checkNull(null, "null-name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when a not null and not empty
     * string is passed in and expects success.
     * </p>
     */
    public void testCheckStringNullOrEmpty_NotNullNotEmptyString() {
        try {
            Helper.checkStringNullOrEmpty("test", "test");
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when a null string is passed in
     * and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_NullString() {
        try {
            Helper.checkStringNullOrEmpty(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when an empty string is passed
     * in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_EmptyString1() {
        try {
            Helper.checkStringNullOrEmpty(" ", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests Helper#checkStringNullOrEmpty(String,String) method. It test the case when an empty string is passed
     * in and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckStringNullOrEmpty_EmptyString2() {
        try {
            Helper.checkStringNullOrEmpty("", "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}