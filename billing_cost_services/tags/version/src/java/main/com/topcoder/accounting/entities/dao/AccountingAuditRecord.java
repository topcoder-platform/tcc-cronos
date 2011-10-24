/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import java.util.Date;

import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents a record of a single audit. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class AccountingAuditRecord extends IdentifiableEntity {
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
     * Represents the timestamp of the auditing moment. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Date timestamp;

    /**
     * Empty constructor.
     */
    public AccountingAuditRecord() {
        // Empty
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
        Helper.setJsonObject(jsonObject, "action", action);
        Helper.setJsonObject(jsonObject, "userName", userName);
        Helper.setJsonObject(jsonObject, "timestamp", timestamp);
        return jsonObject.toJSONString();
    }
}
