/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;


/**
 * <p>
 * Tests functionality and error cases of {@link ClientLogicForFirefoxHelper} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientLogicForFirefoxHelperUnitTest extends TestCase {
    /**
     * <p>
     * Tests the method <code>validateNotNull(Object)</code> when the given value is null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testValidateNotNull_NullObject() {
        try {
            ClientLogicForFirefoxHelper.validateNotNull(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>validateNotNull(Object)</code>.
     * </p>
     */
    public void testValidateNotNull_Accuracy() {
        ClientLogicForFirefoxHelper.validateNotNull(new Object(), "accuracy");
    }

    /**
     * <p>
     * Tests the method <code>validateString(String)</code> when the string value is null, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testValidateString_NullString() {
        try {
            ClientLogicForFirefoxHelper.validateString(null, "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>validateString(String)</code> when the string value is empty, IllegalArgumentException is
     * expected.
     * </p>
     */
    public void testValidateString_EmptyString() {
        try {
            ClientLogicForFirefoxHelper.validateString(" ", "null");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>validateString(String)</code>.
     * </p>
     */
    public void testValidateString_Accuracy() {
        ClientLogicForFirefoxHelper.validateNotNull("string", "string");
    }

    /**
     * <p>
     * Tests the method <code>validateNotNegative(double)</code> when the given double is negative,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testValidateNotNegative_NegativeDouble() {
        try {
            ClientLogicForFirefoxHelper.validateNotNegative(-1, "negative");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>validateNotNegative(double)</code>.
     * </p>
     */
    public void testValidateNotNegative_Accuracy() {
        ClientLogicForFirefoxHelper.validateNotNegative(0, "correct");
    }

    /**
     * <p>
     * Tests the method <code>validatePollTime(int)</code> when the given double is not positive,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testValidatePollTime_NotPositiveDouble() {
        try {
            ClientLogicForFirefoxHelper.validatePollTime(0, "negative");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>validatePollTime(int)</code>.
     * </p>
     */
    public void testValidatePollTime_Accuracy() {
        ClientLogicForFirefoxHelper.validatePollTime(1, "correct");
    }

    /**
     * <p>
     * Tests the accuracy of method <code>buildUrl(String, String[], String[])</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testBuildUrl_Accuracy() throws Exception {
        String url = "http://www.google.com";
        String[] paramNames = {"paramNames1", "paramNames2"};
        String[] paramValues = {"paramValues1", "paramValues2"};

        assertEquals("The url should be build correctly.",
            "http://www.google.com?paramNames1=paramValues1&paramNames2=paramValues2",
            ClientLogicForFirefoxHelper.buildUrl(url, paramNames, paramValues));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getInputStreamContent(InputStream)</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetInputStreamContent_Accuracy() throws Exception {
        byte[] bytes = "xxxxx\naaaaa\nbbbb".getBytes();

        ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        assertEquals("The content of the input stream should be correct.", "xxxxx\naaaaa\nbbbb",
            ClientLogicForFirefoxHelper.getInputStreamContent(input));
    }
}
