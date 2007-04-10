/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.util.errorhandling.BaseException;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>CompanyDAOException</code>.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the exception can be instantiated with the error message
 * and cause properly propagated, and that it comes with correct inheritance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompanyDAOExceptionUnitTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String ERROR_MESSAGE = "test error message";

    /**
     * <p>
     * The problem company used for testing.
     * </p>
     */
    private static final Company COMPANY = new Company();

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message is properly propagated.
     * </p>
     */
    public void testCompanyDAOException1() {
        CompanyDAOException ce = new CompanyDAOException(ERROR_MESSAGE, COMPANY);

        assertNotNull("Unable to instantiate CompanyDAOException.", ce);
        assertEquals("Error message is not properly propagated to super class.", ERROR_MESSAGE, ce.getMessage());
        assertEquals("Problem company is not properly set.", COMPANY, ce.getProblemCompany());
    }

    /**
     * <p>
     * Creation test.
     * </p>
     *
     * <p>
     * Verifies the error message and the cause are properly propagated.
     * </p>
     */
    public void testCompanyDAOException2() {
        Exception cause = new Exception();
        CompanyDAOException ce = new CompanyDAOException(ERROR_MESSAGE, cause, COMPANY);

        assertNotNull("Unable to instantiate CompanyDAOException.", ce);
        assertEquals("Cause is not properly propagated to super class.", cause, ce.getCause());
        assertEquals("Problem company is not properly set.", COMPANY, ce.getProblemCompany());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies CompanyDAOException subclasses BaseException.
     * </p>
     */
    public void testCompanyDAOExceptionInheritance1() {
        assertTrue("CompanyDAOException does not subclass BaseException.",
            new CompanyDAOException(ERROR_MESSAGE, COMPANY) instanceof BaseException);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies CompanyDAOException subclasses BaseException.
     * </p>
     */
    public void testCompanyDAOExceptionInheritance2() {
        assertTrue("CompanyDAOException does not subclass BaseException.",
            new CompanyDAOException(ERROR_MESSAGE, new Exception(), COMPANY) instanceof BaseException);
    }
}
