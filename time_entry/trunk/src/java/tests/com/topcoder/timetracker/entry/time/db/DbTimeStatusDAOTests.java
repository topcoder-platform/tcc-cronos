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
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeStatus;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for DbTimeStatusDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeStatusDAOTests extends TestCase {
    /**
     * <p>
     * The DbTimeStatusDAO instance for testing.
     * </p>
     */
    private DbTimeStatusDAO timeStatusDao;

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

        timeStatusDao = new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TimeStatusBundle", auditManager);
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
        timeStatusDao = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbTimeStatusDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DbTimeStatusDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new DbTimeStatusDAO instance.", timeStatusDao);
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when connName is null and expects success.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new DbTimeStatusDAO instance.", new DbTimeStatusDAO(dbFactory, null,
            "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE, "TimeStatusBundle", auditManager));
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(null, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, " ", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, null, TestHelper.SEARCH_NAMESPACE,
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, " ", TestHelper.SEARCH_NAMESPACE,
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator", null,
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator", " ",
                "TimeStatusBundle", auditManager);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
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
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                TestHelper.SEARCH_NAMESPACE, "TimeStatusBundle", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor DbTimeStatusDAO#DbTimeStatusDAO(DBConnectionFactory,String,String,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the search strategy namespace is unknown and expects for ConfigurationException.
     * </p>
     */
    public void testCtor1_ConfigurationException() {
        try {
            new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator", "unknown_namespace",
                "TimeStatusBundle", auditManager);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] insertedTimeStatuses = timeStatusDao.getTimeStatuses(new long[] {timeStatus1.getId(),
            timeStatus2.getId()});

        TestHelper.assertTimeStatusEquals(timeStatus1, insertedTimeStatuses[0]);
        TestHelper.assertTimeStatusEquals(timeStatus2, insertedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_NullTimeEntries() throws Exception {
        try {
            timeStatusDao.createTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_EmptyTimeEntries() throws Exception {
        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_SameElement() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_NullInTimeEntries() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithIdSet() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatus2.setId(34);

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null creation date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullCreationDate() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("CreationDate");

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null creation user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullCreationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("CreationUser");

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullModificationDate() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationDate");

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_TimeStatusWithNullModificationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationUser");

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#createTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses_DataAccessException() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);

        try {
            timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        timeStatus1.setDescription("Time status.");
        timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] updatedTimeStatuses = timeStatusDao.getTimeStatuses(new long[] {timeStatus1.getId(),
            timeStatus2.getId()});

        TestHelper.assertTimeStatusEquals(timeStatus1, updatedTimeStatuses[0]);
        TestHelper.assertTimeStatusEquals(timeStatus2, updatedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_NullTimeEntries() throws Exception {
        try {
            timeStatusDao.updateTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_EmptyTimeEntries() throws Exception {
        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains same element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_SameTimeStatus() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatuses contains null element and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_NullInTimeEntries() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has id set and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_TimeStatusWithIdSet() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification date and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_TimeStatusWithNullModificationDate() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationDate");
        timeStatus2.setId(100);

        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when a time status has null modification user and expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_TimeStatusWithNullModificationUser() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#updateTimeStatuses(TimeStatus[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses_DataAccessException() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        timeStatusDao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        try {
            timeStatusDao.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#deleteTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        timeStatusDao.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to remove the time statuses.", 0, timeStatusDao.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_NullTimeStatusIds() throws Exception {
        try {
            timeStatusDao.deleteTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is empty and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_EmptyTimeStatusIds() throws Exception {
        try {
            timeStatusDao.deleteTimeStatuses(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds contains equal id and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_EqualTimeStatusId() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            timeStatusDao.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#deleteTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses_DataAccessException() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        timeStatusDao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);

        try {
            timeStatusDao.deleteTimeStatuses(new long[] {timeStatus1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#getTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] timeStatuses = timeStatusDao.getTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        TestHelper.assertTimeStatusEquals(timeStatus1, timeStatuses[0]);
        TestHelper.assertTimeStatusEquals(timeStatus2, timeStatuses[1]);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusIds is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses_NullTimeStatusIds() throws Exception {
        try {
            timeStatusDao.getTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatuses(long[]) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses_DataAccessException() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        timeStatusDao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);

        try {
            timeStatusDao.getTimeStatuses(new long[] {timeStatus1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#searchTimeStatuses(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#searchTimeStatuses(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatus1.setDescription("NewTimeStatus");
        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1});

        // verify the description filter
        Filter filter = timeStatusDao.getTimeStatusFilterFactory().createDescriptionFilter(
            timeStatus1.getDescription(), StringMatchType.ENDS_WITH);
        TimeStatus[] timeStatuses = timeStatusDao.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        TestHelper.assertTimeStatusEquals(timeStatus1, timeStatuses[0]);

        // verify the creation user filter
        filter = timeStatusDao.getTimeStatusFilterFactory().createCreationUserFilter(timeStatus1.getCreationUser(),
            StringMatchType.ENDS_WITH);
        timeStatuses = timeStatusDao.searchTimeStatuses(filter);
        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        TestHelper.assertTimeStatusEquals(timeStatus1, timeStatuses[0]);

        // verify the modification user filter
        filter = timeStatusDao.getTimeStatusFilterFactory().createModificationUserFilter(
            timeStatus1.getModificationUser(), StringMatchType.ENDS_WITH);
        timeStatuses = timeStatusDao.searchTimeStatuses(filter);
        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        TestHelper.assertTimeStatusEquals(timeStatus1, timeStatuses[0]);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when criteria is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses_NullCriteria() throws Exception {
        try {
            timeStatusDao.searchTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeStatuses_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            timeStatusDao.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getTimeStatusFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#getTimeStatusFilterFactory() is correct.
     * </p>
     */
    public void testGetTimeStatusFilterFactory() {
        assertNotNull("Failed to get the time status filter factory.", timeStatusDao.getTimeStatusFilterFactory());
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getAllTimeStatuses() for accuracy.
     * </p>
     *
     * <p>
     * It verifies DbTimeStatusDAO#getAllTimeStatuses() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        assertEquals("The time_status table should be empty.", 0, timeStatusDao.getAllTimeStatuses().length);

        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        timeStatusDao.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] retrievedTimeStatuses = timeStatusDao.getTimeStatuses(new long[] {timeStatus1.getId(),
            timeStatus2.getId()});

        TestHelper.assertTimeStatusEquals(timeStatus1, retrievedTimeStatuses[0]);
        TestHelper.assertTimeStatusEquals(timeStatus2, retrievedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests DbTimeStatusDAO#getAllTimeStatuses() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses_DataAccessException() throws Exception {
        timeStatusDao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        try {
            timeStatusDao.getAllTimeStatuses();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}