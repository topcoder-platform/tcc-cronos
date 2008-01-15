/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.io.Serializable;

/**
 * <p>
 * This class represents the relationship between a user and a client, a combination of it and the
 * <code>{@link UserClientPK}</code> class corresponds to the 'user_client' table in the DDL.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is mutable so not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UserClient implements Serializable {

    /**
     * <p>
     * Represents the primary key of 'user_client' table.
     * </p>
     */
    private UserClientPK comp_id;

    /**
     * <p>
     * Represents the flag whether the user is an admin.
     * </p>
     */
    private Integer adminInd;

    /**
     * <p>
     * Represents the associated client.
     * </p>
     */
    private Client client;

    /**
     * <p>
     * Creates a <code>UserClient</code> instance with default values.
     * </p>
     */
    public UserClient() {
    }

    /**
     * <p>
     * Creates a <code>UserClient</code> instance with the primary key.
     * </p>
     *
     * @param comp_id
     *            the primary key of 'user_client' table.
     */
    public UserClient(UserClientPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * <p>
     * Creates a <code>UserClient</code> instance by set all fields correspondingly.
     * </p>
     *
     * @param comp_id
     *            the primary key.
     * @param adminInd
     *            the flag indicating whether the user is an admin.
     * @param client
     *            the associated client
     */
    public UserClient(UserClientPK comp_id, Integer adminInd, Client client) {
        this.comp_id = comp_id;
        this.adminInd = adminInd;
        this.client = client;
    }

    /**
     * <p>
     * Gets the primary key.
     * </p>
     *
     * @return the primary key.
     */
    public UserClientPK getComp_id() {
        return comp_id;
    }

    /**
     * <p>
     * Sets the primary key.
     * </p>
     *
     * @param comp_id
     *            the primary key.
     */
    public void setComp_id(UserClientPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * <p>
     * Gets the flag indicating whether the user is an admin.
     * </p>
     *
     * @return the flag indicating whether the user is an admin.
     */
    public Integer getAdminInd() {
        return adminInd;
    }

    /**
     * <p>
     * Sets the flag indicating whether the user is an admin.
     * </p>
     *
     * @param adminInd
     *            the flag indicating whether the user is an admin.
     */
    public void setAdminInd(Integer adminInd) {
        this.adminInd = adminInd;
    }

    /**
     * <p>
     * Gets the associated client.
     * </p>
     *
     * @return the associated client
     */
    public Client getClient() {
        return client;
    }

    /**
     * <p>
     * Sets the associated client.
     * </p>
     *
     * @param client
     *            the associated client
     */
    public void setClient(Client client) {
        this.client = client;
    }

}
