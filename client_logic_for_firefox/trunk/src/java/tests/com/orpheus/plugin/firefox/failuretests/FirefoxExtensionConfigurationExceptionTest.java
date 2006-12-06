/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.orpheus.plugin.firefox.failuretests;

import junit.framework.TestCase;
import com.orpheus.plugin.firefox.*;


/**
 * Tests the failure cases for the FirefoxClientException class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionConfigurationExceptionTest extends TestCase {
    private static final String someText = "some text";
    Throwable inner = new Exception();

    /**
     * Tests that the first FirefoxExtensionConfigurationException constructor works.
     */
    public void testCtor1() {
        FirefoxExtensionConfigurationException ex = new FirefoxExtensionConfigurationException(someText);
        assertEquals("Message not properly assigned.", someText, ex.getMessage());
    }

    /**
     * Tests that FirefoxExtensionConfigurationException(String, Throwable) constructor works.
     */
    public void testCtor2_1() {
        FirefoxExtensionConfigurationException ex = new FirefoxExtensionConfigurationException(someText, inner);
        assertTrue("Message not properly assigned.", ex.getMessage().indexOf(someText) > -1);
    }

    /**
     * Tests that FirefoxExtensionConfigurationException(String, Throwable) constructor works.
     */
    public void testCtor2_2() {
        FirefoxExtensionConfigurationException ex = new FirefoxExtensionConfigurationException(someText, inner);
        assertEquals("Inner exception not properly assigned.", inner, ex.getCause());
    }
}
