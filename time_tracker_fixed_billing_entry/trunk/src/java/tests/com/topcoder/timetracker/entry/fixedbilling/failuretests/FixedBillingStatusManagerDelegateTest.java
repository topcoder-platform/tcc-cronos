/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerDelegate;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerDelegate}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingStatusManagerDelegateTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the FixedBillingEntryManagerDelegate for testing.
     * </p>
     */
    private FixedBillingStatusManagerDelegate delegate;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfigs();
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.prepareData(connection);
        FailureTestHelper.deployEJB();
        delegate = new FixedBillingStatusManagerDelegate("com.topcoder.timetracker.entry.fb.ejb.failure2");
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearDatabase(connection);
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        FailureTestHelper.releaseConfigs();
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new FixedBillingStatusManagerDelegate(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#FixedBillingStatusManagerDelegate(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_CFE() throws Exception {
        try {
            new FixedBillingStatusManagerDelegate("unknown");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
        try {
            new FixedBillingStatusManagerDelegate("empty");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
        try {
            new FixedBillingStatusManagerDelegate("wrong");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodSearchFixedBillingStatuses_DAE() throws Exception {
        try {
            delegate.searchFixedBillingStatuses(new EqualToFilter("dummy", new Long(1)));
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodSearchFixedBillingStatuses_IAE() throws Exception {
        try {
            delegate.searchFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatuses_DAE() throws Exception {
        try {
            delegate.getFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatuses_IAE() throws Exception {
        try {
            delegate.getFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            delegate.getFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatus_DAE() throws Exception {
        try {
            delegate.getFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#getFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatus_IAE() throws Exception {
        try {
            delegate.getFixedBillingStatus(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatus_DAE() throws Exception {
        try {
            delegate.deleteFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatus_IAE() throws Exception {
        try {
            delegate.deleteFixedBillingStatus(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatus_DAE() throws Exception {
        FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
        status.setId(1);
        try {
            delegate.updateFixedBillingStatus(status);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.updateFixedBillingStatus(status);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatus_IAE() throws Exception {
        try {
            delegate.updateFixedBillingStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerDelegate#getAllFixedBillingStatuses()}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodGetAllFixedBillingStatuses_DAE() throws Exception {
        try {
            delegate.getAllFixedBillingStatuses();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.getAllFixedBillingStatuses();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingStatuses_DAE() throws Exception {
        FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
        try {
            delegate.createFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingStatuses_IAE() throws Exception {
        try {
            delegate.createFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingStatuses(new FixedBillingStatus[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatus_DAE() throws Exception {
        FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
        try {
            delegate.createFixedBillingStatus(status);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.createFixedBillingStatus(status);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#createFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatus_IAE() throws Exception {
        try {
            delegate.createFixedBillingStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_DAE() throws Exception {
        try {
            delegate.deleteFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#deleteFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_IAE() throws Exception {
        try {
            delegate.deleteFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.deleteFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatuses_DAE() throws Exception {
        FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
        status.setId(1);
        try {
            delegate.updateFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.updateFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerDelegate#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatuses_IAE() throws Exception {
        try {
            delegate.updateFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            delegate.updateFixedBillingStatuses(new FixedBillingStatus[] { null });
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
        return new TestSuite(FixedBillingStatusManagerDelegateTest.class);
    }
}
