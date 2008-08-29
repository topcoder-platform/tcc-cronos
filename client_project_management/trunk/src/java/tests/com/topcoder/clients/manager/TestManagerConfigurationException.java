/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ManagerConfigurationException </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestManagerConfigurationException extends TestCase {

    /**
     * Represents the ManagerConfigurationException instance for testing.
     */
    private ManagerConfigurationException exception = null;

    /**
     * Test method for 'ManagerConfigurationException()'.
     */
    public void testManagerConfigurationException() {
        assertNotNull("The ManagerConfigurationException should be created.", new ManagerConfigurationException());
    }

    /**
     * Test method for 'ManagerConfigurationException(String)'.
     */
    public void testManagerConfigurationExceptionString() {
        exception = new ManagerConfigurationException("error");

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

    }

    /**
     * Test method for 'ManagerConfigurationException(String, Throwable)'.
     */
    public void testManagerConfigurationExceptionStringThrowable() {
        Exception cause = new NullPointerException("NPE");

        exception = new ManagerConfigurationException("error", cause);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());
    }

    /**
     * Test method for 'ManagerConfigurationException(String, Throwable, ExceptionData)'.
     */
    public void testManagerConfigurationExceptionStringThrowableExceptionData() {
        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        exception = new ManagerConfigurationException("error", cause, data);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());

        assertEquals("Equal to 'code'", "code", exception.getApplicationCode());

    }

}
