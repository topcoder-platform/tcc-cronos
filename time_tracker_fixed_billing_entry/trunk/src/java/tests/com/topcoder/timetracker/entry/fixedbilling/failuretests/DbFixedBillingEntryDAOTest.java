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
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.fixedbilling.BatchOperationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAO;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAO}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbFixedBillingEntryDAOTest extends TestCase {
    /**
     * <p>
     * Represents the DBConnectionFactory for testing.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * Represents the AuditManager for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Represents the FixedBillingStatusDAO for testing.
     * </p>
     */
    private FixedBillingStatusDAO statusDAO;

    /**
     * <p>
     * Represents the DbFixedBillingEntryDAO for testing.
     * </p>
     */
    private DbFixedBillingEntryDAO billingEntryDAO1, billingEntryDAO2, billingEntryDAO3, billingEntryDAO4;

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
        auditManager = new MockAuditManager();
        statusDAO = new DbFixedBillingStatusDAO(factory, "informix_connect", "FixedBillingFailure",
                "com.topcoder.search.builder");
        billingEntryDAO1 = new DbFixedBillingEntryDAO(factory, "informix_connect", "FixedBillingFailure",
                "com.topcoder.search.builder", auditManager, statusDAO);
        billingEntryDAO2 = new DbFixedBillingEntryDAO(factory, "informix_connect", "FixedBillingFailure",
                "com.topcoder.search.builder", auditManager, statusDAO);
        billingEntryDAO3 = new DbFixedBillingEntryDAO(factory, "informix_connect_failure_1",
                "FixedBillingFailure", "com.topcoder.search.builder", auditManager, statusDAO);
        billingEntryDAO4 = new DbFixedBillingEntryDAO(factory, "informix_connect_failure_2",
                "FixedBillingFailure", "com.topcoder.search.builder", auditManager, statusDAO);
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
     * <code>{@link DbFixedBillingEntryDAO#DbFixedBillingEntryDAO(DBConnectionFactory,String,String,String,i
     * AuditManager,FixedBillingStatusDAO,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_DBConnectionFactory_failure_IAE() throws Exception {
        try {
            new DbFixedBillingEntryDAO(null, "conn", "FixedBillingFailure", "com.topcoder.search.builder",
                    auditManager, statusDAO);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingEntryDAO(factory, " ", "FixedBillingFailure", "com.topcoder.search.builder",
                    auditManager, statusDAO);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingEntryDAO(factory, "conn", " ", "com.topcoder.search.builder", auditManager, statusDAO);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingEntryDAO(factory, "conn", "FixedBillingFailure", "", auditManager, statusDAO);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingEntryDAO(factory, "conn", "FixedBillingFailure", "com.topcoder.search.builder", null,
                    statusDAO);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbFixedBillingEntryDAO(factory, "conn", "FixedBillingFailure", "com.topcoder.search.builder",
                    auditManager, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_IAE() throws Exception {
        try {
            billingEntryDAO1.updateFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            billingEntryDAO1.updateFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * <p>
     * Wrong connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_DAE_1() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            FixedBillingStatus status = entry.getFixedBillingStatus();
            status.setId(1);
            entry.setFixedBillingStatus(status);
            billingEntryDAO3.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * <p>
     * Table not present.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_DAE_2() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            FixedBillingStatus status = entry.getFixedBillingStatus();
            status.setId(1);
            entry.setFixedBillingStatus(status);
            billingEntryDAO4.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#updateFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * <p>
     * Invalid entry in array.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateFixedBillingEntries_URE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            FixedBillingStatus status = entry.getFixedBillingStatus();
            status.setId(1);
            entry.setFixedBillingStatus(status);
            billingEntryDAO2.updateFixedBillingEntries(new FixedBillingEntry[] { entry }, true);
            fail("UnrecognizedEntityException expected");
        } catch (UnrecognizedEntityException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_IAE() throws Exception {
        try {
            billingEntryDAO1.createFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            billingEntryDAO1.createFixedBillingEntries(new FixedBillingEntry[] { null }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     * <p>
     * Wrong connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_DAE_1() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            billingEntryDAO2.createFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     * <p>
     * Table not present.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_DAE_2() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            billingEntryDAO3.createFixedBillingEntries(new FixedBillingEntry[] { entry }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryDAO#createFixedBillingEntries(FixedBillingEntry[],boolean)}</code>.
     *
     * <p>
     * Invalid entry in array.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateFixedBillingEntries_DAE_3() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            billingEntryDAO4.createFixedBillingEntries(new FixedBillingEntry[] { entry }, true);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_IAE() throws Exception {
        try {
            billingEntryDAO1.getFixedBillingEntries(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            billingEntryDAO1.getFixedBillingEntries(new long[] { -1 });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code>.
     * <p>
     * Wrong database connection.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_DAE_1() throws Exception {
        try {
            billingEntryDAO2.getFixedBillingEntries(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code>.
     * <p>
     * Table not present.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_DAE_2() throws Exception {
        try {
            billingEntryDAO3.getFixedBillingEntries(new long[] { 1 });
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getFixedBillingEntries(long[])}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetFixedBillingEntries_BOE() throws Exception {
        try {
            billingEntryDAO1.getFixedBillingEntries(new long[] { 1 });
            fail("BatchOperationException expected");
        } catch (BatchOperationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingEntries_IAE() throws Exception {
        try {
            billingEntryDAO1.searchFixedBillingEntries(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#searchFixedBillingEntries(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchFixedBillingEntries_DAE() throws Exception {
        try {
            billingEntryDAO1.searchFixedBillingEntries(new EqualToFilter("dummy", new Long(1)));
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getAllFixedBillingEntries()}</code>.
     * <p>
     * Wrong connection.
     * </p>
     */
    public void testMethodGetAllFixedBillingEntries_DAE_1() {
        try {
            billingEntryDAO3.getAllFixedBillingEntries();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#getAllFixedBillingEntries()}</code>.
     * <p>
     * Table not present.
     * </p>
     */
    public void testMethodGetAllFixedBillingEntries_DAE_2() {
        try {
            billingEntryDAO4.getAllFixedBillingEntries();
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[],boolean)}</code>.
     *
     * @throws Exception
     *             if error occurs.
     */
    public void testMethodDeleteFixedBillingEntries_IAE() throws Exception {
        try {
            billingEntryDAO1.deleteFixedBillingEntries(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            billingEntryDAO1.deleteFixedBillingEntries(new long[] { -1 }, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbFixedBillingEntryDAO#deleteFixedBillingEntries(long[],boolean)}</code>.
     * <p>
     * Wrong connection, Table not exists.
     * </p>
     *
     * @throws Exception
     *             if error occurs.
     */
    public void testMethodDeleteFixedBillingEntries_DAE() throws Exception {
        try {
            billingEntryDAO3.deleteFixedBillingEntries(new long[] { 1 }, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            billingEntryDAO4.deleteFixedBillingEntries(new long[] { 1 }, false);
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
        return new TestSuite(DbFixedBillingEntryDAOTest.class);
    }
}