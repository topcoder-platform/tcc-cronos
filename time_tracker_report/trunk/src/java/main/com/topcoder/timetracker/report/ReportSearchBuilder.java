/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import com.topcoder.timetracker.report.informix.ReportEntryBean;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines the contract that must be implemented by the classes that retrieve reports
 * from the database by employing Search Builder Component.
 * </p>
 *
 *
 * <p>
 * <strong>Thread-safety:</strong> It's required that the classes that implement this interface
 * must be thread safe for maintaining the component also thread safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public interface ReportSearchBuilder {
    /**
     * <p>
     * Returns the generic report using the filter, sort by the columns where each columns in i-th
     * position have the i-th order: true for ascending and false for descending.
     * </p>
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of reports satisfied the filtering criterion in the expected order.
     *
     * @throws IllegalArgumentException if <code>sortingColumns</code> contains null or empty
     *         element (<code>sortingColumns</code> can be null). IllegalArgumentException is
     *         also thrown if <code>sortingColumns</code> is not null and
     *         <code>ascendingOrders</code> is null or contains a different number of elements as
     *         <code>sortingColumns</code>. Both <code>sortingColumns</code> and
     *         <code>ascendingOrders</code> always are null, or nut null and contains the same
     *         number of elements.
     * @throws ReportDataAccessException wrap any exception that involved the reading of database
     *         with this exception.
     */
    public ReportEntryBean[] getReport(Filter filter, String[] sortingColumns, boolean[] ascendingOrders)
        throws ReportDataAccessException;
}
