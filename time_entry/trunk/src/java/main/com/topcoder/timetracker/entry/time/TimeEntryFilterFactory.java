/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.util.Date;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Time Tracker Time Entries.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.  The factory is capable of producing
 * filters that conform to a specific schema, and is associated with the <code>TimeEntryManager</code>
 * that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>TimeEntryManager</code> or
 * <code>TimeEntryDAO</code> implementation that produced this <code>TimeEntryFilterFactory</code> instance.
 * This ensures that the Filters can be recognized.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any
 * of the Composite Filters in the Search Builder Component (<code>AndFilter</code>, <code>OrFilter</code>,
 * etc.)
 * </p>
 *
 * <p>
 * Note that all ranges specified are inclusive of the boundaries. A <code>null</code> or <code>Double.NaN</code>
 * boundary specified means that the given end will be unbounded.
 * </p>
 *
 * <p>
 * Thread Safety : Implementations are required to be thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TimeEntryFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's invoice id.
     * </p>
     *
     * @param invoiceId The invoice id to use.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id is &lt; -1.
     */
    public Filter createInvoiceIdFilter(long invoiceId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's
     * description.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param description The description of the time entry.
     * @param matchType The string matching type to use.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or matching type is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's entry date.
     * </p>
     *
     * <p>
     * A date range that may be open-ended can be specified.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with entry
     * date less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with entry
     * date larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with entry date between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.
     * @param rangeEnd The upper bound of the date criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if both parameters are null, or rangeStart &gt; rangeEnd.
     */
    public Filter createEntryDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's amount of hours.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be <code>Double.NaN</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is <code>Double.NaN</code>, then the filter will search the records
     * with hours less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is <code>Double.NaN</code>, then the filter will search the records
     * with hours larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not <code>Double.NaN</code>, then the filter will search the records with hours
     * between the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param rangeStart The lower bound of the criterion.
     * @param rangeEnd The upper bound of the criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if both arguments are <code>Double.NaN</code> or if
     * rangeStart &gt; rangeEnd.
     */
    public Filter createHoursFilter(double rangeStart, double rangeEnd);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's
     * task type.
     * </p>
     *
     * @param taskType The task type of the entry.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the taskType is null.
     */
    public Filter createTaskTypeFilter(TaskType taskType);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's
     * time status.
     * </p>
     *
     * @param timeStatus The status of the entry.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the timeStatus is null.
     */
    public Filter createTimeStatusFilter(TimeStatus timeStatus);

    /**
     * <p>
     * This creates a Filter that will select TimeEntries based on the Entry's billable flag.
     * </p>
     *
     * @param isBillable The billable flag of the entry.
     * @return A filter that will be based off the specified criteria.
     */
    public Filter createBillableFilter(boolean isBillable);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's
     * reject reason id.
     * </p>
     *
     * @param rejectReasonId The reject reason to search for.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id is &lt; 0.
     */
    public Filter createRejectReasonFilter(long rejectReasonId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeEntries based on the Entry's
     * company id.
     * </p>
     *
     * @param companyId the id of the Time Tracker Company to search.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt; 0.
     */
    public Filter createCompanyIdFilter(long companyId);
}