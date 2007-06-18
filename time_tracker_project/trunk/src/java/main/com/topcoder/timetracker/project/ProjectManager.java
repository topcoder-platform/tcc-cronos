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
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -6692721786505280962L;

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
     * This is the rate of pay for the worker. It represents the amount that can be billed for each hour that
     * is spent at work.
     * </p>
     *
     * <p>
     * It is -1 initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative after it is set.
     * </p>
     */
    private double payRate = -1.0;

    /**
     * <p>
     * This is the cost of the worker. It represents the amount that can be billed to client for
     * each hour that is spent at work.
     * </p>
     *
     * <p>
     * It is -1 initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative after it is set.
     * </p>
     */
    private double cost = -1.0;

    /**
     * <p>
     * This variable determines whether the manager is active.
     * </p>
     *
     * <p>
     * It is <code>true</code> initially and can be accessed by getter and setter.
     * </p>
     */
    private boolean active = true;

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

    /**
     * <p>
     * Gets the rate of pay for the worker.
     * </p>
     *
     * <p>
     * It represents the amount that can be billed for each hour that is spent at work.
     * </p>
     *
     * @return the rate of pay for the worker.
     */
    public double getPayRate() {
        return payRate;
    }

    /**
     * <p>
     * Sets the rate of pay for the worker.
     * </p>
     *
     * <p>
     * It represents the amount that can be billed for each hour that is spent at work.
     * </p>
     *
     * @param payRate the rate of pay for the worker.
     *
     * @throws IllegalArgumentException if payRate is a negative number.
     */
    public void setPayRate(double payRate) {
        if (payRate < 0) {
            throw new IllegalArgumentException("The given pay rate is negative.");
        }

        if (payRate != this.payRate) {
            this.payRate = payRate;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the cost of the worker.
     * </p>
     *
     * <p>
     * It represents the amount that can be billed to client for each hour that is spent at work.
     * </p>
     *
     * @return the cost of the worker.
     */
    public double getCost() {
        return cost;
    }

    /**
     * <p>
     * Sets the cost of the worker.
     * </p>
     *
     * <p>
     * It represents the amount that can be billed to client for each hour that is spent at work.
     * </p>
     *
     * @param cost the cost of for the worker.
     *
     * @throws IllegalArgumentException if cost is negative.
     */
    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("The given cost is negative.");
        }

        if (cost != this.cost) {
            this.cost = cost;
            setChanged(true);
        }
    }

    /**
     * Returns a <code>boolean</code> value that determines whether the manager is active.
     *
     * @return <code>true</code> if the manager is active, <code>false</code> otherwise.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets new <code>boolean</code> value that determines whether the manager is active.
     *
     * @param active
     *            activeness of the manager.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
