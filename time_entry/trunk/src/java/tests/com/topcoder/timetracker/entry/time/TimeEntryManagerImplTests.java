/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
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
 * Unit test cases for TimeEntryManagerImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeEntryManagerImplTests extends TestCase {
    /**
     * <p>
     * The TimeEntryManagerImpl instance for testing.
     * </p>
     */
    private TimeEntryManagerImpl impl;

    /**
     * <p>
     * The DbTimeEntryDAO instance for testing.
     * </p>
     */
    private DbTimeEntryDAO dao;

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
     * The TaskTypeDAO instance for testing.
     * </p>
     */
    private TaskTypeDAO taskTypeDao;

    /**
     * <p>
     * The TimeStatusDAO instance for testing.
     * </p>
     */
    private TimeStatusDAO timeStatusDao;

    /**
     * <p>
     * The TimeEntryRejectReasonDAO instance for testing.
     * </p>
     */
    private TimeEntryRejectReasonDAO timeEntryRejectReasonDao;

    /**
     * <p>
     * The RejectReasonManager instance for testing.
     * </p>
     */
    private RejectReasonManager rejectReasonManager;

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

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            TestHelper.SEARCH_NAMESPACE, auditManager);
        timeStatusDao = new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
            TestHelper.SEARCH_NAMESPACE, auditManager);
        dao = new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
            TestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
        timeEntryRejectReasonDao = new DbTimeEntryRejectReasonDAO(dbFactory, TestHelper.CONNECTION_NAME, auditManager);
        rejectReasonManager = new RejectReasonManager("com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager");

        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);
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

        impl = null;
        rejectReasonManager = null;
        timeEntryRejectReasonDao = null;
        dbFactory = null;
        auditManager = null;
        taskTypeDao = null;
        timeStatusDao = null;
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
        return new TestSuite(TimeEntryManagerImplTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerImpl#TimeEntryManagerImpl(TimeEntryDAO,TimeEntryRejectReasonDAO,
     * RejectReasonManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeEntryManagerImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeEntryManagerImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerImpl#TimeEntryManagerImpl(TimeEntryDAO,TimeEntryRejectReasonDAO,
     * RejectReasonManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTimeEntryDao() {
        try {
            new TimeEntryManagerImpl(null, timeEntryRejectReasonDao, rejectReasonManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerImpl#TimeEntryManagerImpl(TimeEntryDAO,TimeEntryRejectReasonDAO,
     * RejectReasonManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryRejectReasonDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTimeEntryRejectReasonDao() {
        try {
            new TimeEntryManagerImpl(dao, null, rejectReasonManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerImpl#TimeEntryManagerImpl(TimeEntryDAO,TimeEntryRejectReasonDAO,
     * RejectReasonManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when rejectReasonManager is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullRejectReasonManager() {
        try {
            new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#addRejectReasonToEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#addRejectReasonToEntry(TimeEntry,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        impl.addRejectReasonToEntry(timeEntry, 1, true);

        long[] rejectReasonIds = impl.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#addRejectReasonToEntry(TimeEntry,long,boolean) for failure.
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
            impl.addRejectReasonToEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#addRejectReasonToEntry(TimeEntry,long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.addRejectReasonToEntry(timeEntry, 1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeRejectReasonFromEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#removeRejectReasonFromEntry(TimeEntry,long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        impl.addRejectReasonToEntry(timeEntry, 1, false);

        impl.removeRejectReasonFromEntry(timeEntry, 1, true);

        long[] rejectReasonIds = impl.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeRejectReasonFromEntry(TimeEntry,long,boolean) for failure.
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
            impl.removeRejectReasonFromEntry(null, 1, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeRejectReasonFromEntry(TimeEntry,long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        impl.addRejectReasonToEntry(timeEntry, 1, false);
        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.removeRejectReasonFromEntry(timeEntry, 1, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllRejectReasonsForEntry(TimeEntry) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#getAllRejectReasonsForEntry(TimeEntry) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        long[] rejectReasonIds = impl.getAllRejectReasonsForEntry(timeEntry);

        assertEquals("There should be no reject reason for time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllRejectReasonsForEntry(TimeEntry) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.getAllRejectReasonsForEntry(timeEntry);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#removeAllRejectReasonsFromEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        impl.addRejectReasonToEntry(timeEntry, 1, true);
        impl.addRejectReasonToEntry(timeEntry, 2, true);

        impl.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = impl.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for failure.
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
            impl.removeAllRejectReasonsFromEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, false);
        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.removeAllRejectReasonsFromEntry(timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#createTimeEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entries.", timeEntry, impl.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry_NullEntry() throws Exception {
        try {
            impl.createTimeEntry(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.createTimeEntry(timeEntry, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        timeEntry.setDescription("Time Entry.");
        impl.updateTimeEntry(timeEntry, false);

        assertEquals("Failed to update the time entry.", timeEntry, impl.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when entry is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry_NullEntry() throws Exception {
        try {
            impl.updateTimeEntry(null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry_UnrecognizedEntityException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        timeEntry.setId(100);

        try {
            impl.updateTimeEntry(timeEntry, false);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntry(TimeEntry,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.updateTimeEntry(timeEntry, false);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntry(long,boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#deleteTimeEntry(long,boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        impl.deleteTimeEntry(timeEntry.getId(), true);

        assertEquals("Failed to remove the time entry.", 0, impl.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntry(long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry_UnrecognizedEntityException() throws Exception {
        try {
            impl.deleteTimeEntry(500, true);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntry(long,boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.deleteTimeEntry(timeEntry.getId(), true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntry(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#getTimeEntry(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        assertEquals("Failed to get the time entries.", timeEntry, impl.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntry(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry_UnrecognizedEntityException() throws Exception {
        try {
            impl.getTimeEntry(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntry(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry_DataAccessException() throws Exception {
        TimeEntry timeEntry = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntry(timeEntry, true);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.getTimeEntry(timeEntry.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] insertedTimeEntries = impl.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to insert the time entries.", timeEntry1, insertedTimeEntries[0]);
        assertEquals("Failed to insert the time entries.", timeEntry2, insertedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_NullTimeEntries() throws Exception {
        try {
            impl.createTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_EmptyTimeEntries() throws Exception {
        try {
            impl.createTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_SameElement() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_NullInTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithIdSet() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullCreationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("CreationUser");

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullModificationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationUser");

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("Date");

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_DataAccessException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        timeEntry1.setDescription("Time Entry.");
        impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, false);

        TimeEntry[] updatedTimeEntries = impl.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to update the time entries.", timeEntry1, updatedTimeEntries[0]);
        assertEquals("Failed to update the time entries.", timeEntry2, updatedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_NullTimeEntries() throws Exception {
        try {
            impl.updateTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_EmptyTimeEntries() throws Exception {
        try {
            impl.updateTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_SameTimeEntry() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntries contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_NullInTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithIdSet() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithNullModificationUser() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationUser");
        timeEntry2.setId(300);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithNullDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("Date");
        timeEntry2.setId(200);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_DataAccessException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, false);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        impl.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()}, true);

        assertEquals("Failed to remove the time entries.", 0, impl.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_NullTimeEntryIds() throws Exception {
        try {
            impl.deleteTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_EmptyTimeEntryIds() throws Exception {
        try {
            impl.deleteTimeEntries(new long[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_EqualTimeEntryId() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            impl.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry1.getId()}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            impl.deleteTimeEntries(new long[] {timeEntry1.getId(), 19998}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#deleteTimeEntries(long[],boolean) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries_DataAccessException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.deleteTimeEntries(new long[] {timeEntry1.getId()}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntries(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#getTimeEntries(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] timeEntries = impl.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to get the time entries.", timeEntries[0], timeEntry1);
        assertEquals("Failed to get the time entries.", timeEntries[1], timeEntry2);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntries(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeEntryIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries_NullTimeEntryIds() throws Exception {
        try {
            impl.getTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntries(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects BatchOperationException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries_BatchOperationException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            impl.getTimeEntries(new long[] {timeEntry1.getId(), 1234});
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntries(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries_DataAccessException() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.getTimeEntries(new long[] {timeEntry1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#searchTimeEntries(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        impl.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        Filter filter = impl.getTimeEntryFilterFactory().createCompanyIdFilter(timeEntry1.getCompanyId());
        TimeEntry[] timeEntries = impl.searchTimeEntries(filter);

        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        assertEquals("Failed to search the time entries.", timeEntry1, timeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#searchTimeEntries(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when filter is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries_NullFilter() throws Exception {
        try {
            impl.searchTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#searchTimeEntries(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeEntries_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            impl.searchTimeEntries(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllTimeEntries() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#getAllTimeEntries() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, impl.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getAllTimeEntries() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries_DataAccessException() throws Exception {
        dao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, auditManager,
            taskTypeDao, timeStatusDao);
        impl = new TimeEntryManagerImpl(dao, timeEntryRejectReasonDao, rejectReasonManager);

        try {
            impl.getAllTimeEntries();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeEntryManagerImpl#getTimeEntryFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeEntryManagerImpl#getTimeEntryFilterFactory() is correct.
     * </p>
     */
    public void testGetTimeEntryFilterFactory() {
        assertNotNull("Failed to get the time entry filter factory.", impl.getTimeEntryFilterFactory());
    }

}