/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.Helper;
import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.ReportDataAccessException;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * The scope of this class is to report the FixedBilling entries using the filter. The name of
 * searchBundle used for query the database is in the
 * FixedBillingEntriesReportBuilder#getSearchBundleName() method.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> The class is thread safe because the parent is thread safe and
 * the class have no variable added for the state.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class FixedBillingEntriesReportBuilder extends InformixReportSearchBuilder {

    /**
     * <p>
     * Represents the name of search bundle for query the database for have the FixedBillingEntry
     * entries. It's used by getSearchBundleName method.
     * </p>
     */
    public static final String SEARCH_BUNDLE = "fixedBillingEntries";

    /**
     * <p>
     * Constructs a new <code>FixedBillingEntriesReportBuilder</code>.
     * </p>
     *
     * @throws ReportConfigException if there is any problem with configuration file.
     */
    public FixedBillingEntriesReportBuilder() throws ReportConfigException {
        super();
    }

    /**
     * <p>
     * This is the main method of this class. Using the filter it return the report for
     * FixedBillingEntry entries. sort by the columns where each columns in i-th position have the
     * i-th order: true for ascending and false for descending. . If filter is null return all
     * entries. If columns are null the result is ordered only by Client name and Project name.
     * </p>
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of FixedBillingEntryReports satisfied the filtering criterion in the expected
     *         order.
     *
     * @throws IllegalArgumentException if <code>sortingColumns</code> contains null or empty
     *         element (<code>sortingColumns</code> can be null). IllegalArgumentException is
     *         also thrown if <code>sortingColumns</code> is not null and
     *         <code>ascendingOrders</code> is null or contains a different number of elements as
     *         <code>sortingColumns</code>.
     * @throws ReportDataAccessException wrap any exception (SQL exceptions, Search Builder
     *         exceptions) with this exception.
     */
    public ReportEntryBean[] getReport(Filter filter, String[] sortingColumns, boolean[] ascendingOrders)
        throws ReportDataAccessException {
        // get the result set
        CustomResultSet resultSet = this.getCustomResultSet(filter, sortingColumns, ascendingOrders);

        // create the array of FixedBillingEntryReport
        FixedBillingEntryReport[] reports = new FixedBillingEntryReport[resultSet.getRecordCount()];
        resultSet.first();
        for (int i = 0; i < reports.length; i++) {
            reports[i] = new FixedBillingEntryReport();

            reports[i].setClient(Helper.createClient(resultSet));
            reports[i].setProject(Helper.createProject(resultSet));
            reports[i].setUser(Helper.createUser(resultSet));

            FixedBillingStatus status = Helper.createFixedBillingStatus(resultSet);
            reports[i].setFixedBillingStatus(status);
            reports[i].setFixedBillingEntry(Helper.createFixedBillingEntry(resultSet, status));

            resultSet.next(); // next record
        }
        return reports;
    }

    /**
     * <p>
     * Retrieves the name of the search bundle which is used to query the database. This search
     * bundle is for working with Fixed Billing Entry reports.
     * </p>
     *
     * @return the name of search bundle used for query the database.
     */
    protected String getSearchBundleName() {
        return SEARCH_BUNDLE;
    }
}
