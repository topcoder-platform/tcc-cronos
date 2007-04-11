/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.timetracker.company.Company;

import junit.framework.TestCase;


/**
 * Unit test for Rate.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RateTest extends TestCase {
    /** The Rate instance used in this test. */
    private Rate rate;

    /**
     * Tests {@link Rate#getCompany()}, the company should equal to the value set.
     */
    public void testGetCompany() {
        Company comp = new Company();

        rate.setCompany(comp);

        assertEquals("company should be set properly", comp, rate.getCompany());
    }

    /**
     * Tests {@link Rate#getDescription()}, the result should equal to the value set.
     */
    public void testGetDescription() {
        String desc = "desc";
        rate.setDescription(desc);

        assertEquals("description should be set properly", desc, rate.getDescription());
    }

    /**
     * Tests {@link Rate#getRate()}, the result should equal to the value set.
     */
    public void testGetRate() {
        double r = 2.3;
        rate.setRate(r);

        assertEquals("rate should be set properly", r, rate.getRate(), 0);
    }

    /**
     * Tests {@link Rate#Rate()}. The instance should be created and the field should be initiated as expected.
     */
    public void testRate() {
        assertNotNull("Rate should be instantiated successfully", rate);

        assertNull("company should be null initially", rate.getCompany());
        assertNull("description should be null initially", rate.getDescription());
        assertEquals("rate should be 0 initially", 0, rate.getRate(), 0);
    }

    /**
     * Tests {@link Rate#setCompany(Company)}. Just set the argument, should be success.
     */
    public void testSetCompany() {
        rate.setCompany(new Company());

        //should be success
    }

    /**
     * Tests {@link Rate#setDescription(String)}. Just set the argument, should be success.
     */
    public void testSetDescription() {
        rate.setDescription("desc");

        //should be success
    }

    /**
     * Tests {@link Rate#setRate(double)}. Just set the argument, should be success.
     */
    public void testSetRate() {
        rate.setRate(2);

        //should be success
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        rate = new Rate();
    }
}
