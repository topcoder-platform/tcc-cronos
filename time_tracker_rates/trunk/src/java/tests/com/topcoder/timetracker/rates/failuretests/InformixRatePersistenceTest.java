/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.RateConfigurationException;
import com.topcoder.timetracker.rates.RatePersistenceException;
import com.topcoder.timetracker.rates.persistence.InformixRatePersistence;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rates.persistence.InformixRatePersistence}</code>
 * class.
 *
 * @author mittu
 * @version 3.2
 */
public class InformixRatePersistenceTest extends TestCase {
    /**
     * <p>
     * Represents the <code>{@link InformixRatePersistence}</code> for failure testing.
     * </p>
     */
    private InformixRatePersistence persistence, badPersistence;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.addConfig();
        persistence = new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix");
        badPersistence = new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix10");
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
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * namespace is null/empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_1() throws Exception {
        try {
            new InformixRatePersistence(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new InformixRatePersistence(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * unknown namespace.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_2() throws Exception {
        try {
            new InformixRatePersistence("unknown");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * objectFactoryNamespace missing.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_3() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix1");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }

    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * unknown objectFactoryNamespace.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_4() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix2");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * bad objectFactoryNamespace.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_5() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix3");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * connectionFactoryKey is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_6() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix4");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * connectionFactoryKey is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_7() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix5");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * Expecting <code>ClassCastException</code>
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_8() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix6");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * Missing auditManagerKey.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_9() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix7");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * Unknown auditManagerKey.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_10() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix8");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#InformixRatePersistence(String)}</code>.
     * <p>
     * auditManagerKey - <code>ClassCastException</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_failure_11() throws Exception {
        try {
            new InformixRatePersistence("com.topcoder.timetracker.rates.failure.informix9");
            fail("RateConfigurationException expected");
        } catch (RateConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_1() throws Exception {
        try {
            persistence.addRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_2() throws Exception {
        try {
            persistence.addRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#addRates(Rate[],boolean)}</code>.
     * <p>
     * rates contain <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_3() throws Exception {
        try {
            persistence.addRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#addRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodAddRates_RateArray_boolean_failure_4() throws Exception {
        try {
            badPersistence.addRates(new Rate[] { FailureTestHelper.createRate(1, 89.2) }, false);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
            e.printStackTrace();
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_1() throws Exception {
        try {
            persistence.deleteRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_2() throws Exception {
        try {
            persistence.deleteRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * rates contains <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_3() throws Exception {
        try {
            persistence.deleteRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#deleteRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodDeleteRates_RateArray_boolean_failure_4() throws Exception {
        try {
            badPersistence.deleteRates(new Rate[] { FailureTestHelper.createRate(1, 34.23) }, false);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#retrieveRates(long)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRates_long_failure() throws Exception {
        try {
            badPersistence.retrieveRates(123);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#retrieveRate(long,long)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_long_long_failure() {
        try {
            badPersistence.retrieveRate(1, 123);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#retrieveRate(String,long)}</code>.
     * <p>
     * description is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_String_long_failure_1() throws Exception {
        try {
            persistence.retrieveRate(null, 123);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#retrieveRate(String,long)}</code>.
     * <p>
     * description is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRetrieveRate_String_long_failure_2() throws Exception {
        try {
            badPersistence.retrieveRate("desc", 123);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_1() throws Exception {
        try {
            persistence.updateRates(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates is empty.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_2() throws Exception {
        try {
            persistence.updateRates(new Rate[] {}, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#updateRates(Rate[],boolean)}</code>.
     * <p>
     * rates contains <code>null</code>.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_3() throws Exception {
        try {
            persistence.updateRates(new Rate[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link InformixRatePersistence#updateRates(Rate[],boolean)}</code>.
     * <p>
     * Bad connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateRates_RateArray_boolean_failure_4() throws Exception {
        try {
            badPersistence.updateRates(new Rate[] { FailureTestHelper.createRate(1, 34.17) }, false);
            fail("RatePersistenceException expected");
        } catch (RatePersistenceException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixRatePersistenceTest.class);
    }
}
