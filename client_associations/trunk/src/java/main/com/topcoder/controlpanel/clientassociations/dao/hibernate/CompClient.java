/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.io.Serializable;

/**
 * <p>
 * This class represents the relationship between a component and a client, a combination of it and the
 * <code>{@link CompClientPK}</code> class corresponds to the 'comp_client' table in the DDL.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is mutable so not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class CompClient implements Serializable {

    /**
     * <p>
     * Represents the primary key of 'comp_client' table.
     * </p>
     */
    private CompClientPK comp_id;

    /**
     * <p>
     * Represents the associated client.
     * </p>
     */
    private Client client;

    /**
     * <p>
     * Creates a <code>CompClient</code> instance using default value.
     * </p>
     */
    public CompClient() {
    }

    /**
     * <p>
     * Creates a <code>CompClient</code> instance with the primary key.
     * </p>
     *
     * @param comp_id
     *            the primary key object.
     */
    public CompClient(CompClientPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * <p>
     * Creates a <code>CompClient</code> instance with the primary key and associated client.
     * </p>
     *
     * @param comp_id
     *            the primary key.
     * @param client
     *            the associated client
     */
    public CompClient(CompClientPK comp_id, Client client) {
        this.comp_id = comp_id;
        this.client = client;
    }

    /**
     * <p>
     * Gets the primary key.
     * </p>
     *
     * @return he primary key.
     */
    public CompClientPK getComp_id() {
        return this.comp_id;
    }

    /**
     * <p>
     * Sets the primary key.
     * </p>
     *
     * @param comp_id
     *            the primary key
     */
    public void setComp_id(CompClientPK comp_id) {
        this.comp_id = comp_id;
    }

    /**
     * <p>
     * Gets the associated client.
     * </p>
     *
     * @return the associated client.
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * <p>
     * Sets the associated client.
     * </p>
     *
     * @param client
     *            the associated client.
     */
    public void setClient(Client client) {
        this.client = client;
    }

}
