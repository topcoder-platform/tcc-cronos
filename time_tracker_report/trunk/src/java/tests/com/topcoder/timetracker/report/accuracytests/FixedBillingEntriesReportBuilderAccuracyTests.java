/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.informix.FixedBillingEntriesReportBuilder;
import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;
import com.topcoder.timetracker.report.informix.InformixFilter;

import junit.framework.TestCase;

/**
 * The test of FixedBillingEntriesReportBuilder.
 *
 * @author brain_cn
 * @version 1.0
 */
public class FixedBillingEntriesReportBuilderAccuracyTests extends TestCase {
    /** The tset FixedBillingEntriesReportBuilder for testing. */
    private FixedBillingEntriesReportBuilder instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new FixedBillingEntriesReportBuilder();
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
     * Test method for 'FixedBillingEntriesReportBuilder()'
     */
    public void testFixedBillingEntriesReportBuilder() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport_1() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2});
        String[] sortingColumns = {"fix_bill_entry_fix_bill_entry_id"};
        boolean[] ascendingOrders = new boolean[] {false};
        FixedBillingEntryReport[] result = (FixedBillingEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 4, result[0].getFixedBillingEntry().getId());
        assertEquals("the sort is incorrcet", 3, result[1].getFixedBillingEntry().getId());
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport_2() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2});
        String[] sortingColumns = {"fix_bill_entry_fix_bill_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        FixedBillingEntryReport[] result = (FixedBillingEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 3, result[0].getFixedBillingEntry().getId());
        assertEquals("the sort is incorrcet", 4, result[1].getFixedBillingEntry().getId());
    }
}
