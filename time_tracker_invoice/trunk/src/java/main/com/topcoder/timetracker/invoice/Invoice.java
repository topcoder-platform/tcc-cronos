/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to access the various properties
 * of a Time Tracker Invoice, This class encapsulates the invoice's information within the Time Tracker component.
 * </p>
 * <p>
 * It also extends from the base TimeTrackerBean to include the id, creation and modification details.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance, or this
 * class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class Invoice extends TimeTrackerBean {

    /** Serial version UID. */
    private static final long serialVersionUID = -8655241523524041382L;

    /**
     * <p>
     * This is the company's id that this invoice belongs to.
     * </p>
     * <p>
     * Initial Value: Long.MIN_VALUE (This indicates an uninitialized value)
     * </p>
     * <p>
     * Accessed In: getCompanyId
     * </p>
     * <p>
     * Modified In: setCompanyId
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private long companyId = Long.MIN_VALUE;

    /**
     * <p>
     * This is the project's id that this invoice will be billed to.
     * </p>
     * <p>
     * Initial Value: Long.MIN_VALUE (This indicates an uninitialized value)
     * </p>
     * <p>
     * Accessed In: getProjectId
     * </p>
     * <p>
     * Modified In: setProjectId
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private long projectId = Long.MIN_VALUE;

    /**
     * <p>
     * The Accounting Invoice number which ties this invoice back to the account system.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getInvoiceNumber
     * </p>
     * <p>
     * Modified In: setInvoiceNumber
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private String invoiceNumber;

    /**
     * <p>
     * Entered by administrator to tie the invoice to a customers purchase order.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getPurchaseOrderNumber
     * </p>
     * <p>
     * Modified In: setPurchaseOrderNumber
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private String purchaseOrderNumber;

    /**
     * <p>
     * Has this invoice been paid.
     * </p>
     * <p>
     * Initial Value: false
     * </p>
     * <p>
     * Accessed In: isPaid
     * </p>
     * <p>
     * Modified In: setPaid
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private boolean paid;

    /**
     * <p>
     * The due date plus the number of days in the terms.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getDueDate
     * </p>
     * <p>
     * Modified In: setDueDate
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private Date dueDate;

    /**
     * <p>
     * The invoice date plus the number of days in the terms.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getInvoiceDate
     * </p>
     * <p>
     * Modified In: setInvoiceDate
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private Date invoiceDate;

    /**
     * <p>
     * The total of all the Service Details.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getServicesSubTotal
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private BigDecimal servicesSubTotal;

    /**
     * <p>
     * The total of all the expense.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getExpenseSubTotal
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private BigDecimal expenseSubTotal;

    /**
     * <p>
     * The sales for this invoice, from the project record.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getSalesTax
     * </p>
     * <p>
     * Modified In: setSalesTax
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private BigDecimal salesTax;

    /**
     * <p>
     * The current status of the invoice.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getInvoiceStatus
     * </p>
     * <p>
     * Modified In: setInvoiceStatus
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private InvoiceStatus invoiceStatus;

    /**
     * <p>
     * The terms of this invoice.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getPaymentTerms
     * </p>
     * <p>
     * Modified In: setPaymentTerms
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private PaymentTerm paymentTerm;

    /**
     * <p>
     * The list of Expense being billed on this invoice.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getExpenseEntries
     * </p>
     * <p>
     * Modified In: setExpenseEntries
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values,can be void, can contain null values.
     * </p>
     */
    private ExpenseEntry[] expenseEntries;

    /**
     * <p>
     * The list of Fixed Billing being billed on this invoice.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getFixedBillingEntries
     * </p>
     * <p>
     * Modified In: setFixedBillingEntries
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values,can be void, can contain null values.
     * </p>
     */
    private FixedBillingEntry[] fixedBillingEntries;

    /**
     * <p>
     * The list of Time Entries being billed on this invoice.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getServiceDetails
     * </p>
     * <p>
     * Modified In: setServiceDetails
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values,can be void, can contain null values.
     * </p>
     */
    private InvoiceServiceDetail[] serviceDetails;

    /**
     * ID of the user who modified this invoice.
     */
    private long modificationUserId;

    /**
     * <p>
     * Construct an empty Invoice.
     * </p>
     */
    public Invoice() {
        // nothing to do
    }

    /**
     * Returns the company id.
     *
     * @return the company id
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the company id.
     * </p>
     *
     * @param companyId
     *            the company id to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
        setChanged(true);
    }

    /**
     * <p>
     * Returns the due date.
     * </p>
     *
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * <p>
     * Sets the due date.
     * </p>
     *
     * @param dueDate
     *            the due date to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        setChanged(true);
    }

    /**
     * <p>
     * Returns the expense entries.
     * </p>
     *
     * @return the expense entries
     */
    public ExpenseEntry[] getExpenseEntries() {
        return expenseEntries == null ? null : (ExpenseEntry[]) expenseEntries.clone();
    }

    /**
     * <p>
     * Sets the expense entries.
     * </p>
     *
     * @param expenseEntries
     *            the expense entries to set
     */
    public void setExpenseEntries(ExpenseEntry[] expenseEntries) {
        expenseSubTotal = new BigDecimal(0);

        this.expenseEntries = expenseEntries;

        // ignore subTotal if expenseEntries is null
        if (expenseEntries != null) {
            for (int i = 0; i < expenseEntries.length; i++) {
                // ignore the element if it is null
                if (expenseEntries[i] != null && expenseEntries[i].getAmount() != null) {
                    expenseSubTotal = expenseSubTotal.add(expenseEntries[i].getAmount());
                }
            }
        }
        setChanged(true);
    }

    /**
     * Returns the expense sub total.
     *
     * @return the expense sub total
     */
    public BigDecimal getExpenseSubTotal() {
        return expenseSubTotal;
    }

    /**
     * Returns the invoice number.
     *
     * @return the invoice number
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>
     * Sets the invoice number.
     * </p>
     *
     * @param invoiceNumber
     *            the invoice number to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        setChanged(true);
    }

    /**
     * <p>
     * Returns the invoice service details.
     * </p>
     *
     * @return the invoice service details
     */
    public InvoiceServiceDetail[] getServiceDetails() {
        return serviceDetails == null ? null : (InvoiceServiceDetail[]) serviceDetails.clone();
    }

    /**
     * <p>
     * Sets the service details.
     * </p>
     *
     * @param serviceDetails
     *            the invoice details to set
     */
    public void setServiceDetails(InvoiceServiceDetail[] serviceDetails) {
        this.serviceDetails = serviceDetails;
        recalculateServicesSubTotal();
        setChanged(true);
    }

    /**
     * Returns the invoice status.
     *
     * @return the invoice status
     */
    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * <p>
     * Sets the invoice status.
     * </p>
     *
     * @param invoiceStatus
     *            the invoice status to set
     */
    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        setChanged(true);
    }

    /**
     * Returns true if the invoice has been paid, false otherwise.
     *
     * @return true if the invoice has been paid, false otherwise
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * <p>
     * Sets the paid.
     * </p>
     *
     * @param paid
     *            the paid to set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
        setChanged(true);
    }

    /**
     * Returns the payment terms.
     *
     * @return the payment terms
     */
    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    /**
     * <p>
     * Sets the payment terms.
     * </p>
     *
     * @param paymentTerms
     *            the payment terms to set
     */
    public void setPaymentTerm(PaymentTerm paymentTerms) {
        this.paymentTerm = paymentTerms;
        setChanged(true);
    }

    /**
     * Returns the project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the project id.
     * </p>
     *
     * @param projectId
     *            the project id to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
        setChanged(true);
    }

    /**
     * Returns the purchase order number.
     *
     * @return the purchase order number
     */
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    /**
     * <p>
     * Sets the purchase order number.
     * </p>
     *
     * @param purchaseOrderNumber
     *            the purchase order number to set
     */
    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
        setChanged(true);
    }

    /**
     * Returns the sales tax.
     *
     * @return the sales tax
     */
    public BigDecimal getSalesTax() {
        return salesTax;
    }

    /**
     * <p>
     * Sets the sales tax.
     * </p>
     *
     * @param salesTax
     *            the sales tax to set
     */
    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
        setChanged(true);
    }

    /**
     * Returns the services sub total.
     *
     * @return the services sub total
     */
    public BigDecimal getServicesSubTotal() {
        return servicesSubTotal;
    }

    /**
     * <p>
     * Returns the fixed billing entries.
     * </p>
     *
     * @return the fixed billing entries
     */
    public FixedBillingEntry[] getFixedBillingEntries() {
        return fixedBillingEntries;
    }

    /**
     * <p>
     * Sets the fixed billing entries.
     * </p>
     *
     * @param fixedBillingEntries
     *            the fixed billing entries to set
     */
    public void setFixedBillingEntries(FixedBillingEntry[] fixedBillingEntries) {
        this.fixedBillingEntries = fixedBillingEntries;
        recalculateServicesSubTotal();
        setChanged(true);
    }

    /**
     * Return the invoice date.
     *
     * @return the invoice date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Set the invoice date.
     *
     * @param invoiceDate
     *            the given date of the invoice
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
        setChanged(true);
    }

    /**
     * Return ID of the user who modified this invoice.
     *
     * @return ID of the user who modified this invoice.
     */
    public long getModificationUserId() {
        return this.modificationUserId;
    }

    /**
     * Set ID of the user who modified this invoice.
     *
     * @param modificationUserId
     *            ID of the user who modified this invoice.
     */
    public void setModificationUserId(long modificationUserId) {
        this.modificationUserId = modificationUserId;
    }

    /**
     * Thie method recalculates services subtotal for the invoice. It is called by either
     * <code>setServiceDetails</code> or <code>setFixedBillingEntries</code> methods.
     */
    private void recalculateServicesSubTotal() {
        this.servicesSubTotal = new BigDecimal(0.0);

        // Calculate total for service details (time enrties) first
        if (this.serviceDetails != null) {
            for (int i = 0; i < this.serviceDetails.length; ++i) {
                // ignores the element if it is null
                if (this.serviceDetails[i] != null && this.serviceDetails[i].getAmount() != null) {
                    this.servicesSubTotal = this.servicesSubTotal.add(this.serviceDetails[i].getAmount());
                }
            }
        }
        // Calculate total for fixed billing enrties
        if (this.fixedBillingEntries != null) {
            for (int i = 0; i < this.fixedBillingEntries.length; ++i) {
                // ignores the element if it is null
                if (this.fixedBillingEntries[i] != null) {
                    this.servicesSubTotal = this.servicesSubTotal.add(new BigDecimal(this.fixedBillingEntries[i].getAmount()));
                }
            }
        }
    }
}
