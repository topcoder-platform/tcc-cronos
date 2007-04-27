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
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for TimeStatusManagerImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeStatusManagerImplTests extends TestCase {
    /**
     * <p>
     * The TimeStatusManagerImpl instance for testing.
     * </p>
     */
    private TimeStatusManagerImpl impl;

    /**
     * <p>
     * The DbTimeStatusDAO instance for testing.
     * </p>
     */
    private DbTimeStatusDAO dao;

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
        dao = new DbTimeStatusDAO(dbFactory, TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
            TestHelper.SEARCH_NAMESPACE, "TimeStatusBundle", auditManager);

        impl = new TimeStatusManagerImpl(dao);
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
        return new TestSuite(TimeStatusManagerImplTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatusManagerImpl#TimeStatusManagerImpl(TimeStatusDAO) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created TimeStatusManagerImpl instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new TimeStatusManagerImpl instance.", impl);
    }

    /**
     * <p>
     * Tests ctor TimeStatusManagerImpl#TimeStatusManagerImpl(TimeStatusDAO) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatusDao is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullTimeStatusDao() {
        try {
            new TimeStatusManagerImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#createTimeStatus(TimeStatus) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time statuses.", timeStatus, impl.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus_NullTimeStatus() throws Exception {
        try {
            impl.createTimeStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus_DataAccessException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.createTimeStatus(timeStatus);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#updateTimeStatus(TimeStatus) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);

        timeStatus.setDescription("Time status.");
        impl.updateTimeStatus(timeStatus);

        assertEquals("Failed to update the time status.", timeStatus, impl.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus_NullTimeStatus() throws Exception {
        try {
            impl.updateTimeStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus_UnrecognizedEntityException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        timeStatus.setId(187);

        try {
            impl.updateTimeStatus(timeStatus);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatus(TimeStatus) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus_DataAccessException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);
        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.updateTimeStatus(timeStatus);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatus(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#deleteTimeStatus(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);

        impl.deleteTimeStatus(timeStatus.getId());

        assertEquals("Failed to remove the time status.", 0, impl.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus_UnrecognizedEntityException() throws Exception {
        try {
            impl.deleteTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus_DataAccessException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);
        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.deleteTimeStatus(timeStatus.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatus(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#getTimeStatus(long) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);

        assertEquals("Failed to get the time statuses.", timeStatus, impl.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects UnrecognizedEntityException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus_UnrecognizedEntityException() throws Exception {
        try {
            impl.getTimeStatus(500);
            fail("UnrecognizedEntityException expected.");
        } catch (UnrecognizedEntityException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatus(long) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus_DataAccessException() throws Exception {
        TimeStatus timeStatus = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatus(timeStatus);
        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.getTimeStatus(timeStatus.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] insertedTimeStatuses = impl.getTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to insert the time statuses.", timeStatus1, insertedTimeStatuses[0]);
        assertEquals("Failed to insert the time statuses.", timeStatus2, insertedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for failure.
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

        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        timeStatus1.setDescription("Time status.");
        impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] updatedTimeStatuses = impl.getTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to update the time statuses.", timeStatus1, updatedTimeStatuses[0]);
        assertEquals("Failed to update the time statuses.", timeStatus2, updatedTimeStatuses[1]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
            impl.updateTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
            impl.updateTimeStatuses(new TimeStatus[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);

        try {
            impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.updateTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#deleteTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        impl.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to remove the time statuses.", 0, impl.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for failure.
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
            impl.deleteTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for failure.
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
            impl.deleteTimeStatuses(new long[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        try {
            impl.deleteTimeStatuses(new long[] {timeStatus1.getId(), timeStatus1.getId()});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.deleteTimeStatuses(new long[] {timeStatus1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#getTimeStatuses(long[]) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1, timeStatus2});

        TimeStatus[] timeStatuses = impl.getTimeStatuses(new long[] {timeStatus1.getId(), timeStatus2.getId()});

        assertEquals("Failed to get the time statuses.", timeStatuses[0], timeStatus1);
        assertEquals("Failed to get the time statuses.", timeStatuses[1], timeStatus2);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatuses(long[]) for failure.
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
            impl.getTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatuses(long[]) for failure.
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
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.getTimeStatuses(new long[] {timeStatus1.getId()});
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#searchTimeStatuses(Filter) for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#searchTimeStatuses(Filter) is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatus1.setDescription("NewTimeStatus");
        impl.createTimeStatuses(new TimeStatus[] {timeStatus1});

        Filter filter = impl.getTimeStatusFilterFactory().createDescriptionFilter(timeStatus1.getDescription(),
            StringMatchType.ENDS_WITH);
        TimeStatus[] timeStatuses = impl.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        assertEquals("Failed to search the time statuses.", timeStatus1, timeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#searchTimeStatuses(Filter) for failure.
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
            impl.searchTimeStatuses(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#searchTimeStatuses(Filter) for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     */
    public void testSearchTimeStatuses_DataAccessException() {
        Filter filter = new EqualToFilter("no_column", "value");
        try {
            impl.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatusFilterFactory() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#getTimeStatusFilterFactory() is correct.
     * </p>
     */
    public void testGetTimeStatusFilterFactory() {
        assertNotNull("Failed to get the time status filter factory.", impl.getTimeStatusFilterFactory());
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getAllTimeStatuses() for accuracy.
     * </p>
     *
     * <p>
     * It verifies TimeStatusManagerImpl#getAllTimeStatuses() is correct.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        assertEquals("The time_status table should be empty.", 0, impl.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getAllTimeStatuses() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses_DataAccessException() throws Exception {
        dao = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator", TestHelper.SEARCH_NAMESPACE,
            "TimeStatusBundle", auditManager);
        impl = new TimeStatusManagerImpl(dao);

        try {
            impl.getAllTimeStatuses();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

}