/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.usermapping.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.clients.model.AuditableEntity;

/**
 * <p>
 *  This class represents the UserClientMapping java bean.
 *  An UserClientMapping can contain a client identifier and a user identifier.
 * </p>
 *
 * <p>
 *  This is a simple java bean (with a default no-arg constructor and for each property,
 *  a corresponding getter/setter method).
 *  Any attribute in this bean is OPTIONAL so NO VALIDATION IS PERFORMED here.
 *  This class is Serializable (base class is Serializable).
 * </p>
 *
 * <p>
 *  <strong>Thread safety:</strong>
 *  This class contains only mutable fields so therefore it is not thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userClientMapping", propOrder = {"clientId, userId" })
@Entity
@Table(name = "client_user_xref")
public class UserClientMapping extends AuditableEntity {

    /**
     * This field represents the 'clientId' property of the UserClientMapping.
     */
    @Column(name = "client_id")
    private long clientId;

    /**
     *  This field represents the 'userId' property of the UserClientMapping.
     */
    @Column(name = "user_id")
    private long userId;

    /**
     * Default no-arg constructor.
     */
    public UserClientMapping() {
        // do nothing.
    }

    /**
     * Getter for 'clientId' property.
     *
     * @return the value of the 'clientId' property.
     *      It can be any value.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Setter for 'clientId' property.
     *
     * @param clientId
     *      the new clientId to be used for 'clientId' property.
     *      It can be any value.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for 'userId' property.
     *
     * @return the value of the 'userId' property.
     *      It can be any value.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for 'userId' property.
     *
     * @param userId
     *      the new userId to be used for 'userId' property.
     *      It can be any value.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
}