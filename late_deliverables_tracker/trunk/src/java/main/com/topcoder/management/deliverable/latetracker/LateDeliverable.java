/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.project.Project;
import com.topcoder.project.phases.Phase;

/**
 * <p>
 * This class is a container for information about a single late deliverable. It is a
 * simple JavaBean (POJO) that provides getters and setters for all private attributes and
 * performs no argument validation in the setters.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public class LateDeliverable {
    /**
     * <p>
     * The deliverable that is late.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private Deliverable deliverable;

    /**
     * <p>
     * The phase for this late deliverable.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * The project for this late deliverable.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Has getter and setter.
     * </p>
     */
    private Project project;

    /**
     * Creates an instance of <code>LateDeliverable</code>.
     */
    public LateDeliverable() {
    }

    /**
     * Retrieves the deliverable that is late.
     *
     * @return the deliverable that is late.
     */
    public Deliverable getDeliverable() {
        return deliverable;
    }

    /**
     * Sets the deliverable that is late.
     *
     * @param deliverable
     *            the deliverable that is late.
     */
    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }

    /**
     * Retrieves the phase for this late deliverable.
     *
     * @return the phase for this late deliverable.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Sets the phase for this late deliverable.
     *
     * @param phase
     *            the phase for this late deliverable.
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * Retrieves the project for this late deliverable.
     *
     * @return the project for this late deliverable.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project for this late deliverable.
     *
     * @param project
     *            the project for this late deliverable.
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
