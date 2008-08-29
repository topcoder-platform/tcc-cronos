/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ClientEntityNotFoundException </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestClientEntityNotFoundException extends TestCase {

    /**
     * Represents the ClientEntityNotFoundException instance for testing.
     */
    private ClientEntityNotFoundException exception = null;

    /**
     * Test method for 'ClientEntityNotFoundException()'.
     */
    public void testClientEntityNotFoundException() {
        assertNotNull("The ClientEntityNotFoundException should be created.", new ClientEntityNotFoundException());
    }

    /**
     * Test method for 'ClientEntityNotFoundException(String)'.
     */
    public void testClientEntityNotFoundExceptionString() {
        exception = new ClientEntityNotFoundException("error");

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

    }

    /**
     * Test method for 'ClientEntityNotFoundException(String, Throwable)'.
     */
    public void testClientEntityNotFoundExceptionStringThrowable() {
        Exception cause = new NullPointerException("NPE");

        exception = new ClientEntityNotFoundException("error", cause);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());
    }

    /**
     * Test method for 'ClientEntityNotFoundException(String, Throwable, ExceptionData)'.
     */
    public void testClientEntityNotFoundExceptionStringThrowableExceptionData() {
        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        exception = new ClientEntityNotFoundException("error", cause, data);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());

        assertEquals("Equal to 'code'", "code", exception.getApplicationCode());

    }

}
