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
 * This class represents set of criteria that can be used to generate a billing cost export history report. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostExportHistoryCriteria implements JsonPrintable, Serializable {
    /**
     * Represents the start date of the export date range. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Date startDate;

    /**
     * Represents the end date of the export date range. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Date endDate;

    /**
     * Represents the name of the accountant who performed the export. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private String accountantName;

    /**
     * Represents the ID of the payment area. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Long paymentAreaId;

    /**
     * Empty constructor.
     */
    public BillingCostExportHistoryCriteria() {
        // Empty
    }

    /**
     * <p>
     * Getter method for startDate, simply return the namesake instance variable.
     * </p>
     *
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Setter method for startDate, simply assign the value to the instance variable.
     * </p>
     *
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Getter method for endDate, simply return the namesake instance variable.
     * </p>
     *
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Setter method for endDate, simply assign the value to the instance variable.
     * </p>
     *
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * Getter method for paymentAreaId, simply return the namesake instance variable.
     * </p>
     *
     * @return the paymentAreaId
     */
    public Long getPaymentAreaId() {
        return paymentAreaId;
    }

    /**
     * <p>
     * Setter method for paymentAreaId, simply assign the value to the instance variable.
     * </p>
     *
     * @param paymentAreaId
     *            the paymentAreaId to set
     */
    public void setPaymentAreaId(Long paymentAreaId) {
        this.paymentAreaId = paymentAreaId;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        Helper.setJsonObject(jsonObject, "startDate", startDate);
        Helper.setJsonObject(jsonObject, "endDate", endDate);
        Helper.setJsonObject(jsonObject, "accountantName", accountantName);
        Helper.setJsonObject(jsonObject, "paymentAreaId", paymentAreaId);
        return jsonObject.toJSONString();
    }
}
