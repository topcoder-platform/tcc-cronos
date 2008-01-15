/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import java.io.Serializable;

/**
 * <p>
 * This class represents the primary key of the 'comp_client' table, a combination of it and the CompClient class
 * describes the whole relationship between a component and a client and corresponds to the 'comp_client' table in the
 * DDL.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is mutable so not thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class CompClientPK implements Serializable {

    /**
     * <p>
     * Represents the component id.
     * </p>
     */
    private Long componentId;

    /**
     * <p>
     * Represents the client id.
     * </p>
     */
    private Integer clientId;

    /**
     * <p>
     * Creates a <code>CompClientPK</code> instance with default values.
     * </p>
     */
    public CompClientPK() {
    }

    /**
     * <p>
     * Creates a <code>CompClientPK</code> instance with the component id and client id.
     * </p>
     *
     * @param componentId
     *            the component id.
     * @param clientId
     *            the client id.
     */
    public CompClientPK(Long componentId, Integer clientId) {
        this.componentId = componentId;
        this.clientId = clientId;
    }

    /**
     * <p>
     * Gets the component id.
     * </p>
     *
     * @return the component id.
     */
    public Long getComponentId() {
        return this.componentId;
    }

    /**
     * <p>
     * Sets the component id.
     * </p>
     *
     * @param componentId
     *            the component id.
     */
    public void setComponentId(Long componentId) {
        this.componentId = componentId;
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

}
