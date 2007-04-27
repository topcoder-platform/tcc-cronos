/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusManagerImpl;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

/**
 * Accuracy test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusManagerImpl}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class FixedBillingStatusManagerImplTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the FixedBillingStatusDAO for testing.
     * </p>
     */
    private FixedBillingStatusDAO fixedBillingStatusDao;

    /**
     * <p>
     * Represents the FixedBillingStatusManagerImpl for testing.
     * </p>
     */
    private FixedBillingStatusManagerImpl impl;

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
        fixedBillingStatusDao = new DbFixedBillingStatusDAO(new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"), "informix_connect_failure_1",
                "FixedBillingFailure", "com.topcoder.search.builder");
        impl = new FixedBillingStatusManagerImpl(fixedBillingStatusDao);
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
     * <code>{@link FixedBillingStatusManagerImpl#FixedBillingStatusManagerImpl(FixedBillingStatusDAO)}</code>.
     */
    public void testConstructor_IAE() {
        try {
            new FixedBillingStatusManagerImpl(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatuses_IAE() throws Exception {
        try {
            impl.createFixedBillingStatuses(new FixedBillingStatus[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.createFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatuses_DAE() throws Exception {
        try {
            impl.createFixedBillingStatuses(new FixedBillingStatus[] { FailureTestHelper
                    .createFixedBillingStatus() });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingStatus_IAE() throws Exception {
        try {
            impl.updateFixedBillingStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingStatus_DAE() throws Exception {
        try {
            FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
            status.setId(1);
            impl.updateFixedBillingStatus(status);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodGetFixedBillingStatuses_IAE() throws Exception {
        try {
            impl.getFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.getFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodGetFixedBillingStatuses_DAE() throws Exception {
        try {
            impl.getFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#getAllFixedBillingStatuses()}</code>.
     */
    public void testMethodGetAllFixedBillingStatuses_DAE() {
        try {
            impl.getAllFixedBillingStatuses();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodGetFixedBillingStatus_IAE() throws Exception {
        try {
            impl.getFixedBillingStatus(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#getFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodGetFixedBillingStatus_DAE() throws Exception {
        try {
            impl.getFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_IAE() throws Exception {
        try {
            impl.deleteFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.deleteFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_DAE() throws Exception {
        try {
            impl.deleteFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatus_IAE() throws Exception {
        try {
            impl.deleteFixedBillingStatus(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#deleteFixedBillingStatus(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatus_DAE() throws Exception {
        try {
            impl.deleteFixedBillingStatus(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatuses_IAE() throws Exception {
        try {
            impl.updateFixedBillingStatuses(new FixedBillingStatus[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            impl.updateFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingStatuses_DAE() throws Exception {
        FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
        status.setId(1);
        try {
            impl.updateFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingStatuses_IAE() throws Exception {
        try {
            impl.searchFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link FixedBillingStatusManagerImpl#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingStatuses_DAE() throws Exception {
        try {
            impl.searchFixedBillingStatuses(new EqualToFilter("dummy", new Long(1)));
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingStatus_IAE() throws Exception {
        try {
            impl.createFixedBillingStatus(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link FixedBillingStatusManagerImpl#createFixedBillingStatus(FixedBillingStatus)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodCreateFixedBillingStatus_DAE() throws Exception {
        try {
            impl.createFixedBillingStatus(FailureTestHelper.createFixedBillingStatus());
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(FixedBillingStatusManagerImplTest.class);
    }
}
