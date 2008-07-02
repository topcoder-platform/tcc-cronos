/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.entity;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link Helper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkNull(Object, String)} method.
     * </p>
     * <p>
     * No error expected
     * </p>
     *
     */
    public void test_accuracy_checkNull() {
        Helper.checkNull(new Object(), "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkNull(Object, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Object obj : null value
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkNull() throws Exception {
        try {
            Helper.checkNull(null, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' cannot be null.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkString(String, String)} method.
     * </p>
     * <p>
     * No error expected
     * </p>
     *
     */
    public void test_accuracy_checkString() {
        Helper.checkString("valid", "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkString(String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String str : null value
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkString() throws Exception {
        try {
            Helper.checkString(null, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkString(String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String str : Empty string
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkString1() throws Exception {
        try {
            Helper.checkString("", "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' cannot be empty.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkString(String, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      String str : Empty string with only spaces
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkString2() throws Exception {
        try {
            Helper.checkString("  ", "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' cannot be empty.");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkPositive(long, String)} method.
     * </p>
     * <p>
     * No error expected
     * </p>
     *
     */
    public void test_accuracy_checkPositive() {
        Helper.checkPositive(1, "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkPositive(long, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      long num : negative value
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkPositive() throws Exception {
        try {
            Helper.checkPositive(-1, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkPositive(double, String)} method.
     * </p>
     * <p>
     * No error expected
     * </p>
     *
     */
    public void test_accuracy_checkPositive1() {
        Helper.checkPositive(1.0, "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkPositive(double, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      double num : negative value
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkPositive4() throws Exception {
        try {
            Helper.checkPositive(-1.0, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' should be positive number");
        }
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkCollection(Collection, String)} method.
     * </p>
     * <p>
     * No error expected
     * </p>
     *
     */
    public void test_accuracy_checkCollection() {
        List<Object> list = new ArrayList<Object>();
        list.add(new Object());
        Helper.checkCollection(list, "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkCollection(Collection, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection col : null value
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkCollection() throws Exception {
        try {
            Helper.checkCollection(null, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The parameter 'test' cannot be null.");
        }
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkCollection(Collection, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      Collection col : contains null
     *      String paramName : Valid value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalArgumentException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkCollection2() throws Exception {
        try {
            List<Object> list = new ArrayList<Object>();
            list.add(null);
            Helper.checkCollection(list, "test");
            fail("IllegalArgumentException Expected.");
        } catch (IllegalArgumentException e) {
            // Check for the expected error message
            assertEquals("The Exception message for IllegalArgumentException is wrong", e.getMessage(),
                    "The collection 'test' contains null elements.");
        }
    }
}
