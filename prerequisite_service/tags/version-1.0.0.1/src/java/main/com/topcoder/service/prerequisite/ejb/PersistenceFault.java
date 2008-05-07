/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the "persistence_fault" element in WSDL. It's contained in the related
 * <code>PersistenceException</code> class.
 * </p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * </p>
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;persistence_message&quot;
 *             type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"persistenceMessage"})
@XmlRootElement(name = "persistence_fault")
public class PersistenceFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 457251793112594363L;
    /**
     * <p>
     * Represents the persistence message.
     * </p>
     */
    @XmlElement(name = "persistence_message", required = true)
    private String persistenceMessage;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public PersistenceFault() {
    }

    /**
     * <p>
     * Gets the persistence message.
     * </p>
     *
     * @return the persistence message. Null will return by default.
     */
    public String getPersistenceMessage() {
        return persistenceMessage;
    }

    /**
     * <p>
     * Sets the persistence message.
     * </p>
     *
     * @param persistenceMessage
     *            the persistence message, can be null, can be empty
     */
    public void setPersistenceMessage(String persistenceMessage) {
        this.persistenceMessage = persistenceMessage;
    }
}
