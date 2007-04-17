/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryManagerImpl;
import com.topcoder.timetracker.entry.time.TimeEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

/**
 * <p>
 * Accuracy Unit test cases for TimeEntryManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeEntryManagerImplAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeEntryManagerImpl instance for testing.
     * </p>
     */
    private TimeEntryManagerImpl instance;

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

        DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(AccuracyTestHelper.DB_FACTORY_NAMESPACE);
        AuditManager auditManager = new AuditDelegate(AccuracyTestHelper.AUDIT_NAMESPACE);
        TaskTypeDAO taskTypeDao = new DbTaskTypeDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME,
            "TaskTypeIdGenerator", AccuracyTestHelper.SEARCH_NAMESPACE, auditManager);
        TimeStatusDAO timeStatusDao = new DbTimeStatusDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME,
            "TimeStatusIdGenerator", AccuracyTestHelper.SEARCH_NAMESPACE, auditManager);
        DbTimeEntryDAO dao = new DbTimeEntryDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
            AccuracyTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
        TimeEntryRejectReasonDAO timeEntryRejectReasonDao = new DbTimeEntryRejectReasonDAO(dbFactory,
            AccuracyTestHelper.CONNECTION_NAME, auditManager);
        RejectReasonManager rejectReasonManager = new RejectReasonManager(
            "com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager");

        instance = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);
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
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeEntryManagerImplAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerImpl#TimeEntryManagerImpl(TimeEntryDAO,TimeEntryRejectReasonDAO,
     * RejectReasonManager) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeEntryManagerImpl instance.", instance);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entry.", timeEntry, instance.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);

        timeEntry.setDescription("Time Entry.");
        instance.updateTimeEntry(timeEntry, false);

        assertEquals("Failed to update the time entry.", timeEntry, instance.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntry(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);
        instance.deleteTimeEntry(timeEntry.getId(), true);

        assertEquals("Failed to remove the time entries.", 0, instance.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntry(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entry.", timeEntry, instance.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#addRejectReasonToEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);
        instance.addRejectReasonToEntry(timeEntry, 1, true);

        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeRejectReasonFromEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);
        instance.addRejectReasonToEntry(timeEntry, 1, true);
        instance.removeRejectReasonFromEntry(timeEntry, 1, true);
        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry);

        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);
        instance.addRejectReasonToEntry(timeEntry, 1, true);
        instance.addRejectReasonToEntry(timeEntry, 2, true);
        instance.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllRejectReasonsForEntry(TimeEntry) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntry(timeEntry, true);
        long[] rejectReasonIds = instance.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Expected no reject reason for time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] {timeEntry}, true);

        Filter filter = instance.getTimeEntryFilterFactory().createCompanyIdFilter(timeEntry.getCompanyId());
        TimeEntry[] timeEntries = instance.searchTimeEntries(filter);

        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        assertEquals("Failed to search the time entries.", timeEntry, timeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        TimeEntry[] insertedTimeEntries = instance.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to insert the time entries.", timeEntry, insertedTimeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] {timeEntry}, true);

        timeEntry.setDescription("Time Entry.");
        instance.updateTimeEntries(new TimeEntry[] {timeEntry}, false);
        TimeEntry[] updatedTimeEntries = instance.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to update the time entries.", timeEntry, updatedTimeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        instance.deleteTimeEntries(new long[] {timeEntry.getId()}, true);

        assertEquals("Failed to remove the time entries.", 0, instance.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntries(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        TimeEntry[] timeEntries = instance.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to get the time entries.", timeEntries[0], timeEntry);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllTimeEntries() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, instance.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntryFilterFactory() for accuracy.
     * </p>
     */
    public void testGetTimeEntryFilterFactory() {
        assertNotNull("Failed to get the time entry filter factory.", instance.getTimeEntryFilterFactory());
    }

}