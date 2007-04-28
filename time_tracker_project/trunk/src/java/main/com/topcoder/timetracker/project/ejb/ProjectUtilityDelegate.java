/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.InvalidCompanyException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.
 * </p>
 *
 * <p>
 * It is responsible for looking up the local interface of the SessionBean for
 * <code>ProjectUtility</code>, and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectUtilityDelegate implements ProjectUtility {
    /**
     * <p>
     * This is the local interface for the <code>ProjectUtility</code> business services.
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
    private final ProjectUtilityLocal projectUtility;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     *
     * @throws IllegalArgumentException if namespace is null or an empty String.
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public ProjectUtilityDelegate(String namespace) throws ConfigurationException {
        ProjectUtilityLocalHome home = (ProjectUtilityLocalHome) Util.createEJBLocalHome(namespace,
            ProjectUtilityLocalHome.class);

        try {
            this.projectUtility = home.create();
        } catch (CreateException e) {
            throw new ConfigurationException("Unable to create the local interface object.", e);
        }
    }

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
    public void addProject(Project project, boolean audit) throws DataAccessException {
        projectUtility.addProject(project, audit);
    }

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
    public void updateProject(Project project, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        projectUtility.updateProject(project, audit);
    }

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
    public void removeProject(long projectId, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        projectUtility.removeProject(projectId, audit);
    }

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
    public Project getProject(long projectId) throws UnrecognizedEntityException, DataAccessException {
        return projectUtility.getProject(projectId);
    }

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectUtilityDelegate#searchProjects(Filter)}, or
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
    public Project[] searchProjects(Filter filter) throws DataAccessException {
        return projectUtility.searchProjects(filter);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityDelegate#addProject(Project, boolean)} method.
     * </p>
     *
     * @param projects An array of projects for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptionif projects is null, empty or contains null values, or some
     * project contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addProjects(Project[] projects, boolean audit) throws DataAccessException {
        projectUtility.addProjects(projects, audit);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityDelegate#updateProject(Project, boolean)} method.
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
    public void updateProjects(Project[] projects, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectUtility.updateProjects(projects, audit);
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilityDelegate#removeProject(long, boolean)} method.
     * </p>
     *
     * @param projectIds An array of projectIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeProjects(long[] projectIds, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectUtility.removeProjects(projectIds, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectUtilityDelegate#getProject(long)} method.
     * </p>
     *
     * @param projectsIds An array of projectIds for which projects should be retrieved.
     * @return The projects corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a project with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] getProjects(long[] projectsIds) throws UnrecognizedEntityException, DataAccessException {
        return projectUtility.getProjects(projectsIds);
    }

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
        throws InvalidCompanyException, UnrecognizedEntityException, DuplicateEntityException, DataAccessException {
        projectUtility.addEntryToProject(projectId, entryId, type, audit);
    }

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
        throws UnrecognizedEntityException, DataAccessException {
        projectUtility.removeEntryFromProject(projectId, entryId, type, audit);
    }

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
    public long[] retrieveEntriesForProject(long projectId, EntryType type) throws DataAccessException {
        return projectUtility.retrieveEntriesForProject(projectId, type);
    }

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
    public long[] retrieveEntriesForClient(long clientId, EntryType type) throws DataAccessException {
        return projectUtility.retrieveEntriesForClient(clientId, type);
    }

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectUtilityDelegate#searchProjects(Filter)}.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        return projectUtility.getProjectFilterFactory();
    }

    /**
     * <p>
     * Retrieves all the projects that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of projects retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException {
        return projectUtility.enumerateProjects();
    }
}
