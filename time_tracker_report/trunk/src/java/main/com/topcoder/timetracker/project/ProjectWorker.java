/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.project;

import com.topcoder.timetracker.common.TimeTrackerBean;
import java.util.Date;

/**
 * <p>
 * This class holds the information of a projec worker. No parameter checking is provided in this
 * class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class ProjectWorker extends TimeTrackerBean {

    /**
     * <p>
     * This is the date when the user started work (or is scheduled to start work).
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * This is the date when the user ended work (or is scheduled to end work).
     */
    private Date endDate;

    /**
     * <p>
     * This is the rate of pay for the worker. It represents the amount that can be billed for each
     * hour that is spent at work.
     * </p>
     */
    private double payRate;

    /**
     * <p>
     * This is an identifier that is used to represent the the project that this user is working on.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * This is a number that uniquely identifies the user that is working on the project. It is used
     * to tie in with the Time Tracker User component.
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
        return this.startDate;
    }

    /**
     * <p>
     * Sets the date when the user started work (or is scheduled to start work).
     * </p>
     *
     * @param startDate the date when the user started work (or is scheduled to start work).
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Gets the date when the user ended work (or is scheduled to end work).
     * </p>
     *
     * @return the date when the user ended work (or is scheduled to end work).
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * <p>
     * Sets the date when the user ended work (or is scheduled to end work).
     * </p>
     *
     * @param endDate the date when the user ended work (or is scheduled to end work).
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>
     * Gets the rate of pay for the worker. It represents the amount that can be billed for each
     * hour that is spent at work.
     * </p>
     *
     * @return the rate of pay for the worker.
     */
    public double getPayRate() {
        return this.payRate;
    }

    /**
     * <p>
     * Sets the rate of pay for the worker. It represents the amount that can be billed for each
     * hour that is spent at work.
     * </p>
     *
     * @param payRate the rate of pay for the worker.
     */
    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    /**
     * <p>
     * Gets an identifier that is used to represent the project that this user is working on.
     * </p>
     *
     * @return an identifier that is used to represent the the project that this user is working on.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets an identifier that is used to represent the project that this user is working on.
     * </p>
     *
     * @param projectId an identifier that is used to represent the the project that this user is
     *        working on.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets a number that uniquely identifies the user that is working on the project. It is used to
     * tie in with the Time Tracker User component.
     * </p>
     *
     * @return a number that uniquely identifies the user that is working on the project.
     */
    public long getUserId() {
        return this.userId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the user that is working on the project. It is used to
     * tie in with the Time Tracker User component.
     * </p>
     *
     * @param userId a number that uniquely identifies the user that is working on the project.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
