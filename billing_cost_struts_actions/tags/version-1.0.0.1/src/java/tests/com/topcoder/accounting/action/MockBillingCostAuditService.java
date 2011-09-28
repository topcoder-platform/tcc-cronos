/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
 * <p>
 * the mock implementation for billing cost audit service.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBillingCostAuditService implements BillingCostAuditService {
    /** test flag. */
    private boolean flag;

    /** empty constructor. */
    public MockBillingCostAuditService() {

    }

    /**
     * setter for the flag.
     *
     * @param flag the flag.
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * This mock method for saving the given audit record.
     *
     * @param record the audit record to add
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public void auditAccountingAction(AccountingAuditRecord record)
            throws BillingCostServiceException {
        if (flag) {
            throw new BillingCostServiceException("for testing.");
        }
    }

    /**
     * mock implementation for get aounting audit history.
     *
     * @param criteria the search criteria
     * @param pageNumber the 1-based number of the page to return (if -1, then
     *            all pages are returned)
     * @param pageSize the size of the page to return
     * @return the account audits that meet the search criteria
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public PagedResult<AccountingAuditRecord> getAccoutingAuditHistory(
            AccountingAuditRecordCriteria criteria, int pageNumber, int pageSize)
            throws BillingCostServiceException {
        if (flag) {
            throw new BillingCostServiceException("for testing.");
        }
        PagedResult<AccountingAuditRecord> rt = new PagedResult<AccountingAuditRecord>();
        rt.setPageNo(pageNumber);
        rt.setPageSize(pageSize);
        // add sample record for testing.
        List<AccountingAuditRecord> records = new ArrayList<AccountingAuditRecord>();
        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setAction("sample action");
        record.setUserName("petr");
        record.setTimestamp(new Date());
        records.add(record);

        rt.setRecords(records);
        return rt;
    }

    /**
     * mock implementation for billing cost export details.
     *
     * @param billingCostExportId the ID of the export whose details are to be
     *            retrieved
     * @param pageNo the 1-based number of the page to return (if -1, then all
     *            pages are returned)
     * @param pageSize the size of the page to return (ignored if page=-1)
     * @return the billing cost export details
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(
            long billingCostExportId, int pageNo, int pageSize)
            throws BillingCostServiceException {
        if (billingCostExportId == -1) {
            throw new BillingCostServiceException("for testing.");
        }
        PagedResult<BillingCostExportDetail> rt = new PagedResult<BillingCostExportDetail>();
        BillingCostExportDetail detail = new BillingCostExportDetail();
        detail.setCustomer("topcoder");
        rt.setRecords(Arrays.asList(detail));
        return rt;
    }

    /**
     * mock implementation for billing cost export history.
     *
     * @param criteria the search criteria: If null/empty, there is no
     *            filtering.
     * @param page the 1-based number of the page to return (if -1, then all
     *            pages are returned)
     * @param pageSize the size of the page to return (ignored if page=-1)
     * @return the account audits that meet the search criteria
     */
    public PagedResult<BillingCostExport> getBillingCostExportHistory(
            BillingCostExportHistoryCriteria criteria, int page, int pageSize)
            throws BillingCostServiceException {
        if (flag) {
            throw new BillingCostServiceException("for testing.");
        }
        return null;
    }

    /**
     * mock implementation for billing cost export details.
     *
     * @param inQuickBooks the flag describing whether the desired details are
     *            for exports that have been imported to QuickBooks or not
     * @param pageNo the 1-based number of the page to return (if -1, then all
     *            pages are returned)
     * @param pageSize the size of the page to return (ignored if page=-1)
     * @return the billing cost export details
     * @throws BillingCostServiceException If there are any errors during the
     *             execution of this method
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(
            boolean inQuickBooks, int pageNo, int pageSize)
            throws BillingCostServiceException {
        if (flag) {
            throw new BillingCostServiceException("for testing.");
        }
        return null;
    }

    /**
     * not used in the test.
     *
     * @return null always.
     */
    public String getLatestInvoiceNumber() throws BillingCostServiceException {
        return null;
    }

    /**
     * not used in the test.
     *
     * @param updates not used.
     */
    public void updateBillingCostExportDetails(
            List<QuickBooksImportUpdate> updates)
            throws BillingCostServiceException {

    }

}
