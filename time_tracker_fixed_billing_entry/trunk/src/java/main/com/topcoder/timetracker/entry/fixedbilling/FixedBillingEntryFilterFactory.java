/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;

import java.util.Date;


/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through Time Tracker
 * FixedBillingEntries. It offers a convenient way of specifying search criteria to use. The factory is capable of
 * producing filters that conform to a specific schema, and is associated with the
 * <code>FixedBillingEntryManager</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>FixedBillingEntryManager</code> or
 * <code>FixedBillingEntryDAO</code> implementation that produced this <code>FixedBillingEntryFilterFactory</code>
 * instance. This ensures that the Filters can be recognized by the utility.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this  factory with any of the
 * Composite Filters in the Search Builder Component (AndFilter, OrFilter, etc.)
 * </p>
 *
 * <p>
 * Note that all ranges specified are inclusive of the boundaries. If a null is given at a range (or for int/long
 * ranges, a value of Integer.MIN_VALUE), it signifies that that particular bound (min or max) should be  ignored.
 * </p>
 *
 * <p>
 * Thread Safety: This class is required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingEntryFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's invoice id.
     * </p>
     *
     * @param invoiceId The invoice id to use.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if invoiceId is &lt;= 0.
     */
    Filter createInvoiceIdFilter(long invoiceId);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's description.
     * </p>
     *
     * @param description The description of the time entry.
     * @param matchType The String matching that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String or matchType is null.
     */
    Filter createDescriptionFilter(String description, StringMatchType matchType);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's entry date.   A date range that
     * may be open-ended can be specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.  May be null to specify that this has no lower bound.
     * @param rangeEnd The upper bound of the date criterion.  May be null to specify that this has no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd), or if both
     *         arguments are null.
     */
    Filter createEntryDateFilter(Date rangeStart, Date rangeEnd);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's amount spent.
     * </p>
     *
     * @param rangeStart The lower bound of the criterion.   May be Double.MIN_VALUE to indicate no lower bound.
     * @param rangeEnd The upper bound of the criterion.  May be Double.MIN_VALUE to indicate no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart &gt; rangeEnd), or if both
     *         arguments are null.
     */
    Filter createAmountFilter(double rangeStart, double rangeEnd);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's fixed billing status.
     * </p>
     *
     * @param status The status of the entry.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the status is null.
     */
    Filter createFixedBillingStatusFilter(FixedBillingStatus status);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's RejectReason.
     * </p>
     *
     * @param rejectReasonId The reject reason to search for.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the rejectReasonId is &lt;= 0.
     */
    Filter createRejectReasonFilter(long rejectReasonId);

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's companyId.
     * </p>
     *
     * @param companyId the id of the Time Tracker Company to search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    Filter createCompanyIdFilter(long companyId);
}
