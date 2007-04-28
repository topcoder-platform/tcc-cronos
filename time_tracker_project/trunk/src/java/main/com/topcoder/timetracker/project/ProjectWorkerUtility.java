/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a Project Worker.
 * </p>
 *
 * <p>
 * CRUDE and search methods are provided to manage the ProjectWorkers inside a persistent store.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for ProjectWorkers based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectWorkerUtility {
    /**
     * <p>
     * Defines a <code>ProjectWorker</code> to be recognized within the persistent store managed by this utility.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectWorker's creation and modification date and user to the current
     * date. These creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param worker The worker for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if worker is null, or or the worker contains null property which
     * is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorker(ProjectWorker worker, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided ProjectWorker parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectWorker's modification date and user to the current
     * date. These modification details will also reflect in the persistent store.
     * </p>
     *
     * @param worker The worker for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if worker is null or or the worker contains null property
     * which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorker(ProjectWorker worker, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectWorker with the specified
     * id.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project id of the worker to remove.
     * @param userId the user id of the worker to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptino if the id is &lt;= 0.
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectWorker(long projectId, long userId, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectWorkerFilterFactory</code> returned by {@link ProjectWorkerUtility#getProjectWorkerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectWorkerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of workers satisfying the criteria.
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

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtility#addProjectWorker(ProjectWorker, boolean)} method.
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
     * This is a batch version of the {@link ProjectWorkerUtility#updateProjectWorker(ProjectWorker, boolean)}
     * method.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtility#removeProjectWorker(long, long, boolean)}
     * method.
     * </p>
     *
     * @param projectIds An array of project id for which this operation should be performed.
     * @param userIds An array of user id for which this operation should be performed.
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
     * Retrieves the <code>ProjectWorkerFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for ProjectWorkers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectWorkerUtility#searchProjectWorkers(Filter)} method.
     * </p>
     *
     * @return The <code>ProjectWorkerFilterFactory</code> used for building search filters.
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory();
}
