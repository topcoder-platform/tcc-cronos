/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * This class represents the request of <code>updateResources()</code> method.
 * </p>
 *
 * <p>
 * It will be automatically used by the web services to handle the request of the related operation,
 * for this reason the checking is not performed.
 * </p>
 *
 * <p>
 * The reason to introduce it is to use a field declared as <code>CopiedResource</code> type to override
 * the <code>Resource</code> type declared by the <code>updateResources()</code> method.
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
@XmlRootElement(name = "updateResourcesRequest")
public class UpdateResourcesRequest {

    /**
     * <p>
     * Represents the resources of the request.
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
    private CopiedResource[] resources;

    /**
     * <p>
     * Represents the project of the request.
     * </p>
     * <p>
     * Initial Value: 0, means that is not set.
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
    private long project;

    /**
     * <p>
     * Represents the operator of the request.
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
    private String operator;

    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public UpdateResourcesRequest() {
        //empty
    }

    /**
     * <p>
     * Gets the value of the <code>resources</code> property.
     * </p>
     *
     * @return the value of the <code>resources</code> property.
     */
    public CopiedResource[] getResources() {
        return resources;
    }

    /**
     * <p>
     * Sets the value of the <code>resources</code> property.
     * </p>
     *
     * @param resources the value of the <code>resources</code> property to set.
     */
    public void setResources(CopiedResource[] resources) {
        this.resources = resources;
    }

    /**
     * <p>
     * Gets the value of the <code>project</code> property.
     * </p>
     *
     * @return the value of the <code>project</code> property.
     */
    public long getProject() {
        return project;
    }

    /**
     * <p>
     * Sets the value of the <code>project</code> property.
     * </p>
     *
     * @param project the value of the <code>project</code> property to set.
     */
    public void setProject(long project) {
        this.project = project;
    }

    /**
     * <p>
     * Gets the value of the <code>operator</code> property.
     * </p>
     *
     * @return the value of the <code>operator</code> property.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * <p>
     * Sets the value of the <code>operator</code> property.
     * </p>
     *
     * @param operator the value of the <code>operator</code> property to set.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
