/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import java.util.Date;

import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents the details of a record of an export of billing costs.<br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostExportDetail extends IdentifiableEntity {
    /**
     * Represents the ID of the part export record. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private long billingCostExportId;

    /**
     * Represents the ID number of the contest for which the record was created. It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private long contestId;

    /**
     * Represents the name of the Customer. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String customer;

    /**
     * Represents the ID of the related payment detail. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Long paymentDetailId;

    /**
     * Represents the ID of the related project info type. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Long projectInfoTypeId;

    /**
     * Represents the amount being billed. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private float billingAmount;

    /**
     * Represents the type of payment (whether Prize for members or Contest Fee). It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private String paymentType;

    /**
     * Represents the name of the Contest for which the billing is done. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private String contestName;

    /**
     * Represents the flag whether the export has been moved to QuickBooks. It is managed with a getter and setter.
     * It may have any value. It is fully mutable.
     */
    private boolean inQuickBooks;

    /**
     * Represents the invoice number. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String invoiceNumber;

    /**
     * Represents the date and time when the data was imported into QuickBooks. It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private Date quickBooksImportTimestamp;

    /**
     * Empty constructor.
     */
    public BillingCostExportDetail() {
        // Empty
    }

    /**
     * <p>
     * Getter method for billingCostExportId, simply return the namesake instance variable.
     * </p>
     *
     * @return the billingCostExportId
     */
    public long getBillingCostExportId() {
        return billingCostExportId;
    }

    /**
     * <p>
     * Setter method for billingCostExportId, simply assign the value to the instance variable.
     * </p>
     *
     * @param billingCostExportId
     *            the billingCostExportId to set
     */
    public void setBillingCostExportId(long billingCostExportId) {
        this.billingCostExportId = billingCostExportId;
    }

    /**
     * <p>
     * Getter method for contestId, simply return the namesake instance variable.
     * </p>
     *
     * @return the contestId
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>
     * Setter method for contestId, simply assign the value to the instance variable.
     * </p>
     *
     * @param contestId
     *            the contestId to set
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Getter method for customer, simply return the namesake instance variable.
     * </p>
     *
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * <p>
     * Setter method for customer, simply assign the value to the instance variable.
     * </p>
     *
     * @param customer
     *            the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * <p>
     * Getter method for paymentDetailId, simply return the namesake instance variable.
     * </p>
     *
     * @return the paymentDetailId
     */
    public Long getPaymentDetailId() {
        return paymentDetailId;
    }

    /**
     * <p>
     * Setter method for paymentDetailId, simply assign the value to the instance variable.
     * </p>
     *
     * @param paymentDetailId
     *            the paymentDetailId to set
     */
    public void setPaymentDetailId(Long paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    /**
     * <p>
     * Getter method for projectInfoTypeId, simply return the namesake instance variable.
     * </p>
     *
     * @return the projectInfoTypeId
     */
    public Long getProjectInfoTypeId() {
        return projectInfoTypeId;
    }

    /**
     * <p>
     * Setter method for projectInfoTypeId, simply assign the value to the instance variable.
     * </p>
     *
     * @param projectInfoTypeId
     *            the projectInfoTypeId to set
     */
    public void setProjectInfoTypeId(Long projectInfoTypeId) {
        this.projectInfoTypeId = projectInfoTypeId;
    }

    /**
     * <p>
     * Getter method for billingAmount, simply return the namesake instance variable.
     * </p>
     *
     * @return the billingAmount
     */
    public float getBillingAmount() {
        return billingAmount;
    }

    /**
     * <p>
     * Setter method for billingAmount, simply assign the value to the instance variable.
     * </p>
     *
     * @param billingAmount
     *            the billingAmount to set
     */
    public void setBillingAmount(float billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * <p>
     * Getter method for paymentType, simply return the namesake instance variable.
     * </p>
     *
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * <p>
     * Setter method for paymentType, simply assign the value to the instance variable.
     * </p>
     *
     * @param paymentType
     *            the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * <p>
     * Getter method for contestName, simply return the namesake instance variable.
     * </p>
     *
     * @return the contestName
     */
    public String getContestName() {
        return contestName;
    }

    /**
     * <p>
     * Setter method for contestName, simply assign the value to the instance variable.
     * </p>
     *
     * @param contestName
     *            the contestName to set
     */
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    /**
     * <p>
     * Getter method for inQuickBooks, simply return the namesake instance variable.
     * </p>
     *
     * @return the inQuickBooks
     */
    public boolean isInQuickBooks() {
        return inQuickBooks;
    }

    /**
     * <p>
     * Setter method for inQuickBooks, simply assign the value to the instance variable.
     * </p>
     *
     * @param inQuickBooks
     *            the inQuickBooks to set
     */
    public void setInQuickBooks(boolean inQuickBooks) {
        this.inQuickBooks = inQuickBooks;
    }

    /**
     * <p>
     * Getter method for invoiceNumber, simply return the namesake instance variable.
     * </p>
     *
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * <p>
     * Setter method for invoiceNumber, simply assign the value to the instance variable.
     * </p>
     *
     * @param invoiceNumber
     *            the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * <p>
     * Getter method for quickBooksImportTimestamp, simply return the namesake instance variable.
     * </p>
     *
     * @return the quickBooksImportTimestamp
     */
    public Date getQuickBooksImportTimestamp() {
        return quickBooksImportTimestamp;
    }

    /**
     * <p>
     * Setter method for quickBooksImportTimestamp, simply assign the value to the instance variable.
     * </p>
     *
     * @param quickBooksImportTimestamp
     *            the quickBooksImportTimestamp to set
     */
    public void setQuickBooksImportTimestamp(Date quickBooksImportTimestamp) {
        this.quickBooksImportTimestamp = quickBooksImportTimestamp;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.setLong("id", getId());
        jsonObject.setLong("billingCostExportId", billingCostExportId);
        jsonObject.setLong("contestId", contestId);
        Helper.setJsonObject(jsonObject, "customer", customer);
        Helper.setJsonObject(jsonObject, "paymentDetailId", paymentDetailId);
        Helper.setJsonObject(jsonObject, "projectInfoTypeId", projectInfoTypeId);
        jsonObject.setFloat("billingAmount", billingAmount);
        Helper.setJsonObject(jsonObject, "paymentType", paymentType);
        Helper.setJsonObject(jsonObject, "contestName", contestName);
        jsonObject.setBoolean("inQuickBooks", inQuickBooks);
        Helper.setJsonObject(jsonObject, "invoiceNumber", invoiceNumber);
        Helper.setJsonObject(jsonObject, "quickBooksImportTimestamp", quickBooksImportTimestamp);
        return jsonObject.toJSONString();
    }
}
