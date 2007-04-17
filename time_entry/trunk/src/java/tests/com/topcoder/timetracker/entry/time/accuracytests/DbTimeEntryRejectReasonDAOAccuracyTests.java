/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryRejectReasonDAO;

/**
 * <p>
 * Accuracy Unit test cases for DbTimeEntryRejectReasonDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbTimeEntryRejectReasonDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * DbTimeEntryRejectReasonDAO instance for testing.
     * </p>
     */
    private DbTimeEntryRejectReasonDAO instance;

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
        AccuracyTestHelper.clearConfig();
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.AUDIT_CONFIG_FILE);
        AccuracyTestHelper.setUpDataBase();
        AccuracyTestHelper.setUpEJBEnvironment(null, null, null);

        timeEntry = AccuracyTestHelper.createTimeEntry(null);
        ManagerFactory.getTimeEntryManager().createTimeEntry(timeEntry, false);
        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new AuditDelegate(AccuracyTestHelper.AUDIT_NAMESPACE);

        instance = new DbTimeEntryRejectReasonDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME, auditManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();

        instance = null;
        timeEntry = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTimeEntryRejectReasonDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryRejectReasonDAO#DbTimeEntryRejectReasonDAO(DBConnectionFactory,
     * String,AuditManager) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbTimeEntryRejectReasonDAO instance.", instance);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#addRejectReasonToEntry(long,TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        instance.addRejectReasonToEntry(1, timeEntry, true);

        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeRejectReasonFromEntry(long,TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        instance.addRejectReasonToEntry(1, timeEntry, true);
        instance.removeRejectReasonFromEntry(1, timeEntry, true);
        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry.getId());

        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        instance.addRejectReasonToEntry(1, timeEntry, true);
        instance.addRejectReasonToEntry(2, timeEntry, true);
        instance.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests DbTimeEntryRejectReasonDAO#getAllRejectReasonsForEntry(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry.getId());
        assertEquals("Expected no reject reason for time entry.", 0, rejectReasonIds.length);
    }

}