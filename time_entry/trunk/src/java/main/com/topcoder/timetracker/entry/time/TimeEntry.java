/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This class represents the Time Entry entity. Extends <code>BaseDataObject</code>. Uses its primaryId member for
 * holding the TimeEntriesID defined in the database. This entity is used in <code>TimeEntryDAO</code>. As such, when
 * the <code>DAO</code> creates this record, it will ignore the creation and modification fields and assign its own
 * values. It will also ignore the primary Id vaue as it will assign its own. Simlarly, the <code>DAO</code> will
 * ignore all of these fields when updating. The creation fields and the primary fields are never updated, and the
 * <code>DAO</code> will assign its own values to the modification fields. As such, from the perspective of the
 * application using this entity, these fields can be considered to be effectively read-only (as assigning fresh
 * values to these fields has no impact in the <code>DAO</code>, and thus on the database. Please see <code>
 * TimeEntryDAO</code> for more details.
 * </p>
 *
 * <p>
 * Version 1.1 changes: The current TimeEntry has an added (which makes it a composite Value Object) RejectReason
 * dependency which allows for linking rejectReasons to time entries.
 * </p>
 *
 * <p>
 * Changes in version 2.0: add company id field.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @author argolite, TCSDEVELOPER
 * @author arylio
 * @version 2.0
 *
 * @since 1.0
 */
public class TimeEntry extends BaseDataObject {
    /**
     * <p>
     * Represents the date of the time entry.
     * </p>
     */
    private Date date = null;

    /**
     * <p>
     * Represents the number of hours the employee worked.
     * </p>
     */
    private float hours = 0.0F;

    /**
     * <p>
     * Represents the foreign key to the task type.
     * </p>
     */
    private int taskTypeId = 0;

    /**
     * <p>
     * Represents the foreign key to the time status.
     * </p>
     */
    private int timeStatusId = 0;

    /**
     * <p>
     * Represents a flag to indicate whether the entry is billable or not.
     * </p>
     */
    private boolean billable = false;

    /**
     * <p>
     * Represents the company ID associated with the time entry.
     * Initialized to zero. Accessed by GetCompanyId and SetCompanyId.
     * Notes: The company id must match taskType company id.
     * </p>
     *
     * @since 2.0
     */
    private int companyId = 0;

    /**
     * <p>
     * Represents the map of reject reasons currently set for this time entry entit. The key is type of
     * <code>Integer</code> which holds the Id of the reject reason. The value is type of <code>RejectReason</code>
     * which holds the reject reason.
     * </p>
     *
     * @since 1.1
     */
    private final Map rejectReasons = new HashMap();

    /**
     * <p>
     * Sole constructor.
     * </p>
     */
    public TimeEntry() {
        // empty here
    }

    /**
     * <p>
     * Gets the date of the time entry.
     * </p>
     *
     * @return The date of the time entry.
     */
    public Date getDate() {
        if (this.date == null) {
            return null;
        }

        return new Date(this.date.getTime());
    }

    /**
     * <p>
     * Sets the date of the time entry.
     * </p>
     *
     * @param date The date of the time entry.
     */
    public void setDate(Date date) {
        if (date == null) {
            this.date = null;
        } else {
            this.date = new Date(date.getTime());
        }
    }

    /**
     * <p>
     * Gets the hours the employee worked.
     * </p>
     *
     * @return The hours the employee worked.
     */
    public float getHours() {
        return this.hours;
    }

    /**
     * <p>
     * Sets the hours the employee worked.
     * </p>
     *
     * @param hours The hours the employee worked.
     */
    public void setHours(float hours) {
        this.hours = hours;
    }

    /**
     * <p>
     * Checks if the hours are billable or not.
     * </p>
     *
     * @return <code>True</code> if the hours are billable. <code>False</code> if not.
     */
    public boolean isBillable() {
        return this.billable;
    }

    /**
     * <p>
     * Checks if the hours are billable or not.
     * </p>
     *
     * @return <code>True</code> if the hours are billable. <code>False</code> if not.
     */
    public boolean getBillable() {
        return isBillable();
    }

    /**
     * <p>
     * Sets whether if the hours are billable or not.
     * </p>
     *
     * @param billable <code>True</code> if the hours are billable. <code>False</code> if not.
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * <p>
     * Gets the task type id.
     * </p>
     *
     * @return The task type Id
     */
    public int getTaskTypeId() {
        return this.taskTypeId;
    }

    /**
     * <p>
     * Sets the task type id.
     * </p>
     *
     * @param taskTypeId The ID of the task type.
     */
    public void setTaskTypeId(int taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    /**
     * <p>
     * Gets the time status id.
     * </p>
     *
     * @return The time status Id.
     */
    public int getTimeStatusId() {
        return this.timeStatusId;
    }

    /**
     * <p>
     * Sets the time status id.
     * </p>
     *
     * @param timeStatusId The ID of the time status.
     */
    public void setTimeStatusId(int timeStatusId) {
        this.timeStatusId = timeStatusId;
    }

    /**
     * <p>
     * Adds a new reject reason into the rejectreasons Map. If a reason with this ID already exists, it will replace
     * the old one. If the input is null then we throw an .
     * </p>
     *
     * @param rejectReason reject reason for this time entry
     *
     * @throws IllegalArgumentException if the <code>rejectReason</code> is <code>null</code>.
     *
     * @since 1.1
     */
    public void addRejectReason(RejectReason rejectReason) {
        if (rejectReason == null) {
            throw new IllegalArgumentException("rejectReason should not be null.");
        }

        this.rejectReasons.put(new Integer(rejectReason.getPrimaryId()), rejectReason);
    }

    /**
     * <p>
     * Gets a reject reason with given Id.
     * </p>
     *
     * @param rejectReasonId the Id to fetch the reject reason.
     *
     * @return the reject reason with given Id.
     *
     * @since 1.1
     */
    public RejectReason getRejectReason(int rejectReasonId) {
        return (RejectReason) this.rejectReasons.get(new Integer(rejectReasonId));
    }

    /**
     * <p>
     * Gets all the associated reject reasons.
     * </p>
     *
     * @return An array of all the associated reject reasons.
     *
     * @since 1.1
     */
    public RejectReason[] getAllRejectReasons() {
        return (RejectReason[]) this.rejectReasons.values().toArray(new RejectReason[0]);
    }

    /**
     * <p>
     * Removes a specific reject reason with given Id.
     * </p>
     *
     * @param rejectReasonId id of the reject reason we want to remove.
     *
     * @since 1.1
     */
    public void removeRejectReason(int rejectReasonId) {
        this.rejectReasons.remove(new Integer(rejectReasonId));
    }

    /**
     * <p>
     * Gets the company id this time entry associated to.
     * </p>
     *
     * @return the company id.
     *
     * @since 2.0
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Set the company id to new value, it must match taskType's company id.
     * </p>
     *
     * @since 2.0
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
