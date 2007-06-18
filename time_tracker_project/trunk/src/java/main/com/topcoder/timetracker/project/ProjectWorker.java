/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.project.db.Util;

import java.util.Date;

/**
 * <p>
 * This is a bean class representing a user that is working on the project.
 * </p>
 *
 * <p>
 * It contains various properties, such as the id of the user who is working on the project,
 * and the id of the project which the user is working on.
 * </p>
 *
 * <p>
 * The pay rate and work timeline of the worker is also available.
 * </p>
 *
 * <p>
 * It also extends from the base <code>TimeTrackerBean</code> to include the  creation and modification details.
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
public class ProjectWorker extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 1250442665954945589L;

    /**
     * <p>
     * This is the date when the user started work (or is scheduled to start work).
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * This is the date when the user ended work (or is scheduled to end work).
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Date endDate;

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
     * This variable determines whether the worker is active.
     * </p>
     *
     * <p>
     * It is <code>true</code> initially and can be accessed by getter and setter.
     * </p>
     */
    private boolean active = true;

    /**
     * <p>
     * This is an identifier that is used to represent the the project that this user is working on.
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
    private long projectId;

    /**
     * <p>
     * This is a number that uniquely identifies the user that is working on the project.
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
    public ProjectWorker() {
        // empty
    }

    /**
     * <p>
     * Gets the date when the user started work (or is scheduled to start work).
     * </p>
     *
     * @return the date when the user started work (or is scheduled to start work).
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Sets the date when the user started work (or is scheduled to start work).
     * </p>
     *
     * @param startDate the date when the user started work (or is scheduled to start work).
     *
     * @throws IllegalArgumentException if startDateis null.
     */
    public void setStartDate(Date startDate) {
        Util.checkNull(startDate, "startDate");

        if (!startDate.equals(this.startDate)) {
            this.startDate = startDate;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the date when the user ended work (or is scheduled to end work).
     * </p>
     *
     * @return the date when the user ended work (or is scheduled to end work).
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Sets the date when the user ended work (or is scheduled to end work).
     * </p>
     *
     * @param endDate the date when the user ended work (or is scheduled to end work).
     *
     * @throws IllegalArgumentException if endDate is null.
     */
    public void setEndDate(Date endDate) {
        Util.checkNull(endDate, "endDate");

        if (!endDate.equals(this.endDate)) {
            this.endDate = endDate;
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
     * Returns a <code>boolean</code> value that determines whether the worker is active.
     *
     * @return <code>true</code> if the worker is active, <code>false</code> otherwise.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets new <code>boolean</code> value that determines whether the worker is active.
     *
     * @param active
     *            activeness of the worker.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * <p>
     * Gets an identifier that is used to represent the project that this user is working on.
     * </p>
     *
     * @return an identifier that is used to represent the the project that this user is working on.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets an identifier that is used to represent the project that this user is working on.
     * </p>
     *
     * @param projectId an identifier that is used to represent the the project that this user is working on.
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
     * Gets a number that uniquely identifies the user that is working on the project.
     * </p>
     *
     * <p>
     * It is used to tie in with the Time Tracker User component.
     * </p>
     *
     * @return a number that uniquely identifies the user that is working on the project.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the user that is working on the project.
     * </p>
     *
     * <p>
     * It is used to tie in with the Time Tracker User component.
     * </p>
     *
     * @param userId a number that uniquely identifies the user that is working on the project.
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