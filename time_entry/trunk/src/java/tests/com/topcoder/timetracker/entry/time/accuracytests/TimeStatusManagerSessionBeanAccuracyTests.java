/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.StringMatchType;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TimeStatusManagerSessionBean;

/**
 * <p>
 * Accuracy Unit test cases for TimeStatusManagerSessionBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeStatusManagerSessionBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeStatusManagerSessionBean instance for testing.
     * </p>
     */
    private TimeStatusManagerSessionBean instance;

    /**
     * <p>
     * The TimeStatusManagerDelegate instance for testing.
     * </p>
     */
    private TimeStatusManagerDelegate delegate;

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

        instance = new TimeStatusManagerSessionBean();
        AccuracyTestHelper.setUpEJBEnvironment(null, null, instance);

        delegate = new TimeStatusManagerDelegate(
            "com.topcoder.timetracker.entry.time.delegate.TimeStatusManagerDelegate");
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

        delegate = null;
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
        return new TestSuite(TimeStatusManagerSessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeStatusManagerSessionBean#TimeStatusManagerSessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeStatusManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time status.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatus(TimeStatus) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        timeStatus.setDescription("Time status.");
        delegate.updateTimeStatus(timeStatus);

        assertEquals("Failed to update the time status.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatus(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatus(timeStatus);
        delegate.deleteTimeStatus(timeStatus.getId());

        assertEquals("Failed to remove the time status.", 0, delegate.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatus(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatus() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatus(timeStatus);

        assertEquals("Failed to insert the time status.", timeStatus, delegate.getTimeStatus(timeStatus.getId()));
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#searchTimeStatuses(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        timeStatus.setDescription("NewTimeStatus");
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus});

        Filter filter = delegate.getTimeStatusFilterFactory().createDescriptionFilter(timeStatus.getDescription(),
            StringMatchType.ENDS_WITH);
        TimeStatus[] timeStatuses = delegate.searchTimeStatuses(filter);

        assertEquals("Failed to search the time statuses.", 1, timeStatuses.length);
        assertEquals("Failed to search the time statuses.", timeStatus, timeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#createTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] insertedTimeStatuses = delegate.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to insert the time statuses.", timeStatus, insertedTimeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#updateTimeStatuses(TimeStatus[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus});

        timeStatus.setDescription("Time status.");
        delegate.updateTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] updatedTimeStatuses = delegate.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to update the time statuses.", timeStatus, updatedTimeStatuses[0]);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#deleteTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus});
        delegate.deleteTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to remove the time statuses.", 0, delegate.getAllTimeStatuses().length);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatuses(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeStatuses() throws Exception {
        TimeStatus timeStatus = AccuracyTestHelper.createTimeStatus(null);
        delegate.createTimeStatuses(new TimeStatus[] {timeStatus});
        TimeStatus[] timeStatuses = delegate.getTimeStatuses(new long[] {timeStatus.getId()});

        assertEquals("Failed to get the time statuses.", timeStatuses[0], timeStatus);
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getTimeStatusFilterFactory() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeStatusFilterFactory() throws Exception {
        assertNotNull("Failed to get the time status filter factory.", instance.getTimeStatusFilterFactory());
    }

    /**
     * <p>
     * Tests TimeStatusManagerSessionBean#getAllTimeStatuses() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeStatuses() throws Exception {
        assertEquals("The time_status table should be empty.", 0, delegate.getAllTimeStatuses().length);
    }

}