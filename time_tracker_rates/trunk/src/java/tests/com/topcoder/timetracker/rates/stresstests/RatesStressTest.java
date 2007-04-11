/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.stresstests;

import java.util.Date;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateConfigurationException;
import com.topcoder.timetracker.rates.RateManager;
import com.topcoder.timetracker.rates.RateManagerException;
import com.topcoder.timetracker.rates.TestHelper;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the stress test for Time Tracker Rates.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class RatesStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int[] COUNTS = new int[]{1, 100, 500, 2000 };

    /**
     * Sets up test environment. This will load the config, configure JNDI for MockEJB, clear the
     * relative database tables and insert 2 records into table "rate". See
     * {@link TestHelper#getDefaultRates()} for the default rates that are added.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        StressTestHelper.loadConfig("stress/Config.xml");
        StressTestHelper.loadConfig("stress/DBConnectionFactory.xml");
        StressTestHelper.initJNDI();
        StressTestHelper.initDB();
    }

    /**
     * Clears the test environment. This will clear all the namesapce in CM and restore the JNDI
     * config.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        StressTestHelper.restoreJNDI();
        StressTestHelper.clearConfig();
        StressTestHelper.clearDefaultRates();
    }

    /**
     * <p>
     * This method tests the creation of rate manager in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForCreateRateManager() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create the rate manager reference
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    // should not happen if configured properly
                }

                rateManager = null;
            }
        }
    }

    /**
     * <p>
     * This method tests the functionality of adding rate of null company in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForAddingRateOfNullCompany() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create the rate manager reference
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    // should not happen if configured properly
                }

                // create a new rate for company #3, type #21
                Company myCompany = new Company();
                myCompany.setId(3);

                // create the rete with null company
                Rate myRate = new Rate();
                myRate.setId(21);
                myRate.setCompany(null);
                myRate.setRate(50.0);

                // persist
                try {
                    rateManager.addRate(myRate, true);
                } // fails, so no audit
                catch (RateManagerException e) {
                    // expected. This exception should be thrown, as null company is invalid
                }
            }
        }
    }

    /**
     * <p>
     * This method tests the functionality of adding rates with valid company of this component in
     * high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForAddingRates() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create a new manager, using default namespace
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    //should not happen if configured properly
                }

                // create a new rate for company #3, type #21
                Company myCompany = new Company();
                myCompany.setId(1);

                // note - no company set
                Rate myRate = new Rate();
                myRate.setId(1);
                myRate.setCompany(null);
                myRate.setRate(50.0);
                myRate.setCreationDate(new Date());
                myRate.setCreationUser("user");
                myRate.setModificationDate(new Date());
                myRate.setModificationUser("user");
                myRate.setCompany(myCompany);
                
                Rate[] rates = new Rate[1];
                rates[0] = myRate;
                rateManager.addRates(rates, true);
                
                //Clear the database
                rateManager.deleteRates(rates, true);
            }
        }
    }

    /**
     * <p>
     * This method tests the functionality of update the rate in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForUpdatingRate() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create the rate manager
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    //should not happen if configured properly
                }

                // create a new rate for company #3, type #21
                Company myCompany = new Company();
                myCompany.setId(1);

                Rate myRate = new Rate();
                myRate.setId(1);
                myRate.setCompany(null);
                myRate.setRate(50.0);
                myRate.setCreationDate(new Date());
                myRate.setCreationUser("user");
                myRate.setModificationDate(new Date());
                myRate.setModificationUser("user");
                myRate.setCompany(myCompany);

                //Persist with batch
                Rate[] rates = new Rate[1];
                rates[0] = myRate;
                rateManager.addRates(rates, true);
                
                //Double the rates to please the staff, persist the change
                myRate.setRate(myRate.getRate() * 2.0);
                rateManager.updateRate(myRate, false);
                
                //Clear the database
                rateManager.deleteRates(rates, true);
            }
        }
    }

    /**
     * <p>
     * This method tests the functionality of retrieving rates in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForRetrievingRates() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create a new manager, using default namespace
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    //should not happen if configured properly
                }

                // create a new rate for company #3, type #21
                Company myCompany = new Company();
                myCompany.setId(1);

                Rate myRate = new Rate();
                myRate.setId(1);
                myRate.setCompany(null);
                myRate.setRate(50.0);
                myRate.setCreationDate(new Date());
                myRate.setCreationUser("user");
                myRate.setModificationDate(new Date());
                myRate.setModificationUser("user");
                myRate.setCompany(myCompany);

                // persist with batch
                Rate[] rates = new Rate[1];
                rates[0] = myRate;
                rateManager.addRates(rates, true);
                
                // retrieve the rate:
                Rate[] companyRates = rateManager.retrieveRates(myCompany.getId());
                assertTrue((companyRates.length == 1) && (companyRates[0].getId() == 1));
                
                // clear the database
                rateManager.deleteRates(rates, true);
            }
        }
    }

    /**
     * <p>
     * This method tests the functionality of removing rates in high stress.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testRatesStressForRemovingRates() throws Exception {
        for (int count = 0; count < COUNTS.length; ++count) {
            for (int i = 0; i < count; ++i) {
                // create a new manager, using default namespace
                RateManager rateManager = null;

                try {
                    rateManager = new RateManager();
                } catch (RateConfigurationException rce) {
                    //should not happen if configured properly
                }

                // create a new rate for company #3, type #21
                Company myCompany = new Company();
                myCompany.setId(1);

                Rate myRate = new Rate();
                myRate.setId(1);
                myRate.setCompany(null);
                myRate.setRate(50.0);
                myRate.setCreationDate(new Date());
                myRate.setCreationUser("user");
                myRate.setModificationDate(new Date());
                myRate.setModificationUser("user");
                myRate.setCompany(myCompany);

                // persist with batch
                Rate[] rates = new Rate[1];
                rates[0] = myRate;
                rateManager.addRates(rates, true);
                
                // clear the database
                rateManager.deleteRates(rates, true);
            }
        }
    }
}
