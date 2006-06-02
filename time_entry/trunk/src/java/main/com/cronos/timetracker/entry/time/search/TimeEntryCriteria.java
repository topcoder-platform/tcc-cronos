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
 * @author arylio
 *
 * @version 2.0
 * @since 1.1
 */
public class TimeEntryCriteria extends Criteria {
    /**
     * <p>
     * Represents a criterion for the time_entry.description field.
     * </p>
     */
    public static final TimeEntryCriteria DESCRIPTION = new TimeEntryCriteria("time_entry.description");

    /**
     * <p>
     * Represents a criterion for the time_entry.time_status_id field.
     * </p>
     */
    public static final TimeEntryCriteria TIME_STATUS_ID = new TimeEntryCriteria("time_entry.time_status_id");

    /**
     * <p>
     * Represents a criterion for the time_entry.task_type_id field.
     * </p>
     */
    public static final TimeEntryCriteria TASK_TYPE_ID = new TimeEntryCriteria("time_entry.task_type_id");

    /**
     * <p>
     * Represents a criterion for the time_entry.creation_user field.
     * </p>
     */
    public static final TimeEntryCriteria CREATION_USER = new TimeEntryCriteria("time_entry.creation_user");

    /**
     * <p>
     * Represents a criterion for the time_entry.modification_user field.
     * </p>
     */
    public static final TimeEntryCriteria MODIFICATION_USER = new TimeEntryCriteria("time_entry.modification_user");

    /**
     * <p>
     * Represents a criterion for the time_entry.billable field.
     * </p>
     */
    public static final TimeEntryCriteria BILLABLE_FLAG = new TimeEntryCriteria("time_entry.billable");

    /**
     * <p>
     * Represents a criterion for the time_reject_reason.reject_reason_id field.
     * </p>
     */
    public static final TimeEntryCriteria REJECT_REASON_ID =
        new TimeEntryCriteria("time_reject_reason.reject_reason_id");

    /**
     * <p>
     * Represents a criterion for the time_entry.hours field.
     * </p>
     */
    public static final TimeEntryCriteria HOURS = new TimeEntryCriteria("time_entry.hours");

    /**
     * <p>
     * Represents a criterion for the time_entry.entry_date field.
     * </p>
     */
    public static final TimeEntryCriteria DATE = new TimeEntryCriteria("time_entry.entry_date");

    /**
     * <p>
     * Represents a criterion for the time_entry.creation_date field.
     * </p>
     */
    public static final TimeEntryCriteria CREATION_DATE = new TimeEntryCriteria("time_entry.creation_date");

    /**
     * <p>
     * Represents a criterion for the time_entry.modification_date field.
     * </p>
     */
    public static final TimeEntryCriteria MODIFICATION_DATE = new TimeEntryCriteria("time_entry.modification_date");

    /**
     * <p>
     * Represents a criterion for the time_entry.company_id field.
     * </p>
     */
    public static final TimeEntryCriteria COMPANY_ID = new TimeEntryCriteria("time_entry.company_id");

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
