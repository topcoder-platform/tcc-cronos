/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;

/**
 * <p>
 * This data object represents a competition and is a dummy class whose only properties are an ID and a parent project.
 * Future components may design and use sub-classes of this class.
 * </p>
 * <p>
 * We simply define two properties and getter/setters for these properties. Note that this is a dumb DTO - no validation
 * is done. We also override hash code and equality testing to allow this object to be used in hash sets.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class Competition implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 6507810893310812187L;

    /**
     * <p>
     * Represents the ID of this competition.
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence. This variable is mutable and is retrieved by the {@link #getCompetitionId()}
     * method and set by the {@link #setCompetitionId(Long)} method. It is initialized to null, and may be set to ANY
     * value.
     * </p>
     */
    private Long competitionId;

    /**
     * <p>
     * Represents the project to which this competition belongs.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getProject()} method and set by the
     * {@link #setProject(Project)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Constructs a <code>Competition</code> instance.
     * </p>
     */
    public Competition() {
    }

    /**
     * <p>
     * Gets the ID of the competition.
     * </p>
     *
     * @return The ID of the competition.
     */
    public Long getCompetitionId() {
        return competitionId;
    }

    /**
     * <p>
     * Sets the ID of this competition.
     * </p>
     *
     * @param competitionId
     *            The desired ID of this competition. ANY value.
     */
    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    /**
     * <p>
     * Gets the project to which the competition belongs.
     * </p>
     *
     * @return The project to which the competition belongs.
     */
    public Project getProject() {
        return project;
    }

    /**
     * <p>
     * Sets the project to which this competition belongs.
     * </p>
     *
     * @param project
     *            The desired project to which this competition should belong.
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * <p>
     * Computes the hash code for this competition, in accordance with the equality test.
     * </p>
     *
     * @return the hash code for this competition, in accordance with the equality test.
     */
    public int hashCode() {
        return null == competitionId ? super.hashCode() : competitionId.hashCode();
    }

    /**
     * <p>
     * Compares whether this object is equal to the given object.
     * </p>
     *
     * @param obj
     *            The object to test equality with. Possibly null.
     * @return Whether this object is equal to the given object.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Competition)) {
            return false;
        }

        Competition competition = (Competition) obj;
        if (null == competitionId || null == competition.getCompetitionId()) {
            return this == obj;
        }

        return competitionId.equals(competition.getCompetitionId());
    }
}
