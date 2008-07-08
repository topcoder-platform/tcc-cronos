/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

import junit.framework.TestCase;


/**
 * The unit test for the class {@link Helper}.
 *
 * @author yqw
 * @version 2.0
 */
public class HelperTests extends TestCase {
    /**
     * The test for the method
     * {@link Helper#getStringProperty(String, ConfigurationObject, boolean)}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testGetStringProperty() throws Exception {
        String propertyName = "name";
        ConfigurationObject configuration = new DefaultConfigurationObject("test");
        configuration.setPropertyValue(propertyName, "value");

        boolean required = true;
        String value = Helper.getStringProperty(propertyName, configuration, required);
        assertEquals("The value is incorrect.", "value", value);
    }

    /**
     * test the method {@link Helper#getStringProperty(String, ConfigurationObject, boolean)}.
     *
     * @throws Exception
     *             all exception throw to Junit.
     */
    public void testGetStringProperty_1() throws Exception {
        String propertyName = "name";
        ConfigurationObject configuration = new DefaultConfigurationObject("test");

        // configuration.setPropertyValue(propertyName, "value");
        boolean required = true;

        try {
            Helper.getStringProperty(propertyName, configuration, required);
            fail("MessageBoardConfigurationException should be thrown.");
        } catch (MessageBoardConfigurationException e) {
            // good
        }
    }

    /**
     * test the method {@link Helper#getErrorMessage(String, String)}.
     */
    public void testGetErrorMessage() {
        String key = ErrorMessagesCache.INVALID_ARGUMENT;
        String defaultMessage = "invalid arguments exception";
        String value = Helper.getErrorMessage(key, defaultMessage);
        assertNotNull("The instance should not be null.", value);
        assertEquals("The value is incorrect.", defaultMessage, value);
    }

    /**
     * test the method {@link Helper#checkNull(Object, String)}.
     */
    public void testcheckNull() {
        try {
            Helper.checkNull(null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link Helper#checkNull(Object, String)}.
     */
    public void testcheckNull_1() {
        Helper.checkNull(new Object(), "test");
        // no excption
    }

    /**
     * test the method {@link Helper#checkNullOrEmpty(String, String)}.
     */
    public void testcheckNullOrEmpty() {
        try {
            Helper.checkNullOrEmpty(null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link Helper#checkNullOrEmpty(String, String)}.
     */
    public void testcheckNullOrEmpty_2() {
        try {
            Helper.checkNullOrEmpty("    \t", "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * test the method {@link Helper#checkNullOrEmpty(String, String)}.
     */
    public void testcheckNullOrEmpty_3() {
        Helper.checkNull("valid string value", "test");

        // no excption
    }
}
