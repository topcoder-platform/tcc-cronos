
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker ProjectWorker data from a persistent store.  It is also responsible for
 * generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6e26]
 */
public interface ProjectWorkerDAO {
/**
 * <p>
 * Adds the specified ProjectWorkers into the persistent store.  Unique ids will automatically 
 * be generated and assigned to the ProjectWorkers.  There is also the
 * option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6dee]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void addProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided 
 * array of ProjectWorkers.
 * There is also the option to perform an audit. If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6dd6]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void updateProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectWorkers with the specified
 * projectWorkerIds.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6dbe]
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
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6da6]
 * @return The ProjectWorkerFilterFactory used for building search filters.
 */
    public com.topcoder.timetracker.project.ProjectWorkerFilterFactory getProjectWorkerFilterFactory();
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
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6d8e]
 * @param filter The filter containing the search criteria.
 * @return An array of workers  satisfying the criteria.
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
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6d76]
 * @return An array of workers currently in the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectWorker[] enumerateProjectWorkers();
}


