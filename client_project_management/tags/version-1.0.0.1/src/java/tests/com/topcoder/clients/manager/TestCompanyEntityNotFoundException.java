/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>CompanyEntityNotFoundException </code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestCompanyEntityNotFoundException extends TestCase {

    /**
     * Represents the CompanyEntityNotFoundException instance for testing.
     */
    private CompanyEntityNotFoundException exception = null;

    /**
     * Test method for 'CompanyEntityNotFoundException()'.
     */
    public void testCompanyEntityNotFoundException() {
        assertNotNull("The CompanyEntityNotFoundException should be created.", new CompanyEntityNotFoundException());
    }

    /**
     * Test method for 'CompanyEntityNotFoundException(String)'.
     */
    public void testCompanyEntityNotFoundExceptionString() {
        exception = new CompanyEntityNotFoundException("error");

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

    }

    /**
     * Test method for 'CompanyEntityNotFoundException(String, Throwable)'.
     */
    public void testCompanyEntityNotFoundExceptionStringThrowable() {
        Exception cause = new NullPointerException("NPE");

        exception = new CompanyEntityNotFoundException("error", cause);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());
    }

    /**
     * Test method for 'CompanyEntityNotFoundException(String, Throwable, ExceptionData)'.
     */
    public void testCompanyEntityNotFoundExceptionStringThrowableExceptionData() {
        Exception cause = new NullPointerException("NPE");

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        exception = new CompanyEntityNotFoundException("error", cause, data);

        assertEquals("The error message should be 'error'", "error", exception.getMessage());

        assertEquals("The cause should be npe.", cause, exception.getCause());

        assertEquals("Equal to 'code'", "code", exception.getApplicationCode());

    }

}
