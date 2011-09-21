/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests.mock;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;

/**
 * Mock implementation of BillingCostAuditService interface.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBillingCostAuditService implements BillingCostAuditService {
    /**
     * Represents the list of AccountingAuditRecord audit.
     */
    private static final List<AccountingAuditRecord> RECORDS = new ArrayList<AccountingAuditRecord>();

    /**
     * Create the instance.
     */
    public MockBillingCostAuditService() {
        RECORDS.clear();
    }

    /**
     * Gets all records.
     *
     * @return all records.
     */
    public static List<AccountingAuditRecord> getRecords() {
        return RECORDS;
    }

    /**
     * Mock implementation.
     *
     * @param record
     *            the record.
     * @throws BillingCostServiceException never.
     */

    public void auditAccountingAction(AccountingAuditRecord record) throws BillingCostServiceException {
        RECORDS.add(record);
    }

    /***
     * Mock implementation.
     *
     * @param criteria
     *            the criteria.
     * @param pageNumber
     *            the page number.
     * @param pageSize
     *            the page size.
     * @return the paged result.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public PagedResult<AccountingAuditRecord> getAccoutingAuditHistory(AccountingAuditRecordCriteria criteria,
        int pageNumber, int pageSize) throws BillingCostServiceException {
        PagedResult<AccountingAuditRecord> result = new PagedResult<AccountingAuditRecord>();
        result.setPageNo(pageNumber);
        result.setPageSize(pageSize);
        result.setTotalPages(criteria.getAction() == null ? 0 : criteria.getAction().length());

        return result;
    }

    /***
     * Mock implementation.
     *
     * @param billingCostExportId
     *            the id.
     * @param pageNumber
     *            the page number.
     * @param pageSize
     *            the page size.
     * @return the paged result.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(long billingCostExportId, int pageNumber,
        int pageSize) throws BillingCostServiceException {
        PagedResult<BillingCostExportDetail> result = new PagedResult<BillingCostExportDetail>();
        result.setPageNo(1);
        result.setPageSize(10);
        result.setTotalPages((int) billingCostExportId);
        return result;
    }

    /***
     * Mock implementation.
     *
     * @param arg0
     *            the first argument.
     * @param arg1
     *            the second argument.
     * @param arg2
     *            the third argument.
     * @return null.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(boolean arg0, int arg1, int arg2)
        throws BillingCostServiceException {
        return null;
    }

    /**
     * Mock implementation.
     *
     * @param criteria
     *            the criteria.
     * @param pageNo
     *            the page number.
     * @param pageSize
     *            the page size.
     * @return the paged result.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public PagedResult<BillingCostExport> getBillingCostExportHistory(BillingCostExportHistoryCriteria criteria,
        int pageNo, int pageSize) throws BillingCostServiceException {
        PagedResult<BillingCostExport> result = new PagedResult<BillingCostExport>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalPages(criteria.getAccountantName() == null ? 0 : criteria.getAccountantName().length());
        return result;
    }

    /**
     * Mock implementation.
     *
     * @return null.
     *
     * @throws BillingCostServiceException
     *             never.
     */
    public String getLatestInvoiceNumber() throws BillingCostServiceException {
        return null;
    }

    /**
     * Mock implementation.
     *
     * @param arg0
     *            the argument.
     * @throws BillingCostServiceException
     *             never.
     */
    public void updateBillingCostExportDetails(List<QuickBooksImportUpdate> arg0) throws BillingCostServiceException {
    }
}
