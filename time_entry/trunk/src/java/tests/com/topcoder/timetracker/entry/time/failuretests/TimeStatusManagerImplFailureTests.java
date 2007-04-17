/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusManagerImpl;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;

import junit.framework.TestCase;


/**
 * <p>
 * Failure test cases for TimeStatusManagerImpl.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TimeStatusManagerImplFailureTests extends TestCase {
    /**
     * <p>
     * The TimeStatusManagerImpl instance for testing.
     * </p>
     */
    private TimeStatusManagerImpl instance;

    /**
     * <p>
     * The DbTimeStatusDAO instance for testing.
     * </p>
     */
    private DbTimeStatusDAO dbTimeStatusDAO;

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
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadXMLConfig(FailureTestHelper.CONFIG_FILE);
        FailureTestHelper.loadXMLConfig(FailureTestHelper.AUDIT_CONFIG_FILE);
        FailureTestHelper.setUpDataBase();
        FailureTestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(FailureTestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(FailureTestHelper.AUDIT_NAMESPACE);
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, FailureTestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);

        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);
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

        instance = null;
        dbFactory = null;
        auditManager = null;
        dbTimeStatusDAO = null;
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
    public void testCtor() {
        try {
            new TimeStatusManagerImpl(null);
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
     * It tests the case that when timeStatus is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus1() throws Exception {
        try {
            instance.createTimeStatus(null);
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
    public void testCreateTimeStatus2() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.createTimeStatus(timeStatus);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testUpdateTimeStatus1() throws Exception {
        try {
            instance.updateTimeStatus(null);
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
    public void testUpdateTimeStatus2() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
        timeStatus.setId(187);

        try {
            instance.updateTimeStatus(timeStatus);
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
    public void testUpdateTimeStatus3() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatus(timeStatus);
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.updateTimeStatus(timeStatus);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testDeleteTimeStatus1() throws Exception {
        try {
            instance.deleteTimeStatus(500);
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
    public void testDeleteTimeStatus2() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatus(timeStatus);
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.deleteTimeStatus(timeStatus.getId());
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testGetTimeStatus1() throws Exception {
        try {
            instance.getTimeStatus(500);
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
    public void testGetTimeStatus2() throws Exception {
        TimeStatus timeStatus = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatus(timeStatus);
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.getTimeStatus(timeStatus.getId());
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
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses1() throws Exception {
        try {
            instance.createTimeStatuses(null);
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
    public void testCreateTimeStatuses2() throws Exception {
        try {
            instance.createTimeStatuses(new TimeStatus[0]);
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
    public void testCreateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
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
    public void testCreateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, null });
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
    public void testCreateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        timeStatus2.setId(34);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testCreateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("CreationUser");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testCreateTimeStatuses7() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testCreateTimeStatuses8() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);

        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
     * It tests the case that when timeStatuses is null and expects IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses1() throws Exception {
        try {
            instance.updateTimeStatuses(null);
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
    public void testUpdateTimeStatuses2() throws Exception {
        try {
            instance.updateTimeStatuses(new TimeStatus[0]);
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
    public void testUpdateTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus1 });
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
    public void testUpdateTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, null });
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
    public void testUpdateTimeStatuses5() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testUpdateTimeStatuses6() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus("ModificationUser");
        timeStatus2.setId(300);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
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
    public void testUpdateTimeStatuses7() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        TimeStatus timeStatus2 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });

        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.updateTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testDeleteTimeStatuses1() throws Exception {
        try {
            instance.deleteTimeStatuses(null);
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
    public void testDeleteTimeStatuses2() throws Exception {
        try {
            instance.deleteTimeStatuses(new long[0]);
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
    public void testDeleteTimeStatuses3() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        try {
            instance.deleteTimeStatuses(new long[] { timeStatus1.getId(), timeStatus1.getId() });
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
    public void testDeleteTimeStatuses4() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.deleteTimeStatuses(new long[] { timeStatus1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testGetTimeStatuses1() throws Exception {
        try {
            instance.getTimeStatuses(null);
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
    public void testGetTimeStatuses2() throws Exception {
        TimeStatus timeStatus1 = FailureTestHelper.createTestingTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] { timeStatus1 });

        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.getTimeStatuses(new long[] { timeStatus1.getId() });
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testSearchTimeStatuses1() throws Exception {
        try {
            instance.searchTimeStatuses(null);
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
    public void testSearchTimeStatuses2() {
        Filter filter = new EqualToFilter("no_column", "value");

        try {
            instance.searchTimeStatuses(filter);
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
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
    public void testGetAllTimeStatuses() throws Exception {
        dbTimeStatusDAO = new DbTimeStatusDAO(dbFactory, "empty", "TimeStatusIdGenerator",
                FailureTestHelper.SEARCH_NAMESPACE, auditManager);
        instance = new TimeStatusManagerImpl(dbTimeStatusDAO);

        try {
            instance.getAllTimeStatuses();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}
