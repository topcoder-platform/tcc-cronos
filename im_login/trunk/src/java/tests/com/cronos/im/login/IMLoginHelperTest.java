/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This unit test addresses the functionality provided by the <code>{@link IMLoginHelper}</code> class.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 * @see IMLoginHelper
 */
public class IMLoginHelperTest extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests of <code>IMLoginHelperTest</code>.
     */
    public static Test suite() {
        return new TestSuite(IMLoginHelperTest.class);
    }

    /**
     * <p>
     * Detail test of <code>{@link IMLoginHelper#validateNotNull(Object object, String name)}</code> method.
     * </p>
     *
     * <p>
     * valid object.
     * </p>
     *
     */
    public void testValidateNotNullAccuracy() {
        IMLoginHelper.validateNotNull(new Object(), "object");
    }

    /**
     * <p>
     * Failure test of <code>{@link IMLoginHelper#validateNotNull(Object object, String name)}</code> method.
     * </p>
     *
     * <p>
     * object is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testValidateNotNullFailure() {
        try {
            IMLoginHelper.validateNotNull(null, "object");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link IMLoginHelper#validateEmptyString(String string, String name)}</code> method.
     * </p>
     *
     * <p>
     * valid string.
     * </p>
     *
     */
    public void testValidateEmptyStringAccuracy() {
        IMLoginHelper.validateEmptyString("string", "string");
    }

    /**
     * <p>
     * Failure test of <code>{@link IMLoginHelper#validateEmptyString(String string, String name)}</code> method.
     * </p>
     *
     * <p>
     * string is <code>null</code>.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testValidateEmptyStringFailure1() {
        try {
            IMLoginHelper.validateEmptyString(null, "string");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test of <code>{@link IMLoginHelper#validateEmptyString(String string, String name)}</code> method.
     * </p>
     *
     * <p>
     * string is empty.
     * </p>
     *
     * <p>
     * Expects <code>IllegalArgumentException</code> to be thrown.
     * </p>
     *
     */
    public void testValidateEmptyStringFailure2() {
        try {
            IMLoginHelper.validateEmptyString(" ", "string");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Detail test of <code>{@link IMLoginHelper#getConfig(String namespace, String key, String defaultVal)}</code>
     * method.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public void testGetConfigAccuracy() throws Exception {
        TestHelper.loadConfigs(true, "config.xml");
        assertEquals("failed to get the config", "def", IMLoginHelper.getConfig("", "no key", "def"));
        assertEquals("failed to get the config", TestHelper.USER_PROFILE_KEY, IMLoginHelper.getConfig(
                "com.cronos.im.LoginAction", "userProfileKey", "def"));
        TestHelper.releaseConfigs();
    }
}
