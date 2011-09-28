/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests.mock;

import java.util.List;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.accounting.service.BillingCostDataService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.security.TCSubject;

/**
 * Mock implementation of BillingCostDataService interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBillingCostDataService implements BillingCostDataService {
    /**
     * Represents the list of PaymentIdentifier.
     */
    private List<PaymentIdentifier> paymentIds;

    /**
     * Represents the payment area id.
     */
    private long paymentAreaId;

    /**
     * Represents the subject.
     */
    private TCSubject tcSubject;

    /**
     * Creates the instance.
     */
    public MockBillingCostDataService() {
    }

    /**
     * Gets the list of PaymentIdentifier.
     *
     * @return the list of PaymentIdentifier.
     */
    public List<PaymentIdentifier> getPaymentIds() {
        return paymentIds;
    }

    /**
     * Gets the payment area id.
     *
     * @return the payment area id.
     */
    public long getPaymentAreaId() {
        return paymentAreaId;
    }

    /**
     * Gets the subject.
     *
     * @return the subject.
     */
    public TCSubject getTcSubject() {
        return tcSubject;
    }

    /**
     * Mock implementation.
     *
     * @param paymentIds
     *            the list of PaymentIdentifier.
     * @param paymentAreaId
     *            the payment area id.
     * @param tcSubject
     *            the subject.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public void exportBillingCostData(List<PaymentIdentifier> paymentIds, long paymentAreaId, TCSubject tcSubject)
        throws BillingCostServiceException {
        this.paymentIds = paymentIds;
        this.paymentAreaId = paymentAreaId;
        this.tcSubject = tcSubject;
    }

    /**
     * Mock implementation.
     *
     * @param arg0
     *            the criteria for search.
     * @param arg1
     *            the page number.
     * @param arg2
     *            the page size.
     * @return the page result.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public PagedResult<BillingCostReportEntry> getBillingCostReport(BillingCostReportCriteria arg0, int arg1, int arg2)
        throws BillingCostServiceException {

        PagedResult<BillingCostReportEntry> result = new PagedResult<BillingCostReportEntry>();
        result.setPageNo(arg1);
        result.setPageSize(arg2);
        result.setTotalPages(arg0.getProjectId() == null ? 0 : arg0.getProjectId().intValue());
        return result;
    }
}
