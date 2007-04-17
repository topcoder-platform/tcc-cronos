
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker ProjectManager data from a persistent store.  It is also responsible for
 * generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm7259]
 */
public interface ProjectManagerDAO {
/**
 * <p>
 * Defines a set of  ProjectManagers to be recognized within the persistent store managed by this utility.  A unique
 * ids will automatically be generated and assigned to the ProjectManagers.  There is also the
 * option to perform an audit. If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f7c]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public void addProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided 
 * array of ProjectManagers.
 * There is also the option to perform an audit.  If an audit is specified, then the audit must be 
 * rolled back in the case that the operation fails.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f64]
 * @param managers An array of managers for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if managers is null or contains null elements.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void updateProjectManagers(com.topcoder.timetracker.project.ProjectManager[] managers, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it no longer contains data on the ProjectManagers with the specified
 * ids.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f4c]
 * @param managerIds An array of managerIds for which this operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentException if the aray is null or contains values <=0.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
 */
    public void removeProjectManagers(long[] managerIds, boolean audit);
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
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f34]
 * @param filter The filter containing the search criteria.
 * @return An array of managers satisfying the criteria.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] searchProjectManagers(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>
 * Retrieves the ProjectManagerFilterFactory that is capable of creating SearchFilters to use when searching
 * for ProjectManagers.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProjectManagers method.
 * </p>
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f1c]
 * @return The ProjectManagerFilterFactory used for building search filters.
 * @throws DataAccessException if a problem occurs while accessing the data store.
 */
    public com.topcoder.timetracker.project.ProjectManagerFilterFactory getProjectManagerFilterFactory();
/**
 * <p>
 * Retrieves all the project managers that are currently in the persistent store.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im29c3b8em110be585c09mm6f04]
 * @return An array of managers currently in the persistent store.
 */
    public com.topcoder.timetracker.project.ProjectManager[] enumerateProjectManagers();
}


