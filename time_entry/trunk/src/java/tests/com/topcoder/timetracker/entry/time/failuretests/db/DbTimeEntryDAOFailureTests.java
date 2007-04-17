/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.db.DbTaskTypeDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeEntryDAO;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;
import com.topcoder.timetracker.entry.time.failuretests.FailureTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for DbTimeEntryDAO.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class DbTimeEntryDAOFailureTests extends TestCase {
    /**
     * <p>
     * The DbTimeEntryDAO instance for testing.
     * </p>
     */
    private DbTimeEntryDAO instance;

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
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);

        taskTypeDao = new DbTaskTypeDAO(dbFactory, FailureTestHelper.CONNECTION_NAME,
                "TaskTypeIdGenerator", FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        timeStatusDao = new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME,
                "TimeStatusIdGenerator", FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME,
                "TimeEntryIdGenerator", FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao,
                timeStatusDao);
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
        taskTypeDao = null;
        timeStatusDao = null;
        instance = null;
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
    public void testCtor1() throws Exception {
        try {
            new DbTimeEntryDAO(null, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
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
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, " ", "TimeEntryIdGenerator", FailureTestHelper.SEARCH_NAMESPACE,
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
    public void testCtor3() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, null,
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
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
    public void testCtor4() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "  ",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);
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
    public void testCtor5() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", null,
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
    public void testCtor6() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator", "  ",
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
    public void testCtor7() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, null, taskTypeDao, timeStatusDao);
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
    public void testCtor8() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, null, timeStatusDao);
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
    public void testCtor9() throws Exception {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, null);
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
    public void testCtor10() {
        try {
            new DbTimeEntryDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeEntryIdGenerator",
                "unknown_namespace", auditManager, taskTypeDao, timeStatusDao);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
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
    public void testCreateTimeEntries1() throws Exception {
        try {
            instance.createTimeEntries(null, true);
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
    public void testCreateTimeEntries2() throws Exception {
        try {
            instance.createTimeEntries(new TimeEntry[0], true);
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
    public void testCreateTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry1 }, true);
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
    public void testCreateTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, null }, true);
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
    public void testCreateTimeEntries5() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries6() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("CreationDate");

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries7() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("CreationUser");

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries8() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationDate");

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries9() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationUser");

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries10() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("Date");

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testCreateTimeEntries11() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        instance = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);

        try {
            instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
     * It tests the case that when timeEntries is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries1() throws Exception {
        try {
            instance.updateTimeEntries(null, true);
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
    public void testUpdateTimeEntries2() throws Exception {
        try {
            instance.updateTimeEntries(new TimeEntry[0], true);
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
    public void testUpdateTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry1 }, true);
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
    public void testUpdateTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, null }, true);
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
    public void testUpdateTimeEntries5() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries6() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationDate");
        timeEntry2.setId(100);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries7() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("ModificationUser");
        timeEntry2.setId(300);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries8() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry("Date");
        timeEntry2.setId(200);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries9() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, false);

        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        timeEntry2.setId(34);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
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
    public void testUpdateTimeEntries10() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        TimeEntry timeEntry2 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, false);

        instance = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);

        try {
            instance.updateTimeEntries(new TimeEntry[] { timeEntry1, timeEntry2 }, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testDeleteTimeEntries1() throws Exception {
        try {
            instance.deleteTimeEntries(null, true);
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
    public void testDeleteTimeEntries2() throws Exception {
        try {
            instance.deleteTimeEntries(new long[0], true);
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
    public void testDeleteTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            instance.deleteTimeEntries(new long[] { timeEntry1.getId(), timeEntry1.getId() },
                true);
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
    public void testDeleteTimeEntries4() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            instance.deleteTimeEntries(new long[] { timeEntry1.getId(), 19998 }, true);
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
    public void testDeleteTimeEntries5() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        instance = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);

        try {
            instance.deleteTimeEntries(new long[] { timeEntry1.getId() }, true);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testGetTimeEntries1() throws Exception {
        try {
            instance.getTimeEntries(null);
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
    public void testGetTimeEntries2() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        try {
            instance.getTimeEntries(new long[] { timeEntry1.getId(), 1234 });
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
    public void testGetTimeEntries3() throws Exception {
        TimeEntry timeEntry1 = FailureTestHelper.createTestingTimeEntry(null);
        instance.createTimeEntries(new TimeEntry[] { timeEntry1 }, true);

        instance = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);

        try {
            instance.getTimeEntries(new long[] { timeEntry1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testSearchTimeEntries1() throws Exception {
        try {
            instance.searchTimeEntries(null);
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
    public void testSearchTimeEntries2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            instance.searchTimeEntries(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testGetAllTimeEntries() throws Exception {
        instance = new DbTimeEntryDAO(dbFactory, "empty", "TimeEntryIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager, taskTypeDao, timeStatusDao);

        try {
            instance.getAllTimeEntries();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
