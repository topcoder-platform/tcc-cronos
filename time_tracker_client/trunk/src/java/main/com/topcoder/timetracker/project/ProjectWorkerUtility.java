
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a Project Worker.  CRUDE and search methods are provided to manage the 
 * ProjectWorkers inside a persistent store.  It is also possible to search the persistent store
 * for ProjectWorkers based on different search criteria.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is required for implementations of this interface.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm707a]
 */
public interface ProjectWorkerUtility {
/**
 * <p>
 * Defines a ProjectWorker to be recognized within the persistent store managed by this utility.  A unique
 * id will automatically be generated and assigned to the ProjectWorker.  There is also the
 * option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * <p>
 * The implementation will set the ProjectWorker's creation and modification date and user to the current
 * date and username (the username is implementation-dependent).   These creation/modification
 * details will also reflect in the persistent store.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm7042]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if worker is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public void addProjectWorker(com.topcoder.timetracker.project.ProjectWorker worker, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided ProjectWorker parameter.
 * There is also the option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * <p>
 * The implementation will set the ProjectWorker's modification date and user to the current
 * date and username (the username is implementation-dependent).   These modification
 * details will also reflect in the persistent store.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm702a]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if worker is null.
 */
    public void updateProjectWorker(com.topcoder.timetracker.project.ProjectWorker worker, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectWorker with the specified
 * id.
 * </p>
 * <p>
 * There is also the option to perform an audit. If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm7012]
 * @param projectWorkerId The id of the worker to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentExceptino if the id is <= 0.
 */
    public void removeProjectWorker(long projectWorkerId, boolean audit);
/**
 * <p>
 * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectWorkerFilterFactory returned by getProjectWorkerFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectWorkerFilterFactory.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6ffa]
 * @param filter The filter containing the search criteria.
 * @return An array of workers satisfying the criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectWorker[] searchProjectWorkers(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>
 * Retrieves all the project workers that are currently in the persistent store.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6fe2]
 * @return An array of workers currently in the persistent store.
 * @exception DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectWorker[] enumerateProjectWorkers();
/**
 * <p>
 * This is a batch version of the addProjectWorker method.  If any of the ProjectWorkers 
 * fail to be added, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6f53]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void addProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit);
/**
 * <p>
 * This is a batch version of the updateProjectWorker method.  If any of the ProjectWorkers
 * fail to be updated, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6f3b]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void updateProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit);
/**
 * <p>
 * This is a batch version of the removeProjectWorkermethod.  If any of the ProjectWorkers
 * fail to be removed, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6f23]
 * @param projectWorkerIds An array of workerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 */
    public void removeProjectWorkers(long[] projectWorkerIds, boolean audit);
/**
 * <p>
 * Retrieves the ProjectWorkerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectWorkers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectWorkers method.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6f0b]
 * @return The ProjectWorkerFilterFactory used for building search filters.
 */
    public com.topcoder.timetracker.project.ProjectWorkerFilterFactory getProjectWorkerFilterFactory();
}


