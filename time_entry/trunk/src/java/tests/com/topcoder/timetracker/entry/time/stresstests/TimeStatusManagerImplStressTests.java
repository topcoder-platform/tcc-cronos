/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.util.Calendar;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TestHelper;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusManagerImpl;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;

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
public class TimeStatusManagerImplStressTests extends TestCase {
    /**
     * Represents TimeStatusManagerImpl instance for test.
     */
    private TimeStatusManagerImpl mgr;

    /**
     * <p>
     * Represents the DbTimeStatusDAO instance for testing.
     * </p>
     */
    private DbTimeStatusDAO timeStatusDao;

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
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        dbFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditManager = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        timeStatusDao = new DbTimeStatusDAO(dbFactory,
                TestHelper.CONNECTION_NAME, "TimeStatusIdGenerator",
                TestHelper.SEARCH_NAMESPACE, auditManager);
        mgr = new TimeStatusManagerImpl(timeStatusDao);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
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
        return new TestSuite(TimeStatusManagerImplStressTests.class);
    }

    /**
     * <p>
     * Tests the performance of
     * TimeStatusManagerImpl#createTimeStatus(TimeStatus).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testCreateTimeStatus() throws Exception {
        TimeStatus timeStatus = new TimeStatus();
        Calendar oldDate = Calendar.getInstance();
        oldDate.set(1996, 1, 1);

        timeStatus = TestHelper.createTestingTimeStatus(null);
        timeStatus.setCreationDate(oldDate.getTime());

        Date currentDate = new Date();
        // Insert time status into database.
        mgr.createTimeStatus(timeStatus);

        TimeStatus[] insertedTimeStatuses = mgr.getAllTimeStatuses();

        assertTimeStatus(timeStatus, insertedTimeStatuses[0]);
        assertEquals("CreationDate is incorrect.", currentDate.toString(),
                insertedTimeStatuses[0].getCreationDate().toString());
        assertEquals("ModificationDate is incorrect.", currentDate.toString(),
                insertedTimeStatuses[0].getModificationDate().toString());
    }

    /**
     * <p>
     * Tests the performance of
     * TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatuses[] = new TimeStatus[100];

        for (int i = 0; i < timeStatuses.length; ++i) {
            timeStatuses[i] = TestHelper.createTestingTimeStatus(null);
            timeStatuses[i].setDescription(i + "");
            timeStatuses[i].setCreationUser("tc1");
        }

        long startTime = System.currentTimeMillis();
        // Insert time status into database.
        mgr.createTimeStatuses(timeStatuses);
        System.out.println("Creating 100 task statuses takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TimeStatus[] insertedTimeStatuses = mgr.getAllTimeStatuses();

        for (int i = 0; i < timeStatuses.length; ++i) {
            assertTimeStatus(timeStatuses[i], insertedTimeStatuses[i]);
        }
    }

    /**
     * Assert the two time status instances are equal.
     * 
     * @param expected
     *            Expected time status.
     * @param actual
     *            Actual time status.
     */
    static void assertTimeStatus(TimeStatus expected, TimeStatus actual) {
        assertEquals("TimeStatus#id is incorrect.", expected.getId(), actual
                .getId());
        assertEquals("TimeStatus#description is incorrect.", expected
                .getDescription(), actual.getDescription());
    }

    /**
     * <p>
     * Tests the performance of
     * TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatuses[] = new TimeStatus[100];

        for (int i = 0; i < timeStatuses.length; ++i) {
            timeStatuses[i] = TestHelper.createTestingTimeStatus(null);
            timeStatuses[i].setDescription(i + "");
            timeStatuses[i].setCreationUser("tc1");
        }

        // Insert time status into database.
        mgr.createTimeStatuses(timeStatuses);

        for (int i = 0; i < timeStatuses.length; ++i) {
            timeStatuses[i].setDescription(i + " updated");
            timeStatuses[i].setCreationUser("tc1");
        }

        long startTime = System.currentTimeMillis();
        mgr.updateTimeStatuses(timeStatuses);
        System.out.println("Updating 100 time statuses takes "
                + (System.currentTimeMillis() - startTime) + " ms");

        TimeStatus[] updatedTimeStatuses = mgr.getAllTimeStatuses();

        for (int i = 0; i < timeStatuses.length; ++i) {
            assertTimeStatus(timeStatuses[i], updatedTimeStatuses[i]);
        }
    }

    /**
     * <p>
     * Tests the performance of
     * TimeStatusManagerImpl#deleteTimeStatuses(long[]).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatuses[] = new TimeStatus[100];

        for (int i = 0; i < timeStatuses.length; ++i) {
            timeStatuses[i] = TestHelper.createTestingTimeStatus(null);
            timeStatuses[i].setDescription(i + "");
            timeStatuses[i].setCreationUser("tc1");
        }

        // Insert time status into database.
        mgr.createTimeStatuses(timeStatuses);

        long[] ids = new long[50];
        for (int i = 0; i < 50; ++i) {
            ids[i] = timeStatuses[i].getId();
        }

        mgr.deleteTimeStatuses(ids);

        assertEquals("Failed to remove the time statuses.", 50, mgr
                .getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests the performance of TimeStatusManagerImpl#getTimeStatuses(long[]).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatuses[] = new TimeStatus[100];

        for (int i = 0; i < timeStatuses.length; ++i) {
            timeStatuses[i] = TestHelper.createTestingTimeStatus(null);
            timeStatuses[i].setDescription(i + "");
            timeStatuses[i].setCreationUser("tc1");
        }

        // Insert time status into database.
        mgr.createTimeStatuses(timeStatuses);

        long[] ids = new long[50];
        for (int i = 0; i < 50; ++i) {
            ids[i] = timeStatuses[i].getId();
        }

        TimeStatus[] retrievdTimeStatuses = mgr.getTimeStatuses(ids);

        for (int i = 0; i < 50; ++i) {
            assertTimeStatus(timeStatuses[i], retrievdTimeStatuses[i]);
        }
    }

    /**
     * <p>
     * Tests the performance of
     * TimeStatusManagerImpl#searchTimeStatuses(Filter).
     * </p>
     * 
     * @throws Exception
     *             Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus1 = TestHelper.createTestingTimeStatus(null);
        timeStatus1.setDescription("NewTimeStatus");
        TimeStatus timeStatus2 = TestHelper.createTestingTimeStatus(null);
        timeStatus2.setDescription("OldTimeStatus");

        mgr.createTimeStatuses(new TimeStatus[] { timeStatus1, timeStatus2 });

        Filter filter = mgr.getTimeStatusFilterFactory()
                .createDescriptionFilter("New", StringMatchType.STARTS_WITH);
        TimeStatus[] timeStatuses = mgr.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1,
                timeStatuses.length);
        assertTimeStatus(timeStatus1, timeStatuses[0]);

        filter = mgr.getTimeStatusFilterFactory().createDescriptionFilter(
                "Old", StringMatchType.STARTS_WITH);

        long startTime = System.currentTimeMillis();
        timeStatuses = mgr.searchTimeStatuses(filter);
        System.out
                .println("Searching 1 time status in 100 time statuses takes "
                        + (System.currentTimeMillis() - startTime) + " ms");

        assertEquals("Failed to search the time statuses.", 1,
                timeStatuses.length);
        assertTimeStatus(timeStatus2, timeStatuses[0]);
    }
}