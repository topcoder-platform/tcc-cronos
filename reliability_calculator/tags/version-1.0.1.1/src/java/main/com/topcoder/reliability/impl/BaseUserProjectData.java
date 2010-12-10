/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl;

import java.util.Date;

/**
 * <p>
 * This is a base class for UserProjectReliabilityData and UserProjectParticipationData. It holds fields that are
 * common for both entities: project ID, user ID and resolution date. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public abstract class BaseUserProjectData {
    /**
     * The ID of the project. Can be any value. Has getter and setter.
     */
    private long projectId;

    /**
     * The ID of the user. Can be any value. Has getter and setter.
     */
    private long userId;

    /**
     * The resolution date. Represents the earliest date/time when it became clear whether the user provided a passing
     * submission for the project (i.e. user was reliable in this project). Can be any value. Has getter and setter.
     */
    private Date resolutionDate;

    /**
     * <p>
     * Creates an instance of BaseUserProjectData.
     * </p>
     */
    protected BaseUserProjectData() {
        // Empty
    }

    /**
     * <p>
     * Gets the ID of the project.
     * </p>
     *
     * @return the ID of the project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of the project.
     * </p>
     *
     * @param projectId
     *            the ID of the project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the ID of the user.
     * </p>
     *
     * @return the ID of the user.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets the ID of the user.
     * </p>
     *
     * @param userId
     *            the ID of the user.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * <p>
     * Gets the resolution date.
     * </p>
     *
     * @return the resolution date.
     */
    public Date getResolutionDate() {
        return resolutionDate;
    }

    /**
     * <p>
     * Sets the resolution date.
     * </p>
     *
     * @param resolutionDate
     *            the resolution date.
     */
    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }
}
