/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.filterfactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

import java.util.Date;


/**
 * <p>
 * This is an implementation of the <code>FixedBillingEntryFilterFactory</code> that may be used  to build filters.
 * </p>
 *
 * <p>
 * Thread Safety: This is thread-safe, since it is not modified after construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class MappedFixedBillingEntryFilterFactory extends MappedBaseFilterFactory
    implements FixedBillingEntryFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Invoice Id.
     * </p>
     */
    public static final String INVOICE_ID_COLUMN_NAME = "INVOICE_ID_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Description.
     * </p>
     */
    public static final String DESCRIPTION_COLUMN_NAME = "DESCRIPTION_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Entry Date.
     * </p>
     */
    public static final String ENTRY_DATE_COLUMN_NAME = "ENTRY_DATE_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Amount.
     * </p>
     */
    public static final String AMOUNT_COLUMN_NAME = "AMOUNT_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Fixed Billing Status.
     * </p>
     */
    public static final String FIXED_BILLING_STATUS_COLUMN_NAME = "FIXED_BILLING_STATUS_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Reject Reason Column Name.
     * </p>
     */
    public static final String REJECT_REASON_COLUMN_NAME = "REJECT_REASON_COLUMN_NAME";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Company Id Column Name.
     * </p>
     */
    public static final String COMPANY_ID_COLUMN_NAME = "COMPANY_ID_COLUMN_NAME";

    /**
     * <p>
     * Creates a MappedFixedBillingEntryFilterFactory.
     * </p>
     */
    public MappedFixedBillingEntryFilterFactory() {
    }

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's invoice id. If invoiceId is
     * 0, return a null filter.
     * </p>
     *
     * @param invoiceId The invoice id to use.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if invoiceId is &lt; 0.
     */
    public Filter createInvoiceIdFilter(long invoiceId) {
        if (invoiceId == 0) {
            return new NullFilter(INVOICE_ID_COLUMN_NAME);
        } else {
            Helper.checkLongValue("invoiceId", invoiceId);
            return new EqualToFilter(INVOICE_ID_COLUMN_NAME, new Long(invoiceId));
        }
    }

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's description.
     * </p>
     *
     * @param description The description of the time entry.
     * @param matchType The String match type that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String or matchType is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType) {
        return Helper.createFilterByMatchType(DESCRIPTION_COLUMN_NAME, description,
            matchType);
    }

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's entry date. A date range that
     * may be open-ended can be specified.
     * </p>
     *
     * @param rangeStart The lower bound of the date criterion.  May be null to specify that this has no lower bound.
     * @param rangeEnd The upper bound of the date criterion.  May be null to specify that this has no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid , or if both arguments are null.
     */
    public Filter createEntryDateFilter(Date rangeStart, Date rangeEnd) {
        return Helper.createDateRangeFilter(ENTRY_DATE_COLUMN_NAME,
                rangeStart, rangeEnd);
    }

    /**
     * <p>
     * This creates a Filter that will select FixedBillingEntries based on the Entry's amount spent.
     * </p>
     *
     * @param rangeStart The lower bound of the criterion. May be Double.MIN_VALUE to indicate no lower bound.
     * @param rangeEnd The upper bound of the criterion. May be Double.MIN_VALUE to indicate no upper bound.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid, or if both arguments are null.
     */
    public Filter createAmountFilter(double rangeStart, double rangeEnd) {
        return Helper.createDoubleRangeFilter(AMOUNT_COLUMN_NAME, rangeStart, rangeEnd);
    }

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
    public Filter createFixedBillingStatusFilter(FixedBillingStatus status) {
        Helper.checkNull("status", status);

        return new EqualToFilter(FIXED_BILLING_STATUS_COLUMN_NAME,
            new Long(status.getId()));
    }

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
    public Filter createRejectReasonFilter(long rejectReasonId) {
        Helper.checkLongValue("rejectReasonId", rejectReasonId);

        return new EqualToFilter(REJECT_REASON_COLUMN_NAME, new Long(rejectReasonId));
    }

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
    public Filter createCompanyIdFilter(long companyId) {
        Helper.checkLongValue("companyId", companyId);

        return new EqualToFilter(COMPANY_ID_COLUMN_NAME, new Long(companyId));
    }
}
