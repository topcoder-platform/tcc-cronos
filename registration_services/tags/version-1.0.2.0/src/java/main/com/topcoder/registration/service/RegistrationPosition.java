/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.topcoder.management.project.Project;
import com.topcoder.management.resource.ResourceRole;
import java.io.Serializable;
import com.topcoder.project.phases.Phase;

/**
 * <p>
 * The interface that defines the information about the available positions/roles in a project
 * during registration. It also provides detailed information about the registration phase of this
 * project, such as its start and end date.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining setters and getters for these
 * properties.
 * </p>
 * <p>
 * It has one implementation: RegistrationPositionImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RegistrationPosition extends Serializable {

    /**
     * <p>
     * Gets the project. Can be null if not yet set.
     * </p>
     * @return The project associated with this registration position
     */
    public Project getProject();

    /**
     * <p>
     * Sets the project.
     * </p>
     * @param project
     *            The project associated with this registration position
     * @throws IllegalArgumentException
     *             If project is null
     */
    public void setProject(Project project);

    /**
     * <p>
     * Gets the phase. Can be null if not yet set.
     * </p>
     * @return The phase associated with this registration position
     */
    public Phase getPhase();

    /**
     * <p>
     * Sets the phase.
     * </p>
     * @param phase
     *            The phase associated with this registration position
     * @throws IllegalArgumentException
     *             If phase is null
     */
    public void setPhase(Phase phase);

    /**
     * <p>
     * Gets the available roles in this registration position. Can be empty if either not yet set or
     * if no roles exist for this position registration.
     * </p>
     * @return The project associated with this registration position
     */
    public ResourceRole[] getAvailableRoles();

    /**
     * <p>
     * Sets the <code>availableRoles</code>.
     * </p>
     * @param availableRoles
     *            The roles available in this registration position
     * @throws IllegalArgumentException
     *             If availableRoles is null or contains null elements
     */
    public void setAvailableRoles(ResourceRole[] availableRoles);
}
