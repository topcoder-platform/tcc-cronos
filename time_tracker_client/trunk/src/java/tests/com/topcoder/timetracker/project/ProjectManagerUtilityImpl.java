
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectManager;

/**
 * <p>
 * This is a default implementation of the ProjectManagerUtility interface.  It delegatesto a ProjectManagerDAO
 * to perform most of the work.  CRUDE and search methods are provided to manage the
 * ProjectManagers inside a persistent store.  It is also possible to search the persistent store
 * for ProjectManagers based on different search criteria.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is dependent on the DAO implementations.  Since the DAOs are also required
 * to be thread-safe, this class is thread safe.
 * </p>
 * <p>Changes in version 3.2:</p>
 * <p>Transaction management removed, audits for failed operations are no longer rolled back.</p>
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm64ad]
 */
public class ProjectManagerUtilityImpl implements com.topcoder.timetracker.project.ProjectManagerUtility {

/**
 * <p>
 * This is an implementation of the ProjectManagerDAO that is used to retrieve and modify the
 * persistent store with regards to the ProjectManager objects.  All methods delegate to this instance.
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
 * Valid Values: Not null instances of ProjectManagerDAO
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5623]
 */
    private final com.topcoder.timetracker.project.ProjectManagerDAO managerDAO;

/**
 * <p>
 * Constructor that accepts all the needed DAO instances to perform the 
 * management operations.
 * </p>
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mm5612]
 * @param managerDAO The DAO used to manage the ProjectManagers.
 * @throws IllegalArgumentException if workerDao is null.
 */
    public  ProjectManagerUtilityImpl(com.topcoder.timetracker.project.ProjectManagerDAO managerDAO) {        
        this.managerDAO = managerDAO;
    } 

/**
 * <p>
 * Defines a ProjectManager to be recognized within the persistent store managed by this utility.  A unique
 * id will automatically be generated and assigned to the ProjectManager.  There is also the
 * option to perform an audit.
 * </p>
 * <p>
 * The implementation will set the ProjectManager's creation and modification date to the current
 * date.   These creation/modification
 * details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO using a single-element array.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6474]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 */
    public void addProjectManager(com.topcoder.timetracker.project.ProjectManager manager, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided ProjectManager parameter.
 * There is also the option to perform an audit.
 * </p>
 * <p>
 * The implementation will set the ProjectManager's modification date to the current
 * date.   These modification
 * details will also reflect in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO using a single-element array.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6468]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManager(com.topcoder.timetracker.project.ProjectManager manager, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectManager with the specified
 * projectId.
 * </p>
 * <p>
 * There is also the option to perform an audit. 
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO using a single-element array.
 * </p>
 * 
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm645b]
 * @param managerId The id of the manager to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managerId is a value <= 0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManager(long managerId, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectManagerFilterFactory returned by getProjectManagerFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectManagerFilterFactory.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm644f]
 * @return An array of managers satisfying the criteria.
 * @param filter The filter containing the search criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] searchProjectManagers(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>
 * Retrieves all the project managers that are currently in the persistent store.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6445]
 * @return An array of managers currently in the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] enumerateProjectManagers() {        
        // your code here
        return null;
    } 

/**
 * <p>
 * This is a batch version of the addProjectManager method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Update the creation and modification date of each ProjectManager(the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm643b]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 */
    public void addProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the updateProjectManager method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Update the modification date of each ProjectManager(the dates for all of them should
 *      be the same, so use the same Date object for all).
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm642f]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * This is a batch version of the removeProjectManager method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6422]
 * @param managerIds An array of managerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManagers(long[] managerIds, boolean audit) {        
        // your code here
    } 

/**
 * <p>
 * Retrieves the ProjectManagerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectManagers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectManagers method.
 * </p>
 * <p>
 * Implementation Notes:
 *    - Delegate to the managerDAO.
 * </p>
 * 
 * 
 * @poseidon-object-id [I31c7c1ffm110c44643b7mm6417]
 */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {        
        return null;
    } 
 }
