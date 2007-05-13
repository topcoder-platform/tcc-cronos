/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.time.db.Util;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to
 * access the various properties of a Time Entry, ATime Entry is an amount of time spent
 * by a user on a specific task.
 * </p>
 *
 * <p>
 * This class extends <code>BaseEntry</code> to retrieve the properties common to a
 * Time Tracker Entry.
 * </p>
 *
 * <p>
 * It also inherits from the base <code>TimeTrackerBean</code> to include the id, creation
 * and modification details.
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
public class TimeEntry extends BaseEntry {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -5585756901780113582L;

	/**
     * <p>
     * This is the amount of hours that have been spent working on a task.
     * </p>
     *
     * <p>
     * It is initialized to <code>0.0</code> at first and can be accessed by getter and setter.
     * <p>
     *
     * <p>
     * The valid value for this variable is number &gt;= 0.
     * </p>
     */
    private double hours = 0.0;

    /**
     * <p>
     * This indicates whether the <code>TimeEntry</code> can be billed to the client.
     * </p>
     *
     * <p>
     * It is false initially and can be accessed by getter and setter.
     * </p>
     */
    private boolean billable;

    /**
     * <p>
     * This is the status of the entry, which is an indication of how the TimeEntry is being
     * processed by the Time Tracker system.
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
    private TimeStatus timeStatus;

    /**
     * <p>
     * This indicates the type of task that was being performed by this <code>TimeEntry</code>.
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
    private TaskType taskType;

    /**
     * <p>
     * This is the Time Tracker Invoice id which is associated with this <code>TimeEntry</code>(if any).
     * </p>
     *
     * <p>
     * It is <code>-1</code> initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * The value for this variable must be &gt;= -1.
     * </p>
     */
    private long invoiceId = -1;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public TimeEntry() {
        // empty
    }

    /**
     * <p>
     * Indicates whether the <code>TimeEntry</code> can be billed to the client.
     * </p>
     *
     * @return whether the <code>TimeEntry</code> can be billed to the client.
     */
    public boolean isBillable() {
        return billable;
    }

    /**
     * <p>
     * Indicates whether the <code>TimeEntry</code> can be billed to the client.
     * </p>
     *
     * @return  whether the <code>TimeEntry</code> can be billed to the client.
     */
    public boolean getBillable() {
        return this.billable;
    }

    /**
     * <p>
     * Sets whether the <code>TimeEntry</code> can be billed to the client.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param billable whether the <code>TimeEntry</code> can be billed to the client.
     */
    public void setBillable(boolean billable) {
        if (billable != this.billable) {
            this.billable = billable;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the status of the entry, which is an indication of how the <code>TimeEntry</code> is being
     * processed by the Time Tracker system.
     * </p>
     *
     * @return the status of the entry
     */
    public TimeStatus getStatus() {
        return timeStatus;
    }

    /**
     * <p>
     * Sets the status of the entry, which is an indication of how the <code>TimeEntry</code> is being
     * processed by the Time Tracker system.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param timeStatus the status of the entry
     *
     * @throws IllegalArgumentException if status is null.
     */
    public void setStatus(TimeStatus timeStatus) {
        Util.checkNull(timeStatus, "timeStatus");

        if (timeStatus != this.timeStatus) {
            this.timeStatus = timeStatus;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the type of task that was being performed by this <code>TimeEntry</code>.
     * </p>
     *
     * @return the type of task that was being performed by this <code>TimeEntry</code>.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * <p>
     * Sets the type of task that was being performed by this <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param taskType the type of task that was being performed by this <code>TimeEntry</code>.
     *
     * @throws IllegalArgumentException if taskType is null.
     */
    public void setTaskType(TaskType taskType) {
        Util.checkNull(taskType, "taskType");

        if (taskType != this.taskType) {
            this.taskType = taskType;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the Time Tracker Invoice id which is associated with this <code>TimeEntry</code>(if any).
     * </p>
     *
     * @return the Time Tracker Invoice id which is associated with this <code>TimeEntry</code> (if any).
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * <p>
     * Sets is the Time Tracker Invoice id which is associated with this <code>TimeEntry</code> (if any).
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param invoiceId the Time Tracker Invoice id which is associated with
     * this <code>TimeEntry</code> (if any).
     *
     * @throws IllegalArgumentException if the invoice id is &lt; -1
     */
    public void setInvoiceId(long invoiceId) {
        if (invoiceId < -1) {
            throw new IllegalArgumentException("The invoice id is less than -1.");
        }

        if (invoiceId != this.invoiceId) {
            this.invoiceId = invoiceId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the amount of hours that have been spent working on a task.
     * </p>
     *
     * @return the amount of hours that have been spent working on a task.
     */
    public double getHours() {
        return hours;
    }

    /**
     * <p>
     * Sets the amount of hours that have been spent working on a task.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param hours the amount of hours that have been spent working on a task.
     *
     * @throws IllegalArgumentException if hours &lt; 0.
     */
    public void setHours(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("The hours is negative.");
        }

        if (hours != this.hours) {
            this.hours = hours;
            setChanged(true);
        }
    }
}
