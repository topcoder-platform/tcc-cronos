/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.delegate.TimeEntryManagerDelegate;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerSessionBean;

/**
 * <p>
 * Accuracy Unit test cases for TimeEntryManagerSessionBean.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class TimeEntryManagerSessionBeanAccuracyTests extends TestCase {
    /**
     * <p>
     * TimeEntryManagerSessionBean instance for testing.
     * </p>
     */
    private TimeEntryManagerSessionBean instance;

    /**
     * <p>
     * The TimeEntryManagerDelegate instance for testing.
     * </p>
     */
    private TimeEntryManagerDelegate delegate;

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

        instance = new TimeEntryManagerSessionBean();
        AccuracyTestHelper.setUpEJBEnvironment(null, instance, null);
        delegate = new TimeEntryManagerDelegate("com.topcoder.timetracker.entry."
            + "time.delegate.TimeEntryManagerDelegate");
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
        return new TestSuite(TimeEntryManagerSessionBeanAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor TimeEntryManagerSessionBean#TimeEntryManagerSessionBean() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create TimeEntryManagerSessionBean instance.", delegate);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entry.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        timeEntry.setDescription("Time Entry.");
        delegate.updateTimeEntry(timeEntry, false);

        assertEquals("Failed to update the time entry.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntry(long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);
        delegate.deleteTimeEntry(timeEntry.getId(), true);

        assertEquals("Failed to remove the time entries.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntry(long) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);

        assertEquals("Failed to insert the time entry.", timeEntry, delegate.getTimeEntry(timeEntry.getId()));
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#addRejectReasonToEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddRejectReasonToEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);
        delegate.addRejectReasonToEntry(timeEntry, 1, true);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds.length);
        assertEquals("Failed to add the reject reason to the time entry.", 1, rejectReasonIds[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeRejectReasonFromEntry(TimeEntry,long,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveRejectReasonFromEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);
        delegate.addRejectReasonToEntry(timeEntry, 1, true);
        delegate.removeRejectReasonFromEntry(timeEntry, 1, true);
        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);

        assertEquals("Failed to remove the reject reason of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#removeAllRejectReasonsFromEntry(TimeEntry,boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRemoveAllRejectReasonsFromEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);
        delegate.addRejectReasonToEntry(timeEntry, 1, true);
        delegate.addRejectReasonToEntry(timeEntry, 2, true);
        delegate.removeAllRejectReasonsFromEntry(timeEntry, true);

        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Failed to remove all the reject reasons of the time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getAllRejectReasonsForEntry(TimeEntry) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllRejectReasonsForEntry() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntry(timeEntry, true);
        long[] rejectReasonIds = delegate.getAllRejectReasonsForEntry(timeEntry);
        assertEquals("Expected no reject reason for time entry.", 0, rejectReasonIds.length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#searchTimeEntries(Filter) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry}, true);

        Filter filter = delegate.getTimeEntryFilterFactory().createCompanyIdFilter(timeEntry.getCompanyId());
        TimeEntry[] timeEntries = delegate.searchTimeEntries(filter);

        assertEquals("Failed to search the time entries.", 1, timeEntries.length);
        assertEquals("Failed to search the time entries.", timeEntry, timeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#createTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        TimeEntry[] insertedTimeEntries = delegate.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to insert the time entries.", timeEntry, insertedTimeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#updateTimeEntries(TimeEntry[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry}, true);

        timeEntry.setDescription("Time Entry.");
        delegate.updateTimeEntries(new TimeEntry[] {timeEntry}, false);
        TimeEntry[] updatedTimeEntries = delegate.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to update the time entries.", timeEntry, updatedTimeEntries[0]);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#deleteTimeEntries(long[],boolean) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        delegate.deleteTimeEntries(new long[] {timeEntry.getId()}, true);

        assertEquals("Failed to remove the time entries.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntries(long[]) for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntries() throws Exception {
        TimeEntry timeEntry = AccuracyTestHelper.createTimeEntry(null);
        delegate.createTimeEntries(new TimeEntry[] {timeEntry}, true);
        TimeEntry[] timeEntries = delegate.getTimeEntries(new long[] {timeEntry.getId()});

        assertEquals("Failed to get the time entries.", timeEntries[0], timeEntry);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getAllTimeEntries() for accuracy.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetAllTimeEntries() throws Exception {
        assertEquals("The time_entry table should be empty.", 0, delegate.getAllTimeEntries().length);
    }

    /**
     * <p>
     * Tests TimeEntryManagerSessionBean#getTimeEntryFilterFactory() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetTimeEntryFilterFactory() throws Exception {
        assertNotNull("Failed to get the time entry filter factory.", instance.getTimeEntryFilterFactory());
    }

}