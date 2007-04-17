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
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;

/**
 * <p>
 * Accuracy Unit test cases for DbTimeEntryDAO.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class DbTimeEntryDAOAccuracyTests extends TestCase {
    /**
     * <p>
     * DbTimeEntryDAO instance for testing.
     * </p>
     */
    private DbTimeEntryDAO instance;

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

        instance = new DbTimeEntryDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
            AccuracyTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
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
        return new TestSuite(DbTimeEntryDAOAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,
     * AuditManager,TaskTypeDAO,TimeStatusDAO) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create DbTimeEntryDAO instance.", instance);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#searchTimeEntries(Filter) for accuracy.
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
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for accuracy.
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
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for accuracy.
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
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for accuracy.
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
     * Tests DbTimeEntryDAO#getTimeEntries(long[]) for accuracy.
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
     * Tests DbTimeEntryDAO#getAllTimeEntries() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, instance.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntryFilterFactory() for accuracy.
     * </p>
     */
    public void testGetTimeEntryFilterFactory() {
        assertNotNull("Failed to get the time entry filter factory.", instance.getTimeEntryFilterFactory());
    }

}