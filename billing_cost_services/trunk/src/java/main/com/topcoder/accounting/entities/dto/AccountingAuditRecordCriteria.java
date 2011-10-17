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
 * This class represents set of criteria that can be used to generate an audit report.<br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class AccountingAuditRecordCriteria implements JsonPrintable, Serializable {
    /**
     * Represents the start date of the audit date range. It is managed wIth a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Date startDate;

    /**
     * Represents the end date of the audit date range. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Date endDate;

    /**
     * Represents the name of the audited user action. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private String action;

    /**
     * Represents the username of the User who performed the action. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private String userName;

    /**
     * Empty constructor.
     */
    public AccountingAuditRecordCriteria() {
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
     * Getter method for action, simply return the namesake instance variable.
     * </p>
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * <p>
     * Setter method for action, simply assign the value to the instance variable.
     * </p>
     *
     * @param action
     *            the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>
     * Getter method for userName, simply return the namesake instance variable.
     * </p>
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>
     * Setter method for userName, simply assign the value to the instance variable.
     * </p>
     *
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
        Helper.setJsonObject(jsonObject, "action", action);
        Helper.setJsonObject(jsonObject, "userName", userName);
        return jsonObject.toJSONString();
    }
}
