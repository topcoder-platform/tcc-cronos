/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p> This class represents the "status_not_allowed_fault" element in WSDL. It's contained in the related
 * exception-message class, it's the fault bean. It was generated by JBoss tools and I changed the name and other little
 * changes.</p>
 *
 * <p> This class is not thread safe because it's highly mutable</p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"statusIdNotAllowed"})
@XmlRootElement(name = "status_not_allowed_fault")
public class StatusNotAllowedFault {
    /**
     * <p> Represents the status Not Allowed in the exception. I updated the WSDL, now it's long.</p>
     */
    @XmlElement(name = "status_not_found", required = true)
    private long statusIdNotAllowed;

    /**
     * <p> This is the default constructor. It does nothing.</p>
     */
    public StatusNotAllowedFault() {
    }

    /**
     * <p> Gets the value of the statusIdNotAllowed property.</p>
     *
     * @return the value of the statusIdNotAllowed property.
     */
    public long getStatusIdNotAllowed() {
        return statusIdNotAllowed;
    }

    /**
     * <p> Sets the value of the statusIdNotAllowed property.</p>
     *
     * @param statusIdNotAllowed the value to set
     */
    public void setStatusIdNotAllowed(long statusIdNotAllowed) {
        this.statusIdNotAllowed = statusIdNotAllowed;
    }
}

