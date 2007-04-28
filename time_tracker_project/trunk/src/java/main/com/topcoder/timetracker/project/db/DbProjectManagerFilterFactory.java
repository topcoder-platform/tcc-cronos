/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import com.topcoder.timetracker.project.ProjectManagerFilterFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an implementation of the <code>ProjectManagerFilterFactory</code> that may be used
 * for building searches in the database.
 * </p>
 *
 * <p>
 * It maintains a set of column names that are necessary for the filter criterion that it supports, and builds filters
 * according to the specified column names.
 * </p>
 *
 * <p>
 * It extends <code>DbBaseFilterFactory</code> to implement the base functionality that is needed.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectManagerFilterFactory extends DbBaseFilterFactory implements ProjectManagerFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Manager Project Id.
     * </p>
     */
    public static final String MANAGER_PROJECT_ID_COLUMN_NAME = "MANAGER_PROJECT_ID";

    /**
     * <p>
     * This is the map key to use to specify the column name for the Manager User Id.
     * </p>
     */
    public static final String MANAGER_USER_ID_COLUMN_NAME = "MANAGER_USER_ID";

    /**
     * <p>
     * Creates a <code>DbProjectManagerFilterFactory</code> instance.
     * </p>
     */
    public DbProjectManagerFilterFactory() {
        // empty
    }

    /**
     * <p>
     * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker project they
     * are managing.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param projectId The id of the project which the manager is associated with.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createProjectIdFilter(long projectId) {
        Util.checkIdValue(projectId, "project");

        return new EqualToFilter(MANAGER_PROJECT_ID_COLUMN_NAME, new Long(projectId));
    }

    /**
     * <p>
     * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker user they
     * are managing.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param userId The id of the user which the manager is associated with.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createUserIdFilter(long userId) {
        Util.checkIdValue(userId, "user");

        return new EqualToFilter(MANAGER_USER_ID_COLUMN_NAME, new Long(userId));
    }
}
