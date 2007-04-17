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
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusManagerImpl;
import com.topcoder.timetracker.entry.time.db.DbTimeStatusDAO;

/**
 * <p>
 * Accuracy Unit test cases for TimeStatusManagerImpl.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeStatusManagerImplAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeStatusManagerImpl instance for testing.
     * </p>
     */
    private TimeStatusManagerImpl instance;

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
        DbTimeStatusDAO dao = new DbTimeStatusDAO(dbFactory, AccuracyTestHelper.CONNECTION_NAME,
            "TimeStatusIdGenerator", AccuracyTestHelper.SEARCH_NAMESPACE, auditManager);

        instance = new TimeStatusManagerImpl(dao);
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
        return new TestSuite(TimeStatusManagerImplAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatusManagerImpl#TimeStatusManagerImpl(TimeStatusDAO) for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeStatusManagerImpl instance.", instance);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time status.", timeStatus, instance.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatus(timeStatus);

        timeStatus.setDescription("Time status.");
        instance.updateTimeStatus(timeStatus);

        assertEquals("Failed to update the time status.", timeStatus, instance.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatus(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatus(timeStatus);
        instance.deleteTimeStatus(timeStatus.getId());

        assertEquals("Failed to remove the time status.", 0, instance.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatus(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time status.", timeStatus, instance.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#searchTimeStatuses(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        timeStatus.setDescription("NewTimeStatus");
        instance.createTimeStatuses(new TimeStatus[] {timeStatus});

        Filter filter = instance.getTimeStatusFilterFactory().createDescriptionFilter(timeStatus.getDescription(),
            StringMatchType.ENDS_WITH);
        TimeStatus[] timeStatuses = instance.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        assertEquals("Failed to search the time statuses.", timeStatus, timeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#createTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] insertedTimeStatuses = instance.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to insert the time statuses.", timeStatus, insertedTimeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#updateTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] {timeStatus});

        timeStatus.setDescription("Time status.");
        instance.updateTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] updatedTimeStatuses = instance.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to update the time statuses.", timeStatus, updatedTimeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#deleteTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] {timeStatus});
        instance.deleteTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to remove the time statuses.", 0, instance.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        instance.createTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] timeStatuses = instance.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to get the time statuses.", timeStatuses[0], timeStatus);
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getTimeStatusFilterFactory() for accuracy.
     * </p>
     */
    public void testGetTimeStatusFilterFactory() {
        assertNotNull("Failed to get the time status filter factory.", instance.getTimeStatusFilterFactory());
    }

    /**
     * <p>
     * Tests TimeStatusManagerImpl#getAllTimeStatuses() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        assertEquals("The time_status table should be empty.", 0, instance.getAllTimeStatuses().length);
    }
}