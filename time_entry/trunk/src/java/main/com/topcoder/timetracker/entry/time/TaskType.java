/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.time.db.Util;

/**
 * <p>
 * This is a bean class that indicates a type of task for which a Time Entry may be submitted.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance,
 * or this class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TaskType extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -1983282473653698422L;

    /**
     * <p>
     * This is a short String that describes the task type.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null or empty string after it is set.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This indicates whether the <code>TaskType</code> is active or not.
     * </p>
     *
     * <p>
     * It is false initially and can be accessed by getter and setter.
     * </p>
     */
    private boolean active;

    /**
     * <p>
     * This is the id of the company with which the <code>TaskType</code> is registered with.
     * </p>
     *
     * <p>
     * This represents the company that acknowledges the given <code>TaskType</code>.
     * </p>
     *
     * <p>
     * It is <code>-1</code> initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be negative after it is set.
     * </p>
     */
    private long companyId = -1;

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
     * @return The description of the task type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets a short String that describes the task type.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param description The description of the task type.
     *
     * @throws IllegalArgumentException if description is a null or empty String.
     */
    public void setDescription(String description) {
        Util.checkString(description, "description");

        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }

    /**
     * <p>
     * This indicates whether the <code>TaskType</code> is active or not.
     * </p>
     *
     * @return whether the <code>TaskType</code> is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * This indicates whether the <code>TaskType</code> is active or not.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param active  whether the <code>TaskType</code> is active or not.
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }

    /**
     * <p>
     * This indicates whether the <code>TaskType</code>  is active or not.
     * </p>
     *
     * @return whether the <code>TaskType</code> is active or not.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * <p>
     * Gets the id of the company with which the <code>TaskType</code> is registered with.
     * </p>
     *
     * <p>
     * This represents the company that acknowledges the given <code>TaskType</code>.
     * </p>
     *
     * @return the id of the company with which the <code>TaskType</code> is registered with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which the <code>TaskType</code> is registered with.
     * </p>
     *
     * <p>
     * This represents the company that acknowledges the given <code>TaskType</code>.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param companyId the id of the company with which the <code>TaskType</code> is registered with.
     *
     * @throws IllegalArgumentException if companyId is &lt; 0.
     */
    public void setCompanyId(long companyId) {
        Util.checkIdValue(companyId, "companyId");

        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }
}
