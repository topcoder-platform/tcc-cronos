/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.List;

import com.topcoder.clients.model.Project;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * Represents a type of Result that also provides the cockpit and billing
 * projects associated with the user. Returned in the provisionUser method of
 * the service.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ProvisionUserResult extends Result {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 5238878732580322794L;

    /**
     * <p>
     * Represents the list of cockpit projects provisioned to the user.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter
     * </p>
     */
    private List<ProjectData> cockpitProjects;

    /**
     * <p>
     * Represents the list of billing projects provisioned to the user.
     * </p>
     * <p>
     * It is set in the setter. It can be retrieved in the getter
     * </p>
     */
    private List<Project> billingProjects;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public ProvisionUserResult() {
    }

    /**
     * <p>
     * Gets the list of cockpit projects provisioned to the user.
     * </p>
     *
     * @return the list of cockpit projects provisioned to the user
     */
    public List<ProjectData> getCockpitProjects() {
        return cockpitProjects;
    }

    /**
     * <p>
     * Sets the list of cockpit projects provisioned to the user.
     * </p>
     *
     * @param cockpitProjects
     *            the list of cockpit projects provisioned to the user
     */
    public void setCockpitProjects(List<ProjectData> cockpitProjects) {
        this.cockpitProjects = cockpitProjects;
    }

    /**
     * <p>
     * Gets the list of billing projects provisioned to the user.
     * </p>
     *
     * @return the list of billing projects provisioned to the user
     */
    public List<Project> getBillingProjects() {
        return billingProjects;
    }

    /**
     * <p>
     * Sets the list of billing projects provisioned to the user.
     * </p>
     *
     * @param billingProjects
     *            the list of billing projects provisioned to the user
     */
    public void setBillingProjects(List<Project> billingProjects) {
        this.billingProjects = billingProjects;
    }
}
