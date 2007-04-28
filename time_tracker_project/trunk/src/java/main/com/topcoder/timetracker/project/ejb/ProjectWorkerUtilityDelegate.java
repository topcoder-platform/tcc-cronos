/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.
 * </p>
 *
 * <p>
 * It is responsible for looking up the local interface of the SessionBean for
 * <code>ProjectWorkerUtility</code>, and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectWorkerUtilityDelegate implements ProjectWorkerUtility {
    /**
     * <p>
     * This is the local interface for the <code>ProjectWorkerUtility</code> business services.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final ProjectWorkerUtilityLocal projectWorkerUtility;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager
     * namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     *
     * @throws IllegalArgumentException if namespace is null or an empty String.
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public ProjectWorkerUtilityDelegate(String namespace) throws ConfigurationException {
        ProjectWorkerUtilityLocalHome home = (ProjectWorkerUtilityLocalHome) Util.createEJBLocalHome(namespace,
            ProjectWorkerUtilityLocalHome.class);

        try {
            this.projectWorkerUtility = home.create();
        } catch (CreateException e) {
            throw new ConfigurationException("Unable to create the local interface object.", e);
        }
    }

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
    public void addProjectWorker(ProjectWorker worker, boolean audit) throws DataAccessException {
        projectWorkerUtility.addProjectWorker(worker, audit);
    }

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
    public void updateProjectWorker(ProjectWorker worker, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectWorkerUtility.updateProjectWorker(worker, audit);
    }

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
    public void removeProjectWorker(long projectId, long userId, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectWorkerUtility.removeProjectWorker(projectId, userId, audit);
    }

    /**
     * <p>
     * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectWorkerFilterFactory</code> returned by
     * {@link ProjectWorkerUtilityDelegate#getProjectWorkerFilterFactory()},
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
    public ProjectWorker[] searchProjectWorkers(Filter filter) throws DataAccessException {
        return projectWorkerUtility.searchProjectWorkers(filter);
    }

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
    public ProjectWorker[] enumerateProjectWorkers() throws DataAccessException {
        return projectWorkerUtility.enumerateProjectWorkers();
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtilityDelegate#addProjectWorker(ProjectWorker, boolean)}
     * method.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException {
        projectWorkerUtility.addProjectWorkers(workers, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtilityDelegate#updateProjectWorker(ProjectWorker, boolean)}
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
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectWorkerUtility.updateProjectWorkers(workers, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtilityDelegate#removeProjectWorker(long, long, boolean)}
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
    public void removeProjectWorkers(long[] projectIds, long[] userIds, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        projectWorkerUtility.removeProjectWorkers(projectIds, userIds, audit);
    }

    /**
     * <p>
     * Retrieves the <code>ProjectWorkerFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for ProjectWorkers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectWorkerUtilityDelegate#searchProjectWorkers(Filter)} method.
     * </p>
     *
     * @return The <code>ProjectWorkerFilterFactory</code> used for building search filters.
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {
        return projectWorkerUtility.getProjectWorkerFilterFactory();
    }
}