/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.bean;

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
public class HelperTest extends TestCase {
    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>Helper</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>Helper</code> class.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>checkPort(int)</code>.
     * </p>
     * <p>
     * Passes in valid port value. No exception should be thrown.
     * </p>
     */
    public void testCheckPort_Accuracy() {
        Helper.checkPort(22);
        // ok
    }

    /**
     * <p>
     * Failure test for <code>checkPort(int)</code>.
     * </p>
     * <p>
     * Passes in invalid port value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckPort_Failure1() throws Exception {
        try {
            Helper.checkPort(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkNull(Object,String)</code>.
     * </p>
     * <p>
     * Passes in not null value. No exception should be thrown.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        Helper.checkNull("not null", "test");
        // ok
    }

    /**
     * <p>
     * Failure test for <code>checkNull(Object,String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckNull_Failure1() throws Exception {
        try {
            Helper.checkNull(null, "null value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkEmpty(String,String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCheckEmpty_Accuracy() {
        // allow null
        Helper.checkEmpty(null, "test value");
        // ok
    }

    /**
     * <p>
     * Failure test for <code>checkEmpty(String,String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckEmpty_Failure1() throws Exception {
        try {
            Helper.checkEmpty(" ", "empty value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkString(String,String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCheckString_Accuracy() {
        Helper.checkString("string", "test value");
    }

    /**
     * <p>
     * Failure test for <code>checkString(String,String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckString_Failure1() throws Exception {
        try {
            Helper.checkString(null, "null value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>checkString(String,String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckString_Failure2() throws Exception {
        try {
            Helper.checkString(" ", "empty value");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
