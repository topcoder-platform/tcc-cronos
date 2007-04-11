/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.entry.base.BaseEntry;

/**
 * <p>
 * This class holds the information of a time entry. No parameter checking is provided in this
 * class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class TimeEntry extends BaseEntry {

    /**
     * <p>
     * This is the amount of hours that have been spent working on a task.
     * </p>
     */
    private double hours = 0.0;

    /**
     * <p>
     * This indicates whether the TimeEntry can be billed to the client.
     * </p>
     */
    private boolean billable;

    /**
     * <p>
     * This is the status of the entry, which is an indication of how the TimeEntry is being
     * processed by the Time Tracker system..
     * </p>
     */
    private TimeStatus timeStatus;

    /**
     * <p>
     * This indicates the type of task that was being performed by this TimeEntry.
     * </p>
     */
    private TaskType taskType;

    /**
     * <p>
     * This is the Time Tracker Invoice which is associated with this TimeEntry (if any).
     * </p>
     */
    // private com.topcoder.timetracker.invoice.Invoice invoice;
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
     * Indicates whether the TimeEntry can be billed to the client.
     * </p>
     *
     * @return whether the TimeEntry can be billed to the client.
     */
    public boolean isBillable() {
        return this.billable;
    }

    /**
     * <p>
     * Indicates whether the TimeEntry can be billed to the client.
     * </p>
     *
     * @return whether the TimeEntry can be billed to the client.
     */
    public boolean getBillable() {
        return this.billable;
    }

    /**
     * <p>
     * Sets whether the TimeEntry can be billed to the client.
     * </p>
     *
     * @param billable whether the TimeEntry can be billed to the client.
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * <p>
     * Gets the status of the entry, which is an indication of how the TimeEntry is being processed
     * by the Time Tracker system..
     * </p>
     *
     * @return the status of the entry
     */
    public TimeStatus getStatus() {
        return this.timeStatus;
    }

    /**
     * <p>
     * Sets the status of the entry, which is an indication of how the TimeEntry is being processed
     * by the Time Tracker system..
     * </p>
     *
     * @param timeStatus the status of the entry
     */
    public void setStatus(TimeStatus timeStatus) {
        this.timeStatus = timeStatus;
    }

    /**
     * <p>
     * Gets the type of task that was being performed by this TimeEntry.
     * </p>
     *
     * @return the type of task that was being performed by this TimeEntry.
     */
    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * <p>
     * Sets the type of task that was being performed by this TimeEntry.
     * </p>
     *
     * @param taskType the type of task that was being performed by this TimeEntry.
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * <p>
     * Gets the Time Tracker Invoice which is associated with this TimeEntry (if any).
     * </p>
     *
     * @return the Time Tracker Invoice which is associated with this TimeEntry (if any).
     */
    // public Invoice getInvoice() {
    // return this.invoice;
    // }
    /**
     * <p>
     * Sets is the Time Tracker Invoice which is associated with this TimeEntry (if any).
     * </p>
     *
     * @param invoice the Time Tracker Invoice which is associated with this TimeEntry (if any).
     *        This parameter may be null.
     */
    // public void setInvoice(Invoice invoice) {
    // this.invoice = invoice;
    // }
    /**
     * <p>
     * Gets the amount of hours that have been spent working on a task.
     * </p>
     *
     * @return the amount of hours that have been spent working on a task.
     */
    public double getHours() {
        return this.hours;
    }

    /**
     * <p>
     * Sets the amount of hours that have been spent working on a task.
     * </p>
     *
     * @param hours the amount of hours that have been spent working on a task.
     * @throws IllegalArgumentException if hours < 0.
     */
    public void setHours(double hours) {
        this.hours = hours;
    }
}
