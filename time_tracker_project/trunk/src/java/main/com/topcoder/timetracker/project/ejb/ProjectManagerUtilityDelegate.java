/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
import com.topcoder.timetracker.project.ProjectManagerUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.
 * </p>
 *
 * <p>
 * It is responsible for looking up the local interface of the SessionBean for <code>ProjectManagerUtility</code>,
 * and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManagerUtilityDelegate implements ProjectManagerUtility {
    /**
     * <p>
     * This is the local interface for the <code>ProjectManagerUtility</code> business services.
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
    private final ProjectManagerUtilityLocal projectManagerUtility;

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
    public ProjectManagerUtilityDelegate(String namespace) throws ConfigurationException {
        ProjectManagerUtilityLocalHome home = (ProjectManagerUtilityLocalHome) Util.createEJBLocalHome(namespace,
            ProjectManagerUtilityLocalHome.class);

        try {
            this.projectManagerUtility = home.create();
        } catch (CreateException e) {
            throw new ConfigurationException("Unable to create the local interface object.", e);
        }
    }

    /**
     * <p>
     * Defines a <code>ProjectManager</code> to be recognized within the persistent store managed by this utility.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectManager's creation and modification date and user to the current
     * date. These creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * @param manager The manager for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if manager is null, or the manager contains null property which
     * is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectManager(ProjectManager manager, boolean audit) throws DataAccessException {
        projectManagerUtility.addProjectManager(manager, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided ProjectManager parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectManager's modification date and user to the current
     * date. These modification details will also reflect in the persistent store.
     * </p>
     *
     * @param manager The manager for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if manager is null, or the manager contains null property
     * which is required in the persistence
     * @throws UnrecognizedEntityException if a manager with the given id was not found in the
     * persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectManager(ProjectManager manager, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectManagerUtility.updateProjectManager(manager, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectManager with the specified
     * id.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param projectId The project id of the manager to remove.
     * @param userId the user id of the manager to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managerId is a value &lt;= 0.
     * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectManager(long projectId, long userId, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectManagerUtility.removeProjectManager(projectId, userId, audit);
    }

    /**
     * <p>
     * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectManagerFilterFactory</code> returned by
     * {@link ProjectManagerUtility#getProjectManagerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectManagerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of managers satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] searchProjectManagers(Filter filter) throws DataAccessException {
        return projectManagerUtility.searchProjectManagers(filter);
    }

    /**
     * <p>
     * Retrieves all the project managers that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of managers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] enumerateProjectManagers() throws DataAccessException {
        return projectManagerUtility.enumerateProjectManagers();
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectManagerUtility#addProjectManager(ProjectManager, boolean)}
     * method.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectManagers(ProjectManager[] managers, boolean audit) throws DataAccessException {
        projectManagerUtility.addProjectManagers(managers, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectManagerUtility#updateProjectManager(ProjectManager, boolean)}
     * method.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectManagers(ProjectManager[] managers, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        projectManagerUtility.updateProjectManagers(managers, audit);
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectManagerUtilityDelegate#removeProjectManager(long, long, boolean)}
     * method.
     * </p>
     *
     * @param projectIds An array of project id for which this operation should be performed.
     * @param userIds An array of project id for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws UnrecognizedEntityException if a manager with the given id was not found in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectManagers(long[] projectIds, long[] userIds, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        projectManagerUtility.removeProjectManagers(projectIds, userIds, audit);
    }

    /**
     * <p>
     * Retrieves the <code>ProjectManagerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectManagers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectManagerUtilityDelegate#searchProjectManagers(Filter)} method.
     * </p>
     *
     * @return The <code>ProjectManagerFilterFactory</code> used for building search filters.
     */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {
        return projectManagerUtility.getProjectManagerFilterFactory();
    }
}
