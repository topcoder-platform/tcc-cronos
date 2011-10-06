package com.topcoder.accounting.service;
import com.topcoder.accounting.entities.dto.*;
import com.topcoder.accounting.entities.dao.*;
import java.lang.*;
import java.util.List;
/**
 * #### Purpose
 * This interface defines the service contract for the retrieval of billing cost export data info as well as for the creation and retrieval of account audits.
 * 
 * #### Thread Safety
 * Implementations are expected to be effectively thread-safe
*/
public interface BillingCostAuditService{
/**
 * #### Purpose
 * This method gets the billing cost export details for the given export. If none found, returns an empty list in the PagedResult entity.
 * 
 * The results will be pages as requested. pagng is 1-based, and if it is -1 then all pages are returned. If paging is requested, then pageSize is used to set the size of the page
 * 
 * #### Parameters
 * billingCostExportId - the ID of the export whose details are to be retrieved
 * page - the 1-based number of the page to return (if -1, then all pages are returned)
 * pageSize - the size of the page to return (ignored if page=-1)
 * return - the billing cost export details
 * 
 * #### Exceptions
 * IllegalArgumentException - If page = 0 or <-1
 * IllegalArgumentException - If pageSize < 1 unless page = -1
 * BillingCostServiceException - If there are any errors during the execution of this method
 * @param pageNo 
 * @param billingCostExportId 
 * @param Return 
 * @param pageSize 
 * @return 
*/
public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(long billingCostExportId, int pageNo, int pageSize) throws BillingCostServiceException;
/**
 * #### Purpose
 * This method gets the billing cost export details for the given QuickBooks import state. If none found, returns an empty list in the PagedResult entity.
 * 
 * The results will be pages as requested. pagng is 1-based, and if it is -1 then all pages are returned. If paging is requested, then pageSize is used to set the size of the page
 * 
 * #### Parameters
 * inQuickBooks - the flag describing whether the desired details are for exports that have been imported to QuickBooks or not
 * page - the 1-based number of the page to return (if -1, then all pages are returned)
 * pageSize - the size of the page to return (ignored if page=-1)
 * return - the billing cost export details
 * 
 * #### Exceptions
 * IllegalArgumentException - If page = 0 or <-1
 * IllegalArgumentException - If pageSize < 1 unless page = -1
 * BillingCostServiceException - If there are any errors during the execution of this method
 * @param pageNo 
 * @param Return 
 * @param pageSize 
 * @param inQuickBooks 
 * @return 
*/
public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(boolean inQuickBooks, int pageNo, int pageSize) throws BillingCostServiceException;
/**
 * #### Purpose
 * This method saves the given audit record
 * 
 * #### Parameters
 * record - the audit record to add
 * 
 * #### Exceptions
 * IllegalArgumentException - If record is null
 * BillingCostServiceException - If there are any errors during the execution of this method
 * @param record 
 * @param Return 
*/
public void auditAccountingAction(AccountingAuditRecord record) throws BillingCostServiceException;
/**
 * #### Purpose
 * This method updates the given billing cost export details with the given invoice number
 * 
 * #### Parameters
 * updates - the billing cost export details to be updated with the given invoice number
 * 
 * #### Exceptions
 * IllegalArgumentException - If updates is null/empty (i.e. the list must not be null/empty and invoiceNumber must be provided)
 * EntityNotFoundException - If any of the billingCostExportDetailIds is not found
 * BillingCostServiceException - If there are any errors during the execution of this method
 * @param updates 
 * @param Return 
*/
public void updateBillingCostExportDetails(List<QuickBooksImportUpdate> updates) throws BillingCostServiceException;
/**
 * #### Purpose
 * This method gets the latest invoice number
 * 
 * #### Parameters
 * return - the latest invoice number
 * 
 * #### Exceptions
 * BillingCostServiceException - If there are any errors during the execution of this method
 * @param Return 
 * @return 
*/
public String getLatestInvoiceNumber() throws BillingCostServiceException;
}

