/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;
import com.topcoder.timetracker.report.informix.InformixFilter;
import com.topcoder.timetracker.report.informix.InformixReportDAO;
import com.topcoder.timetracker.report.informix.TimeEntryReport;

import junit.framework.TestCase;

/**
 * The test of InformixReportDAO.
 *
 * @author brain_cn
 * @version 1.0
 */
public class InformixReportDAOAccuracyTests extends TestCase {
    /** The tset InformixReportDAO for testing. */
    private InformixReportDAO instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new InformixReportDAO();
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
     * Test method for 'InformixReportDAO()'
     */
    public void testInformixReportDAO() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'InformixReportDAO(String)'
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAOString() throws Exception {
        assertNotNull("the instance is created", new InformixReportDAO(InformixReportDAO.DEFAULT_NAMESPACE));
    }

    /**
     * Test method for 'getExpenseEntriesReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetExpenseEntriesReport() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2});
        String[] sortingColumns = {"expense_entry_expense_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        ExpenseEntryReport[] result = (ExpenseEntryReport[]) instance.getExpenseEntriesReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 3, result[0].getExpenseEntry().getId());
        assertEquals("the sort is incorrcet", 4, result[1].getExpenseEntry().getId());
    }

    /**
     * Test method for 'getFixedBillingEntriesReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingEntriesReport() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2});
        String[] sortingColumns = {"fix_bill_entry_fix_bill_entry_id"};
        boolean[] ascendingOrders = new boolean[] {false};
        FixedBillingEntryReport[] result = (FixedBillingEntryReport[]) instance.getFixedBillingEntriesReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 2;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 4, result[0].getFixedBillingEntry().getId());
        assertEquals("the sort is incorrcet", 3, result[1].getFixedBillingEntry().getId());
    }

    /**
     * Test method for 'getTimeEntriesReport(Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetTimeEntriesReport() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2, 3});
        String[] sortingColumns = {"time_entry_time_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        TimeEntryReport[] result = (TimeEntryReport[]) instance.getTimeEntriesReport(filter, sortingColumns, ascendingOrders);

        int expectSize = 3;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 3, result[0].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 4, result[1].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 5, result[2].getTimeEntry().getId());
    }

    /**
     * Test method for 'getReport(String, Filter, String[], boolean[])'
     *
     * @throws Exception to JUnit
     */
    public void testGetReport() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {2, 3});
        String[] sortingColumns = {"time_entry_time_entry_id"};
        boolean[] ascendingOrders = new boolean[] {true};
        TimeEntryReport[] result = (TimeEntryReport[]) instance.getReport("timeEntries", filter, sortingColumns, ascendingOrders);

        int expectSize = 3;
        // Verify the result
        assertEquals("the size is incorrect", result.length, expectSize);
        assertEquals("the sort is incorrcet", 3, result[0].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 4, result[1].getTimeEntry().getId());
        assertEquals("the sort is incorrcet", 5, result[2].getTimeEntry().getId());
    }

}
