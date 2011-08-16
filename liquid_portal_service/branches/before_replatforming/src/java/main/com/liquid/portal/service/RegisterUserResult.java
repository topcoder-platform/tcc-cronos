/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * A type of Result that also provides the registered user ID. Returned in the
 * registerUser method of the service.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registerUserResult", propOrder = { "userId"})
public class RegisterUserResult extends Result implements Serializable {

    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 9200338518294319835L;

    /**
     * <p>
     * The ID of the registered user.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     */
    private long userId;

    /**
     * <p> Default empty constructor. </p>
     */
    public RegisterUserResult() {
    }
     /**
     * Default constructor.
     */
    public RegisterUserResult(String message, int resultCode) {
        super(message, resultCode);
    }

    /**
     * <p>
     * Gets the ID of the registered user.
     * </p>
     *
     * @return the ID of the registered user
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets the ID of the registered user.
     * </p>
     *
     * @param userId
     *            the ID of the registered user
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
