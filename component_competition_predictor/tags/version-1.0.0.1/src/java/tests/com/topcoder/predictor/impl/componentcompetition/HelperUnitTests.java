/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.predictor.impl.componentcompetition;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * Unit tests for Helper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests extends TestCase {

    /**
     * <p>
     * Accuracy test for the checkNotNull method. No exception is thrown for non-null object.
     * </p>
     */
    public void test_checkNotNull() {
        // no exception
        Helper.checkNotNull("string", "name");
    }

    /**
     * <p>
     * Failure test for the checkNotNull method. IllegalArgumentException is thrown for null object.
     * </p>
     */
    public void test_checkNotNull_failure() {
        try {
            Helper.checkNotNull(null, "name");
            fail("IllegalArgumentException is thrown for null object.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkNotNullNotEmpty(String, String) method. No exception is thrown for non-null
     * non-empty string.
     * </p>
     */
    public void test_checkNotNullNotEmpty1() {
        // no exception
        Helper.checkNotNullNotEmpty("string", "name");
    }

    /**
     * <p>
     * Failure test for the checkNotNullNotEmpty(String, String) method.
     * IllegalArgumentException is thrown for null string.
     * </p>
     */
    public void test_checkNotNullNotEmpty1_failure1() {
        try {
            Helper.checkNotNullNotEmpty((String) null, "name");
            fail("IllegalArgumentException is thrown for null string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the checkNotNullNotEmpty(String, String) method.
     * IllegalArgumentException is thrown for empty string.
     * </p>
     */
    public void test_checkNotNullNotEmpty1_failure2() {
        try {
            Helper.checkNotNullNotEmpty(" ", "name");
            fail("IllegalArgumentException is thrown for empty string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkNotNullNotEmpty(List&lt;?&gt;, String) method.
     * No exception is thrown for non-null non-empty string.
     * </p>
     */
    public void test_checkNotNullNotEmpty2() {
        // no exception
        List<String> list = new ArrayList<String>();
        list.add("a");
        Helper.checkNotNullNotEmpty(list, "name");
    }

    /**
     * <p>
     * Failure test for the checkNotNullNotEmpty(List&lt;?&gt;, String) method.
     * IllegalArgumentException is thrown for null string.
     * </p>
     */
    public void test_checkNotNullNotEmpty2_failure1() {
        try {
            Helper.checkNotNullNotEmpty((List<?>) null, "name");
            fail("IllegalArgumentException is thrown for null string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the checkNotNullNotEmpty(List&lt;?&gt;, String) method.
     * IllegalArgumentException is thrown for empty string.
     * </p>
     */
    public void test_checkNotNullNotEmpty2_failure2() {
        try {
            Helper.checkNotNullNotEmpty(new ArrayList<String>(), "name");
            fail("IllegalArgumentException is thrown for empty string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkPositive method. No exception is thrown for positive integer.
     * </p>
     */
    public void test_checkPositive() {
        // no exception
        Helper.checkPositive(123, "name");
    }

    /**
     * <p>
     * Failure test for the checkPositive method. IllegalArgumentException is thrown for 0 value.
     * </p>
     */
    public void test_checkPositive_failure1() {
        try {
            Helper.checkPositive(0, "name");
            fail("IllegalArgumentException is thrown for 0 value.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the checkPositive method. IllegalArgumentException is thrown for negative value.
     * </p>
     */
    public void test_checkPositive_failure2() {
        try {
            Helper.checkPositive(-123, "name");
            fail("IllegalArgumentException is thrown for negative value.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkNotNegative method. No exception is thrown for positive integer.
     * </p>
     */
    public void test_checkNotNegative_1() {
        // no exception
        Helper.checkNotNegative(123, "name");
    }

    /**
     * <p>
     * Accuracy test for the checkNotNegative method. No exception is thrown for 0.
     * </p>
     */
    public void test_checkNotNegative_2() {
        // no exception
        Helper.checkNotNegative(0, "name");
    }

    /**
     * <p>
     * Failure test for the checkNotNegative method. IllegalArgumentException is thrown for negative value.
     * </p>
     */
    public void test_checkNotNegative_failure() {
        try {
            Helper.checkNotNegative(-123, "name");
            fail("IllegalArgumentException is thrown for negative value.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkList method.
     * No exception is thrown for valid list.
     * </p>
     */
    public void test_checkList() {
        // no exception
        List<String> list = new ArrayList<String>();
        list.add("a");
        Helper.checkList(list, "name");
    }

    /**
     * <p>
     * Failure test for the checkList method.
     * IllegalArgumentException is thrown for null element.
     * </p>
     */
    public void test_checkList_failure1() {
        List<String> list = new ArrayList<String>();
        list.add(null);
        try {
            Helper.checkList(list, "name");
            fail("IllegalArgumentException is thrown for null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test for the checkList method.
     * IllegalArgumentException is thrown for empty string element.
     * </p>
     */
    public void test_checkList_failure2() {
        List<String> list = new ArrayList<String>();
        list.add(" ");
        try {
            Helper.checkList(list, "name");
            fail("IllegalArgumentException is thrown for empty string element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test for the checkArray method.
     * No exception is thrown for valid list.
     * </p>
     */
    public void test_checkArray() {
        // no exception
        Helper.checkArray(new String[]{"a"}, "name");
    }

    /**
     * <p>
     * Failure test for the checkArray method.
     * IllegalArgumentException is thrown for null element.
     * </p>
     */
    public void test_checkArray_failure1() {
        try {
            Helper.checkArray(new String[]{null}, "name");
            fail("IllegalArgumentException is thrown for null element.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
