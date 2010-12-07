/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

/**
 * <p>
 * This class is a container for information about user's reliability corresponding to one project in which this user
 * has participated. It is a simple JavaBean (POJO) that provides getters and setters for all private attributes and
 * performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public class UserProjectReliabilityData extends BaseUserProjectData {
    /**
     * <p>
     * The reliability of the user before the resolution date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Double reliabilityBeforeResolution;

    /**
     * <p>
     * The reliability of the user after the resolution date.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private double reliabilityAfterResolution;

    /**
     * <p>
     * The reliability of the user at the moment when the user registered for this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private Double reliabilityOnRegistration;

    /**
     * <p>
     * The value indicating whether the user was reliable in this project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private boolean reliable;

    /**
     * <p>
     * Creates an instance of UserProjectReliabilityData.
     * </p>
     */
    public UserProjectReliabilityData() {
        // Empty
    }

    /**
     * <p>
     * Gets the reliability of the user before the resolution date.
     * </p>
     *
     * @return the reliability of the user before the resolution date.
     */
    public Double getReliabilityBeforeResolution() {
        return reliabilityBeforeResolution;
    }

    /**
     * <p>
     * Sets the reliability of the user before the resolution date.
     * </p>
     *
     * @param reliabilityBeforeResolution
     *            the reliability of the user before the resolution date.
     */
    public void setReliabilityBeforeResolution(Double reliabilityBeforeResolution) {
        this.reliabilityBeforeResolution = reliabilityBeforeResolution;
    }

    /**
     * <p>
     * Gets the reliability of the user after the resolution date.
     * </p>
     *
     * @return the reliability of the user after the resolution date.
     */
    public double getReliabilityAfterResolution() {
        return reliabilityAfterResolution;
    }

    /**
     * <p>
     * Sets the reliability of the user after the resolution date.
     * </p>
     *
     * @param reliabilityAfterResolution
     *            the reliability of the user after the resolution date.
     */
    public void setReliabilityAfterResolution(double reliabilityAfterResolution) {
        this.reliabilityAfterResolution = reliabilityAfterResolution;
    }

    /**
     * <p>
     * Gets the reliability of the user at the moment when the user registered for this project.
     * </p>
     *
     * @return the reliability of the user at the moment when the user registered for this project.
     */
    public Double getReliabilityOnRegistration() {
        return reliabilityOnRegistration;
    }

    /**
     * <p>
     * Sets the reliability of the user at the moment when the user registered for this project.
     * </p>
     *
     *@param reliabilityOnRegistration
     *            the reliability of the user at the moment when the user registered for this project.
     */
    public void setReliabilityOnRegistration(Double reliabilityOnRegistration) {
        this.reliabilityOnRegistration = reliabilityOnRegistration;
    }

    /**
     * <p>
     * Gets the value indicating whether the user was reliable in this project.
     * </p>
     *
     * @return the value indicating whether the user was reliable in this project.
     */
    public boolean isReliable() {
        return reliable;
    }

    /**
     * <p>
     * Sets the value indicating whether the user was reliable in this project.
     * </p>
     *
     * @param reliable
     *            the value indicating whether the user was reliable in this project.
     */
    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }
}
