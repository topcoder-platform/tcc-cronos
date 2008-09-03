/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>ProjectEntityNotFoundException </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestProjectEntityNotFoundException extends TestCase {

    /**
     * Represents the ProjectEntityNotFoundException instance for testing.
     */
    private ProjectEntityNotFoundException exception = null;

    /**
     * Test method for 'ProjectEntityNotFoundException()'.
     */
    public void testProjectEntityNotFoundException() {
        assertNotNull("The ProjectEntityNotFoundException should be created.", new ProjectEntityNotFoundException());
    }

    /**
     * Test method for 'ProjectEntityNotFoundException(String)'.
     */
    public void testProjectEntityNotFoundExceptionString() {
        exception = new ProjectEntityNotFoundException("error");

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

    }

    /**
     * Test method for 'ProjectEntityNotFoundException(String, Throwable)'.
     */
    public void testProjectEntityNotFoundExceptionStringThrowable() {
        Exception cause = new NullPointerException("NPE");

        exception = new ProjectEntityNotFoundException("error", cause);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());
    }

    /**
     * Test method for 'ProjectEntityNotFoundException(String, Throwable, ExceptionData)'.
     */
    public void testProjectEntityNotFoundExceptionStringThrowableExceptionData() {
        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        exception = new ProjectEntityNotFoundException("error", cause, data);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());

        assertEquals("Equal to 'code'", "code", exception.getApplicationCode());

    }

}
