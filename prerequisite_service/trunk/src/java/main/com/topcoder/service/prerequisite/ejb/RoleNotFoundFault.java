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
 * This class represents the "role_not_found_fault" element in WSDL. It's contained in the related
 * <code>RoleNotFoundException</code> class, it's the fault bean.
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
 *         &lt;element name=&quot;role_not_found&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}long&quot;/&gt;
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
@XmlType(name = "", propOrder = {"roleIdNotFound"})
@XmlRootElement(name = "role_not_found_fault")
public class RoleNotFoundFault implements Serializable {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -6441027975947975489L;
    /**
     * <p>
     * Represents the role id not found.
     * </p>
     */
    @XmlElement(name = "role_not_found", required = true)
    private long roleIdNotFound;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public RoleNotFoundFault() {
    }

    /**
     * <p>
     * Gets the role id not found.
     * </p>
     *
     * @return the role id not found. 0 will return by default.
     */
    public long getRoleIdNotFound() {
        return roleIdNotFound;
    }

    /**
     * <p>
     * Sets the role id not found.
     * </p>
     *
     * @param roleIdNotFound
     *            the role id not found, any value can be set.
     */
    public void setRoleIdNotFound(long roleIdNotFound) {
        this.roleIdNotFound = roleIdNotFound;
    }
}
