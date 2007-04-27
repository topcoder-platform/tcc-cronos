/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTimeEntryDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeEntryDAOTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryDAO instance for testing.
     * </p>
     */
    private DbTimeEntryDAO timeEntryDao;

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
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.SEARCH_CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, TestHelper.CONNECTION_NAME, "TaskTypeIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TaskTypeBundle", auditManager);
        timeStatusDao = new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TimeStatusBundle", auditManager);
        timeEntryDao = new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
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
        return new TestSuite(DbTimeEntryDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTimeEntryDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTimeEntryDAO instance.", timeEntryDao);
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
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
            new DbTimeEntryDAO(null, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
                "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbTimeEntryDAO instance.", new DbTimeEntryDAO(dbFactory, null,
            "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle", auditManager, taskTypeDao,
            timeStatusDao));
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
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
            new DbTimeEntryDAO(dbFactory, " ", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle",
                auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullIdGen() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, null, TestHelper.SEARCH_NAMESPACE,
                "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyIdGen() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "  ", TestHelper.SEARCH_NAMESPACE,
                "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullSearchStrategyNamespace() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", null, "TimeEntryBundle",
                auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_EmptySearchStrategyNamespace() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", "  ", "TimeEntryBundle",
                auditManager, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when auditor is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullAuditor() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle", null, taskTypeDao, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when taskTypeDao is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullTaskTypeDao() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle", auditManager, null, timeStatusDao);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusDao is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullTimeStatusDao() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                TestHelper.SEARCH_NAMESPACE, "TimeEntryBundle", auditManager, taskTypeDao, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeEntryDAO#DbTimeEntryDAO(DBConnectionFactory,String,String,String,AuditManager,
     * TaskTypeDAO,TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search strategy namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_ConfigurationException() {
        try {
            new DbTimeEntryDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", "unknown_namespace",
                "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] insertedTimeEntries = timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId(),
            timeEntry2.getId()});

        assertEquals("Failed to insert the time entries.", timeEntry1, insertedTimeEntries[0]);
        assertEquals("Failed to insert the time entries.", timeEntry2, insertedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null creation date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullCreationDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("CreationDate");

        try {
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries_TimeEntryWithNullModificationDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationDate");

        try {
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#createTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);

        try {
            timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        timeEntry1.setDescription("Time Entry.");
        timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, false);

        TimeEntry[] updatedTimeEntries = timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to update the time entries.", timeEntry1, updatedTimeEntries[0]);
        assertEquals("Failed to update the time entries.", timeEntry2, updatedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.updateTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
            timeEntryDao.updateTimeEntries(new TimeEntry[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, null}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time entry has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries_TimeEntryWithNullModificationDate() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationDate");
        timeEntry2.setId(100);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("ModificationUser");
        timeEntry2.setId(300);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry("Date");
        timeEntry2.setId(200);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, false);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#updateTimeEntries(TimeEntry[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, false);

        timeEntryDao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);
        try {
            timeEntryDao.updateTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#deleteTimeEntries(long[],boolean) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        timeEntryDao.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()}, true);

        assertEquals("Failed to remove the time entries.", 0, timeEntryDao.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for failure.
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
            timeEntryDao.deleteTimeEntries(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for failure.
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
            timeEntryDao.deleteTimeEntries(new long[0], true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            timeEntryDao.deleteTimeEntries(new long[] {timeEntry1.getId(), timeEntry1.getId()}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            timeEntryDao.deleteTimeEntries(new long[] {timeEntry1.getId(), 19998}, true);
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#deleteTimeEntries(long[],boolean) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        timeEntryDao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);

        try {
            timeEntryDao.deleteTimeEntries(new long[] {timeEntry1.getId()}, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntries(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#getTimeEntries(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] timeEntries = timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId(), timeEntry2.getId()});

        assertEquals("Failed to get the time entries.", timeEntries[0], timeEntry1);
        assertEquals("Failed to get the time entries.", timeEntries[1], timeEntry2);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntries(long[]) for failure.
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
            timeEntryDao.getTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntries(long[]) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        try {
            timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId(), 1234});
            fail("BatchOperationException expected.");
        } catch (BatchOperationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntries(long[]) for failure.
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
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        timeEntryDao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);

        try {
            timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#searchTimeEntries(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        timeEntry1.setDescription("description");
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        // verify the company id filter
        Filter filter = timeEntryDao.getTimeEntryFilterFactory().createCompanyIdFilter(timeEntry1.getCompanyId());
        TimeEntry[] timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the time status filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createTimeStatusFilter(timeEntry1.getStatus());
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the task type filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createTaskTypeFilter(timeEntry1.getTaskType());
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the task type filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createTaskTypeFilter(timeEntry1.getTaskType());
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the billable filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createBillableFilter(timeEntry1.isBillable());
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the hours filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createHoursFilter(timeEntry1.getHours() - 1.0,
            timeEntry1.getHours() + 1.0);
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the creation user filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createCreationUserFilter(timeEntry1.getCreationUser(),
            StringMatchType.ENDS_WITH);
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);

        // verify the modification user filter
        filter = timeEntryDao.getTimeEntryFilterFactory().createModificationUserFilter(
            timeEntry1.getModificationUser(), StringMatchType.ENDS_WITH);
        timeEntries = timeEntryDao.searchTimeEntries(filter);
        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        TestHelper.assertTimeEntryEquals(timeEntry1, timeEntries[0]);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#searchTimeEntries(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries2() throws Exception {
        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1}, true);

        Filter filter = timeEntryDao.getTimeEntryFilterFactory().createRejectReasonFilter(1);
        TimeEntry[] timeEntries = timeEntryDao.searchTimeEntries(filter);

        assertEquals("Failed to search the time entries.", 0, timeEntries.length);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#searchTimeEntries(Filter) for failure.
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
            timeEntryDao.searchTimeEntries(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#searchTimeEntries(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeEntries_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            timeEntryDao.searchTimeEntries(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getAllTimeEntries() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#getAllTimeEntries() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, timeEntryDao.getAllTimeEntries().length);

        TimeEntry timeEntry1 = TestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = TestHelper.createTestingTimeEntry(null);

        timeEntryDao.createTimeEntries(new TimeEntry[] {timeEntry1, timeEntry2}, true);

        TimeEntry[] retrievedTimeEntries = timeEntryDao.getTimeEntries(new long[] {timeEntry1.getId(),
            timeEntry2.getId()});

        assertEquals("Failed to get the time entries.", timeEntry1, retrievedTimeEntries[0]);
        assertEquals("Failed to get the time entries.", timeEntry2, retrievedTimeEntries[1]);
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getAllTimeEntries() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries_DataAccessException() throws Exception {
        timeEntryDao = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeEntryBundle", auditManager, taskTypeDao, timeStatusDao);

        try {
            timeEntryDao.getAllTimeEntries();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeEntryDAO#getTimeEntryFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeEntryDAO#getTimeEntryFilterFactory() is correct.
     * </p>
     */
    public void testGetTimeEntryFilterFactory() {
        assertNotNull("Failed to get the time entry filter factory.", timeEntryDao.getTimeEntryFilterFactory());
    }
}