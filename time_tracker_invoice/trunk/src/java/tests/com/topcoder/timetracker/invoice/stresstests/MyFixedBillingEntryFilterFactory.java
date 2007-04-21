/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

/**
 * A mocked class for testing.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyFixedBillingEntryFilterFactory implements FixedBillingEntryFilterFactory {

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createInvoiceIdFilter(long)
     */
    public Filter createInvoiceIdFilter(long id) {
        return new EqualToFilter("Invoice_id", new Long(id));
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createDescriptionFilter(java.lang.String,
     *      com.topcoder.timetracker.entry.fixedbilling.StringMatchType)
     */
    public Filter createDescriptionFilter(String arg0, StringMatchType arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createEntryDateFilter(java.util.Date,
     *      java.util.Date)
     */
    public Filter createEntryDateFilter(Date arg0, Date arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createAmountFilter(double,
     *      double)
     */
    public Filter createAmountFilter(double arg0, double arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createFixedBillingStatusFilter(com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus)
     */
    public Filter createFixedBillingStatusFilter(FixedBillingStatus arg0) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createRejectReasonFilter(long)
     */
    public Filter createRejectReasonFilter(long arg0) {
        return null;
    }

    /**
     *
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory#createCompanyIdFilter(long)
     */
    public Filter createCompanyIdFilter(long arg0) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.BaseFilterFactory#createCreationDateFilter(java.util.Date,
     *      java.util.Date)
     */
    public Filter createCreationDateFilter(Date arg0, Date arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.BaseFilterFactory#createModificationDateFilter(java.util.Date,
     *      java.util.Date)
     */
    public Filter createModificationDateFilter(Date arg0, Date arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.BaseFilterFactory#createCreationUserFilter(java.lang.String,
     *      com.topcoder.timetracker.entry.fixedbilling.StringMatchType)
     */
    public Filter createCreationUserFilter(String arg0, StringMatchType arg1) {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.BaseFilterFactory#createModificationUserFilter(java.lang.String,
     *      com.topcoder.timetracker.entry.fixedbilling.StringMatchType)
     */
    public Filter createModificationUserFilter(String arg0, StringMatchType arg1) {
        return null;
    }

}