/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeStatus;

import java.util.Date;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.StringMatchType;

/**
 * <p>
 * This class implements the interface <code>TimeEntryFilterFactory</code>.
 * </p>
 *
 * <p>
 * It defines a factory that is capable of creating search filters used for searching through
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
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeEntryFilterFactory extends DbBaseFilterFactory implements TimeEntryFilterFactory {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -4344942949268600542L;

	/**
     * <p>
     * This is the map key to use to specify the column name for the Invoice Id.
     * </p>
     */
    public static final String INVOICE_ID_COLUMN_NAME = "INVOICE_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the description.
     * </p>
     */
    public static final String DESCRIPTION_COLUMN_NAME = "DESCRIPTION";

    /**
     * <p>
     * This is the map key to use to specify the column name for the entry date.
     * </p>
     */
    public static final String ENTRY_DATE_COLUMN_NAME = "ENTRY_DATE";

    /**
     * <p>
     * This is the map key to use to specify the column name for the hours.
     * </p>
     */
    public static final String HOURS_COLUMN_NAME = "HOURS";

    /**
     * <p>
     * This is the map key to use to specify the column name for the task type.
     * </p>
     */
    public static final String TASK_TYPE_COLUMN_NAME = "TASK_TYPE_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the time status.
     * </p>
     */
    public static final String TIME_STATUS_COLUMN_NAME = "TIME_STATUS_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the billable flag.
     * </p>
     */
    public static final String BILLABLE_COLUMN_NAME = "BILLABLE";

    /**
     * <p>
     * This is the map key to use to specify the column name for the rejectReasons.
     * </p>
     */
    public static final String REJECT_REASONS_COLUMN_NAME = "REJECT_REASONS_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the company id.
     * </p>
     */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the client id.
     * </p>
     */
    public static final String CLIENT_ID_COLUMN_NAME = "CLIENT";

    /**
     * <p>
     * This is the map key to use to specify the column name for the project id.
     * </p>
     */
    public static final String PROJECT_ID_COLUMN_NAME = "PROJECT";

    /**
     * <p>
     * Creates a <code>DbTimeEntryFilterFactory</code>.
     * </p>
     *
     * @param columnNames The column definitions to use.
     *
     * @throws IllegalArgumentException if columnNames contains null or empty String keys
     * or values, or if it is missing a Map Entry for the static constants defined in this class.
     */
    public DbTimeEntryFilterFactory() {
        // empty
    }

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
    public Filter createInvoiceIdFilter(long invoiceId) {
        if (invoiceId < -1) {
            throw new IllegalArgumentException("The invoice id is less than -1.");
        }

        return new EqualToFilter(INVOICE_ID_COLUMN_NAME, new Long(invoiceId));
    }

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
     * @param matchtype The string matching type to use.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or matching type is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchtype) {
        Util.checkString(description, "description");
        Util.checkNull(matchtype, "matchType");

        return Util.createFilter(matchtype, DESCRIPTION_COLUMN_NAME, description);
    }

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
    public Filter createEntryDateFilter(Date rangeStart, Date rangeEnd) {
        return Util.createRangeFilter(ENTRY_DATE_COLUMN_NAME, rangeStart, rangeEnd);
    }

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
    public Filter createHoursFilter(double rangeStart, double rangeEnd) {
        if (Double.isNaN(rangeStart) && Double.isNaN(rangeEnd)) {
            throw new IllegalArgumentException("Both range start and end are NaN.");
        }

        if (Double.isNaN(rangeStart) && !Double.isNaN(rangeEnd)) {
            return new LessThanOrEqualToFilter(HOURS_COLUMN_NAME, new Double(rangeEnd));
        }

        if (!Double.isNaN(rangeStart) && Double.isNaN(rangeEnd)) {
            return new GreaterThanOrEqualToFilter(HOURS_COLUMN_NAME, new Double(rangeStart));
        }

        if (rangeStart > rangeEnd) {
            throw new IllegalArgumentException("The given range start is larger than the range end.");
        }

        return new BetweenFilter(HOURS_COLUMN_NAME, new Double(rangeEnd), new Double(rangeStart));
    }

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
    public Filter createTaskTypeFilter(TaskType taskType) {
        Util.checkNull(taskType, "taskType");
        return new EqualToFilter(TASK_TYPE_COLUMN_NAME, new Long(taskType.getId()));
    }

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
    public Filter createTimeStatusFilter(TimeStatus timeStatus) {
        Util.checkNull(timeStatus, "timeStatus");
        return new EqualToFilter(TIME_STATUS_COLUMN_NAME, new Long(timeStatus.getId()));
    }

    /**
     * <p>
     * This creates a Filter that will select TimeEntries based on the Entry's billable flag.
     * </p>
     *
     * @param isBillable The billable flag of the entry.
     * @return A filter that will be based off the specified criteria.
     */
    public Filter createBillableFilter(boolean isBillable) {
        return new EqualToFilter(BILLABLE_COLUMN_NAME, new Long(isBillable ? 1 : 0));
    }

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
    public Filter createRejectReasonFilter(long rejectReasonId) {
        Util.checkIdValue(rejectReasonId, "rejectReasonId");

        return new EqualToFilter(REJECT_REASONS_COLUMN_NAME, new Long(rejectReasonId));
    }

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
    public Filter createCompanyIdFilter(long companyId) {
        Util.checkIdValue(companyId, "companyId");

        return new EqualToFilter(COMPANY_ID_COLUMN_NAME, new Long(companyId));
    }
}
