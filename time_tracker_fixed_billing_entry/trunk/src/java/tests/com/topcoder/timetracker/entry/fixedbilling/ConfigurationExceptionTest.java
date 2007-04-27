/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test cases for <code>{@link ConfigurationException}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class ConfigurationExceptionTest extends TestCase {
    /** The String instance for testing. */
    private String message;

    /** The Throwable instance for testing. */
    private Throwable cause;

    /** The ConfigurationException instance for testing. */
    private ConfigurationException exception;

    /**
     * Set up the initial values.
     */
    public void setUp() {
        message = "msg";
        cause = new Exception();
    }

    /**
     * Test the <code>{@link ConfigurationException#ConfigurationException(String)}</code> with success process.
     */
    public void testConstructor_String_Success() {
        exception = new ConfigurationException(message);
        assertNotNull("Unable to create the instance.", exception);
        assertEquals("The return message should be same.", message, exception.getMessage());
        assertNull("The return exception should be null.", exception.getCause());
    }

    /**
     * Test the <code>{@link ConfigurationException#ConfigurationException(String, Throwable)}</code> with success
     * process.
     */
    public void testConstructor_StringThrowable_Success() {
        exception = new ConfigurationException(message, cause);
        assertNotNull("Unable to create the instance.", exception);
        assertTrue("The return message should be contained.", exception.getMessage().indexOf(message) != -1);
        assertEquals("The return exception should be same.", cause, exception.getCause());
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(ConfigurationExceptionTest.class);
    }
}
