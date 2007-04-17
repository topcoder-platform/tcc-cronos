
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker Project data from a persistent store.  It is also responsible for
 * generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 * <p>
 * Thread safety:
 * Thread safety is required for implementations of this interface.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7238]
 */
public interface ProjectDAO {
/**
 * <p>
 * Adds the specified projects into the persistent store.  Unique project ids will automatically 
 * be generated and assigned to the projects.  There is also the
 * option to perform an audit.  If an audit is specified, then the operation must be rolled back in 
 * the case that the audit fails.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm7200]
 * @param project An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void addProjects(com.topcoder.timetracker.project.Project[] project, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
 * There is also the option to perform an audit.  If an audit is specified, then the operation 
 * must be rolled back in the case that the audit fails.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm71e8]
 * @param project An array of projects for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentExceptionif projects is null or contains null values.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void updateProjects(com.topcoder.timetracker.project.Project[] project, boolean audit);
/**
 * <p>
 * Modifies the persistent store so that it  no longer contains data on the project with the specified
 * projectId.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm71d0]
 * @param projectId An array of projectIds for which the operation should be performed.
 * @param audit Indicates whether an audit should be performed.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public void removeProjects(long[] projectId, boolean audit);
/**
 * <p>
 * Searches the persistent store for any projects that satisfy the criteria that was specified in the
 * provided search filter.  The provided filter should be created using either the filters that are
 * created using the ProjectFilterFactory returned by getProjectFilterFactory of this instance, or
 * a composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component)
 * that combines the filters created using ProjectFilterFactory.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm71b8]
 * @param projectIds An array of projectIds for which the operation should be performed.
 * @return The projects corresponding to the provided ids.
 * @throws IllegalArgumentException if projectIds is null or contains values <= 0.
 * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] getProjects(long[] projectIds);
/**
 * <p>
 * Retrieves the ProjectFilterFactory that is capable of creating SearchFilters to use when searching
 * for projects.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProject method.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm71a0]
 * @param filter The filter used to search for projects.
 * @return The projects satisfying the conditions in the search filter.
 * @throws IllegalArgumentException if filter is null.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] searchProjects(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>
 * Retrieves the ProjectFilterFactory that is capable of creating SearchFilters to use when searching
 * for projects.  This is used to conveniently specify the search criteria that should be used.  The
 * filters returned by the given factory should be used with this instance's searchProject method.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im4cccfaa2m110bbe8e4bcmm6a35]
 * @return the ProjectFilterFactory that is capable of creating SearchFilters to use when searching for projects.
 */
    public com.topcoder.timetracker.project.ProjectFilterFactory getProjectFilterFactory();
/**
 * <p>
 * Retrieves all the projects that are currently in the persistent store.
 * </p>
 * <p>
 * An empty array is returned if no entities are found.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm6f77]
 * @return An array of all projects retrieved from the persistent store.
 * @throws DataAccessException if a problem occurs while accessing the persistent store.
 */
    public com.topcoder.timetracker.project.Project[] enumerateProjects();
}


