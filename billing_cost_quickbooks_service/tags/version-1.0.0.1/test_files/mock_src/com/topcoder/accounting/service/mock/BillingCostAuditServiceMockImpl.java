package com.topcoder.accounting.service.mock;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;

public class BillingCostAuditServiceMockImpl implements BillingCostAuditService {
    private PagedResult<BillingCostExportDetail> billingCostExportDetails;
    private String latestInvoiceNumber;
    private List<QuickBooksImportUpdate> updates;

    public BillingCostAuditServiceMockImpl() {
        List<BillingCostExportDetail> records = new ArrayList<BillingCostExportDetail>();
        records.add(preapreExportDetail(1, "customer9", 2.1f));
        records.add(preapreExportDetail(2, "customer9", 2.2f));
        records.add(preapreExportDetail(3, "customer10", 2.3f));

        this.billingCostExportDetails = new PagedResult<BillingCostExportDetail>();
        this.billingCostExportDetails.setRecords(records);
    }

    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(long billingCostExportId, int pageNo,
            int pageSize) throws BillingCostServiceException {
        throw new UnsupportedOperationException();
    }

    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails(boolean inQuickBooks, int pageNo,
            int pageSize) throws BillingCostServiceException {
        // TODO Auto-generated method stub
        return this.billingCostExportDetails;
    }

    public void auditAccountingAction(AccountingAuditRecord record) throws BillingCostServiceException {
        // do nothing
    }

    public void updateBillingCostExportDetails(List<QuickBooksImportUpdate> updates) throws BillingCostServiceException {
        this.updates = updates;
    }

    public String getLatestInvoiceNumber() throws BillingCostServiceException {
        return this.latestInvoiceNumber;
    }

    /**
     * <p>
     * Getter method for the billingCostExportDetails.
     * </p>
     * @return the billingCostExportDetails
     */
    public PagedResult<BillingCostExportDetail> getBillingCostExportDetails() {
        return billingCostExportDetails;
    }

    /**
     * <p>
     * Setter method for the billingCostExportDetails.
     * </p>
     * @param billingCostExportDetails the billingCostExportDetails to set
     */
    public void setBillingCostExportDetails(PagedResult<BillingCostExportDetail> billingCostExportDetails) {
        this.billingCostExportDetails = billingCostExportDetails;
    }

    /**
     * <p>
     * Setter method for the latestInvoiceNumber.
     * </p>
     * @param latestInvoiceNumber the latestInvoiceNumber to set
     */
    public void setLatestInvoiceNumber(String latestInvoiceNumber) {
        this.latestInvoiceNumber = latestInvoiceNumber;
    }

    /**
     * <p>
     * Getter method for the updates.
     * </p>
     * @return the updates
     */
    public List<QuickBooksImportUpdate> getUpdates() {
        return updates;
    }

    /**
     * Prepare an instance of BillingCostExportDetail.
     * @param id the id to set
     * @param customer the customer to set
     * @param billingAmount the billing amount to set
     * @return a new instance of BillingCostExportDetail populated with the given values
     */
    private static BillingCostExportDetail preapreExportDetail(long id, String customer, float billingAmount) {
        BillingCostExportDetail billingCostExportDetail = new BillingCostExportDetail();
        billingCostExportDetail.setId(id);
        billingCostExportDetail.setCustomer(customer);
        billingCostExportDetail.setBillingAmount(billingAmount);
        return billingCostExportDetail;
    }
}
