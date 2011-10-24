/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import java.io.Serializable;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents a composite payment identifier. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class PaymentIdentifier implements JsonPrintable, Serializable {
    /**
     * Represents the ID number of the contest for which the record was created. It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private Long contestId;

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
     * Empty constructor.
     */
    public PaymentIdentifier() {
        // Empty
    }

    /**
     * <p>
     * Getter method for contestId, simply return the namesake instance variable.
     * </p>
     *
     * @return the contestId
     */
    public Long getContestId() {
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
    public void setContestId(Long contestId) {
        this.contestId = contestId;
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
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        Helper.setJsonObject(jsonObject, "contestId", contestId);
        Helper.setJsonObject(jsonObject, "projectInfoTypeId", projectInfoTypeId);
        Helper.setJsonObject(jsonObject, "paymentDetailId", paymentDetailId);
        return jsonObject.toJSONString();
    }
}
