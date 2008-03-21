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
 * This class represents the "user_not_authorized_fault" element in WSDL. It's contained in the related
 * <code>UserNotAuthorizedException</code> class.
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
 *         &lt;element name=&quot;user_not_authorized&quot;
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
@XmlType(name = "", propOrder = {"userIdNotAuthorized"})
@XmlRootElement(name = "user_not_authorized_fault")
public class UserNotAuthorizedFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2845106480040387301L;
    /**
     * <p>
     * Represents the user id not authorized.
     * </p>
     */
    @XmlElement(name = "user_not_authorized", required = true)
    private long userIdNotAuthorized;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public UserNotAuthorizedFault() {
    }

    /**
     * <p>
     * Gets the user id not authorized.
     * </p>
     *
     * @return the user id not authorized. 0 will return by default.
     */
    public long getUserIdNotAuthorized() {
        return userIdNotAuthorized;
    }

    /**
     * <p>
     * Sets the user id not authorized.
     * </p>
     *
     * @param userIdNotAuthorized
     *            the user id not authorized, any value is valid.
     */
    public void setUserIdNotAuthorized(long userIdNotAuthorized) {
        this.userIdNotAuthorized = userIdNotAuthorized;
    }
}
