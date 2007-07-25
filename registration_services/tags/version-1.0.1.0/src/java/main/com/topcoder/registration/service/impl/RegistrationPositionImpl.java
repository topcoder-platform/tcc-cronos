/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.topcoder.management.project.Project;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.registration.service.RegistrationPosition;

/**
 * <p>
 * This class is a simple implementation of the <code>RegistrationPosition</code> interface. It
 * implements all methods in that interface, and provides a default constructor if the user wants to
 * set all values via the setters, and one full constructor to set these values in one go.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread-safe.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public class RegistrationPositionImpl implements RegistrationPosition {

    /**
     * <p>
     * Represents a project that is active in the registration phase. This value can be set in the
     * full constructor or in the bean setter method, and retrieved in the bean getter method. Once
     * set, the value will never be null.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Represents the details of the registration phase for the project in this registration
     * position. This value can be set in the full constructor or in the bean setter method, and
     * retrieved in the bean getter method. Once set, the value will never be null.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * Represents the available roles in the project in this registration position. This value can
     * be set in the full constructor or in the bean setter method, and retrieved in the bean getter
     * method. This value will never be null. It will be empty before it is set, and may be empty
     * after having set also, indicating that there are no roles available for this project.
     * However, once set, it will never have null elements in the array.
     * </p>
     */
    private ResourceRole[] availableRoles;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public RegistrationPositionImpl() {
        this.phase = null;
        this.project = null;
        this.availableRoles = new ResourceRole[0];
    }

    /**
     * <p>
     * Full constructor.
     * </p>
     * <p>
     * This convenience constructor allows for setting all values in one go.
     * </p>
     * @param project
     *            a project that is active in the registration phase
     * @param phase
     *            the details of the registration phase for the project in this registration
     *            position
     * @param availableRoles
     *            the available roles in the project in this registration position
     * @throws IllegalArgumentException
     *             If any parameter is null, or availableRoles contains null elements
     */
    public RegistrationPositionImpl(Project project, Phase phase, ResourceRole[] availableRoles) {
        Util.checkObjNotNull(project, "project");
        Util.checkObjNotNull(phase, "phase");
        Util.checkArrrayNullOrHasNull(availableRoles, "availableRoles");

        this.phase = phase;
        this.project = project;
        this.availableRoles = (ResourceRole[]) availableRoles.clone();
    }

    /**
     * <p>
     * Gets the project.
     * </p>
     * <p>
     * Can be null if not yet set.
     * </p>
     * @return the project associated with this registration position
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * <p>
     * Sets the project.
     * </p>
     * @param project
     *            the project associated with this registration position
     * @throws IllegalArgumentException
     *             if project is null
     */
    public void setProject(Project project) {
        Util.checkObjNotNull(project, "project");
        this.project = project;
    }

    /**
     * <p>
     * Gets the phase.
     * </p>
     * <p>
     * Can be null if not yet set.
     * </p>
     * @return The phase associated with this registration position
     */
    public Phase getPhase() {
        return this.phase;
    }

    /**
     * <p>
     * Sets the phase.
     * </p>
     * @param phase
     *            the phase associated with this registration position
     * @throws IllegalArgumentException
     *             if phase is null
     */
    public void setPhase(Phase phase) {
        Util.checkObjNotNull(phase, "phase");
        this.phase = phase;
    }

    /**
     * <p>
     * Gets the available roles in this registration position.
     * </p>
     * <p>
     * Can be empty if either not yet set or if no roles exist for this position registration.
     * </p>
     * @return the project associated with this registration position
     */
    public ResourceRole[] getAvailableRoles() {
        return (ResourceRole[]) availableRoles.clone();
    }

    /**
     * <p>
     * Sets the availableRoles.
     * </p>
     * @param availableRoles
     *            the roles available in this registration position
     * @throws IllegalArgumentException
     *             if availableRoles is null or contains null elements
     */
    public void setAvailableRoles(ResourceRole[] availableRoles) {
        Util.checkArrrayNullOrHasNull(availableRoles, "availableRoles");
        this.availableRoles = (ResourceRole[]) availableRoles.clone();
    }
}
