/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import java.util.Date;

import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents a record of an export of billing costs.<br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostExport extends IdentifiableEntity {
    /**
     * Represents the name of the accountant who performed the export. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private String accountantName;

    /**
     * Represents the payment area. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private PaymentArea paymentArea;

    /**
     * Represents the number of records exported as a batch. It is managed with a getter and setter. It may have
     * any value, but is expected to be a positive number. It is fully mutable.
     */
    private int recordsCount;

    /**
     * Represents the date and time at which the export was performed. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private Date timestamp;

    /**
     * Empty constructor.
     */
    public BillingCostExport() {
        // Empty
    }

    /**
     * <p>
     * Getter method for accountantName, simply return the namesake instance variable.
     * </p>
     *
     * @return the accountantName
     */
    public String getAccountantName() {
        return accountantName;
    }

    /**
     * <p>
     * Setter method for accountantName, simply assign the value to the instance variable.
     * </p>
     *
     * @param accountantName
     *            the accountantName to set
     */
    public void setAccountantName(String accountantName) {
        this.accountantName = accountantName;
    }

    /**
     * <p>
     * Getter method for paymentArea, simply return the namesake instance variable.
     * </p>
     *
     * @return the paymentArea
     */
    public PaymentArea getPaymentArea() {
        return paymentArea;
    }

    /**
     * <p>
     * Setter method for paymentArea, simply assign the value to the instance variable.
     * </p>
     *
     * @param paymentArea
     *            the paymentArea to set
     */
    public void setPaymentArea(PaymentArea paymentArea) {
        this.paymentArea = paymentArea;
    }

    /**
     * <p>
     * Getter method for recordsCount, simply return the namesake instance variable.
     * </p>
     *
     * @return the recordsCount
     */
    public int getRecordsCount() {
        return recordsCount;
    }

    /**
     * <p>
     * Setter method for recordsCount, simply assign the value to the instance variable.
     * </p>
     *
     * @param recordsCount
     *            the recordsCount to set
     */
    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    /**
     * <p>
     * Getter method for timestamp, simply return the namesake instance variable.
     * </p>
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * <p>
     * Setter method for timestamp, simply assign the value to the instance variable.
     * </p>
     *
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.setLong("id", getId());
        Helper.setJsonObject(jsonObject, "accountantName", accountantName);
        if (paymentArea != null) {
            JSONObject nestObject = new JSONObject();
            nestObject.setLong("id", paymentArea.getId());
            Helper.setJsonObject(nestObject, "name", paymentArea.getName());
            jsonObject.setNestedObject("paymentArea", nestObject);
        } else {
            jsonObject.setNull("paymentArea");
        }
        jsonObject.setInt("recordsCount", recordsCount);
        Helper.setJsonObject(jsonObject, "timestamp", timestamp);
        return jsonObject.toJSONString();
    }
}
