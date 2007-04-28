/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker ProjectWorker data from a persistent store.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectWorkerDAO {
    /**
     * <p>
     * Adds the specified ProjectWorkers into the persistent store.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided array of ProjectWorkers.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given project id and user id was not found
     * in the data store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectWorkers with the specified
     * <code>prjectIds</code> and <code>userIds</code>.
     * </p>
     *
     * @param projectIds An array of project id for which this operation should be performed.
     * @param userIds An array of user id which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws DataAccessException if a problem occurs while accessing the data store.
     * @throws UnrecognizedEntityException if a worker with the given project id and user id was not found in the
     * data store.
     */
    public void removeProjectWorkers(long[] projectIds, long[] userIds, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Retrieves the <code>ProjectWorkerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectWorkers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectWorkerDAO#searchProjectWorkers(Filter)}.
     * </p>
     *
     * @return The <code>ProjectWorkerFilterFactory</code> used for building search filters.
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory();

    /**
     * <p>
     * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectWorkerFilterFactory</code> returned by {@link ProjectWorkerDAO#getProjectWorkerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectWorkerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of workers  satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] searchProjectWorkers(Filter filter) throws DataAccessException;

    /**
     * <p>
     * Retrieves all the project workers that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of workers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] enumerateProjectWorkers() throws DataAccessException;
}
