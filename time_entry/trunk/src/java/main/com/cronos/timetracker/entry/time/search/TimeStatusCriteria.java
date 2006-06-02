/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

/**
 * <p>
 * This is a simple listing of available search criteria elements for Time Status. This is an enum, which basically
 * gives the user an enumeration of all currently available criterias that can be used in searches.
 * </p>
 *
 * <p>
 * Thread safety: Immutable class so there are no thread safety issues.
 * </p>
 *
 * @author arylio
 * @version 2.0
 */
public class TimeStatusCriteria extends Criteria {
    /**
     * <p>
     * Represents a criterion for the time_status.description field.
     * </p>
     */
    public static final TimeStatusCriteria DESCRIPTION = new TimeStatusCriteria("time_status.description");

    /**
     * <p>
     * Represents a criterion for the time_status.creation_date field.
     * </p>
     */
    public static final TimeStatusCriteria CREATION_DATE = new TimeStatusCriteria("time_status.creation_date");

    /**
     * <p>
     * Represents a criterion for the time_entry.modification_date field.
     * </p>
     */
    public static final TimeStatusCriteria MODIFICATION_DATE = new TimeStatusCriteria("time_status.modification_date");

    /**
     * <p>
     * Represents a criterion for the time_status.creation_user field.
     * </p>
     */
    public static final TimeStatusCriteria CREATION_USER = new TimeStatusCriteria("time_status.creation_user");

    /**
     * <p>
     * Represents a criterion for the time_status.modification_user field.
     * </p>
     */
    public static final TimeStatusCriteria MODIFICATION_USER = new TimeStatusCriteria("time_status.modification_user");

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
    private TimeStatusCriteria(String name) {
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
