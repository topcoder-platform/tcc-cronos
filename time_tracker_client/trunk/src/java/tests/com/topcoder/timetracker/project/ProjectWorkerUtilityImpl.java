
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a default implementation of the ProjectWorkerUtility interface.  It delegatesto a ProjectWorkerDAO
 * to perform most of the work.  CRUDE and search methods are provided to manage the
 * ProjectWorkers inside a persistent store.  It is also possible to search the persistent store
 * for ProjectWorkers based on different search criteria.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is dependent on the DAO implementations.  Since the DAOs are also required
 * to be thread-safe, this class is thread safe.
 * </p>
 * <p> Changes in version 3.2:</p>
 * <p>Transaction management removed, audits for failed operations are no longer rolled back.</p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm64ae]
 */
public class ProjectWorkerUtilityImpl implements com.topcoder.timetracker.project.ProjectWorkerUtility {

/**
 * <p>
 * This is an implementation of the ProjectWorkerDAO that is used to retrieve and modify the
 * persistent store with regards to the ProjectWorker objects.  All methods delegate to this instance.
 * </p>
 * <p>
 * Initialized In:  Constructor
 * </p>
 * <p>
 * Accessed In: No Access
 * </p>
 * <p>
 * Modified In: Not Modified
 * </p>
 * <p>
 * Utilized In: All methods of this class
 * </p>
 * <p>
 * Valid Values: Not null instances of ProjectWorkerDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5698]
 */
    private final com.topcoder.timetracker.project.ProjectWorkerDAO workerDAO;

/**
 * <p>
 * Constructor that accepts all the needed DAO instances to perform the 
 * management operations.
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5676]
 * @param workerDao The DAO used to manage the ProjectWorkers.
 * @throws IllegalArgumentException if workerDao is null.
 */
    public  ProjectWorkerUtilityImpl(com.topcoder.timetracker.project.ProjectWorkerDAO workerDao) {        
        this.workerDAO = workerDao;
    } 

/**
 * <p>
 * Defines a ProjectWorker to be recognized within the persistent store managed by this utility.  A unique
 * id will automatically be generated and assigned to the ProjectWorker.  There is also the
 * option to perform an audit. 
 * </p>
 * <p>
 * The implementation will set the ProjectWorker's creation and modification date to the current
 * date.   These creation/modification
 * details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO using a single-element array.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm634d]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if worker is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public void addProjectWorker(com.topcoder.timetracker.project.ProjectWorker worker, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided ProjectWorker parameter.
 * There is also the option to perform an audit.
 * </p>
 * <p>
 * The implementation will set the ProjectWorker's modification date to the current
 * date.   These modification
 * details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Update the modification date of each ProjectWorker (the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the workerDAO using a single-element array.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6341]
 * @param worker The worker for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if worker is null.
 */
    public void updateProjectWorker(com.topcoder.timetracker.project.ProjectWorker worker, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectWorker with the specified
 * id.
 * </p>
 * <p>
 * There is also the option to perform an audit. 
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO using a single-element array.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6334]
 * @param projectWorkerId The id of the worker to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentExceptino if the id is <= 0.
 */
    public void removeProjectWorker(long projectWorkerId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectWorkerFilterFactory returned by getProjectWorkerFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectWorkerFilterFactory.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6328]
 * @return An array of workers satisfying the criteria.
 * @param filter The filter containing the search criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectWorker[] searchProjectWorkers(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves all the project workers that are currently in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm631e]
 * @return An array of workers currently in the persistent store.
 * @exception DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectWorker[] enumerateProjectWorkers() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This is a batch version of the addProjectWorker method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Update the creation and modification date of each ProjectWorker(the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6314]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void addProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the updateProjectWorker method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6308]
 * @param workers An array of workers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if workers is null or contains null elements.
 */
    public void updateProjectWorkers(com.topcoder.timetracker.project.ProjectWorker[] workers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the removeProjectWorkermethod.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62fb]
 * @param projectWorkerIds An array of workerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 */
    public void removeProjectWorkers(long[] projectWorkerIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Retrieves the ProjectWorkerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectWorkers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectWorkers method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the workerDAO.
 * </p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm62f0]
 * @return The ProjectWorkerFilterFactory used for building search filters.
 */
    public com.topcoder.timetracker.project.ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {        
        // your code here
        return null;
    } 
/**
 * 
 * 
 * @poseidon-object-id [I2673c754m11121c0cb0cmm1ca9]
 */
    private com.topcoder.timetracker.project.ProjectWorkerDAO projectWorkerDAO;
 }
