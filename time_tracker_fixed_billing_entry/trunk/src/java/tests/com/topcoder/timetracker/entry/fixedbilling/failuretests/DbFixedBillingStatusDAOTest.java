/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbFixedBillingStatusDAOTest extends TestCase {
    /**
     * <p>
     * Represents the DBConnectionFactory for testing.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * Represents the FixedBillingStatusDAO for testing.
     * </p>
     */
    private FixedBillingStatusDAO statusDAO1, statusDAO3, statusDAO4;

    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

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
        factory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        statusDAO1 = new DbFixedBillingStatusDAO(factory, "informix_connect", "FixedBillingFailure",
                "com.topcoder.search.builder");
        statusDAO3 = new DbFixedBillingStatusDAO(factory, "informix_connect_failure_1",
                "FixedBillingFailure", "com.topcoder.search.builder");
        statusDAO4 = new DbFixedBillingStatusDAO(factory, "informix_connect_failure_2",
                "FixedBillingFailure", "com.topcoder.search.builder");
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
     * <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory,String,String,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new DbFixedBillingStatusDAO(null, "informix_connect_failure_1", "FixedBillingFailure",
                    "com.topcoder.search.builder");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingStatusDAO(factory, " ", "FixedBillingFailure", "com.topcoder.search.builder");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingStatusDAO(factory, " ", "FixedBillingFailure", "com.topcoder.search.builder");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingStatusDAO(factory, "informix_connect_failure_1", " ", "com.topcoder.search.builder");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingStatusDAO(factory, "informix_connect_failure_1", "FixedBillingFailure", " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingStatusDAO#DbFixedBillingStatusDAO(DBConnectionFactory,String,String,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_CFE() throws Exception {
        try {
            new DbFixedBillingStatusDAO(factory, "informix_connect_failure_1", "FixedBillingFailure_Wrong",
                    "com.topcoder.search.builder");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
        try {
            new DbFixedBillingStatusDAO(factory, "informix_connect_failure_1", "FixedBillingFailure",
                    "com.topcoder.search.builder_Wrong");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#getAllFixedBillingStatuses()}</code>.
     * <p>
     * Wrong connection, table name missing.
     * </p>
     */
    public void testMethodGetAllFixedBillingStatuses_DAE() {
        try {
            statusDAO3.getAllFixedBillingStatuses();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            statusDAO4.getAllFixedBillingStatuses();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatuses_IAE() throws Exception {
        try {
            statusDAO1.getFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            statusDAO1.getFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#getFixedBillingStatuses(long[])}</code>.
     * <p>
     * Wrong connection, table name missing.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingStatuses_DAE() throws Exception {
        try {
            statusDAO3.getFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            statusDAO4.getFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_IAE() throws Exception {
        try {
            statusDAO1.deleteFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            statusDAO1.deleteFixedBillingStatuses(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#deleteFixedBillingStatuses(long[])}</code>.
     * <p>
     * Wrong connection, table doesnot exist.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteFixedBillingStatuses_DAE() throws Exception {
        try {
            statusDAO3.deleteFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            statusDAO4.deleteFixedBillingStatuses(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingStatuses_IAE() throws Exception {
        try {
            statusDAO1.updateFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            statusDAO1.updateFixedBillingStatuses(new FixedBillingStatus[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingStatusDAO#updateFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodUpdateFixedBillingStatuses_DAE() throws Exception {
        try {
            FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
            status.setId(1);
            statusDAO3.updateFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FixedBillingStatus status = FailureTestHelper.createFixedBillingStatus();
            status.setId(1);
            statusDAO4.updateFixedBillingStatuses(new FixedBillingStatus[] { status });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatuses_IAE() throws Exception {
        try {
            statusDAO1.createFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            statusDAO1.createFixedBillingStatuses(new FixedBillingStatus[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingStatusDAO#createFixedBillingStatuses(FixedBillingStatus[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingStatuses_DAE() throws Exception {
        try {
            statusDAO3.createFixedBillingStatuses(new FixedBillingStatus[] { FailureTestHelper
                    .createFixedBillingStatus() });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            statusDAO4.createFixedBillingStatuses(new FixedBillingStatus[] { FailureTestHelper
                    .createFixedBillingStatus() });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingStatuses_IAE() throws Exception {
        try {
            statusDAO1.searchFixedBillingStatuses(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingStatusDAO#searchFixedBillingStatuses(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingStatuses_DAE() throws Exception {
        try {
            statusDAO1.searchFixedBillingStatuses(new EqualToFilter("dummy", new Long(1)));
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
        return new TestSuite(DbFixedBillingStatusDAOTest.class);
    }
}
