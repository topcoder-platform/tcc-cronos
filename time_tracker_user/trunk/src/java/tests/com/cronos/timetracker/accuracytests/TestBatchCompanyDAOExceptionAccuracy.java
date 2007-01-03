/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.company.Company;
import com.cronos.timetracker.company.BatchCompanyDAOException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>BatchCompanyDAOException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestBatchCompanyDAOExceptionAccuracy extends TestCase {

    /**
     * Represents the BatchCompanyDAOException instance for test.
     */
    private BatchCompanyDAOException exception = null;

    /**
     * Represents Company instances for test.
     */
    private Company[] company = null;

    /**
     * Represents the Throwable instances for test.
     */
    private Throwable[] causes = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        company = new Company[1];
        company[0] = new Company();
        company[0].setCompanyName("topcoder");

        causes = new Throwable[1];
        causes[0] = new NullPointerException("Null Pointer meet");

        exception = new BatchCompanyDAOException("error", causes, company);
    }

    /**
     * Test constructor.
     *
     */
    public void testBatchCompanyDAOException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * test method getProblemCompanies.
     *
     */
    public void testGetProblemCompany() {
        assertEquals("Equal is expected.", company, exception.getProblemCompanies());
    }

    /**
     * Test method getCauses.
     *
     */
    public void testGetCauses() {
        assertEquals("Equal is expected.", causes, exception.getCauses());
    }

}
