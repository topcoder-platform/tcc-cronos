/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is a bean class that represents a Project Manager.
 * </p>
 *
 * <p>
 * A ProjectManager is a user that is responsible for managing the project. Various properties are available,
 * such as the ids of the project and user involved.
 * </p>
 *
 * <p>
 * It also extends from the base TimeTrackerBean to include the creation and modification details.
 * </p>
 *
 * <p>
 * Note that a project may have more than one ProjectManager at any given time.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe. Each thread is expected to work on it's own instance,
 * or this class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManager extends TimeTrackerBean {
    /**
     * <p>
     * This is an identifier that is used to represent the the project that this user is managing.
     * </p>
     *
     * <p>
     * It is -1 initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative or zero after it is set.
     * </p>
     */
    private long projectId = -1;

    /**
     * <p>
     * This is a number that uniquely identifies the user that is managing the project.
     * </p>
     *
     * <p>
     * It is used to tie in with the Time Tracker User component.
     * </p>
     *
     * <p>
     * It is zero initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative or zero after it is set.
     * </p>
     */
    private long userId;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public ProjectManager() {
        // empty
    }

    /**
     * <p>
     * Gets an identifier that is used to represent the project that this user is managing.
     * </p>
     *
     * @return an identifier that is used to represent the project that this user is managing.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets an identifier that is used to represent the project that this user is managing.
     * </p>
     *
     * @param projectId an identifier that is used to represent the project that this user is managing.
     *
     * @throws IllegalArgumentException if projectId is &lt;= 0.
     */
    public void setProjectId(long projectId) {
        Util.checkIdValue(projectId, "project");

        if (projectId != this.projectId) {
            this.projectId = projectId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets a number that uniquely identifies the user that is managing the project.
     * </p>
     *
     * <p>
     * It is used to tie in with the Time Tracker User component.
     * </p>
     *
     * @return a number that uniquely identifies the user that is managing the project.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the user that is managing the project.
     * </p>
     *
     * <p>
     * It is used to tie in with the Time Tracker User component.
     * </p>
     *
     * @param userId a number that uniquely identifies the user that is managing the project.
     *
     * @throws IllegalArgumentException if userId is &lt;= 0.
     */
    public void setUserId(long userId) {
        Util.checkIdValue(userId, "user");

        if (userId != this.userId) {
            this.userId = userId;
            setChanged(true);
        }
    }
}
