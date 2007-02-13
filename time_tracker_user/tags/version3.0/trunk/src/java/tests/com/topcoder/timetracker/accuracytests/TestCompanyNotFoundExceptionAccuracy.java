/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyNotFoundException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>CompanyNotFoundException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestCompanyNotFoundExceptionAccuracy extends TestCase {

    /**
     * Represents the CompanyNotFoundException instance for test.
     */
    private CompanyNotFoundException exception = null;

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

        exception = new CompanyNotFoundException("error", new NullPointerException(), company);
    }

    /**
     * Test constructor.
     *
     */
    public void testCompanyNotFoundException() {
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
