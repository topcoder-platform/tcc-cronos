package com.topcoder.accounting.entities.dao;

import java.util.*;
import java.lang.*;

/**
 * #### Purpose This class represents the details of a record of an export of billing costs This class provides a method
 * that serializes the entity contents into a JSON string. #### Thread Safety This class is mutable and not thread safe
 */
public class BillingCostExportDetail extends IdentifiableEntity {
    /**
     * #### Purpose Represents the ID of the part export record #### Usage it is managed with a getter and setter ####
     * Legal Values It may have any value #### Mutability It is fully mutable
     */
    private long billingCostExportId;
    /**
     * #### Purpose Represents the ID number of the contest for which the record was created. #### Usage it is managed
     * with a getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private long contestId;
    /**
     * #### Purpose Represents the name of the Customer #### Usage it is managed with a getter and setter #### Legal
     * Values It may have any value #### Mutability It is fully mutable
     */
    private String customer;
    /**
     * #### Purpose Represents the ID of the related payment detail #### Usage it is managed with a getter and setter
     * #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private Long paymentDetailId;
    /**
     * #### Purpose Represents the ID of the related project info type #### Usage it is managed with a getter and setter
     * #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private Long projectInfoTypeId;
    /**
     * #### Purpose Represents the amount being billed #### Usage it is managed with a getter and setter #### Legal
     * Values It may have any value #### Mutability It is fully mutable
     */
    private float billingAmount;
    /**
     * #### Purpose Represents the type of payment (whether Prize for members or Contest Fee) #### Usage it is managed
     * with a getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private String paymentType;
    /**
     * #### Purpose Represents the name of the Contest for which the billing is done #### Usage it is managed with a
     * getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private String contestName;
    /**
     * #### Purpose Represents the flag whether the export has been moved to QuockBooks #### Usage it is managed with a
     * getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private boolean inQuickBooks;
    /**
     * #### Purpose Represents the invoice number #### Usage it is managed with a getter and setter #### Legal Values It
     * may have any value #### Mutability It is fully mutable
     */
    private String invoiceNumber;
    /**
     * #### Purpose Represents the date and time when the data was imported into QuickBooks #### Usage it is managed
     * with a getter and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private Date quickBooksImportTimestamp;

    /**
     * #### Purpose Empty constructor
     */
    public BillingCostExportDetail() {
    }

    /**
     * <p>
     * Getter method for the billingCostExportId.
     * </p>
     * @return the billingCostExportId
     */
    public long getBillingCostExportId() {
        return billingCostExportId;
    }

    /**
     * <p>
     * Setter method for the billingCostExportId.
     * </p>
     * @param billingCostExportId the billingCostExportId to set
     */
    public void setBillingCostExportId(long billingCostExportId) {
        this.billingCostExportId = billingCostExportId;
    }

    /**
     * <p>
     * Getter method for the contestId.
     * </p>
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Setter method for the contestId.
     * </p>
     * @param contestId the contestId to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Getter method for the customer.
     * </p>
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * <p>
     * Setter method for the customer.
     * </p>
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * <p>
     * Getter method for the paymentDetailId.
     * </p>
     * @return the paymentDetailId
     */
    public Long getPaymentDetailId() {
        return paymentDetailId;
    }

    /**
     * <p>
     * Setter method for the paymentDetailId.
     * </p>
     * @param paymentDetailId the paymentDetailId to set
     */
    public void setPaymentDetailId(Long paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    /**
     * <p>
     * Getter method for the projectInfoTypeId.
     * </p>
     * @return the projectInfoTypeId
     */
    public Long getProjectInfoTypeId() {
        return projectInfoTypeId;
    }

    /**
     * <p>
     * Setter method for the projectInfoTypeId.
     * </p>
     * @param projectInfoTypeId the projectInfoTypeId to set
     */
    public void setProjectInfoTypeId(Long projectInfoTypeId) {
        this.projectInfoTypeId = projectInfoTypeId;
    }

    /**
     * <p>
     * Getter method for the billingAmount.
     * </p>
     * @return the billingAmount
     */
    public float getBillingAmount() {
        return billingAmount;
    }

    /**
     * <p>
     * Setter method for the billingAmount.
     * </p>
     * @param billingAmount the billingAmount to set
     */
    public void setBillingAmount(float billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * <p>
     * Getter method for the paymentType.
     * </p>
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * <p>
     * Setter method for the paymentType.
     * </p>
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * <p>
     * Getter method for the contestName.
     * </p>
     * @return the contestName
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p>
     * Setter method for the contestName.
     * </p>
     * @param contestName the contestName to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>
     * Getter method for the inQuickBooks.
     * </p>
     * @return the inQuickBooks
     */
    public boolean isInQuickBooks() {
        return inQuickBooks;
    }

    /**
     * <p>
     * Setter method for the inQuickBooks.
     * </p>
     * @param inQuickBooks the inQuickBooks to set
     */
    public void setInQuickBooks(boolean inQuickBooks) {
        this.inQuickBooks = inQuickBooks;
    }

    /**
     * <p>
     * Getter method for the invoiceNumber.
     * </p>
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>
     * Setter method for the invoiceNumber.
     * </p>
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * <p>
     * Getter method for the quickBooksImportTimestamp.
     * </p>
     * @return the quickBooksImportTimestamp
     */
    public Date getQuickBooksImportTimestamp() {
        return quickBooksImportTimestamp;
    }

    /**
     * <p>
     * Setter method for the quickBooksImportTimestamp.
     * </p>
     * @param quickBooksImportTimestamp the quickBooksImportTimestamp to set
     */
    public void setQuickBooksImportTimestamp(Date quickBooksImportTimestamp) {
        this.quickBooksImportTimestamp = quickBooksImportTimestamp;
    }

    /**
     * #### Purpose Provides the JSON representation of the contents of this entity. #### Parameters return - the JSON
     * representation of the contents of this entity. #### Implementation Notes See CS 1.3.2 for details
     * @param Return
     * @return
     */
    public String toJSONString() {
        throw new UnsupportedOperationException("Not implemented in mock.");
    }
}
