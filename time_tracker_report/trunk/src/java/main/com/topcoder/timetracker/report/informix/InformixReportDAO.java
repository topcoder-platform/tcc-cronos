/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.ReportDAO;
import com.topcoder.timetracker.report.ReportSearchBuilder;
import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.ReportDataAccessException;

import com.topcoder.timetracker.report.Helper;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;

import com.topcoder.search.builder.filter.Filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class implements ReportDAO and provide the ability to create the reports using the ER
 * diagrams of Informix database described in architecture of this component. This class uses
 * InformixReportSarchBuilder for create the report for TimeEntry,ExpenseEntry or FixedBilling and
 * use Object Factory for create other reports that implement Report interface or
 * ReportSearchBuilder interface.
 * </p>
 *
 * <p>
 * <strong>Usage: </strong>
 * <ul>
 * <li>This class provides 2 constructors with the only difference about namespace in the
 * configuration file. The first uses default namespace, while the other takes the namespace from
 * the caller. The guild-lines of how to create a configuration file are described in Component Specification.
 * A sample configuration file "config.xml" is very helpful as well. </li>
 *
 * <li>There are 4 public services provided by the class. 3 of them are for retrieving specific
 * reports of Expense Entries, Fixed Billing Entries, and Time Entries. The last one takes a type of
 * reports as an extra argument to be able to retrieve reports of other types. General parameters'
 * descriptions:
 *
 * <ul>
 * <li><b>filter:</b> the filter is used to filter the results. A null value means that all
 * reports should be returned.</li>
 * <li><b>sortingColumns:</b> provides the names of columns on which the results will be sorted.
 * It can be null. </li>
 * <li><b>ascendingOrders:</b> Orders of the columns specified in sortingColumns. A true value
 * means ascending order. If sortingColumns is null, this parameter should be null as well.
 * Otherwise, it always contains the same number of elements as sortingColumns.</li>
 * </ul>
 *
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> This class has only get methods so that the class is thread
 * safe.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class InformixReportDAO implements ReportDAO {
    /**
     * <p>
     * Represents the default namespace for this class, for load the configuration.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.timetracker.report.InformixReportDAO";

    /**
     * <p>
     * Represents the type of Time entries report. It's used by the methods that use the type and
     * it's the key for the relative ReportSearchBuilder.
     * </p>
     */
    public static final String TIME_ENTRIES = "timeEntries";

    /**
     * <p>
     * Represents the type of Time expense report. It's used by the methods that use the type and
     * it's the key for the relative ReportSearchBuilder.
     * </p>
     */
    public static final String EXPENSE_ENTRIES = "expenseEntries";

    /**
     * <p>
     * Represents the type of Time fixed billing report. It's used by the methods that use the type
     * and it's the key for the relative ReportSearchBuilder.
     * </p>
     */
    public static final String FIXED_BILLING_ENTRIES = "fixedBillingEntries";

    /**
     * <p>
     * Represents the property for namespace for Object Factory Component. It's used in constructor
     * and it's used by Object Factory component for create the objects.
     * </p>
     */
    private static final String OBJECT_FACTORY_NAMESPACE_PROPERTY = "objectFactoryNameSpace";

    /**
     * <p>
     * Represents the property that contains the sub-properties for instantiate the
     * ReportSearchBuilders using Object Factory. It's used in constructor for retrieve the
     * property.
     * </p>
     */
    private static final String REPORT_SEARCH_BUILDERS_PROPERTY = "reportsSearchBuilders";

    /**
     * <p>
     * Represents the map type ReportSearchBuilder. It's initialized in constructor , not mutable,
     * not null, can't be void because at minimum contain ExpenseEntriesReportBuilder,
     * FixedBillingEntriesReportBuilder and TimeEntriesReportBuilder. The key are String; the values
     * are classes implementing ReporSearchBuilder interface. The values aren't null. The key
     * represents a String that is used for retrieve the relative ReportSearch Builder: an human
     * representation.
     * </p>
     */
    private final Map reportSearchBuilders;

    /**
     * <p>
     * Constructs a new <code>InformixReportDAO</code> with default namespace.
     * </p>
     *
     * @throws ReportConfigException if an error occurs when config file is read.
     */
    public InformixReportDAO() throws ReportConfigException {
        this.reportSearchBuilders = this.createReportSearchBuildersMap(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Constructs a new <code>InformixReportDAO</code> with a provided namespace.
     * </p>
     *
     * @param namespace the namespace for reading the configuration
     *
     * @throws IllegalArgumentException if <code>namespace</code> is null or empty string.
     * @throws ReportConfigException if some errors occur when config file is read.
     */
    public InformixReportDAO(String namespace) throws ReportConfigException {
        Helper.checkString(namespace, "namespace");
        this.reportSearchBuilders = this.createReportSearchBuildersMap(namespace);
    }

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
            boolean[] ascendingOrders) throws ReportDataAccessException {
        // get the correct builder
        ExpenseEntriesReportBuilder builder =
                (ExpenseEntriesReportBuilder) reportSearchBuilders.get(EXPENSE_ENTRIES);

        // get the result
        ReportEntryBean[] reportBeanList = builder.getReport(filter, sortingColumns, ascendingOrders);

        // convert to array of ExpenseEntryReport
        ExpenseEntryReport[] expenseEntryReport = new ExpenseEntryReport[reportBeanList.length];
        for (int i = 0; i < reportBeanList.length; i++) {
            expenseEntryReport[i] = (ExpenseEntryReport) reportBeanList[i];
        }

        // return the array
        return expenseEntryReport;
    }

    /**
     * <p>
     * Returns the list of <code>FixedBillingEntryReport</code>, filtered by <code>filter</code>
     * and ordered by columns using the relative ascending orders in the same index. If
     * <code>filter</code> is null, all entries will be returned. If <code>sortingColumns</code>
     * is null, the results are ordered only by Client and Project name. If there is no entry, an empty
     * array is returned.
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
            boolean[] ascendingOrders) throws ReportDataAccessException {
        // get the correct builder
        FixedBillingEntriesReportBuilder builder =
                (FixedBillingEntriesReportBuilder) reportSearchBuilders.get(FIXED_BILLING_ENTRIES);

        // get the result
        ReportEntryBean[] reportBeanList = builder.getReport(filter, sortingColumns, ascendingOrders);

        // convert to array of FixedBillingEntryReport
        FixedBillingEntryReport[] fixedBillingEntryReport =
                new FixedBillingEntryReport[reportBeanList.length];
        for (int i = 0; i < reportBeanList.length; i++) {
            fixedBillingEntryReport[i] = (FixedBillingEntryReport) reportBeanList[i];
        }

        // return the array
        return fixedBillingEntryReport;
    }

    /**
     * <p>
     * Returns the list of <code>TimeEntryReport</code>, filtered by <code>filter</code> and
     * ordered by columns using the relative ascending orders in the same index. If
     * <code>filter</code> is null, all entries will be returned. If <code>sortingColumns</code>
     * is null, the results are ordered only by Client and Project name. If there aren't entries then an
     * empty array is returned.
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
            boolean[] ascendingOrders) throws ReportDataAccessException {
        // get the correct builder
        TimeEntriesReportBuilder builder = (TimeEntriesReportBuilder) reportSearchBuilders.get(TIME_ENTRIES);

        // get the result
        ReportEntryBean[] reportBeanList = builder.getReport(filter, sortingColumns, ascendingOrders);

        // convert to array of TimeEntryReport
        TimeEntryReport[] timeEntryReport = new TimeEntryReport[reportBeanList.length];
        for (int i = 0; i < reportBeanList.length; i++) {
            timeEntryReport[i] = (TimeEntryReport) reportBeanList[i];
        }

        // return the array
        return timeEntryReport;
    }

    /**
     * <p>
     * Returns the reports for <code>type</code>,filtered by <code>filter</code>, and sorted
     * by the columns where each columns in i-th position have the i-th order: true for ascending
     * and false for descending. If there is no entry, an empty array is returned. If
     * <code>type</code> is not found in the map of builder, an empty array will be returned.
     * </p>
     *
     * @param type the type of Report.
     * @param filter A filter which is used to filter the results. A null value means that all
     *        entries are returned.
     * @param sortingColumns Names of the columns on which the results will be sorted.
     * @param ascendingOrders Orders of the columns. A true value means ascending order.
     *
     * @return array of reports of type <code>type</code> satisfied the filtering criterion in the
     *         expected order.
     *
     * @throws IllegalArgumentException if <code>type</code> is null or empty. If
     *         <code>sortingColumns</code> contains null or empty element (<code>sortingColumns</code>
     *         can be null). IllegalArgumentException is also thrown if <code>sortingColumns</code>
     *         is not null and <code>ascendingOrdersr</code> is null or contains a different
     *         number of elements as <code>sortingColumns</code>.
     * @throws ReportDataAccessException if some error occurs when the data is read from database
     */
    public ReportEntryBean[] getReport(String type, Filter filter, String[] sortingColumns,
            boolean[] ascendingOrders) throws ReportDataAccessException {
        // parameter checking
        Helper.checkString(type, "type");
        if (!this.reportSearchBuilders.containsKey(type)) {
            return new ReportEntryBean[0];
        }

        // get the correct builder then return the result
        ReportSearchBuilder builder = (ReportSearchBuilder) reportSearchBuilders.get(type);
        return builder.getReport(filter, sortingColumns, ascendingOrders);
    }

    /**
     * <p>
     * Constructs an <code>InformixReportDAO</code> with the given namespace.
     * <code>ConfigManager</code> is used to retrieve the necessary information from the
     * configuration file. Please consults the configuration guide-lines for more information about
     * the configuration.
     * </p>
     *
     * <p>
     * This method will be called by constructors. The map will always contain at least 3 elements:
     * builders of types ExpenseEntriesReportBuilder, FixedBillingEntriesReportBuilder,
     * TimeEntriesReportBuilder.
     * </p>
     *
     * @param namespace the namespace for read the configuration
     *
     * @return the map of builders.
     *
     * @throws ReportConfigException if some errors occur when config file is read.
     */
    private Map createReportSearchBuildersMap(String namespace) throws ReportConfigException {
        ObjectFactory objFactory = Helper.getObjectFactory(namespace, OBJECT_FACTORY_NAMESPACE_PROPERTY);
        Map builderMap = new HashMap();
        try {
            Property searchBuilderProperty =
                    ConfigManager.getInstance().getPropertyObject(namespace, REPORT_SEARCH_BUILDERS_PROPERTY);
            if (searchBuilderProperty != null) {
                // flags indicates if required builders are present or not
                boolean hasExpense = false, hasFixedBill = false, hasTime = false;

                // loop through all sub-properties
                Enumeration enumProperty = searchBuilderProperty.propertyNames();
                while (enumProperty.hasMoreElements()) {
                    String propertyName = (String) enumProperty.nextElement(); // this is the type
                    String key = (String) searchBuilderProperty.getProperty(propertyName).getValue().trim();

                    Object obj = objFactory.createObject(key);
                    if (!(obj instanceof InformixReportSearchBuilder)) {
                        throw new ReportConfigException("The object created using key "
                                + key
                                + " is not a valid instance of InformixReportSearchBuilder");
                    }

                    builderMap.put(propertyName, (InformixReportSearchBuilder) obj);

                    // set flags
                    if (propertyName.equals(EXPENSE_ENTRIES)) {
                        hasExpense = true;
                    }
                    if (propertyName.equals(FIXED_BILLING_ENTRIES)) {
                        hasFixedBill = true;
                    }
                    if (propertyName.equals(TIME_ENTRIES)) {
                        hasTime = true;
                    }
                } // end of while

                // checks if required builders are present
                if (!hasExpense || !hasFixedBill || !hasTime) {
                    throw new ReportConfigException(
                            "Property 'reportsSearchBuilders' lacks of one or more required builders.");
                }
                // returns map of builders
                return builderMap;
            }

            // property 'reportsSearchBuilders' is missing
            throw new ReportConfigException("Property 'reportsSearchBuilders' is missing.");
        } catch (UnknownNamespaceException e) {
            throw new ReportConfigException("An error occurred while creating the map.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ReportConfigException("An error occurred while creating the map.", e);
        }
    }
}
