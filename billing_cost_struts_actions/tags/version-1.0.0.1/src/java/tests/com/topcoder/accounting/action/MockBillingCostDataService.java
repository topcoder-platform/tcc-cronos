/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.util.List;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.accounting.service.BillingCostDataService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * the mock implementation for billing cost data service.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBillingCostDataService implements BillingCostDataService {

    /**
     * mock implementation for export billing cost data.
     *
     * @param paymentIds the billing entry identifiers
     * @param paymentAreaId the ID of the payment area
     * @param user the TCSubject
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public void exportBillingCostData(List<PaymentIdentifier> paymentIds,
            long paymentAreaId, TCSubject user)
            throws BillingCostServiceException {
        if (paymentAreaId == -1) {
            throw new BillingCostServiceException("for testing.");
        }
    }

    /**
     * mock implementation for get billing cost report.
     *
     * @param criteria the search criteria: If null/empty, there is no
     *            filtering.
     * @param page the 1-based number of the page to return (if -1, then all
     *            pages are returned)
     * @param pageSize the size of the page to return (ignored if page=-1)
     * @return the billing costs that meet the search criteria
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public PagedResult<BillingCostReportEntry> getBillingCostReport(
            BillingCostReportCriteria criteria, int page, int pageSize)
            throws BillingCostServiceException {
        if (page == -1) {
            throw new BillingCostServiceException("for testing.");
        }
        return null;
    }

}
