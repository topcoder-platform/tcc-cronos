/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.failuretests;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.jndi.MockContextFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateConfigurationException;
import com.topcoder.timetracker.rates.RateManager;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rates.RateManager}</code> class.
 *
 * @author mittu
 * @version 3.2
 */
public class RateManagerTest extends TestCase {
    /**
     * <p>
     * Represents the InitialContext for test.
     * </p>
     */
    private InitialContext context;

    /**
     * <p>
     * Represents the RateManager for test.
     * </p>
     */
    private RateManager manager;

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
        context.bind("java:comp/env/of_namespace", "of.valid");
        context.bind("java:comp/env/of_rate_persistence_key", "informix_rate_persistence");
        System.setProperty("of_namespace", "of.valid");
        System.setProperty("of_rate_persistence_key", "informix_rate_persistence");
        FailureTestHelper.mockEJBDeploy(context);
        manager = new RateManager("com.topcoder.timetracker.rates.failure.manager");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.releaseConfigs();
        manager = null;
    }

    /**
     * Failure test for <code>{@link RateManager#RateManager(String)}</code>.
     * <p>
     * rate_local_ejb_reference_name is empty
     * </p>
     */
    public void testConstructor_String_failure_1() {
        try {
            new RateManager("com.topcoder.timetracker.rates.failure.manager1");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#RateManager(String)}</code>.
     * <p>
     * rate_local_ejb_reference_name is unknown.
     * </p>
     */
    public void testConstructor_String_failure_2() {
        try {
            new RateManager("com.topcoder.timetracker.rates.failure.manager2");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#RateManager(String)}</code>.
     * <p>
     * rate_local_ejb_reference_name is null/empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testConstructor_String_failure_3() throws Exception {
        try {
            new RateManager(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new RateManager(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#updateRate(Rate,boolean)}</code>.
     * <p>
     * rate is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRate_Rate_boolean_failure() throws Exception {
        try {
            manager.updateRate(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#retrieveRate(String,long)}</code>.
     * <p>
     * description is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRate_String_long_failure() throws Exception {
        try {
            manager.retrieveRate(null, 1243);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#addRate(Rate,boolean)}</code>.
     * <p>
     * rate is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRate_Rate_boolean_failure() throws Exception {
        try {
            manager.addRate(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_1() throws Exception {
        try {
            manager.updateRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_2() throws Exception {
        try {
            manager.updateRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_3() throws Exception {
        try {
            manager.updateRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_1() throws Exception {
        try {
            manager.deleteRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_2() throws Exception {
        try {
            manager.deleteRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_3() throws Exception {
        try {
            manager.deleteRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#deleteRate(Rate,boolean)}</code>.
     * <p>
     * rate is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRate_Rate_boolean_failure() throws Exception {
        try {
            manager.deleteRate(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_1() throws Exception {
        try {
            manager.addRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_2() throws Exception {
        try {
            manager.addRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RateManager#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_3() throws Exception {
        try {
            manager.addRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RateManagerTest.class);
    }
}
