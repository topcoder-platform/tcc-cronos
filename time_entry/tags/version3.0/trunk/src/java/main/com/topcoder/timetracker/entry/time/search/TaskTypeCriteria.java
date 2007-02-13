/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This is a simple listing of available search criteria elements for Task Types. This is an enum, which basically
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
public class TaskTypeCriteria extends Criteria {
    /**
     * <p>
     * Represents a criterion for the comp_task_type.company_id field.
     * </p>
     */
	public static final TaskTypeCriteria COMPANY_ID = new TaskTypeCriteria("comp_task_type.company_id");

	/**
     * <p>
     * Represents a criterion for the task_type.description field.
     * </p>
     */
    public static final TaskTypeCriteria DESCRIPTION = new TaskTypeCriteria("task_type.description");
    
	/**
     * <p>
     * Represents a criterion for the task_type.active field.
     * </p>
     */
    public static final TaskTypeCriteria ACTIVE = new TaskTypeCriteria("task_type.active");

    /**
     * <p>
     * Represents a criterion for the task_type.creation_date field.
     * </p>
     */
    public static final TaskTypeCriteria CREATION_DATE = new TaskTypeCriteria("task_type.creation_date");

    /**
     * <p>
     * Represents a criterion for the task_type.modification_date field.
     * </p>
     */
    public static final TaskTypeCriteria MODIFICATION_DATE = new TaskTypeCriteria("task_type.modification_date");

    /**
     * <p>
     * Represents a criterion for the task_type.creation_user field.
     * </p>
     */
    public static final TaskTypeCriteria CREATION_USER = new TaskTypeCriteria("task_type.creation_user");

    /**
     * <p>
     * Represents a criterion for the task_type.modification_user field.
     * </p>
     */
    public static final TaskTypeCriteria MODIFICATION_USER = new TaskTypeCriteria("task_type.modification_user");

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
    private TaskTypeCriteria(String name) {
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
