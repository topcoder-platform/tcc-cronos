
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a Project Manager.  CRUDE and search methods are provided to manage the 
 * ProjectManagers inside a persistent store.  It is also possible to search the persistent store
 * for ProjectManagers based on different search criteria.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is required for implementations of this interface.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7221]
 */
public interface ProjectManagerUtility {
/**
 * <p>
 * Defines a ProjectManager to be recognized within the persistent store managed by this utility.  A unique
 * id will automatically be generated and assigned to the ProjectManager.  There is also the
 * option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * <p>
 * The implementation will set the ProjectManager's creation and modification date and user to the current
 * date and username (the username is implementation-dependent).   These creation/modification
 * details will also reflect in the persistent store.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm71e9]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 */
    public void addProjectManager(com.topcoder.timetracker.project.ProjectManager manager, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided ProjectManager parameter.
 * There is also the option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * <p>
 * The implementation will set the ProjectManager's modification date and user to the current
 * date and username (the username is implementation-dependent).   These modification
 * details will also reflect in the persistent store.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm71d1]
 * @param manager The manager for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if manager is null.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManager(com.topcoder.timetracker.project.ProjectManager manager, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectManager with the specified
 * id.
 * </p>
 * <p>
 * There is also the option to perform an audit. If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm71b9]
 * @param managerId The id of the manager to remove.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managerId is a value <= 0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManager(long managerId, boolean audit);
/**
 * <p>
 * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectManagerFilterFactory returned by getProjectManagerFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectManagerFilterFactory.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm71a1]
 * @param filter The filter containing the search criteria.
 * @return An array of managers satisfying the criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] searchProjectManagers(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>
 * Retrieves all the project managers that are currently in the persistent store.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7189]
 * @return An array of managers currently in the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] enumerateProjectManagers();
/**
 * <p>
 * This is a batch version of the addProjectManager method.  If any of the ProjectManagers
 * fail to be added, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7171]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 */
    public void addProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit);
/**
 * <p>
 * This is a batch version of the updateProjectManager method.  If any of the ProjectManagers
 * fail to be updated, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7159]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit);
/**
 * <p>
 * This is a batch version of the removeProjectManager method.  If any of the ProjectManagers
 * fail to be removed, then the entire batch operation will be rolled back.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7141]
 * @param managerIds An array of managerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManagers(long[] managerIds, boolean audit);
/**
 * <p>
 * Retrieves the ProjectManagerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectManagers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectManagers method.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7027]
 * @return The ProjectManagerFilterFactory used for building search filters.
 */
    public com.topcoder.timetracker.project.ProjectManagerFilterFactory getProjectManagerFilterFactory();
}


