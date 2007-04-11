/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.informix.InformixFilter;
import com.topcoder.timetracker.report.informix.TimeEntriesReportBuilder;
import com.topcoder.timetracker.report.informix.TimeEntryReport;

import junit.framework.TestCase;

/**
 * The test of TimeEntriesReportBuilder.
 *
 * @author brain_cn
 * @version 1.0
 */
public class TimeEntriesReportBuilderAccuracyTests extends TestCase {
    /** The tset TimeEntriesReportBuilder for testing. */
    private TimeEntriesReportBuilder instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new TimeEntriesReportBuilder();
        ConfigHelper.clearData();
        ConfigHelper.setupData();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.clearData();
        ConfigHelper.releaseNamespaces();
    }

    /**
     * Test method for 'TimeEntriesReportBuilder()'
     */
    public void testTimeEntriesReportBuilder() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport_1() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2, 3});
        String[] sortingColumns = {"time_entry_time_entry_id"};
        boolean[] ascendingOrders = new boolean[] {false};
        TimeEntryReport[] result = (TimeEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 3;
        // Verify the result, it should be sort by project first
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 4, result[0].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 3, result[1].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 5, result[2].getTimeEntry().getId());
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport_2() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2, 3});
        String[] sortingColumns = {"time_entry_time_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        TimeEntryReport[] result = (TimeEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 3;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 3, result[0].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 4, result[1].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 5, result[2].getTimeEntry().getId());
    }
}
