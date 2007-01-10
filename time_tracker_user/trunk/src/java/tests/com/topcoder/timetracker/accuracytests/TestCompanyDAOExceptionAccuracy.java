/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyDAOException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>CompanyDAOException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestCompanyDAOExceptionAccuracy extends TestCase {

    /**
     * Represents the CompanyDAOException instance for test.
     */
    private CompanyDAOException exception = null;

    /**
     * Represents Company instance for test.
     */
    private Company company = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        company = new Company();
        company.setCompanyName("topcoder");

        exception = new CompanyDAOException("error", new NullPointerException(), company);
    }

    /**
     * Test constructor.
     *
     */
    public void testCompanyDAOException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * test method getProblemCompany.
     *
     */
    public void testGetProblemCompany() {
        assertEquals("Equal is expected.", company, exception.getProblemCompany());
    }

}
