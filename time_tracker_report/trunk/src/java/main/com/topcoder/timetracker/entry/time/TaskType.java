/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This is a bean class that indicates a type of task for which a Time Entry may be submitted.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class TaskType extends TimeTrackerBean {

    /**
     * <p>
     * This is a short String that describes the task type.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This indicates whether the TaskType is active or not.
     * </p>
     */
    private boolean active;

    /**
     * <p>
     * This is the id of the company with which the TaskType is registered with. This represents the
     * company that acknowledges the given TaskType.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public TaskType() {
        // empty
    }

    /**
     * <p>
     * Gets a short String that describes the task type.
     * </p>
     *
     * @return The description of the tasktype.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets a short String that describes the task type.
     * </p>
     *
     * @param description The description of the tasktype.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * This indicates whether the TaskType is active or not.
     * </p>
     *
     * @return whether the TaskType is active or not.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * <p>
     * This indicates whether the TaskType is active or not.
     * </p>
     *
     * @param active whether the TaskType is active or not.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * <p>
     * This indicates whether the TaskType is active or not.
     * </p>
     *
     * @return whether the TaskType is active or not.
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * <p>
     * Gets the id of the company with which the TaskType is registered with. This represents the
     * company that acknowledges the given TaskType.
     * </p>
     *
     * @return the id of the company with which the TaskType is registered with.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Setsthe id of the company with which the TaskType is registered with. This represents the
     * company that acknowledges the given TaskType.
     * </p>
     *
     * @param companyId the id of the company with which the TaskType is registered with.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
