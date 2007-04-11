/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.Company;


/**
 * Demo of this component.
 *
 * @author TCSDEVELOPER
 * @version 3.2
  */
public class DemoTest extends TestCase {
    /**
     * Demo.
     *
     * @throws Exception to JUnit
     */
    public void testDemo() throws Exception {
        //Create a new manager, using default namespace
        RateManager rateman = null;

        try {
            rateman = new RateManager();
        } catch (RateConfigurationException rce) {
            //should not happen if configured properly
        }

        //Create a new rate for company #3, type #21
        Company myCompany = new Company();
        myCompany.setId(1);

        //Note - no company set
        Rate myRate = new Rate();
        myRate.setId(1);
        myRate.setCompany(null);
        myRate.setRate(50.0);
        myRate.setCreationDate(new Date());
        myRate.setCreationUser("user");
        myRate.setModificationDate(new Date());
        myRate.setModificationUser("user");

        //Persist:
        try {
            rateman.addRate(myRate, true);
        } catch (RateManagerException rme) {
            // fails, so no audit
            // this should be thrown, as null company is invalid
            System.out.println("company should not be null");
        }

        //Persist with batch
        myRate.setCompany(myCompany);
        Rate[] rates = new Rate[1];
        rates[0] = myRate;
        rateman.addRates(rates, true); // 1 Audit header added

        //Double the rates to please the staff, persist the change
        myRate.setRate(myRate.getRate() * 2.0);
        rateman.updateRate(myRate, false); // Don¡¯t audit

        //retrieve the rate:
        Rate[] companyRates = rateman.retrieveRates(myCompany.getId());
        assertTrue((companyRates.length == 1) && (companyRates[0].getId() == 1));

        //remove the rate (in batch) then try to retrieve it:
        rateman.deleteRates(rates, true); // 1 Audit header added
        assertTrue(rateman.retrieveRate(21, 3) == null); // no match, null returned.
    }

    /**
     * Sets up test environment. This will load the config, configure JNDI for MockEJB, clear the relative
     * database tables and insert 2 records into table "rate". See {@link TestHelper#getDefaultRates()} for the
     * default rates that are added.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig("DemoConfig.xml");
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.initJNDI();
        TestHelper.initDB();
    }

    /**
     * Clears the test environment. This will clear all the namesapce in CM and restore the JNDI config.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.restoreJNDI();

        TestHelper.clearConfig();

        //clears the default rates so that it may be regenerated before the next test.
        TestHelper.clearDefaultRates();
    }
}
