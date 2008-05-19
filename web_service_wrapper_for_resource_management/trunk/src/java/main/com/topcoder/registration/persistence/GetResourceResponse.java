/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * This class represents the response of <code>getResource()</code> method.
 * </p>
 *
 * <p>
 * It is used automatically to wrap the response of the web services and it contains the result of
 * the related operation, for this reason the checking is not performed.
 * </p>
 *
 * <p>
 * The reason to introduce it is to use a field declared as <code>CopiedResource</code> type to override
 * the <code>Resource</code> type declared by the <code>getResource()</code> method.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @see CopiedResource
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getResourceResponse")
public class GetResourceResponse {

    /**
     * <p>
     * Represents the resource of the response.
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
     * Valid Values: all values.
     * </p>
     */
    private CopiedResource resource;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public GetResourceResponse() {
        //empty
    }

    /**
     * <p>
     * Gets the value of the <code>resource</code> property.
     * </p>
     *
     * @return the value of the <code>resource</code> property.
     */
    public CopiedResource getResource() {
        return resource;
    }

    /**
     * <p>
     * Sets the value of the <code>resource</code> property.
     * </p>
     *
     * @param resource the value of the <code>resource</code> property to set.
     */
    public void setResource(CopiedResource resource) {
        this.resource = resource;
    }
}
