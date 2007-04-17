/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for DbTimeEntryRejectReasonDAO.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTimeEntryRejectReasonDAOFailureTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryRejectReasonDAO instance for testing.
     * </p>
     */
    private DbTimeEntryRejectReasonDAO instance;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * The TimeEntry instance for testing.
     * </p>
     */
    private TimeEntry timeEntry;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);

        timeEntry = FailureTestHelper.createTestingTimeEntry(null);
        ManagerFactory.getTimeEntryManager().createTimeEntry(timeEntry, false);

        dbFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);

        instance = new DbTimeEntryRejectReasonDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.tearDownDataBase();
        FailureTestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        instance = null;
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,String,
     * AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception {
        try {
            new DbTimeEntryRejectReasonDAO(null, FailureTestHelper.CONNECTION_NAME, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,
     * String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new DbTimeEntryRejectReasonDAO(dbFactory, "  ", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,String,
     * AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditManager is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor3() throws Exception {
        try {
            new DbTimeEntryRejectReasonDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#addRejectReasonToEntry(long,TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry1() throws Exception {
        try {
            instance.addRejectReasonToEntry(1, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#addRejectReasonToEntry(long,TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry2() throws Exception {
        instance = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            instance.addRejectReasonToEntry(1, timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeRejectReasonFromEntry(long,TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry1() throws Exception {
        try {
            instance.removeRejectReasonFromEntry(1, null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeRejectReasonFromEntry(long,TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry2() throws Exception {
        instance.addRejectReasonToEntry(1, timeEntry, true);
        instance = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            instance.removeRejectReasonFromEntry(1, timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
