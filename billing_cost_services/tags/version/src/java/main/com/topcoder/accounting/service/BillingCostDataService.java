/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import java.util.List;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface defines the service contract for the retrieval and export of billing cost data.
 * </p>
 * <p>
 * Thread Safety: Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public interface BillingCostDataService {
    /**
     * This method gets the billing cost report by the search criteria. If none found, returns an empty list in the
     * PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page.
     *
     * @param criteria
     *            the search criteria: If null/empty, there is no filtering.
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @return the billing costs that meet the search criteria
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<BillingCostReportEntry> getBillingCostReport(BillingCostReportCriteria criteria,
        int pageNo, int pageSize) throws BillingCostServiceException;

    /**
     * This method exports the selected records from billing cost report, as identified by the list of payments
     * identifiers.
     *
     * @param paymentAreaId
     *            the ID of the payment area
     * @param paymentIds
     *            the billing entry identifiers
     * @param user
     *            the TCSubject
     * @throws IllegalArgumentException
     *             If paymentIds is null/empty or contains null entries, or user is null
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public void exportBillingCostData(List<PaymentIdentifier> paymentIds, long paymentAreaId, TCSubject user)
        throws BillingCostServiceException;
}
