/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeEntry;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTimeEntryRejectReasonDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeEntryRejectReasonDAOTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryRejectReasonDAO instance for testing.
     * </p>
     */
    private DbTimeEntryRejectReasonDAO dao;

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
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        timeEntry = TestHelper.createTestingTimeEntry(null);
        ManagerFactory.getTimeEntryManager().createTimeEntry(timeEntry, false);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        dao = new DbTimeEntryRejectReasonDAO(dbFactory, TestHelper.CONNECTION_NAME, auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        dao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTimeEntryRejectReasonDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,String,
     * AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTimeEntryRejectReasonDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTimeEntryRejectReasonDAO instance.", dao);
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
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new DbTimeEntryRejectReasonDAO(null, TestHelper.CONNECTION_NAME, auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,String,
     * AuditManager) for success.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbTimeEntryRejectReasonDAO instance.", new DbTimeEntryRejectReasonDAO(
            dbFactory, null, auditManager));
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
    public void testCtor_EmptyConnName() throws Exception {
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
    public void testCtor_NullAuditManager() throws Exception {
        try {
            new DbTimeEntryRejectReasonDAO(dbFactory, TestHelper.CONNECTION_NAME, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#addRejectReasonToEntry(long,TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryRejectReasonDAO#addRejectReasonToEntry(long,TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        dao.addRejectReasonToEntry(1, timeEntry, true);

        long[] rejectReasonIds = dao.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
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
    public void testAddRejectReasonToEntry_NullTimeEntry() throws Exception {
        try {
            dao.addRejectReasonToEntry(1, null, true);
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
    public void testAddRejectReasonToEntry_DataAccessException() throws Exception {
        dao = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            dao.addRejectReasonToEntry(1, timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeRejectReasonFromEntry(long,TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryRejectReasonDAO#removeRejectReasonFromEntry(long,TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        dao.addRejectReasonToEntry(1, timeEntry, true);

        dao.removeRejectReasonFromEntry(1, timeEntry, true);

        long[] rejectReasonIds = dao.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
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
    public void testRemoveRejectReasonFromEntry_NullTimeEntry() throws Exception {
        try {
            dao.removeRejectReasonFromEntry(1, null, true);
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
    public void testRemoveRejectReasonFromEntry_DataAccessException() throws Exception {
        dao.addRejectReasonToEntry(1, timeEntry, true);
        dao = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            dao.removeRejectReasonFromEntry(1, timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#getAllRejectReasonsForEntry(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryRejectReasonDAO#getAllRejectReasonsForEntry(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        long[] rejectReasonIds = dao.getAllRejectReasonsForEntry(timeEntry.getId());

        assertEquals("There should be no reject reason for time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#getAllRejectReasonsForEntry(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry_DataAccessException() throws Exception {
        dao = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            dao.getAllRejectReasonsForEntry(1);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        dao.addRejectReasonToEntry(1, timeEntry, true);
        dao.addRejectReasonToEntry(2, timeEntry, true);

        dao.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = dao.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_NullTimeEntry() throws Exception {
        try {
            dao.removeAllRejectReasonsFromEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_DataAccessException() throws Exception {
        dao = new DbTimeEntryRejectReasonDAO(dbFactory, "empty", auditManager);

        try {
            dao.removeAllRejectReasonsFromEntry(timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}