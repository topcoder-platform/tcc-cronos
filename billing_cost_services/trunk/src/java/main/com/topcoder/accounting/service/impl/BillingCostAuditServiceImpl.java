/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.EntityNotFoundException;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;

/**
 * <p>
 * This class is an implementation of BillingCostAuditService that uses Hibernate to get the billing cost audit
 * data. Logs with the Log from the Logging Wrapper.
 * </p>
 * <p>
 * Thread Safety: This class is mutable but effectively thread-safe.
 * </p>
 * <b>Simple Config:</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id="billingCostAuditService"
 *           class="com.topcoder.accounting.service.impl.BillingCostAuditServiceImpl"&gt;
 *           &lt;property name="hibernateTemplate" ref="hibernateTemplate" /&gt;
 *           &lt;property name="logger" ref="logger" /&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <b>Usage:</b>
 * <p>
 *
 * <pre>
 * // Get all exports for a given payment area for a specific date range, showing the first page
 * BillingCostExportHistoryCriteria billingCostExportHistoryCriteria = new BillingCostExportHistoryCriteria();
 * billingCostExportHistoryCriteria.setPaymentAreaId(1L);
 * // August 1, 2011
 * calendar.set(2011, 7, 1);
 * billingCostExportHistoryCriteria.setStartDate(calendar.getTime());
 * // August 30, 2011
 * calendar.set(2011, 7, 30);
 * billingCostExportHistoryCriteria.setEndDate(calendar.getTime());
 * PagedResult&lt;BillingCostExport&gt; exports = billingCostAuditService.getBillingCostExportHistory(
 *     billingCostExportHistoryCriteria, 1, 10);
 * // This result would get the first 10 entries in the report for a specific payment area in the month of
 * // august, as part of a monthly export report. Note that these results may include the result we exported
 * // above.
 *
 * // Gets all details for a single cost export, showing the first page
 * // one of the above exports
 * long billingCostExportId = 1;
 * PagedResult&lt;BillingCostExportDetail&gt; details = billingCostAuditService.getBillingCostExportDetails(
 *     billingCostExportId, 1, 10);
 *
 * // Another way of searching details is to search for all that are now in quick books, showing the first
 * // page
 * PagedResult&lt;BillingCostExportDetail&gt; details2 = billingCostAuditService.getBillingCostExportDetails(true, 1, 10);
 *
 * // This method creates a new audit record
 * // new audit record with data
 * AccountingAuditRecord accountingAuditRecord = new AccountingAuditRecord();
 * accountingAuditRecord.setId(10);
 * accountingAuditRecord.setAction(&quot;add&quot;);
 * accountingAuditRecord.setUserName(&quot;admin&quot;);
 * accountingAuditRecord.setTimestamp(new Date());
 * billingCostAuditService.auditAccountingAction(accountingAuditRecord);
 *
 * // Gets all audits records for a given action for a specific date range, showing the first page
 * AccountingAuditRecordCriteria accountingAuditRecordCriteria = new AccountingAuditRecordCriteria();
 * accountingAuditRecordCriteria.setAction(&quot;updateBillingCostExportDetails&quot;);
 * // August 1, 2011
 * calendar.set(2011, 7, 1);
 * accountingAuditRecordCriteria.setStartDate(calendar.getTime());
 * // August 30, 2011
 * calendar.set(2011, 7, 30);
 * accountingAuditRecordCriteria.setEndDate(calendar.getTime());
 * PagedResult&lt;AccountingAuditRecord&gt; auditRecords = billingCostAuditService.getAccountingAuditHistory(
 *     accountingAuditRecordCriteria, 1, 10);
 *
 * // Perform some updates
 * List&lt;QuickBooksImportUpdate&gt; updates = new ArrayList&lt;QuickBooksImportUpdate&gt;();
 * QuickBooksImportUpdate update = new QuickBooksImportUpdate();
 * update.setInvoiceNumber(&quot;55&quot;);
 * update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
 * updates.add(update);
 * billingCostAuditService.updateBillingCostExportDetails(updates);
 *
 * // Get the latest invoice number
 * String latestInvoiceNumber = billingCostAuditService.getLatestInvoiceNumber();
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostAuditServiceImpl extends BaseService implements BillingCostAuditService {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = BillingCostAuditServiceImpl.class.getName();

    /**
     * <p>
     * Represent the query string to get BillingCostExportDetail by latest quickBooksImportTimestamp.
     * </p>
     */
    private static final String QUERY_BILLINGCOSTEXPORTDETAIL = "FROM BillingCostExportDetail billingCostExportDetail"
        + " WHERE billingCostExportDetail.quickBooksImportTimestamp ="
        + " (SELECT max(billingCostExportDetail.quickBooksImportTimestamp) FROM"
        + " BillingCostExportDetail billingCostExportDetail))";

    /**
     * <p>
     * Represent the query string to get BillingCostExportDetail id.
     * </p>
     */
    private static final String QUERY_BILLINGCOSTEXPORTDETAIL_ID = "SELECT billingCostExportDetail.id FROM"
        + " BillingCostExportDetail billingCostExportDetail WHERE billingCostExportDetail.id=?";

    /**
     * Empty constructor.
     */
    public BillingCostAuditServiceImpl() {
        // Empty
    }

    /**
     * This method gets the billing cost export history by the search criteria. If none found, returns an empty
     * list in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page
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
        int pageNo, int pageSize) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getBillingCostExportHistory(BillingCostExportHistoryCriteria, int, int)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria", "pageNo", "pageSize"},
            new Object[] {Helper.getString(criteria), pageNo, pageSize});

        try {
            // Check parameter
            Helper.checkPageAndPageSize(pageNo, pageSize);

            // Create hibernate criteria
            DetachedCriteria hibernateCriteria = DetachedCriteria.forClass(BillingCostExport.class);

            if (criteria != null) {
                // add restriction to hibernate criteria
                if (isNotNullOrEmptyString(criteria.getAccountantName())) {
                    hibernateCriteria.add(Restrictions.eq("accountantName", criteria.getAccountantName()));
                }
                if (criteria.getPaymentAreaId() != null) {
                    hibernateCriteria.add(Restrictions.eq("paymentArea.id", criteria.getPaymentAreaId()));
                }
                if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
                    hibernateCriteria.add(Restrictions.ge("timestamp", criteria.getStartDate()));
                } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
                    hibernateCriteria.add(Restrictions.le("timestamp", criteria.getEndDate()));
                } else if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                    hibernateCriteria.add(Restrictions.between("timestamp", criteria.getStartDate(),
                        criteria.getEndDate()));
                }
            }

            PagedResult<BillingCostExport> searchResults = Helper.getPagedResult(getHibernateTemplate(),
                hibernateCriteria, pageNo, pageSize, false);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {searchResults.toJSONString()});
            // return searchResults
            return searchResults;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

    /**
     * This method gets the billing cost export details for the given export. If none found, returns an empty list
     * in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page
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
        int pageSize) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getBillingCostExportDetails(long,int, int)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"billingCostExportId", "pageNo",
            "pageSize"}, new Object[] {billingCostExportId, pageNo, pageSize});

        try {
            // Check parameter
            Helper.checkPageAndPageSize(pageNo, pageSize);

            // Create hibernate criteria
            DetachedCriteria hibernateCriteria = DetachedCriteria.forClass(BillingCostExportDetail.class);

            // add restriction to hibernate criteria
            hibernateCriteria.add(Restrictions.eq("billingCostExportId", billingCostExportId));

            PagedResult<BillingCostExportDetail> searchResults = Helper.getPagedResult(getHibernateTemplate(),
                hibernateCriteria, pageNo, pageSize, false);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {searchResults.toJSONString()});
            // return searchResults
            return searchResults;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

    /**
     * This method gets the billing cost export details for the given QuickBooks import state. If none found,
     * returns an empty list in the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page
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
        int pageSize) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getBillingCostExportDetails(boolean,int, int)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"inQuickBooks", "pageNo",
            "pageSize"}, new Object[] {inQuickBooks, pageNo, pageSize});

        try {
            // Check parameter
            Helper.checkPageAndPageSize(pageNo, pageSize);

            // Create hibernate criteria
            DetachedCriteria hibernateCriteria = DetachedCriteria.forClass(BillingCostExportDetail.class);

            // add restriction to hibernate criteria
            hibernateCriteria.add(Restrictions.eq("inQuickBooks", inQuickBooks));

            PagedResult<BillingCostExportDetail> searchResults = Helper.getPagedResult(getHibernateTemplate(),
                hibernateCriteria, pageNo, pageSize, true);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {searchResults.toJSONString()});
            // return searchResults
            return searchResults;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

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
    public void auditAccountingAction(AccountingAuditRecord record) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".auditAccountingAction(AccountingAuditRecord)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"record"}, new Object[] {record});

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNull(record, "record");
            // Save the entity:
            getHibernateTemplate().persist(record);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

    /**
     * This method gets the account audit history by the search criteria. If none found, returns an empty list in
     * the PagedResult entity. <br>
     * The results will be pages as requested. pageNo is 1-based, and if it is -1 then all pages are returned. If
     * paging is requested, then pageSize is used to set the size of the page
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
        int pageNo, int pageSize) throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria", "pageNo", "pageSize"},
            new Object[] {Helper.getString(criteria), pageNo, pageSize});

        try {
            // Check parameter
            Helper.checkPageAndPageSize(pageNo, pageSize);

            // Create hibernate criteria
            DetachedCriteria hibernateCriteria = DetachedCriteria.forClass(AccountingAuditRecord.class);

            if (criteria != null) {
                // add restriction to hibernate criteria
                if (isNotNullOrEmptyString(criteria.getAction())) {
                    hibernateCriteria.add(Restrictions.eq("action", criteria.getAction()));
                }
                if (isNotNullOrEmptyString(criteria.getUserName())) {
                    hibernateCriteria.add(Restrictions.eq("userName", criteria.getUserName()));
                }
                if (criteria.getStartDate() != null && criteria.getEndDate() == null) {
                    hibernateCriteria.add(Restrictions.ge("timestamp", criteria.getStartDate()));
                } else if (criteria.getStartDate() == null && criteria.getEndDate() != null) {
                    hibernateCriteria.add(Restrictions.le("timestamp", criteria.getEndDate()));
                } else if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                    hibernateCriteria.add(Restrictions.between("timestamp", criteria.getStartDate(),
                        criteria.getEndDate()));
                }
            }

            PagedResult<AccountingAuditRecord> searchResults = Helper.getPagedResult(getHibernateTemplate(),
                hibernateCriteria, pageNo, pageSize, false);

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {searchResults.toJSONString()});
            // return searchResults
            return searchResults;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

    /**
     * <p>
     * Check string whether null or empty.
     * </p>
     *
     * @param str
     *            the string
     * @return true if the string is not null or empty
     */
    private static boolean isNotNullOrEmptyString(String str) {
        return (str != null && str.trim().length() != 0);
    }

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
        throws BillingCostServiceException {
        String signature = CLASS_NAME + ".updateBillingCostExportDetails(List)";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"updates"},
            new Object[] {Helper.getListString(updates)});

        try {
            // Check parameter
            ParameterCheckUtility.checkNotNullNorEmpty(updates, "updates");
            for (QuickBooksImportUpdate update1 : updates) {
                ParameterCheckUtility.checkNotNull(update1, "element in updates");
                ParameterCheckUtility.checkNotNull(update1.getBillingCostExportDetailIds(),
                    "element's BillingCostExportDetailIds");
                if (update1.getBillingCostExportDetailIds().length == 0) {
                    throw new IllegalArgumentException("element's BillingCostExportDetailIds must not be empty");
                }
                ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(update1.getInvoiceNumber(),
                    "element's invoiceNumber");
            }

            // Check all billing cost export detail id
            for (QuickBooksImportUpdate update : updates) {
                for (long billingCostExportDetailId : update.getBillingCostExportDetailIds()) {
                    List<?> details = getHibernateTemplate().find(QUERY_BILLINGCOSTEXPORTDETAIL_ID,
                        billingCostExportDetailId);
                    // If not found, throw EntityNotFoundException
                    if (details.isEmpty()) {
                        throw LoggingWrapperUtility.logException(getLogger(), signature,
                            new EntityNotFoundException("BillingCostExportDetail is not exist in DB with id["
                                + billingCostExportDetailId + "]"));
                    }
                }
            }

            // For each update:QuickBooksImportUpdate in updates
            for (QuickBooksImportUpdate update : updates) {
                // For each billingCostExportDetailId:long in update.billingCostExportDetailIds
                for (long billingCostExportDetailId : update.getBillingCostExportDetailIds()) {
                    // Get existing detail:
                    BillingCostExportDetail existingDetail = (BillingCostExportDetail) getHibernateTemplate().get(
                        BillingCostExportDetail.class, new Long(billingCostExportDetailId));
                    // Update invoice number:
                    existingDetail.setInvoiceNumber(update.getInvoiceNumber());
                    // Update inQuickBooks flag:
                    existingDetail.setInQuickBooks(true);
                    // Update quickBooksImportTimestamp to now:
                    existingDetail.setQuickBooksImportTimestamp(new Date());
                    // Update:
                    getHibernateTemplate().merge(existingDetail);
                }

            }

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

    /**
     * This method gets the latest invoice number.
     *
     * @return the latest invoice number
     * @throws BillingCostServiceException
     *             If there are any errors during the execution of this method
     */
    public String getLatestInvoiceNumber() throws BillingCostServiceException {
        String signature = CLASS_NAME + ".getLatestInvoiceNumber()";
        // log entrance
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            // Get hibernate session:
            @SuppressWarnings("unchecked")
            List<BillingCostExportDetail> results = getHibernateTemplate().find(QUERY_BILLINGCOSTEXPORTDETAIL);
            String result;
            if (results.isEmpty()) {
                // There is no record in DB, we create a new invoice number
                DateFormat sdf = new SimpleDateFormat("Myy");
                result = sdf.format(new Date()) + "-101";
            } else {
                BillingCostExportDetail detail = results.get(0);
                result = detail.getInvoiceNumber();
            }

            // log exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {result});

            return result;

        } catch (DataAccessException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new BillingCostServiceException(
                "DataAccessException occurs while accessing to db", e));
        }
    }

}
