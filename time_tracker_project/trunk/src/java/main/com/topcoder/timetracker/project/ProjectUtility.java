/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a Time Tracker Project.
 * </p>
 *
 * <p>
 * CRUDE and search methods are provided to manage the Projects inside a persistent store.
 * </p>
 *
 * <p>
 * There are also methods that allow various Time, Expense, and Fixed Billing entries to be associated with
 * a Project.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for Projects based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectUtility {
    /**
     * <p>
     * Defines a project to be recognized within the persistent store managed by this utility.
     * </p>
     *
     * <p>
     * A unique project id will automatically be generated and assigned to the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Project's creation and modification date to the current
     * date. These creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if project is null, or the project contains null property
     * which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProject(Project project, boolean audit) throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Project parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Project's modification date to the current
     * date. These modification details will also reflect in the persistent store.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if project is null, or the project contains null property
     * which is required in the persistence
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProject(Project project, boolean audit) throws DataAccessException, UnrecognizedEntityException;

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the project with the specified
     * projectId.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The projectId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectId &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProject(long projectId, boolean audit) throws DataAccessException, UnrecognizedEntityException;

    /**
     * <p>
     * Retrieves a Project object that reflects the data in the persistent store on the Time Tracker Project
     * with the specified projectId.
     * </p>
     *
     * @param projectId The id of the project to retrieve.
     * @return The project with specified id.
     *
     * @throws IllegalArgumentException if projectId &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project getProject(long projectId) throws DataAccessException, UnrecognizedEntityException;

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectUtility#searchProjects(Filter)}, or
     * a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created
     * using <code>ProjectFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException;

    /**
     * <p>
     * This is a batch version of {@link ProjectUtility#addProject(Project, boolean)} method.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException;

    /**
     * <p>
     * This is a batch version of {@link ProjectUtility#updateProject(Project, boolean)} method.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptionif projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateProjects(Project[] projects, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * This is a batch version of {@link ProjectUtility#removeProject(long, boolean)} method.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProjects(long[] projectIds, boolean audit) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * This is a batch version of the {@link ProjectUtility#getProject(long)} method.
     * </p>
     *
     * @param projectsIds An array of projectIds for which projects should be retrieved.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] getProjects(long[] projectsIds) throws DataAccessException, UnrecognizedEntityException;

    /**
     * <p>
     * This associates an Entry with the project.
     * </p>
     *
     * <p>
     * It could be either an Expense, Time, or Fixed Billing entry, as specified by the entry type provided.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param type The type of the entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws InvalidCompanyException if the entry and project company ids do not match.
     * @throws IllegalArgumentException if any id has a value &lt;= 0 or type is null.
     * @throws UnrecognizedEntityException if a project or entry with the provided id was not found in the data store.
     * @throws DuplicateEntityException if the entry has already been associated with the project.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addEntryToProject(long projectId, long entryId, EntryType type, boolean audit)
        throws DataAccessException, UnrecognizedEntityException, DuplicateEntityException;

    /**
     * <p>
     * This disassociates an Entry with the project.
     * </p>
     *
     * <p>
     * It could be either an Expense, Time, or Fixed Billing entry, as specified by the entry type provided.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param type The type of the entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if either id is &lt;= 0 or type is null.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeEntryFromProject(long projectId, long entryId, EntryType type, boolean audit)
        throws DataAccessException, UnrecognizedEntityException;

    /**
     * <p>
     * Retrieves all the Entries of the specified type that are associated with the specified project.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param projectId The id of the project for which the operation should be performed.
     * @param type The type of Entry to be retrieved.
     * @return An array of long identifiers of the Entries corresponding to the given project id.
     *
     * @throws IllegalArgumentException if the projectId is &lt;= 0 or if type is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] retrieveEntriesForProject(long projectId, EntryType type) throws DataAccessException,
        UnrecognizedEntityException;

    /**
     * <p>
     * Retrieves all the Entries of the specified type that are associated with projects being performed
     * for a specific client.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param clientId The id of the client for which this operation should be performed.
     * @param type The type of Entry to be retrieved.
     * @return An array of long identifiers of the Entries corresponding to the given client id.
     *
     * @throws IllegalArgumentException if the clientId is &lt;= 0 or if type is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] retrieveEntriesForClient(long clientId, EntryType type) throws DataAccessException;

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectUtility#searchProjects(Filter)}.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory();

    /**
     * <p>
     * Retrieves all the projects  that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of projects retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException;
}