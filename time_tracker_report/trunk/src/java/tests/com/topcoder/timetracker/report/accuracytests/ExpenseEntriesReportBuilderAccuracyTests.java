/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.informix.ExpenseEntriesReportBuilder;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.InformixFilter;

import junit.framework.TestCase;

/**
 * The test of ExpenseEntriesReportBuilder.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ExpenseEntriesReportBuilderAccuracyTests extends TestCase {
    /** The tset ExpenseEntriesReportBuilder for testing. */
    private ExpenseEntriesReportBuilder instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new ExpenseEntriesReportBuilder();
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
     * Test method for 'ExpenseEntriesReportBuilder()'
     */
    public void testExpenseEntriesReportBuilder() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {1});
        String[] sortingColumns = {"expense_entry_expense_entry_id"};
        boolean[] ascendingOrders = new boolean[] {false};
        ExpenseEntryReport[] result = (ExpenseEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 2, result[0].getExpenseEntry().getId());
        assertEquals("the sort is incorrcet", 1, result[1].getExpenseEntry().getId());
    }

    /**
     * Test method for 'getReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport_2() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {1});
        String[] sortingColumns = {"expense_entry_expense_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        ExpenseEntryReport[] result = (ExpenseEntryReport[]) instance.getReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 1, result[0].getExpenseEntry().getId());
        assertEquals("the sort is incorrcet", 2, result[1].getExpenseEntry().getId());
    }
}
