/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import junit.framework.TestCase;

/**
 * Test case for RateManager. The RateManager uses RateEjb as default ejb object, and the EJB is configured
 * properly. Most of the tests are similar to {@link RateEjbTest}, so that to test whether the methods are
 * successfully delegated to the ejb.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RateManagerTest extends TestCase {
    /** Default manager instance used in this test. */
    private RateManager manager;

    /**
     * Tests {@link RateManager#addRate(Rate, boolean)}. Rate is added and test if it's persisted properly.
     *
     * @throws Exception to junit
     */
    public void testAddRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRate(rates[0], true);

        assertNotNull("rate should be persisted", manager.retrieveRate(rates[0].getId(),
                rates[0].getCompany().getId()));
    }

    /**
     * Tests {@link RateManager#addRate(Rate, boolean)}, adding a rate twice and RateManagerException is
     * expected.
     *
     * @throws Exception to junit
     */
    public void testAddRateDuplicatedRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRate(rates[0], true);

        try {
            manager.addRate(rates[0], true);
            fail("rate is duplicated, adding will failed and RateManagerException is expected");
        } catch (RateManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link RateManager#addRate(Rate, boolean)} will null rate, IAE is expected.
     *
     * @throws Exception to junit
     */
    public void testAddRateNull() throws Exception {
        try {
            manager.addRate(null, true);
            fail("rate is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}. Rates are added and test whether they can be retrieved
     * after that.
     *
     * @throws Exception to JUnit
     */
    public void testAddRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be persisted",
                manager.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesEmpty() throws Exception {
        try {
            manager.addRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}, adds rates that with the id not exist in table "rate",
     * should be failed because of the fk_comp_rate_rate constraint.
     */
    public void testAddRatesNotExistentRates() {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setId(5); //there's no such id of rate in table "rate"
        rates[1].setId(6); //there's no such id of rate in table "rate"

        try {
            manager.addRates(rates, true);
            fail(
                "none of the rate can be persisted for the fk_comp_rate_rate constraint,"
                + "RatePersistenceException is expected");
        } catch (RateManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesNull() throws Exception {
        try {
            manager.addRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)} with rates containing null, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesNullElem() throws Exception {
        try {
            manager.addRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}, adding rates without company. Rates without company can
     * not be persisted and RateManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            manager.addRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RateManagerException is expected since no"
                + "rate is persisted");
        } catch (RateManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link RateManager#addRates(Rate, boolean)}. A persisted rate should be deleted successfully.
     *
     * @throws Exception to junit
     */
    public void testDeleteRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        manager.deleteRate(rates[0], true);

        assertNull("rate should be deleted, id:" + rates[0].getId() + " compId:" + rates[0].getCompany().getId(),
            manager.retrieveRate(rates[0].getId(), rates[0].getCompany().getId()));
    }



    /**
     * Tests {@link RateManager#addRates(Rate, boolean)} with null rate and IllegalArgumentException is
     * expected.
     *
     * @throws Exception to junit
     */
    public void testDeleteRateNull() throws Exception {
        try {
            manager.deleteRate(null, true);
            fail("rate is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)}. Deleting the existent rates should be success.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        manager.deleteRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should be deleted, id:" + rates[i].getId() + " compId:" + rates[i].getCompany().getId(),
                manager.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesEmpty() throws Exception {
        try {
            manager.deleteRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }



    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNull() throws Exception {
        try {
            manager.deleteRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with rates containing null element, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNullElem() throws Exception {
        try {
            manager.deleteRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with rates that have no company. Rates without
     * company can not be deleted and RateManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            manager.deleteRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RateManagerException is expected"
                + "since no rate is deleted");
        } catch (RateManagerException e) {
            //success
        }
    }

    /**
     * Tests {@link RateManager#getInitialContext()}. Should return non-null.
     *
     * @throws Exception to junit
     */
    public void testGetInitialContext() throws Exception {
        assertNotNull("should return non-null", manager.getInitialContext());
    }

    /**
     * Tests {@link RateManager#RateManager()}, it should be instantiated successfully.
     */
    public void testRateManager() {
        assertNotNull("manager should be instantiated successfully", manager);
    }

    /**
     * Tests {@link RateManager#RateManager(String)}, it should be instantiated successfully.
     *
     * @throws Exception to junit
     */
    public void testRateManagerString() throws Exception {
        assertNotNull("manager should be instantiated successfully", new RateManager(RateManager.DEFAULT_NAMESPACE));
    }

    /**
     * Tests {@link RateManager#RateManager(String)} with empty string, IAE is expected.
     *
     * @throws Exception to junit
     */
    public void testRateManagerStringEmpty() throws Exception {
        try {
            new RateManager(" ");
            fail("namespace is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateManager#RateManager(String)} with null string, IAE is expected.
     *
     * @throws Exception to junit
     */
    public void testRateManagerStringNull() throws Exception {
        try {
            new RateManager(null);
            fail("namespace is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(long, long)} for existent rates. The rates should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateLongLong() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                + rates[i].getCompany().getId(), manager.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(long, long)} for not existent rates, the returned array should be not
     * null but empty.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateLongLongNotExistentRate()
        throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should not be retrieved since it's not persisted yet, id:" + rates[i].getId()
                    + " compId:" + rates[i].getCompany().getId(),
                manager.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(String, long)} for existent rates. The rates should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLong() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                + rates[i].getCompany().getId(),
                manager.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(long, long)} for not existent rates, the returned result should be
     * null.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLongNotExistentRate()
        throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should not be retrieved since it's not persisted yet, id:" + rates[i].getId()
                + " compId:" + rates[i].getCompany().getId(),
                manager.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(String, long)} with null description, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLongNullDesc() throws Exception {
        try {
            manager.retrieveRate(null, 1);
            fail("description is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRates(long)} for existent rates, all rates for the given company should be
     * retrieved.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        Rate[] result = manager.retrieveRates(rates[0].getId());
        assertEquals("should retrieve exactly 2 records", 2, result.length);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("element should not be null", rates[i]);

            assertEquals("retrieve rate's id should equal", rates[i].getId(), result[i].getId());
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRates(long)} for not existent rates, the returned result should be null and
     * not empty.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRatesNotExistentRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        Rate[] result = manager.retrieveRates(rates[0].getId());

        assertNotNull("retrieveRates(long) should never return null", result);

        assertEquals("should retrieve exactly 0 record", 0, result.length);
    }

    /**
     * Tests {@link RateManager#updateRate(Rate, boolean)}, rate should be updated successfully.
     *
     * @throws Exception to junit
     */
    public void testUpdateRate() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);
        manager.updateRate(rates[0], true);

        Rate result = manager.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }



    /**
     * Tests {@link RateManager#updateRate(Rate, boolean)} with null, IAE is expected.
     *
     * @throws Exception to junit
     */
    public void testUpdateRateNull() throws Exception {
        try {
            manager.updateRate(null, true);
            fail("rate is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for existent rates, the rates should be updated.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        manager.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);
        manager.updateRates(new Rate[] {rates[0] }, true);

        Rate result = manager.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesEmpty() throws Exception {
        try {
            manager.updateRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }



    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} with null rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNull() throws Exception {
        try {
            manager.updateRates(null, true);
            fail("rates is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} with rates containing null element, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNullElem() throws Exception {
        try {
            manager.updateRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for rates without company, no rates should be updated
     * and RateManagerException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            manager.updateRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RateManagerException is expected since"
                + "no rate is updated");
        } catch (RateManagerException e) {
            //success
        }
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
        TestHelper.loadConfig("RateManagerTest.xml");
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.initJNDI();
        TestHelper.initDB();

        manager = new RateManager();
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
