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
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO;

/**
 * Failure test for
 * <code>{@link com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAO}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbFixedBillingEntryRejectReasonDAOTest extends TestCase {
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
     * Represents the DbFixedBillingEntryRejectReasonDAO for testing.
     * </p>
     */
    private DbFixedBillingEntryRejectReasonDAO rejectReasonDAO1, rejectReasonDAO2, rejectReasonDAO3;

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
        rejectReasonDAO1 = new DbFixedBillingEntryRejectReasonDAO(factory, "informix_connect", auditManager);
        rejectReasonDAO2 = new DbFixedBillingEntryRejectReasonDAO(factory, "informix_connect_failure_1",
                auditManager);
        rejectReasonDAO3 = new DbFixedBillingEntryRejectReasonDAO(factory, "informix_connect_failure_2",
                auditManager);
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
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory,String,AuditManager)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new DbFixedBillingEntryRejectReasonDAO(null, "informix_connect_failure_2", auditManager);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }

        try {
            new DbFixedBillingEntryRejectReasonDAO(factory, " ", auditManager);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }

        try {
            new DbFixedBillingEntryRejectReasonDAO(factory, "informix_connect_failure_2", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_IAE() throws Exception {
        try {
            rejectReasonDAO1.addRejectReasonToEntry(-1, FailureTestHelper.createFixedBillingEntry(), false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            rejectReasonDAO1.addRejectReasonToEntry(1, null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_URE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(10);
            rejectReasonDAO1.addRejectReasonToEntry(1, entry, false);
            fail("UnrecognizedEntityException expected");
        } catch (UnrecognizedEntityException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#addRejectReasonToEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodAddRejectReasonToEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO2.addRejectReasonToEntry(1, entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO3.addRejectReasonToEntry(1, entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_IAE() throws Exception {
        try {
            rejectReasonDAO1.getAllRejectReasonsForEntry(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }

    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#getAllRejectReasonsForEntry(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodGetAllRejectReasonsForEntry_DAE() throws Exception {
        try {
            rejectReasonDAO2.getAllRejectReasonsForEntry(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            rejectReasonDAO3.getAllRejectReasonsForEntry(1);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_IAE() throws Exception {
        try {
            rejectReasonDAO1.removeAllRejectReasonsFromEntry(null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs.
     */
    public void testMethodRemoveAllRejectReasonsFromEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO2.removeAllRejectReasonsFromEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO3.removeAllRejectReasonsFromEntry(entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveRejectReasonFromEntry_IAE() throws Exception {
        try {
            rejectReasonDAO1.removeRejectReasonFromEntry(-1, FailureTestHelper.createFixedBillingEntry(),
                    false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            rejectReasonDAO1.removeRejectReasonFromEntry(1, null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveRejectReasonFromEntry_URE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(10);
            rejectReasonDAO1.removeRejectReasonFromEntry(1, entry, false);
            fail("UnrecognizedEntityException expected");
        } catch (UnrecognizedEntityException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbFixedBillingEntryRejectReasonDAO#removeRejectReasonFromEntry(long,FixedBillingEntry,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRemoveRejectReasonFromEntry_DAE() throws Exception {
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO2.removeRejectReasonFromEntry(1, entry, false);
            fail("DataAccessException expected");
        } catch (DataAccessException e) {
            // expect
        }
        try {
            FixedBillingEntry entry = FailureTestHelper.createFixedBillingEntry();
            entry.setId(1);
            rejectReasonDAO3.removeRejectReasonFromEntry(1, entry, false);
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
        return new TestSuite(DbFixedBillingEntryRejectReasonDAOTest.class);
    }
}