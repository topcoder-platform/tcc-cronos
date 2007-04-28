/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker <code>ProjectManager</code> data from a persistent store.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectManagerDAO {
    /**
     * <p>
     * Adds the specified project managers into the persistent store.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectManagers(ProjectManager[] managers, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided array of ProjectManagers.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a manager with the given project id and user id was not
     * found in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectManagers(ProjectManager[] managers, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectManagers with the
     * specified project and user ids.
     * </p>
     *
     * @param projectIds An array of projectIds for which this operation should be performed.
     * @param userIds An array of userIds for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws UnrecognizedEntityException if a manager with the given project and user id was not found
     * in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectManagers(long[] projectIds, long[] userIds, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectManagerFilterFactory</code> returned by {@link ProjectManagerDAO#getProjectManagerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectManagerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of managers satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] searchProjectManagers(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves the <code>ProjectManagerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectManagers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used.  The filters returned
     * by the given factory should be used with {@link ProjectManagerDAO#searchProjectManagers(Filter)}.
     * </p>
     *
     * @return The <code>ProjectManagerFilterFactory</code> used for building search filters.
     */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory();

    /**
     * <p>
     * Retrieves all the project managers that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of managers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] enumerateProjectManagers() throws DataAccessException;
}
