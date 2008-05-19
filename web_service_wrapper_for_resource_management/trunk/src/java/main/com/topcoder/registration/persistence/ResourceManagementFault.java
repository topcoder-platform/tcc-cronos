/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * This class represents the fault bean of the related <code>ResourceManagementException</code>
 * exception. The purpose to introduce it is to avoid an issue about JBossWS and Base Exception
 * component.
 * </p>
 *
 * <p>
 * The <code>BaseException</code> is not suitable to be used with JBossWS, because it has a getter
 * <code>getInformation()</code> but there is no corresponding <code>information</code> field.
 * </p>
 *
 * <p>
 * By introducing the fault bean, the JBossWS will instead use the fault bean to marshall/unmarshall
 * the exception and thus the problem about <code>BaseException</code> is avoided.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "stackTrace" })
@XmlRootElement(name = "resourceManagementFault")
public class ResourceManagementFault {

    /**
     * <p>
     * Represents the exception stack trace.
     * </p>
     * <p>
     * Initial Value: null, means that is not set.
     * </p>
     * <p>
     * Accessed In: getter method.
     * </p>
     * <p>
     * Modified In: setter method.
     * </p>
     * <p>
     * Utilized In: none.
     * </p>
     * <p>
     * Valid Values: all values, can be null, can be empty.
     * </p>
     * <p>
     * annotations: "@XmlElement(name = "stackTrace", required = true)"
     * </p>
     */
    @XmlElement(name = "stackTrace", required = true)
    private String stackTrace;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public ResourceManagementFault() {
        //empty
    }

    /**
     * <p>
     * Gets the value of the <code>stackTrace</code> property.
     * </p>
     *
     * @return the value of the <code>stackTrace</code> property.
     */
    public String getStackTrace() {
        return this.stackTrace;
    }

    /**
     * <p>
     * Sets the value of the <code>stackTrace</code> property. Any string value
     * is valid.
     * </p>
     *
     * @param stackTrace the value of the <code>stackTrace</code> property to set.
     */
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
