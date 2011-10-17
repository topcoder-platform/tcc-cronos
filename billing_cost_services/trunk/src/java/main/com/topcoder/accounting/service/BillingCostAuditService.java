/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service;

import java.util.List;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;

/**
 * <p>
 * This interface defines the service contract for the retrieval of billing cost export data info as well as for
 * the creation and retrieval of account audits.
 * </p>
 * <p>
 * Thread Safety: Implementations are expected to be effectively thread-safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public interface BillingCostAuditService {
    /**
     * This method gets the billing cost export history by the search criteria. If none found, returns an empty
     * list in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page.
     *
     * @param criteria
     *            the search criteria: If null/empty, there is no filtering.
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @return the billing cost exports that meet the search criteria
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<BillingCostExport> getBillingCostExportHistory(BillingCostExportHistoryCriteria criteria,
        int pageNo, int pageSize) throws BillingCostServiceException;

    /**
     * This method gets the billing cost export details for the given export. If none found, returns an empty list
     * in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page.
     *
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param billingCostExportId
     *            the ID of the export whose details are to be retrieved
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @return the billing cost export details
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(long billingCostExportId, int pageNo,
        int pageSize) throws BillingCostServiceException;

    /**
     * This method gets the billing cost export details for the given QuickBooks import state. If none found,
     * returns an empty list in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page.
     *
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @param inQuickBooks
     *            the flag describing whether the desired details are for exports that have been imported to
     *            QuickBooks or not
     * @return the billing cost export details
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(boolean inQuickBooks, int pageNo,
        int pageSize) throws BillingCostServiceException;

    /**
     * This method saves the given audit record.
     *
     * @param record
     *            the audit record to add
     * @throws IllegalArgumentException
     *             If record is null
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public void auditAccountingAction(AccountingAuditRecord record) throws BillingCostServiceException;

    /**
     * This method gets the account audit history by the search criteria. If none found, returns an empty list in
     * the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page.
     *
     * @param criteria
     *            the search criteria: If null/empty, there is no filtering.
     * @param pageNo
     *            the 1-based number of the page to return (if -1, then all pages are returned)
     * @param pageSize
     *            the size of the page to return (ignored if pageNo=-1)
     * @return the account audits that meet the search criteria
     * @throws IllegalArgumentException
     *             If pageNo = 0 or <-1 or pageSize < 1 unless pageNo = -1
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public PagedResult<AccountingAuditRecord> getAccountingAuditHistory(AccountingAuditRecordCriteria criteria,
        int pageNo, int pageSize) throws BillingCostServiceException;

    /**
     * This method updates the given billing cost export details with the given invoice number.
     *
     * @param updates
     *            the billing cost export details to be updated with the given invoice number
     * @throws IllegalArgumentException
     *             If updates is null/empty (i.e. the list must not be null/empty and invoiceNumber must be
     *             provided)
     * @throws EntityNotFoundException
     *             If any of the billingCostExportDetailIds is not found
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public void updateBillingCostExportDetails(List<QuickBooksImportUpdate> updates)
        throws BillingCostServiceException;

    /**
     * This method gets the latest invoice number.
     *
     * @return the latest invoice number
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public String getLatestInvoiceNumber() throws BillingCostServiceException;
}
