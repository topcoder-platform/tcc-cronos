/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Test the methods of <c>Helper</c> class for accuracy.
 *
 * @author marius_neo
 * @version 1.0
 */
public class HelperTestCase extends TestCase {
    /**
     * Represents the const string used as "name" argument in tests.
     */
    private final String name = "name";

    /**
     * Tests <c>validateNotNull(Object,String)</c> method for accuracy when a non-null
     * parameter is passed for validation.The validation should run without any
     * exceptions thrown.
     */
    public void testValidateNotNullOk() {
        Helper.validateNotNull("I am not null", name);

    }

    /**
     * Tests <c>validateNotNull(Object,String)</c> method for accuracy when a null
     * parameter is passed for validation.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testValidateNotNullBad() {
        try {
            Helper.validateNotNull(null, name);

            fail("Should have thrown IllegalArgumentException for null value");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>validateNotNullOrEmpty(Object,String)</c> method for accuracy when a non-null
     * and non-empty parameter is passed for validation.The validation should run without any
     * exceptions thrown.
     */
    public void testValidateNotNullOrEmptyOk() {
        Helper.validateNotNullOrEmpty("I am not null and not empty", name);

    }

    /**
     * Tests <c>validateNotNullOrEmpty(Object,String)</c> method for accuracy when a null/empty
     * parameter is passed for validation.
     * <c>IllegalArgumentException</c> is expected to be thrown.
     */
    public void testValidateNotNullOrEmptyBad() {
        try {
            Helper.validateNotNullOrEmpty(null, name);

            fail("Should have thrown IllegalArgumentException for null value");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        try {
            Helper.validateNotNullOrEmpty("", name);

            fail("Should have thrown IllegalArgumentException for empty string value");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests the accuracy of <c>vlaidatePropertyValue(String,String,String) method when the
     * property value to be validated is not null and not empty.
     *
     * @throws FormatterConfigurationException
     *          Should not be thrown.
     */
    public void testValidatePropertyValue() throws FormatterConfigurationException {
        Helper.validatePropertyValue("propertyName", "propertyValue not null and not empty", "some namespace");
    }

    /**
     * Tests the failure of <c>vlaidatePropertyValue(String,String,String) method when the
     * property value to be validated is null.
     *
     * @throws FormatterConfigurationException
     *          Is expected to be thrown.
     */
    public void testValidatePropertyValueNullValue() throws FormatterConfigurationException {
        try {
            Helper.validatePropertyValue("propertyName", null, "some namespace");
            fail("Should have thrown FormatterConfigurationException because of null property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }
    }

    /**
     * Tests the failure of <c>vlaidatePropertyValue(String,String,String) method when the
     * property value to be validated is an empty string.
     *
     * @throws FormatterConfigurationException
     *          Should not be thrown.
     */
    public void testValidatePropertyValueEmpty() throws FormatterConfigurationException {
        try {
            Helper.validatePropertyValue("propertyName", "  ", "some namespace");
            fail("Should have thrown FormatterConfigurationException because of empty string property value");
        } catch (FormatterConfigurationException e) {
            // Success.
        }
    }

    /**
     * Tests <c>validateProfileProperties(Map,String)</c> method for accuracy when a
     * valid argument is passed for being validated to the method. The validation
     * should run without any exceptions thrown.
     */
    public void testValidateProfilePropertiesOk() {
        Map properties = new HashMap();
        properties.put("key1", new String[]{"value1"});
        properties.put("key2", new String[]{"value2_1", "value2_2"});

        Helper.validateProfileProperties(properties, name);
    }

    /**
     * Tests <c>validateProfileProperties(Map,String)</c> method for failure
     * when an null map is passed for validation.
     */
    public void testValidateProfilePropertiesNullMap() {
        try {
            Helper.validateProfileProperties(null, name);

            fail("IllegalArgumentException should be thrown for null properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>validateProfileProperties(Map,String)</c> method for failure
     * when an empty map is passed for validation.
     */
    public void testValidateProfilePropertiesEmptyMap() {
        try {
            Helper.validateProfileProperties(new HashMap(), name);

            fail("IllegalArgumentException should be thrown for empty properties map");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>validateProfileProperties(Map,String)</c> method for failure
     * when a map containing null values or non-string keys or non String[] values is passed for validation.
     */
    public void testValidateProfilePropertiesMapWithInvalidElements() {
        Map properties = new HashMap();
        properties.put("key1", null);
        try {
            Helper.validateProfileProperties(properties, name);

            fail("IllegalArgumentException should be thrown because properties map contains null values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put(new Object(), new String[]{"value1"});
        try {
            Helper.validateProfileProperties(properties, name);

            fail("IllegalArgumentException should be thrown because properties map contains non-string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("key1", new Object());
        try {
            Helper.validateProfileProperties(properties, name);

            fail("IllegalArgumentException should be thrown because properties map contains non String[] values");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Tests <c>validateProfileProperties(Map,String)</c> method for failure
     * when a map containing empty string keys/ empty string array values.
     */
    public void testValidateProfilePropertiesEmptyStringKeysOrEmptyStringArrayValues() {
        Map properties = new HashMap();
        properties.put("key1", new String[]{});
        try {
            Helper.validateProfileProperties(properties, name);

            fail("IllegalArgumentException should be thrown because" +
                " properties map contains empty string array values");
        } catch (IllegalArgumentException e) {
            // Success.
        }

        properties = new HashMap();
        properties.put("", new String[]{"value1"});
        try {
            Helper.validateProfileProperties(properties, name);

            fail("IllegalArgumentException should be thrown because properties" +
                " map contains empty string keys");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }
}
