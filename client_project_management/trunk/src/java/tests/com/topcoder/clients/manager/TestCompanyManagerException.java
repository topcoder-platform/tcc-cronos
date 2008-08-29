/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.model.Company;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.TestCase;

/**
 * Unit test cases for class <code>CompanyManagerException</code>.
 *
 * @author Chenhong
 * @version 1.0
 *
 */
public class TestCompanyManagerException extends TestCase {

    /**
     * Test method for 'CompanyManagerException(Company company)'.
     */
    public void testCompanyManagerExceptionClientClientStatus() {
        CompanyManagerException e = new CompanyManagerException(null);

        assertNull("The company is null.", e.getCompany());
    }

    /**
     * Test method for 'CompanyManagerException(String message, Company company)'.
     */
    public void testCompanyManagerExceptionStringClientClientStatus() {
        Company c = new Company();
        CompanyManagerException e = new CompanyManagerException("error", c);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to company.", c, e.getCompany());

    }

    /**
     * Test method for 'CompanyManagerException(String message, Throwable cause, Company company)'.
     */
    public void testCompanyManagerExceptionStringThrowableClientClientStatus() {
        Company c = new Company();

        Exception cause = new NullPointerException("NPE");
        CompanyManagerException e = new CompanyManagerException("error", cause, c);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to company.", c, e.getCompany());
        assertEquals("The cause should be npe.", cause, e.getCause());

    }

    /**
     * Test method for 'CompanyManagerException(String, Throwable, ExceptionData, Client, ClientStatus)'.
     */
    public void testCompanyManagerExceptionStringThrowableExceptionDataClientClientStatus() {
        Company c = new Company();
        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        Exception cause = new NullPointerException("NPE");
        CompanyManagerException e = new CompanyManagerException("error", cause, data, c);

        assertEquals("Equal to 'error'", "error", e.getMessage());

        assertEquals("Equal to company.", c, e.getCompany());
        assertEquals("The cause should be npe.", cause, e.getCause());

        assertEquals("Equal to 'code'", "code", e.getApplicationCode());
    }

    /**
     * Test method for 'Company getCompany()'.
     */
    public void testGetCompany() {
        Company c = new Company();

        ExceptionData data = new ExceptionData();
        data.setApplicationCode("code");

        Exception cause = new NullPointerException("NPE");
        CompanyManagerException e = new CompanyManagerException("error", cause, data, c);

        assertEquals("Equal to company.", c, e.getCompany());

    }

}
