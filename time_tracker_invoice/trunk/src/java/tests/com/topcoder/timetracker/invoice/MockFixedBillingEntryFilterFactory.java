/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import java.util.Date;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;

/**
 * Mock for <code>FixedBillingEntryFilterFactory</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockFixedBillingEntryFilterFactory implements FixedBillingEntryFilterFactory {

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createAmountFilter(double rangeStart, double rangeEnd) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param companyId
     *            not used
     *
     * @return null
     */
    public Filter createCompanyIdFilter(long companyId) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param description
     *            not used
     * @param matchType
     *            not used
     *
     * @return null
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createEntryDateFilter(Date rangeStart, Date rangeEnd) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param status
     *            not used
     *
     * @return null
     */
    public Filter createFixedBillingStatusFilter(FixedBillingStatus status) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param invoiceId
     *            not used
     *
     * @return null
     */
    public Filter createInvoiceIdFilter(long invoiceId) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param rejectReasonId
     *            not used
     *
     * @return null
     */
    public Filter createRejectReasonFilter(long rejectReasonId) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param username
     *            not used
     * @param matchType
     *            not used
     *
     * @return null
     */
    public Filter createCreationUserFilter(String username, StringMatchType matchType) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {

        return null;
    }

    /**
     * Mock method.
     *
     * @param username
     *            not used
     * @param matchType
     *            not used
     *
     * @return null
     */
    public Filter createModificationUserFilter(String username, StringMatchType matchType) {

        return null;
    }

}
