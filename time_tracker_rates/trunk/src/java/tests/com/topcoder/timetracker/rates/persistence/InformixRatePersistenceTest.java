/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.persistence;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateConfigurationException;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.timetracker.rates.TestHelper;

import junit.framework.TestCase;


/**
 * Test case for InformixRatePersistence.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class InformixRatePersistenceTest extends TestCase {
    /** Default namespace that used in this test. */
    private static final String DEFAULT_NAMESPACE = "valid";

    /** Default InformixRatePersistence instance used in this test. */
    private InformixRatePersistence persistence;

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)}. Rates are added and test whether they
     * can be retrieved after that.
     *
     * @throws Exception to JUnit
     */
    public void testAddRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be persisted",
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)}. One of the rate is invalid, but the
     * rest should be added successfully.
     *
     * @throws Exception to JUnit
     */
    public void testAddRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        persistence.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            if (rates[i].getCompany() == null) {
                continue;
            }

            assertNotNull("rate should be persisted",
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)}. Duplicate Rates are added, only one of
     * them can be added. Then retrieve them to test whether it's added successfully
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesDuplicateRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[1] = rates[0];

        persistence.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be persisted",
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesEmpty() throws Exception {
        try {
            persistence.addRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)}, adds rates that with the id not exist
     * in table "rate",  should be failed because of the fk_comp_rate_rate constraint.
     */
    public void testAddRatesNotExistentRates() {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setId(5); //there's no such id of rate in table "rate"
        rates[1].setId(6); //there's no such id of rate in table "rate"

        try {
            persistence.addRates(rates, true);
            fail(
                "none of the rate can be persisted for the fk_comp_rate_rate constraint,"
                    + "RatePersistenceException is expected");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesNull() throws Exception {
        try {
            persistence.addRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)} with rates containing null, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesNullElem() throws Exception {
        try {
            persistence.addRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#addRates(Rate[], boolean)}, adding rates without company. Rates
     * without company can not be persisted and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            persistence.addRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException"
                    + "is expected since no rate is persisted");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)}. Deleting the existent rates should
     * be success.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        persistence.deleteRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should be deleted, id:" + rates[i].getId() + " compId:" + rates[i].getCompany().getId(),
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)}. One of the rate is invalid but the
     * rest should be deleted successfully.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        rates[0].setCompany(null); //invalid rate
        persistence.deleteRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            if (rates[i].getCompany() == null) {
                continue;
            }

            assertNull("rate should be deleted, id:" + rates[i].getId() + " compId:" + rates[i].getCompany().getId(),
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesEmpty() throws Exception {
        try {
            persistence.deleteRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)}. Deleting a list of not existent
     * rates should be failed and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNotExistentRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        try {
            persistence.deleteRates(rates, true);
            fail(
                "none of the rate exists in persistence, RatePersistenceException is expected since"
                + "no rate is deleted");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNull() throws Exception {
        try {
            persistence.deleteRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)} with rates containing null element,
     * IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNullElem() throws Exception {
        try {
            persistence.deleteRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#deleteRates(Rate[], boolean)} with rates that have no company.
     * Rates without company can not be deleted and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            persistence.deleteRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException"
                + "is expected since no rate is deleted");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with valid namespace, it should be
     * instantiated successfully.
     *
     * @throws Exception to junit
     */
    public void testInformixRatePersistence() throws Exception {
        assertNotNull("InformixRatePersistence should be instantiated successfully", persistence);
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with empty namespace and IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixRatePersistenceEmpty() throws Exception {
        try {
            new InformixRatePersistence(" ");
            fail("namespace is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with config missing
     * "auditManagerKey", RateConfigurationException is expected.
     */
    public void testInformixRatePersistenceMissingAuditManagerKey() {
        try {
            new InformixRatePersistence("valid_missing_auditManagerKey");
            fail("auditManagerKey is not set in config and RateConfigurationException is expected");
        } catch (RateConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with config missing
     * "connectionName". Though connectionName is missing, persistence can still using the default connection, so any
     * persistence process should be success.
     *
     * @throws Exception to JUnit
     */
    public void testInformixRatePersistenceMissingConnName()
        throws Exception {
        InformixRatePersistence p = new InformixRatePersistence("valid_missing_connectionName");

        try {
            p.retrieveRates(1); //tests whether can do the persistence operation
        } catch (RatePersistenceException e) {
            fail("if connection name is not set, default connection will be used, no exception should be thrown");
        }

        //success
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with config missing
     * "connectionFactoryKey", RateConfigurationException is expected.
     */
    public void testInformixRatePersistenceMissingConnectionFactoryKey() {
        try {
            new InformixRatePersistence("valid_missing_connectionFactoryKey");
            fail("connectionFactoryKey is not set in config and RateConfigurationException is expected");
        } catch (RateConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with config missing "logName",
     * RateConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixRatePersistenceMissingLogName()
        throws Exception {
        assertNotNull("should be instantiated successfully even if log name is not provided",
            new InformixRatePersistence("valid_missing_logName"));
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with config missing
     * "objectFactoryNamespace", RateConfigurationException is expected.
     */
    public void testInformixRatePersistenceMissingObjectFactoryNamespace() {
        try {
            new InformixRatePersistence("valid_missing_objectFactoryNamespace");
            fail("objectFactoryNamespace is not set in config and RateConfigurationException is expected");
        } catch (RateConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with not existent namespace,
     * RateConfigurationException is expected.
     */
    public void testInformixRatePersistenceNotExistentNamespace() {
        try {
            new InformixRatePersistence("no_this_namespace");
            fail("namespace does not exist and RateConfigurationException is expected");
        } catch (RateConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#InformixRatePersistence(String)} with null namespace, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testInformixRatePersistenceNull() throws Exception {
        try {
            new InformixRatePersistence(null);
            fail("namespace is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRate(long, long)} for existent rates. The rates should be
     * retrieved as expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateLongLong() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                    + rates[i].getCompany().getId(), persistence.retrieveRate(rates[i].getId(),
                            rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRate(long, long)} for not existent rates, the returned
     * array should be not null but empty.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateLongLongNotExistentRate()
        throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should not be retrieved since it's not persisted yet, id:" + rates[i].getId()
                + " compId:" + rates[i].getCompany().getId(),
                persistence.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRate(String, long)} for existent rates. The rates should be
     * retrieved as expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLong() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                    + rates[i].getCompany().getId(),
                persistence.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRate(long, long)} for not existent rates, the returned
     * result should be null.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLongNotExistentRate()
        throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should not be retrieved since it's not persisted yet, id:" + rates[i].getId()
                    + " compId:" + rates[i].getCompany().getId(),
                persistence.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRate(String, long)} with null description, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLongNullDesc() throws Exception {
        try {
            persistence.retrieveRate(null, 1);
            fail("description is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRates(long)} for existent rates, all rates for the given
     * company should be retrieved.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        Rate[] result = persistence.retrieveRates(rates[0].getId());
        assertEquals("should retrieve exactly 2 records", 2, result.length);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("element should not be null", rates[i]);

            assertEquals("retrieve rate's id should equal", rates[i].getId(), result[i].getId());
        }
    }

    /**
     * Tests {@link InformixRatePersistence#retrieveRates(long)} for not existent rates, the returned result
     * should be null and not empty.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRatesNotExistentRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        Rate[] result = persistence.retrieveRates(rates[0].getId());

        assertNotNull("retrieveRates(long) should never return null", result);

        assertEquals("should retrieve exactly 0 record", 0, result.length);
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} for existent rates, the rates should
     * be updated.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);
        persistence.updateRates(new Rate[] {rates[0] }, true);

        Rate result = persistence.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} for existent rates. One of the rate
     * is invalid but the rest should be updated successfully.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        persistence.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);

        rates[1].setCompany(null); //invalid rate
        persistence.updateRates(rates, true);

        Rate result = persistence.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesEmpty() throws Exception {
        try {
            persistence.updateRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} for not existent rates, no rates
     * should be updated and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNotExistentRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        try {
            persistence.updateRates(rates, true);
            fail(
                "none of the rate exists in persistence, RatePersistenceException is expected since no rate"
                + "is updated");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNull() throws Exception {
        try {
            persistence.updateRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} with rates containing null element,
     * IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNullElem() throws Exception {
        try {
            persistence.updateRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link InformixRatePersistence#updateRates(Rate[], boolean)} for rates without company, no rates
     * should be updated and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            persistence.updateRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException is expected "
                + "since no rate is updated");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Sets up test environment. This will load the config, clear the relative database tables and insert 2
     * records into table "rate". See {@link TestHelper#getDefaultRates()} for the default rates that are added.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig("InformixRatePersistenceTest.xml");
        TestHelper.loadConfig("DBConnectionFactory.xml");

        TestHelper.initDB();
        persistence = new InformixRatePersistence(DEFAULT_NAMESPACE);
    }

    /**
     * Clears the test environment. This will clear all the namesapce in CM.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.clearConfig();

        //clears the default rates so that it may be regenerated before the next test.
        TestHelper.clearDefaultRates();
    }
}
