/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.ejb;

import com.topcoder.timetracker.rates.MockSessionContext;
import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.timetracker.rates.TestHelper;

import junit.framework.TestCase;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Test case for RateEjb. The RateEjb uses InformixRatePersistence as default dao, and the InformixRatePersistence
 * is configured properly. Most of the tests are similar to InformixRatePersistenceTest, so that to test whether the
 * methods are successfully delegated to the dao.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RateEjbTest extends TestCase {
    /** Default RateEjb instance used in this test. */
    private RateEjb ejb;

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}. Rates are added and test whether they can be retrieved
     * after that.
     *
     * @throws Exception to JUnit
     */
    public void testAddRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        ejb.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be persisted", ejb.retrieveRate(rates[i].getId(),
                    rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}. One of the rate is invalid, but the rest should be
     * added successfully.
     *
     * @throws Exception to JUnit
     */
    public void testAddRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        ejb.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            if (rates[i].getCompany() == null) {
                continue;
            }

            assertNotNull("rate should be persisted", ejb.retrieveRate(rates[i].getId(),
                    rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}. Duplicate Rates are added, only one of them can be
     * added. Then retrieve them to test whether it's added successfully
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesDuplicateRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[1] = rates[0];

        ejb.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be persisted", ejb.retrieveRate(rates[i].getId(),
                    rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesEmpty() throws Exception {
        try {
            ejb.addRates(new Rate[2], true);
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
            ejb.addRates(rates, true);
            fail(
                "none of the rate can be persisted for the fk_comp_rate_rate constraint,"
                + "RatePersistenceException is expected");
        } catch (RatePersistenceException e) {
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
            ejb.addRates(null, true);
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
            ejb.addRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#addRates(Rate[], boolean)}, adding rates without company. Rates without company can
     * not be persisted and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testAddRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            ejb.addRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException is expected"
                + "since no rate is persisted");
        } catch (RatePersistenceException e) {
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
        ejb.addRates(rates, true);

        ejb.deleteRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNull("rate should be deleted, id:" + rates[i].getId() + " compId:" + rates[i].getCompany().getId(),
                ejb.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)}. One of the rate is invalid but the rest should be
     * deleted successfully.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        ejb.addRates(rates, true);

        rates[0].setCompany(null); //invalid rate
        ejb.deleteRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            if (rates[i].getCompany() == null) {
                continue;
            }

            assertNull("rate should be deleted, id:" + rates[i].getId() + " compId:" + rates[i].getCompany().getId(),
                ejb.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesEmpty() throws Exception {
        try {
            ejb.deleteRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)}. Deleting a list of not existent rates should be
     * failed and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesNotExistentRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        try {
            ejb.deleteRates(rates, true);
            fail("none of the rate exists in persistence, RatePersistenceException is expected since no"
                    + "rate is deleted");
        } catch (RatePersistenceException e) {
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
            ejb.deleteRates(null, true);
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
            ejb.deleteRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#deleteRates(Rate[], boolean)} with rates that have no company. Rates without
     * company can not be deleted and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testDeleteRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            ejb.deleteRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException is expected since"
                + "no rate is deleted");
        } catch (RatePersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#ejbActivate()}. This method does nothing.
     */
    public void testEjbActivate() {
        ejb.ejbActivate();

        //nothing should happen
    }

    /**
     * Tests {@link RateEjb#ejbCreate())}. The RateEjb should be initialized successfully.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreate() throws Exception {
        assertNotNull("ejb should be initialized successfully", ejb);
    }

    /**
     * Tests {@link RateEjb#ejbCreate())}, the ObjectFactory namespace is not configured, default value will be
     * used and no exception is expected.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreateMissingOFNamespace() throws Exception {
        RateEjb ejb = new RateEjb();
        ejb.setSessionContext(new MockSessionContext());

        Context context = new InitialContext();
        context.unbind("java:comp/env/of_namespace");

        ejb.ejbCreate();

        //should be success
    }

    /**
     * Tests {@link RateEjb#ejbCreate())}, the persistence key is not configured, CreateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testEjbCreateMissingPersistenceKey() throws Exception {
        RateEjb ejb = new RateEjb();
        ejb.setSessionContext(new MockSessionContext());

        Context context = new InitialContext();
        context.unbind("java:comp/env/of_rate_persistence_key");

        try {
            ejb.ejbCreate();
            fail("of_namespace does not exist in env entries and CreateException is expected");
        } catch (CreateException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#ejbCreate()}, the context is not set and CreateException is expected.
     */
    public void testEjbCreateNoContext() {
        RateEjb ejb = new RateEjb();

        try {
            ejb.ejbCreate();
            fail("session context is not set and CreateException is expected");
        } catch (CreateException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#ejbPassivate()}. This method does nothing.
     */
    public void testEjbPassivate() {
        ejb.ejbPassivate();

        //nothing should happen
    }

    /**
     * Tests {@link RateEjb#ejbRemove()}. This method does nothing.
     */
    public void testEjbRemove() {
        ejb.ejbRemove();

        //nothing should happen
    }

    /**
     * Tests {@link RateEjb#RateEjb()}, the instance should be created successfully.
     */
    public void testRateEjb() {
        assertNotNull("should be instantiated successfully", new RateEjb());
    }

    /**
     * Tests {@link RateEjb#retrieveRate(long, long)} for existent rates. The rates should be retrieved as
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateLongLong() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        ejb.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                + rates[i].getCompany().getId(), ejb.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
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
                ejb.retrieveRate(rates[i].getId(), rates[i].getCompany().getId()));
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
        ejb.addRates(rates, true);

        for (int i = 0; i < rates.length; i++) {
            assertNotNull("rate should be retrieved, id:" + rates[i].getId() + " compId:"
                + rates[i].getCompany().getId(),
                ejb.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
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
                ejb.retrieveRate(rates[i].getDescription(), rates[i].getCompany().getId()));
        }
    }

    /**
     * Tests {@link RateEjb#retrieveRate(String, long)} with null description, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRateStringLongNullDesc() throws Exception {
        try {
            ejb.retrieveRate(null, 1);
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
        ejb.addRates(rates, true);

        Rate[] result = ejb.retrieveRates(rates[0].getId());
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

        Rate[] result = ejb.retrieveRates(rates[0].getId());

        assertNotNull("retrieveRates(long) should never return null", result);

        assertEquals("should retrieve exactly 0 record", 0, result.length);
    }

    /**
     * Test {@link RateEjb#setSessionContext(javax.ejb.SessionContext)} with a MockSessionContext, nothing will
     * happen.
     */
    public void testSetSessionContext() {
        RateEjb ejb = new RateEjb();
        ejb.setSessionContext(new MockSessionContext());

        //nothing will happen
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for existent rates, the rates should be updated.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        ejb.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);
        ejb.updateRates(new Rate[] {rates[0] }, true);

        Rate result = ejb.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for existent rates. One of the rate is invalid but
     * the rest should be updated successfully.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRates2() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        ejb.addRates(rates, true);

        String modUser = "new user";
        rates[0].setModificationUser(modUser);

        rates[1].setCompany(null); //invalid rate
        ejb.updateRates(rates, true);

        Rate result = ejb.retrieveRate(rates[0].getId(), rates[0].getCompany().getId());

        assertEquals("modificationUser should be updated", modUser, result.getModificationUser());
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} with empty rates, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesEmpty() throws Exception {
        try {
            ejb.updateRates(new Rate[2], true);
            fail("rates is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for not existent rates, no rates should be updated
     * and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesNotExistentRates() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();

        try {
            ejb.updateRates(rates, true);
            fail(
                "none of the rate exists in persistence, RatePersistenceException is expected since"
                + "no rate is updated");
        } catch (RatePersistenceException e) {
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
            ejb.updateRates(null, true);
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
            ejb.updateRates(new Rate[] {TestHelper.getDefaultRates()[0], null }, true);
            fail("rates has null element and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link RateEjb#updateRates(Rate[], boolean)} for rates without company, no rates should be updated
     * and RatePersistenceException is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateRatesWithoutCompany() throws Exception {
        Rate[] rates = TestHelper.getDefaultRates();
        rates[0].setCompany(null);
        rates[1].setCompany(null);

        try {
            ejb.updateRates(rates, true);
            fail(
                "none of the rate has company, should be skipped and RatePersistenceException is expected"
                + "since no rate is updated");
        } catch (RatePersistenceException e) {
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
        TestHelper.loadConfig("RateEjbTest.xml");
        TestHelper.loadConfig("DBConnectionFactory.xml");
        TestHelper.initJNDI();

        TestHelper.initDB();
        ejb = new RateEjb();
        ejb.setSessionContext(new MockSessionContext());

        ejb.ejbCreate();
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
