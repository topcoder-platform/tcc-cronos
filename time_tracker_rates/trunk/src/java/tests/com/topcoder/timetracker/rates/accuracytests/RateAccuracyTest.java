/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.accuracytests;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.rates.Rate;

/**
 * Accuracy tests for the clas: Rate.
 * 
 * @author kinfkong
 * @version 3.2
 *
 */
public class RateAccuracyTest extends TestCase {

    /**
     * Represents the rate for accuracy tests.
     */
    private Rate rate = null;
    
    /**
     * Sets up the test environment.
     */
    public void setUp() {
        rate = new Rate();
    }
    
    /**
     * Accuracy tests for the constructor.
     *
     */
    public void testRate() {
        assertNotNull("The instance cannot be created.", rate);
    }

    /**
     * Accuracy tests for the method: getCompany.
     *
     */
    public void testGetCompany() {
        Company company = new Company();
        company.setId(100);
        
        // set the company
        rate.setCompany(company);
        assertEquals("The method does not work properly", 100, rate.getCompany().getId());
    }

    /**
     * Accuracy tests for the method: getDescription.
     *
     */
    public void testGetDescription() {
        rate.setDescription("Accuracy tests");
        assertEquals("The method does not work properly.", "Accuracy tests", rate.getDescription());
    }

    /**
     * Accuracy test for the method: getRate.
     *
     */
    public void testGetRate() {
        rate.setRate(1.5);
        assertEquals("The method does not work properly.", 1.5, rate.getRate(), 1e-4);
    }

    /**
     * Accuracy tests for the method: setCompany.
     *
     */
    public void testSetCompany() {
        Company company = new Company();
        company.setId(100);
        
        // set the company
        rate.setCompany(company);
        assertEquals("The method does not work properly", 100, rate.getCompany().getId());
    }

    /**
     * Accuracy tests for the method: setDescription.
     *
     */
    public void testSetDescription() {
        rate.setDescription("Accuracy tests");
        assertEquals("The method does not work properly.", "Accuracy tests", rate.getDescription());
    }

    /**
     * Accuracy test for the method:setRate.
     *
     */
    public void testSetRate() {
        rate.setRate(1.5);
        assertEquals("The method does not work properly.", 1.5, rate.getRate(), 1e-4);
    }

}
