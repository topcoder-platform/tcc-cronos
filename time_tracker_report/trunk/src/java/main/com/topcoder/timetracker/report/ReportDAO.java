/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import com.topcoder.timetracker.report.informix.ExpenseEntryReport;
import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;
import com.topcoder.timetracker.report.informix.TimeEntryReport;
import com.topcoder.timetracker.report.informix.ReportEntryBean;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is the main interface of this component. It defines the scope of this component, the ability
 * to create the report for time entries, fixed billing entries and expense entries and other
 * reports from the type. So the report doesn't contain the presentation but it is only in data
 * logic.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> It's required that the classes that implement this interface
 * must be thread safe for maintaining the component also thread safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public interface ReportDAO {

    /**
     * <p>
     * Returns the list of <code>ExpenseEntryReport</code>, filtered by the <code>filter</code>
     * and ordered by columns using the relative ascending orders in the same index. If
     * <code>filter</code> is null, all entries will be returned. If <code>sortingColumns</code>
     * is null, the result is ordered only by Client name and Project name. If there is no entry, an
     * empty array is returned.
     * </p>
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of <code>ExpenseEntryReport</code> satisfied the filtering criterion in the
     *         expected order.
     *
     * @throws IllegalArgumentException if <code>sortingColumns</code> contains null or empty
     *         element (<code>sortingColumns</code> can be null). IllegalArgumentException is
     *         also thrown if <code>sortingColumns</code> is not null and
     *         <code>ascendingOrdersr</code> is null or contains a different number of elements as
     *         <code>sortingColumns</code>.
     * @throws ReportDataAccessException if some error occurs when the data is read from database.
     */
    public ExpenseEntryReport[] getExpenseEntriesReport(Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException;

    /**
     * <p>
     * Returns the list of <code>FixedBillingEntryReport</code>, filtered by <code>filter</code>
     * and ordered by columns using the relative ascending orders in the same index. If
     * <code>filter</code> is null, all entries will be returned. If <code>sortingColumns</code>
     * is null the results are ordered only by Client and Project name. If there is no entry, an
     * empty array is returned.
     * </p>
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of FixedBillingEntryReport satisfied the filtering criterion in the expected
     *         order.
     *
     * @throws IllegalArgumentException if <code>sortingColumns</code> contains null or empty
     *         element (<code>sortingColumns</code> can be null). IllegalArgumentException is
     *         also thrown if <code>sortingColumns</code> is not null and
     *         <code>ascendingOrdersr</code> is null or contains a different number of elements as
     *         <code>sortingColumns</code>.
     * @throws ReportDataAccessException if some error occurs when the data is read from database.
     */
    public FixedBillingEntryReport[] getFixedBillingEntriesReport(Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException;

    /**
     * <p>
     * Returns the list of <code>TimeEntryReport</code>, filtered by <code>filter</code> and
     * ordered by columns using the relative ascending orders in the same index. If
     * <code>filter</code> is null, all entries will be returned. If <code>sortingColumns</code>
     * is null the result are ordered only by Client and Project name. If there aren't entries
     * then an empty array is returned.
     * </p>
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of TimeEntryReport satisfied the filtering criterion in the expected order.
     *
     * @throws IllegalArgumentException if <code>sortingColumns</code> contains null or empty
     *         element (<code>sortingColumns</code> can be null). IllegalArgumentException is
     *         also thrown if <code>sortingColumns</code> is not null and
     *         <code>ascendingOrdersr</code> is null or contains a different number of elements as
     *         <code>sortingColumns</code>.
     * @throws ReportDataAccessException if some error occurs when the data is read from database
     */
    public TimeEntryReport[] getTimeEntriesReport(Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException;

    /**
     * <p>
     * Returns the reports for <code>type</code>,filtered by <code>filter</code>, and sorted
     * by the columns where each columns in i-th position have the i-th order: true for ascending
     * and false for descending. If there is no entry, an empty array is returned.
     * </p>
     *
     * @param type the type of Report
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of reports of type <code>type</code> satisfied the filtering criterion in the
     *         expected order.
     *
     * @throws IllegalArgumentException if <code>type</code> is null or empty, or does not exist
     *         in the builder map. If <code>sortingColumns</code> contains null or empty element
     *         (<code>sortingColumns</code> can be null). IllegalArgumentException is also thrown
     *         if <code>sortingColumns</code> is not null and <code>ascendingOrdersr</code> is null
     *         or contains a different number of elements as <code>sortingColumns</code>.
     * @throws ReportDataAccessException if some error occurs when the data is read from database
     */
    public ReportEntryBean[] getReport(String type, Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException;
}
