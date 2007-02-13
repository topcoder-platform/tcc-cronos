/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This is a simple listing of available search criteria elements for Time Entries. This is an enum, which basically
 * gives the user an enumeration of all currently available criterias that can be used in searches.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class TimeEntryCriteria extends Enum {
    /**
     * <p>
     * Represents a criterion for the TimeEntries.Description field.
     * </p>
     */
    public static final TimeEntryCriteria DESCRIPTION = new TimeEntryCriteria("TimeEntries.Description");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.TimeStatusesID field.
     * </p>
     */
    public static final TimeEntryCriteria TIME_STATUS_ID = new TimeEntryCriteria("TimeEntries.TimeStatusesID");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.TaskTypesID field.
     * </p>
     */
    public static final TimeEntryCriteria TASK_TYPE_ID = new TimeEntryCriteria("TimeEntries.TaskTypesID");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.CreationUser field.
     * </p>
     */
    public static final TimeEntryCriteria CREATION_USER = new TimeEntryCriteria("TimeEntries.CreationUser");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.ModificationUser field.
     * </p>
     */
    public static final TimeEntryCriteria MODIFICATION_USER = new TimeEntryCriteria("TimeEntries.ModificationUser");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.Billable field.
     * </p>
     */
    public static final TimeEntryCriteria BILLABLE_FLAG = new TimeEntryCriteria("TimeEntries.Billable");

    /**
     * <p>
     * Represents a criterion for the reject_reason.TimeEntries.reject_reason_id field.
     * </p>
     */
    public static final TimeEntryCriteria REJECT_REASON_ID = new TimeEntryCriteria("time_reject_reason.TimeEntriesID");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.Hours field.
     * </p>
     */
    public static final TimeEntryCriteria HOURS = new TimeEntryCriteria("TimeEntries.Hours");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.Date field.
     * </p>
     */
    public static final TimeEntryCriteria DATE = new TimeEntryCriteria("TimeEntries.Date");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.CreationDate field.
     * </p>
     */
    public static final TimeEntryCriteria CREATION_DATE = new TimeEntryCriteria("TimeEntries.CreationDate");

    /**
     * <p>
     * Represents a criterion for the TimeEntries.ModificationDate field.
     * </p>
     */
    public static final TimeEntryCriteria MODIFICATION_DATE = new TimeEntryCriteria("TimeEntries.ModificationDate");

    /**
     * <p>
     * Represents the name of this criterion.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Private Constructor with the given criteria name.
     * </p>
     *
     * @param name the name of this criterion.
     *
     * @throws IllegalArgumentException if name parameter is <code>null</code> or empty.
     */
    private TimeEntryCriteria(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can not be null.");
        }

        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("name can not be empty.");
        }

        this.name = name;
    }

    /**
     * <p>
     * Gets the name of this criterion.
     * </p>
     *
     * @return Name of this criterion.
     */
    public String getName() {
        return this.name;
    }
}
