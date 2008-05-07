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
 * This class represents the "user_already_answered_document_fault" element in WSDL. It's contained in the related
 * <code>UserAlreadyAnsweredDocumentException</code> class, it's the fault bean.
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
 *         &lt;element name=&quot;user_already_answered_document&quot;
 *             type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
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
@XmlType(name = "", propOrder = {"userId"})
@XmlRootElement(name = "user_already_answered_document_fault")
public class UserAlreadyAnsweredDocumentFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8227875687192749481L;
    /**
     * <p>
     * Represents the user id.
     * </p>
     */
    @XmlElement(name = "user_already_answered_document", required = true)
    private long userId;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public UserAlreadyAnsweredDocumentFault() {
    }

    /**
     * <p>
     * Gets the user id.
     * </p>
     *
     * @return the user id. 0 will return by default.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets the user id.
     * </p>
     *
     * @param userId
     *            the user id, any value can be set.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
