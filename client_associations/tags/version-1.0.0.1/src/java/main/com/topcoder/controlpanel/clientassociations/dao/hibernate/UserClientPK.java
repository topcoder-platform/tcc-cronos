/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.io.Serializable;

/**
 * <p>
 * This class represents the primary key of the 'user_client' table, a combination of it and the
 * <code>{@link UserClient}</code> class describes the whole relationship between a user and a client and corresponds
 * to the 'user_client' table in the DDL.
 * </p>
 * <p>
 * <b>Thread Safety</b>:This class is mutable so not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UserClientPK implements Serializable {

    /**
     * <p>
     * Represents the associated user id.
     * </p>
     */
    private Long userId;

    /**
     * <p>
     * Represents the associated client id.
     * </p>
     */
    private Integer clientId;

    /**
     * <p>
     * Creates a <code>UserClientPK</code> instance with default values.
     * </p>
     */
    public UserClientPK() {
    }

    /**
     * <p>
     * Creates a <code>UserClientPK</code> instance with the associated user id and the associated client id.
     * </p>
     *
     * @param userId
     *            the associated user id.
     * @param clientId
     *            the associated client id
     */
    public UserClientPK(Long userId, Integer clientId) {
        this.userId = userId;
        this.clientId = clientId;
    }

    /**
     * <p>
     * Gets the associated user id.
     * </p>
     *
     * @return the associated user id.
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * <p>
     * Sets the associated user id.
     * </p>
     *
     * @param userId
     *            the associated user id.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * <p>
     * Gets the associated client id.
     * </p>
     *
     * @return the associated client id.
     */
    public Integer getClientId() {
        return this.clientId;
    }

    /**
     * <p>
     * Sets the associated client id.
     * </p>
     *
     * @param clientId
     *            the associated client id.
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}
