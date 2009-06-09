package com.topcoder.service.permission;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the "permission_service_fault" element in WSDL. It's contained in the related exception-message
 * class. It was generated by JBoss tools and I changed the name and other little changes.
 * </p>
 * 
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 * 
 * @author TCSASSEMBLER
 * @since Cockpit Project Admin Release Assembly v1.0
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "faultMessage" })
@XmlRootElement(name = "permission_service_fault")
public class PermissionServiceFault implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Represents the persistence message
     * </p>
     */
    @XmlElement(name = "fault_message", required = true)
    private String faultMessage;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public PermissionServiceFault() {
    }

    /**
     * <p>
     * Gets the value of the persistenceMessage property.
     * </p>
     * 
     * @return the value of the persistenceMessage property.
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * <p>
     * Sets the value of the persistenceMessage property.
     * </p>
     * 
     * @param faultMessage
     *            the value of the faultMessage property to set, can be null, can be empty
     */
    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }
}
