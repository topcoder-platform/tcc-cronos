/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.failuretests;

import java.util.Hashtable;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateManagerException;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.timetracker.rates.ejb.LocalHomeRate;
import com.topcoder.timetracker.rates.ejb.LocalRate;
import com.topcoder.timetracker.rates.ejb.RateEjb;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rates.ejb.RateEjb}</code> class.
 *
 * @author mittu
 * @version 3.2
 */
public class RateEjbTest extends TestCase {
    /**
     * <p>
     * Represents the InitialContext for test.
     * </p>
     */
    private InitialContext context;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.addConfig();
        Map env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, MockContextFactory.class.getName());
        context = new InitialContext((Hashtable) env);
        FailureTestHelper.mockEJBDeploy(context);
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.releaseConfigs();
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * No env context to lookup.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_1() throws Exception {
        try {
            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * Unknown object factory namespace.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_2() throws Exception {
        setEnvironment("of.unknown", "ifxPersistence");
        try {
            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * object factory namespace format error.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_3() throws Exception {
        setEnvironment("of.bad", "ifxPersistence");
        try {

            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * missing rate_persistence_key.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_4() throws Exception {
        context.bind("java:comp/env/of_namespace", "of.valid");
        System.setProperty("of_namespace", "of.valid");
        try {
            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * rate_persistence_key not in object factory namespace.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_5() throws Exception {
        setEnvironment("of.valid", "unknown");
        try {
            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#ejbCreate()}</code>.
     * <p>
     * ClassCastException during object creation.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodEjbCreate_failure_6() throws Exception {
        setEnvironment("of.valid", "classCast");
        try {

            LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

            homeRate.create();
            fail("CreationException expected");
        } catch (CreateException e) {
            // expect
            FailureTestHelper.mockEJBRevert();
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#retrieveRates(long)}</code>.
     * <p>
     * EJB with Bad Connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRates_long_failure() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.retrieveRates(144);
            fail("RateManagerException expected");
        } catch (RateManagerException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_1() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(null, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_2() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(new Rate[] {}, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates contains <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_3() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(new Rate[] { null }, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_4() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.deleteRates(new Rate[] { FailureTestHelper.createRate(1, 45.67) }, false);
            fail("RateManagerException expected");
        } catch (RateManagerException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#retrieveRate(long,long)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_long_long_failure() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.retrieveRate(1, 133);
            fail("RateManagerException expected");
        } catch (RateManagerException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#retrieveRate(String,long)}</code>.
     * <p>
     * description is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_String_long_falure_1() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.retrieveRate(null, 123);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#retrieveRate(String,long)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_String_long_falure_2() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.retrieveRate("desc", 133);
            fail("RateManagerException expected");
        } catch (RateManagerException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_1() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(null, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_2() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(new Rate[] {}, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_3() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.deleteRates(new Rate[] { null }, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#addRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_4() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.deleteRates(new Rate[] { FailureTestHelper.createRate(1, 99.2) }, false);
            fail("RateManagerException expected");
        } catch (RateManagerException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_1() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.updateRates(null, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_2() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.updateRates(new Rate[] {}, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_3() throws Exception {
        LocalRate rateEjb = getRateEjb();
        try {
            rateEjb.updateRates(new Rate[] { null }, false);
            fail("EJBException expected");
        } catch (EJBException e) {
            // expect
            assertTrue("Cause should be IAE", e.getCausedByException() instanceof IllegalArgumentException);
        }
    }

    /**
     * Failure test for <code>{@link RateEjb#updateRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_4() throws Exception {
        LocalRate rateEjb = getRateEjbBad();
        try {
            rateEjb.updateRates(new Rate[] { FailureTestHelper.createRate(1, 111.11) }, false);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * <p>
     * Sets the environment for testing.
     * </p>
     *
     * @return the local rate.
     *
     * @throws Exception
     *             to junit.
     */
    private LocalRate getRateEjb() throws Exception {
        setEnvironment("of.valid", "informix_rate_persistence");
        LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

        return homeRate.create();

    }

    /**
     * <p>
     * Sets the environment for testing bad connection.
     * </p>
     *
     * @return the local rate.
     *
     * @throws Exception
     *             to junit.
     */
    private LocalRate getRateEjbBad() throws Exception {
        setEnvironment("of.valid", "informix_rate_persistence_bad");
        LocalHomeRate homeRate = (LocalHomeRate) context.lookup("java:comp/env/rates");

        return homeRate.create();

    }

    /**
     * <p>
     * Sets the environment.
     * </p>
     *
     * @param p1
     *            property 1
     * @param p2
     *            property 2
     * @throws Exception
     *             if any error occurs.
     */
    private void setEnvironment(String p1, String p2) throws Exception {
        context.bind("java:comp/env/of_namespace", p1);
        context.bind("java:comp/env/of_rate_persistence_key", p2);
        System.setProperty("of_namespace", p1);
        System.setProperty("of_rate_persistence_key", p2);
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RateEjbTest.class);
    }
}
