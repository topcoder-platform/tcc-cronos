/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import junit.framework.TestCase;

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
        TestHelper.loadConfig();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
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
            IMHelper.checkString("   ", "name");
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
        String s = IMHelper.getRequiredConfigString("com.topcoder.util.log", "basic.log.target");
        assertEquals("The getRequiredConfigString method is incorrect.", "System.out", s);
    }

    /**
     * Failure test for the getRequiredConfigString method. IMConfigurationException should be thrown if the
     * namespace is unknown.
     */
    public void test_getRequiredConfigString_Failure_1() {
        try {
            IMHelper.getRequiredConfigString("other", "basic.log.target");
            fail("IMConfigurationException should be thrown if the namespace is unknown.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the getRequiredConfigString method. IMConfigurationException should be thrown if the
     * property is unknown.
     */
    public void test_getRequiredConfigString_Failure_2() {
        try {
            IMHelper.getRequiredConfigString("com.topcoder.util.log", "other");
            fail("IMConfigurationException should be thrown if the property is unknown.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the getOptionalConfigString method. The retrieved value should be as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getOptionalConfigString_1() throws Exception {
        String s = IMHelper.getOptionalConfigString("com.topcoder.util.log", "basic.log.target");
        assertEquals("The getOptionalConfigString method is incorrect.", "System.out", s);
    }

    /**
     * Failure test for the getOptionalConfigString method. IMConfigurationException should be thrown if the
     * namespace is unknown.
     */
    public void test_getOptionalConfigString_Failure_1() {
        try {
            IMHelper.getOptionalConfigString("other", "basic.log.target");
            fail("IMConfigurationException should be thrown if the namespace is unknown.");
        } catch (IMConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the getOptionalConfigString method. Null should be returned for missing property.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_getOptionalConfigString_Failure_2() throws Exception {
        String s = IMHelper.getOptionalConfigString("com.topcoder.util.log", "other");
        assertNull("Null should be returned if the property is missing.", s);
    }

    /**
     * Tests the contains method. The result should be correct.
     */
    public void test_contains_1() {
        assertTrue("The contains method is incorrect.", IMHelper.contains(new long[] {2, 1, 3}, 1));
    }

    /**
     * Tests the contains method. The result should be correct.
     */
    public void test_contains_2() {
        assertFalse("The contains method is incorrect.", IMHelper.contains(new long[] {2, 1, 3}, 4));
    }

    /**
     * Tests the contains method. The result should be correct.
     */
    public void test_contains_3() {
        assertFalse("The contains method is incorrect.", IMHelper.contains(null, 4));
    }

}
