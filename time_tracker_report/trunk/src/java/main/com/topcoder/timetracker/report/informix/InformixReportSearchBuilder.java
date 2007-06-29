/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.informix;

import java.util.Comparator;

import com.topcoder.timetracker.report.Helper;
import com.topcoder.timetracker.report.ReportSearchBuilder;
import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.ReportDataAccessException;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * The scope of this class is to retrieve the SearchBundleManager for query the database through the
 * SearchBundles. The SearchBundleManager is retrieve from config file. Every class that extends
 * this class defines the name of SearchBundle for query the database with Search Builder component.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> The class is thread safe because has only a variable state that
 * is initialiazed in constructor and final.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public abstract class InformixReportSearchBuilder implements ReportSearchBuilder {

    /**
     * <p>
     * Represents the namespace of SearchBundleManager. It's used in constructor forretrieve the
     * properties for this namespace.
     * </p>
     */
    private static final String DEFAULT_SEARCH_BUNDLE_MANAGER_NAMESPACE = "informixSearchBundleManager";

    /**
     * <p>
     * Represents the SearchBundleManager for get the SearchBundles. It's initialized in constructor
     * and it isn't null.
     * </p>
     */
    private SearchBundleManager searchBundleManager;

    /**
     * <p>
     * Constructs a new <code>InformixReportSarchBuilder</code> with default namespace.
     * </p>
     *
     * @throws ReportConfigException if an error occurs when config file is read.
     */
    protected InformixReportSearchBuilder() throws ReportConfigException {
        this(DEFAULT_SEARCH_BUNDLE_MANAGER_NAMESPACE);
    }

    /**
     * <p>
     * Constructs a new <code>InformixReportSarchBuilder</code> with specified namespace.
     * </p>
     *
     * @param searchBundlesNamespace
     *            A namespace containing search bundle definitions.
     * @throws ReportConfigException if an error occurs when config file is read.
     */
    protected InformixReportSearchBuilder(String searchBundlesNamespace) throws ReportConfigException {
        if (searchBundlesNamespace == null || searchBundlesNamespace.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "Parameter 'searchBundlesNamespace' must not be null or empty (trimmed) string.");
        }

        try {
            searchBundleManager = new SearchBundleManager(searchBundlesNamespace);
        } catch (SearchBuilderConfigurationException ex) {
            throw new ReportConfigException("Exception while creating search bundle manager.", ex);
        }
    }

    /**
     * <p>
     * Returns the name of the SearchBundle used by the children class.
     * </p>
     *
     * @return the name of the SearchBundle used by the children class.
     */
    protected abstract String getSearchBundleName();

    /**
     * <p>
     * Return the CustomResultSet using the SearchBundle and order the columns. If filter is null
     * return all entries. If columns is null the result is ordered only by Client name and Project
     * name.
     *
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return the CustomResultSet contains report data. Each row of the CustomResultSet is a
     *         report. The type of the reports will be determined by the caller's search bundle.
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
    protected CustomResultSet getCustomResultSet(Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException {
        // parameter checking
        if ((sortingColumns != null && ascendingOrders == null)
                || (sortingColumns == null && ascendingOrders != null)) {
            throw new IllegalArgumentException(
                    "sortingColumns and ascendingOrders should both be null or both be not-null.");
        }
        if (sortingColumns != null) {
            Helper.checkArrayString(sortingColumns, "sortingColumns");
            if (sortingColumns.length != ascendingOrders.length) {
                throw new IllegalArgumentException(
                        "ascendingOrders should have the same number of element as sortingColumns");
            }
        }

        // create filter if it is null
        if (filter == null) {
            filter = new EqualToFilter("all", new Long(1));
        }

        // get the result set from search bundle
        try {
            SearchBundle searchBundle = searchBundleManager.getSearchBundle(this.getSearchBundleName());
            CustomResultSet resultSet = (CustomResultSet) searchBundle.search(filter);

            // creat columns and order arrays
            int numberOfColumns = (sortingColumns == null) ? 2 : 2 + sortingColumns.length;
            String[] finalColumnSorting = new String[numberOfColumns];
            finalColumnSorting[0] = "client_name"; // client
            finalColumnSorting[1] = "project_name"; // project

            boolean[] finalAscendingOrders = new boolean[numberOfColumns];
            finalAscendingOrders[0] = true; // client
            finalAscendingOrders[1] = true; // project

            if (numberOfColumns > 2) {
                for (int i = 0; i < sortingColumns.length; i++) {
                    finalColumnSorting[i + 2] = sortingColumns[i];
                    finalAscendingOrders[i + 2] = ascendingOrders[i];
                }
            }

            // create the comparators: an array (with length=numberOfColumns) of comparators are
            // created, a null value is for ascending order; otherwise, the corresponding column is
            // sorted in descending order.
            Comparator[] comparators = new Comparator[numberOfColumns];
            for (int i = 0; i < numberOfColumns; i++) {
                if (!finalAscendingOrders[i]) {
                    comparators[i] = new Comparator() {
                        public int compare(Object arg0, Object arg1) {
                            return -((Comparable) arg0).compareTo(arg1);
                        }
                    };
                }
            }

            // do the sort then return the result set
            resultSet.sortAscending(finalColumnSorting, comparators);
            return resultSet;
        } catch (SearchBuilderException ex) {
            throw new ReportDataAccessException("An error occurred while retrieving the result set.", ex);
        }
    }
}
