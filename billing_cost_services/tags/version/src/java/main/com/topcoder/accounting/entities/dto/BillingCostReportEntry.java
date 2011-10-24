/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents an entry in the billing cost report. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostReportEntry implements JsonPrintable, Serializable {
    /**
     * Represents the date of the payment. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Date paymentDate;

    /**
     * Represents the name of the Customer. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String customer;

    /**
     * Represents the account to be used for billing. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private String billingAccount;

    /**
     * Represents the name of the project to which the contest belongs. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private String projectName;

    /**
     * Represents the name of the Contest for which the billing is done. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private String contestName;

    /**
     * Represents the ID of the Contest for which billing is done. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private long contestId;

    /**
     * Represents the Reference ID for the record. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private String referenceId;

    /**
     * Represents the category of the contest. (Architecture, wireframes, etc) It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private String category;

    /**
     * Represents the Current Status of the Contest. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private String status;

    /**
     * Represents the The Date on which the Contest was launched. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private Date launchDate;

    /**
     * Represents the Date on which the contest was completed. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private Date completionDate;

    /**
     * Represents the Type of payment (whether Prize for members or Contest Fee). It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private String paymentType;

    /**
     * Represents the Amount billed. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private float amount;

    /**
     * Represents the ID of the related project info type. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Long projectInfoTypeId;

    /**
     * Represents the ID of the related payment detail. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Long paymentDetailId;

    /**
     * Represents the flag whether the export was done. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private boolean exported;

    /**
     * Represents the invoice number. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private String invoiceNumber;

    /**
     * Empty constructor.
     */
    public BillingCostReportEntry() {
        // Empty
    }

    /**
     * <p>
     * Getter method for paymentDate, simply return the namesake instance variable.
     * </p>
     *
     * @return the paymentDate
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * <p>
     * Setter method for paymentDate, simply assign the value to the instance variable.
     * </p>
     *
     * @param paymentDate
     *            the paymentDate to set
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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
     * Getter method for billingAccount, simply return the namesake instance variable.
     * </p>
     *
     * @return the billingAccount
     */
    public String getBillingAccount() {
        return billingAccount;
    }

    /**
     * <p>
     * Setter method for billingAccount, simply assign the value to the instance variable.
     * </p>
     *
     * @param billingAccount
     *            the billingAccount to set
     */
    public void setBillingAccount(String billingAccount) {
        this.billingAccount = billingAccount;
    }

    /**
     * <p>
     * Getter method for projectName, simply return the namesake instance variable.
     * </p>
     *
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <p>
     * Setter method for projectName, simply assign the value to the instance variable.
     * </p>
     *
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
     * Getter method for referenceId, simply return the namesake instance variable.
     * </p>
     *
     * @return the referenceId
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * <p>
     * Setter method for referenceId, simply assign the value to the instance variable.
     * </p>
     *
     * @param referenceId
     *            the referenceId to set
     */
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * <p>
     * Getter method for category, simply return the namesake instance variable.
     * </p>
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * <p>
     * Setter method for category, simply assign the value to the instance variable.
     * </p>
     *
     * @param category
     *            the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * <p>
     * Getter method for status, simply return the namesake instance variable.
     * </p>
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>
     * Setter method for status, simply assign the value to the instance variable.
     * </p>
     *
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>
     * Getter method for launchDate, simply return the namesake instance variable.
     * </p>
     *
     * @return the launchDate
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * <p>
     * Setter method for launchDate, simply assign the value to the instance variable.
     * </p>
     *
     * @param launchDate
     *            the launchDate to set
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * <p>
     * Getter method for completionDate, simply return the namesake instance variable.
     * </p>
     *
     * @return the completionDate
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * <p>
     * Setter method for completionDate, simply assign the value to the instance variable.
     * </p>
     *
     * @param completionDate
     *            the completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
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
     * Getter method for amount, simply return the namesake instance variable.
     * </p>
     *
     * @return the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * <p>
     * Setter method for amount, simply assign the value to the instance variable.
     * </p>
     *
     * @param amount
     *            the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
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
     * Getter method for exported, simply return the namesake instance variable.
     * </p>
     *
     * @return the exported
     */
    public boolean isExported() {
        return exported;
    }

    /**
     * <p>
     * Setter method for exported, simply assign the value to the instance variable.
     * </p>
     *
     * @param exported
     *            the exported to set
     */
    public void setExported(boolean exported) {
        this.exported = exported;
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
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        Helper.setJsonObject(jsonObject, "paymentDate", paymentDate);
        Helper.setJsonObject(jsonObject, "customer", customer);
        Helper.setJsonObject(jsonObject, "billingAccount", billingAccount);
        Helper.setJsonObject(jsonObject, "projectName", projectName);
        Helper.setJsonObject(jsonObject, "contestName", contestName);
        jsonObject.setLong("contestId", contestId);
        Helper.setJsonObject(jsonObject, "referenceId", referenceId);
        Helper.setJsonObject(jsonObject, "category", category);
        Helper.setJsonObject(jsonObject, "status", status);
        Helper.setJsonObject(jsonObject, "launchDate", launchDate);
        Helper.setJsonObject(jsonObject, "completionDate", completionDate);
        Helper.setJsonObject(jsonObject, "paymentType", paymentType);
        jsonObject.setFloat("amount", amount);
        Helper.setJsonObject(jsonObject, "projectInfoTypeId", projectInfoTypeId);
        Helper.setJsonObject(jsonObject, "paymentDetailId", paymentDetailId);
        jsonObject.setBoolean("exported", exported);
        Helper.setJsonObject(jsonObject, "invoiceNumber", invoiceNumber);
        return jsonObject.toJSONString();
    }
}
