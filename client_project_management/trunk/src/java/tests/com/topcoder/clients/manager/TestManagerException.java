/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ManagerException </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestManagerException extends TestCase {

    /**
     * Represents the ManagerException instance for testing.
     */
    private ManagerException exception = null;

    /**
     * Test method for 'ManagerException()'.
     */
    public void testManagerException() {
        assertNotNull("The ManagerException should be created.", new ManagerException());
    }

    /**
     * Test method for 'ManagerException(String)'.
     */
    public void testManagerExceptionString() {
        exception = new ManagerException("error");

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

    }

    /**
     * Test method for 'ManagerException(String, Throwable)'.
     */
    public void testManagerExceptionStringThrowable() {
        Exception cause = new NullPointerException("NPE");

        exception = new ManagerException("error", cause);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());
    }

    /**
     * Test method for 'ManagerException(String, Throwable, ExceptionData)'.
     */
    public void testManagerExceptionStringThrowableExceptionData() {
        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        exception = new ManagerException("error", cause, data);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());

        assertEquals("Equal to 'code'", "code", exception.getApplicationCode());

    }

}
