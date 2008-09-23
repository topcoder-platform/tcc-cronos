/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class represents a client entity. It corresponds to the 'client' table in the DDL.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is mutable so not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class Client implements Serializable {

    /**
     * <p>
     * Represents the client id.
     * </p>
     */
    private Integer clientId;

    /**
     * <p>
     * Represents the client name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the set of component-client relationships associated with the client.
     * </p>
     */
    private Set<CompClient> compClients = new HashSet<CompClient>(0);

    /**
     * <p>
     * Represents the set of user-client relationships associated with the client.
     * </p>
     */
    private Set<UserClient> userClients = new HashSet<UserClient>(0);

    /**
     * <p>
     * Creates a <code>Client</code> instance. All internal fields are set to default.
     * </p>
     */
    public Client() {
    }

    /**
     * <p>
     * Creates a <code>Client</code> instance by assigning corresponding fields.
     * </p>
     *
     * @param clientId
     *            the client id.
     * @param name
     *            the client name
     */
    public Client(Integer clientId, String name) {
        this.clientId = clientId;
        this.name = name;
    }

    /**
     * <p>
     * Creates a <code>Client</code> instance by assigning corresponding fields.
     * </p>
     *
     * @param clientId
     *            the client id.
     * @param name
     *            the client name
     * @param compClients
     *            the set of component-client relationships associated with the client.
     * @param userClients
     *            the set of user-client relationships associated with the client
     */
    public Client(Integer clientId, String name, Set<CompClient> compClients, Set<UserClient> userClients) {
        this.clientId = clientId;
        this.name = name;
        this.compClients = compClients;
        this.userClients = userClients;
    }

    /**
     * <p>
     * Gets the client id.
     * </p>
     *
     * @return the client id.
     */
    public Integer getClientId() {
        return this.clientId;
    }

    /**
     * <p>
     * Sets the client id.
     * </p>
     *
     * @param clientId
     *            the client id.
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * <p>
     * Gets the client name.
     * </p>
     *
     * @return the client name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Sets the client name.
     * </p>
     *
     * @param name
     *            the client name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the set of component-client relationships associated with the client.
     * </p>
     *
     * @return the set of component-client relationships associated with the client.
     */
    public Set<CompClient> getCompClients() {
        return this.compClients;
    }

    /**
     * <p>
     * Sets the set of component-client relationships associated with the client.
     * </p>
     *
     * @param compClients
     *            the set of component-client relationships associated with the client.
     */
    public void setCompClients(Set<CompClient> compClients) {
        this.compClients = compClients;
    }

    /**
     * <p>
     * Gets the set of user-client relationships associated with the client.
     * </p>
     *
     * @return the set of user-client relationships associated with the client
     */
    public Set<UserClient> getUserClients() {
        return this.userClients;
    }

    /**
     * <p>
     * Sets the set of user-client relationships associated with the client.
     * </p>
     *
     * @param userClients
     *            the set of user-client relationships associated with the client
     */
    public void setUserClients(Set<UserClient> userClients) {
        this.userClients = userClients;
    }

}
