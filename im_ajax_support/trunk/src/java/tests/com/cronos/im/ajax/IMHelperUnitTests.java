/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import junit.framework.TestCase;
import org.w3c.dom.Element;

/**
 * <p>
 * Unit test cases for IMHelper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMHelperUnitTests extends TestCase {

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDown();
    }

    /**
     * Tests the checkNull method. No exception should be thrown for non-null object.
     */
    public void test_checkNull_1() {
        // no exception
        IMHelper.checkNull(new Object(), "name");
    }

    /**
     * Tests the checkNull method. IllegalArgumentException should be thrown for null object.
     */
    public void test_checkNull_2() {
        try {
            IMHelper.checkNull(null, "name");
            fail("IllegalArgumentException should be thrown for null object.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the checkString method. No exception should be thrown for valid string array.
     */
    public void test_checkString_1() {
        // no exception
        IMHelper.checkString("string", "name");
    }

    /**
     * Tests the checkString method. IllegalArgumentException should be thrown for null string.
     */
    public void test_checkString_2() {
        try {
            IMHelper.checkString(null, "name");
            fail("IllegalArgumentException should be thrown for null string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the checkString method. IllegalArgumentException should be thrown for empty string.
     */
    public void test_checkString_3() {
        try {
            IMHelper.checkString(null, "name");
            fail("IllegalArgumentException should be thrown for empty string.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Tests the getRequiredConfigString method. The retrieved value should be as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getRequiredConfigString_1() throws Exception {
        String s = IMHelper.getRequiredConfigString("com.cronos.im.ajax.IMAjaxSupportUtility",
                "client_role_name");
        assertEquals("The getRequiredConfigString method is incorrect.", "client", s);
    }

    /**
     * Failure test for the getRequiredConfigString method. IMAjaxConfigurationException should be thrown if
     * the namespace is unknown.
     */
    public void test_getRequiredConfigString_Failure_1() {
        try {
            IMHelper.getRequiredConfigString("other", "client_role_name");
            fail("IMAjaxConfigurationException should be thrown if the namespace is unknown.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the getRequiredConfigString method. IMAjaxConfigurationException should be thrown if
     * the property is unknown.
     */
    public void test_getRequiredConfigString_Failure_2() {
        try {
            IMHelper.getRequiredConfigString("com.cronos.im.ajax.IMAjaxSupportUtility", "other");
            fail("IMAjaxConfigurationException should be thrown if the property is unknown.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getRequiredConfigStrings method. The retrieved value should be as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getRequiredConfigStrings_1() throws Exception {
        String[] s = IMHelper.getRequiredConfigStrings("com.cronos.im.ajax.IMAjaxSupportUtility",
                "client_role_name");
        assertEquals("The getRequiredConfigStrings method is incorrect.", 1, s.length);
        assertEquals("The getRequiredConfigStrings method is incorrect.", "client", s[0]);
    }

    /**
     * Failure test for the getRequiredConfigStrings method. IMAjaxConfigurationException should be thrown if
     * the namespace is unknown.
     */
    public void test_getRequiredConfigStrings_Failure_1() {
        try {
            IMHelper.getRequiredConfigStrings("other", "client_role_name");
            fail("IMAjaxConfigurationException should be thrown if the namespace is unknown.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the getRequiredConfigStrings method. IMAjaxConfigurationException should be thrown if
     * the property is unknown.
     */
    public void test_getRequiredConfigStrings_Failure_2() {
        try {
            IMHelper.getRequiredConfigStrings("com.cronos.im.ajax.IMAjaxSupportUtility", "other");
            fail("IMAjaxConfigurationException should be thrown if the property is unknown.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getSubElementContent and the getElementFromString methods.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getSubElementContent_getElementFromString_1() throws Exception {
        Element e = IMHelper.getElementFromString("<a><sub>content</sub></a>");
        String s = IMHelper.getSubElementContent(e, "sub");
        assertEquals("The getSubElementContent or the getElementFromString method is incorrect.", "content",
                s);
    }

    /**
     * Tests the getTimeStamp method. No exception should be thrown.
     */
    public void test_getTimeStamp_1() {
        IMHelper.getTimeStamp();
    }

}
