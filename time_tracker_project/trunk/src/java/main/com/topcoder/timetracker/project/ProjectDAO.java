/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker Project data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectDAO {
    /**
     * <p>
     * Adds the specified projects into the persistent store.
     * </p>
     *
     * <p>
     * Unique project ids will automatically be generated and assigned to the projects.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptionif projects is null, empty or contains null values.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProjects(Project[] projects, boolean audit) throws UnrecognizedEntityException,
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that the projects that have the given project ids are no more active.
     * </p>
     *
     * <p>
     * Note, the prject records are not deleted from database, only the <tt>active</tt> states will be set
     * to inactive.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProjects(long[] projectIds, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Retrieves an array of <code>Project</code> objects that reflects the data in the persistent
     * store on the Time Tracker Project with the specified <code>projectsIds</code>.
     * </p>
     *
     * @param projectsIds An array of projectIds for which the operation should be performed.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] getProjects(long[] projectsIds) throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectDAO#getProjectFilterFactory()}, or
     * a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectDAO#searchProjects(Filter)}.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory();

    /**
     * <p>
     * Retrieves all the projects that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of all projects retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException;
}
