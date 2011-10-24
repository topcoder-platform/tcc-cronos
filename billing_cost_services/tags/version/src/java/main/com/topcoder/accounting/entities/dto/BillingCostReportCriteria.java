/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents set of criteria that can be used to generate a billing cost report. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class BillingCostReportCriteria implements JsonPrintable, Serializable {
    /**
     * Represents the ID of the project. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private Long projectId;

    /**
     * Represents the start date of the date range. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private Date startDate;

    /**
     * Represents the end date of the date range. It is managed with a getter and setter. It may have any value. It
     * is fully mutable.
     */
    private Date endDate;

    /**
     * Represents the IDs of the statuses. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private long[] statusIds;

    /**
     * Represents the IDs of the contest types. It is managed with a getter and setter. It may have any value. It
     * is fully mutable.
     */
    private long[] contestTypeIds;

    /**
     * Represents the ID of the Customer. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Long customerId;

    /**
     * Represents the ID of the account to be used for billing. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private Long billingAccountId;

    /**
     * Empty constructor.
     */
    public BillingCostReportCriteria() {
        // Empty
    }

    /**
     * <p>
     * Getter method for projectId, simply return the namesake instance variable.
     * </p>
     *
     * @return the projectId
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Setter method for projectId, simply assign the value to the instance variable.
     * </p>
     *
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
     * Getter method for statusIds, simply return the namesake instance variable.
     * </p>
     *
     * @return the statusIds
     */
    public long[] getStatusIds() {
        return statusIds;
    }

    /**
     * <p>
     * Setter method for statusIds, simply assign the value to the instance variable.
     * </p>
     *
     * @param statusIds
     *            the statusIds to set
     */
    public void setStatusIds(long[] statusIds) {
        this.statusIds = statusIds;
    }

    /**
     * <p>
     * Getter method for contestTypeIds, simply return the namesake instance variable.
     * </p>
     *
     * @return the contestTypeIds
     */
    public long[] getContestTypeIds() {
        return contestTypeIds;
    }

    /**
     * <p>
     * Setter method for contestTypeIds, simply assign the value to the instance variable.
     * </p>
     *
     * @param contestTypeIds
     *            the contestTypeIds to set
     */
    public void setContestTypeIds(long[] contestTypeIds) {
        this.contestTypeIds = contestTypeIds;
    }

    /**
     * <p>
     * Getter method for customerId, simply return the namesake instance variable.
     * </p>
     *
     * @return the customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * <p>
     * Setter method for customerId, simply assign the value to the instance variable.
     * </p>
     *
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * <p>
     * Getter method for billingAccountId, simply return the namesake instance variable.
     * </p>
     *
     * @return the billingAccountId
     */
    public Long getBillingAccountId() {
        return billingAccountId;
    }

    /**
     * <p>
     * Setter method for billingAccountId, simply assign the value to the instance variable.
     * </p>
     *
     * @param billingAccountId
     *            the billingAccountId to set
     */
    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        Helper.setJsonObject(jsonObject, "projectId", projectId);
        Helper.setJsonObject(jsonObject, "startDate", startDate);
        Helper.setJsonObject(jsonObject, "endDate", endDate);
        if (statusIds != null) {
            jsonObject.setArray("statusIds", getJsonArray(statusIds));
        } else {
            jsonObject.setNull("statusIds");
        }
        if (contestTypeIds != null) {
            jsonObject.setArray("contestTypeIds", getJsonArray(contestTypeIds));
        } else {
            jsonObject.setNull("contestTypeIds");
        }
        Helper.setJsonObject(jsonObject, "customerId", customerId);
        Helper.setJsonObject(jsonObject, "billingAccountId", billingAccountId);
        return jsonObject.toJSONString();
    }

    /**
     * <p>
     * Get the json array.
     * </p>
     *
     * @param ids
     *            the ids
     * @return the json array
     */
    private static JSONArray getJsonArray(long[] ids) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < ids.length; i++) {
            jsonArray.addLong(ids[i]);
        }
        return jsonArray;
    }
}
