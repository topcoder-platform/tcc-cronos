/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryManagerImpl;
import com.topcoder.timetracker.entry.time.TimeEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Stress test cases for TaskTypeManagerImpl.
 * </p>
 * 
 * @author superZZ
 * @version 3.2
 */
public class TimeEntryManagerImplStressTests extends TestCase {
    /**
     * Represents TimeEntryManagerImpl instance for test.
     */
    private TimeEntryManagerImpl timeEntryMgr;

    /**
     * <p>
     * Represents the DbTimeEntryDAO instance for testing.
     * </p>
     */
    private DbTimeEntryDAO timeEntryDao;

    /**
     * <p>
     * Represents the DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory dbFactory;

    /**
     * <p>
     * Represents the AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Represents the TaskTypeDAO instance for testing.
     * </p>
     */
    private TaskTypeDAO taskTypeDao;

    /**
     * <p>
     * Represents the TimeStatusDAO instance for testing.
     * </p>
     */
    private TimeStatusDAO timeStatusDao;

    /**
     * <p>
     * Represents the RejectReasonManager instance for testing.
     * </p>
     */
    private RejectReasonManager rejectReasonManager;

    /**
     * <p>
     * Represents the TimeEntryRejectReasonDAO instance for testing.
     * </p>
     */
    private TimeEntryRejectReasonDAO timeEntryRejectReasonDao;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME,
                "TaskTypeIdGenerator", TestHelper.SEARCH_NAMESPACE,
                auditManager);
        timeStatusDao = new DbTimeStatusDAO(dbFactory,
                TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                TestHelper.SEARCH_NAMESPACE, auditManager);
        timeEntryDao = new DbTimeEntryDAO(dbFactory,
                TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                TestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao,
                timeStatusDao);
        timeEntryRejectReasonDao = new DbTimeEntryRejectReasonDAO(dbFactory,
                TestHelper.CONNECTION_NAME, auditManager);
        rejectReasonManager = new RejectReasonManager(
                "com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager");

        timeEntryMgr = new TimeEntryManagerImpl(timeEntryDao,
                timeEntryRejectReasonDao, rejectReasonManager);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    protected void t1earDown() throws Exception {
        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();

        dbFactory = null;
        auditManager = null;
        taskTypeDao = null;
        timeStatusDao = null;
        timeEntryDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TimeEntryManagerImplStressTests.class);
    }

    /**
     * <p>
     * Tests the performance of
     * TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry[] entries = new TimeEntry[100];

        for (int i = 0; i < entries.length; ++i) {
            entries[i] = TestHelper.createTestingTimeEntry(null);
            entries[i].setDescription(i + " Time Entry.");
        }

        long startTime = System.currentTimeMillis();
        timeEntryMgr.createTimeEntries(entries, true);
        System.out.println("Creating 100 time entries takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TimeEntry[] retrievedTimeEntries = timeEntryMgr.getAllTimeEntries();

        for (int i = 0; i < entries.length; ++i) {
            assertTimeEntry(entries[i], retrievedTimeEntries[i]);
        }
    }

    /**
     * <p>
     * Tests the performance of
     * TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry[] entries = new TimeEntry[100];

        for (int i = 0; i < entries.length; ++i) {
            entries[i] = TestHelper.createTestingTimeEntry(null);
        }

        long startTime = System.currentTimeMillis();
        timeEntryMgr.createTimeEntries(entries, true);
        System.out.println("Updating 100 time entries takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TimeStatus status = TestHelper.createTestingTimeStatus(null);
        status.setDescription("hi");

        timeStatusDao.createTimeStatuses(new TimeStatus[] { status });

        for (int i = 0; i < entries.length; ++i) {
            entries[i].setHours(1.111);
            entries[i].setDescription(i + " Time Entry.");
        }

        entries[0].setStatus(status);

        timeEntryMgr.updateTimeEntries(entries, false);

        TimeEntry[] updatedTimeEntries = timeEntryMgr.getAllTimeEntries();

        for (int i = 0; i < entries.length; ++i) {
            assertTimeEntry(entries[i], updatedTimeEntries[i]);
        }
        TimeStatusManagerImplStressTests.assertTimeStatus(status, entries[0]
                .getStatus());
    }

    /**
     * Assert the two time entry instances are equal.
     * 
     * @param expected
     *            Expected time entry.
     * @param actual
     *            Actual time entry.
     */
    private static void assertTimeEntry(TimeEntry expected, TimeEntry actual) {
        assertEquals("TimeEntry#billable is incorrect.",
                expected.getBillable(), actual.getBillable());
        assertEquals("TimeEntry#companyId is incorrect.", expected
                .getCompanyId(), actual.getCompanyId());
        assertEquals("TimeEntry#invoiceId is incorrect.", expected
                .getInvoiceId(), actual.getInvoiceId());
        assertEquals("TimeEntry#description is incorrect.", expected
                .getDescription(), actual.getDescription());
        TimeStatusManagerImplStressTests.assertTimeStatus(expected.getStatus(),
                actual.getStatus());
        assertEquals("TimeEntry#hours is incorrect.", expected.getHours(),
                actual.getHours(), 0.00001d);
    }

    /**
     * <p>
     * Tests the performance of TimeEntryManagerImpl#searchTimeEntries(Filter).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry[] entries = new TimeEntry[100];

        for (int i = 0; i < entries.length; ++i) {
            entries[i] = TestHelper.createTestingTimeEntry(null);
            entries[i].setHours(i);
        }

        timeEntryMgr.createTimeEntries(entries, false);

        Filter filter = timeEntryMgr.getTimeEntryFilterFactory()
                .createHoursFilter(98, 99);

        long startTime = System.currentTimeMillis();
        TimeEntry[] foundEntries = timeEntryMgr.searchTimeEntries(filter);
        System.out
                .println("Searching 2 time entries in 100 time entries takes "
                        + (System.currentTimeMillis() - startTime) + " ms");

        assertEquals("Failed to search the time entries.", 2,
                foundEntries.length);
    }
}
