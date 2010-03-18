/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;


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
public class RegisterUserResult extends Result {
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
