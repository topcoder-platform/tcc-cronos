/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dao;

import com.topcoder.accounting.service.impl.Helper;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents the category of payment. (Studio, Software Costs etc.) <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class PaymentArea extends IdentifiableEntity {
    /**
     * Represents the name of the payment category. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public PaymentArea() {
        // Empty
    }

    /**
     * <p>
     * Getter method for name, simply return the namesake instance variable.
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter method for name, simply assign the value to the instance variable.
     * </p>
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.setLong("id", getId());
        Helper.setJsonObject(jsonObject, "name", name);
        return jsonObject.toJSONString();
    }
}
