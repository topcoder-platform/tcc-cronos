/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;

import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.InvalidCompanyException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;
import com.topcoder.search.builder.filter.Filter;
import javax.ejb.SessionBean;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage Projects
 * within the Time Tracker Application.
 * </p>
 *
 * <p>
 * It contains the same methods as <code>ProjectUtility</code>, and delegates to
 * an instance of <code>ProjectUtility</code>.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the
 * transaction level declared in the deployment descriptor for this class will be <tt>REQUIRED</tt>.
 * </p>
 *
 * <p>
 * Thread Safety: This class is managed by the EJB Container, which will ensure that it's
 * accessed by no more than one thread at the same time.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectUtilitySessionBean implements ProjectUtility, SessionBean {
    /**
     * <p>
     * This is the instance of <code>ProjectUtility</code> that this session bean delegates
     * all the work to.
     * </p>
     *
     * <p>
     * It is set in the {@link ProjectUtilitySessionBean#ejbCreate()}.
     * </p>
     */
    private ProjectUtility impl;

    /**
     * <p>
     * This is the instance of <code>SessionContext</code> that was provided by the
     * EJB container.
     * </p>
     *
     * <p>
     * It is stored and made available to subclasses. It is also used when performing a rollback
     * in the case that an exception occurred.
     * </p>
     *
     * <p>
     * It is null initially and will be set by EJB container.
     * </p>
     *
     * <p>
     * Getter and setter are provided.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProjectUtilitySessionBean() {
        // empty
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().addProject(project, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().updateProject(project, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().removeProject(projectId, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a Project object that reflects the data in the persistent store on the Time Tracker Project
     * with the specified projectId.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            return getImpl().getProject(projectId);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any projects that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectFilterFactory</code> returned by {@link ProjectUtilitySessionBean#searchProjects(Filter)}, or
     * a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created
     * using <code>ProjectFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param filter The filter used to search for projects.
     * @return The projects satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] searchProjects(Filter filter) throws DataAccessException {
        try {
            return getImpl().searchProjects(filter);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilitySessionBean#addProject(Project, boolean)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().addProjects(projects, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilitySessionBean#updateProject(Project, boolean)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().updateProjects(projects, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of {@link ProjectUtilitySessionBean#removeProject(long, boolean)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().removeProjects(projectIds, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectUtilitySessionBean#getProject(long)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            return getImpl().getProjects(projectsIds);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        throws UnrecognizedEntityException, InvalidCompanyException, DuplicateEntityException, DataAccessException {
        try {
            getImpl().addEntryToProject(projectId, entryId, type, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().removeEntryFromProject(projectId, entryId, type, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            return getImpl().retrieveEntriesForProject(projectId, type);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            return getImpl().retrieveEntriesForClient(clientId, type);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for projects.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters
     * returned by the given factory should be used with {@link ProjectUtilitySessionBean#searchProjects(Filter)}.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction and <code>null</code> will be returned.
     * </p>
     *
     * @return the <code>ProjectFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for projects.
     */
    public ProjectFilterFactory getProjectFilterFactory() {
        try {
            return getImpl().getProjectFilterFactory();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            return null;
        }
    }

    /**
     * <p>
     * Retrieves all the projects  that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return An array of projects retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public Project[] enumerateProjects() throws DataAccessException {
        try {
            return getImpl().enumerateProjects();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This method will instantiate the project utility implementation for providing the functionality of
     * this class.
     * </p>
     *
     * <p>
     * The project utility implementation is created via object factory.
     * </p>
     *
     * @throws CreateException if there's a problem instantiating the project utility implementation
     */
    public void ejbCreate() throws CreateException {
        this.impl = (ProjectUtility) Util.createObject(ProjectUtilitySessionBean.class.getName(), ProjectUtility.class);
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * <p>
     * Sets the <code>SessionContext</code> to use for this session.
     * </p>
     *
     * <p>
     * This method is included to comply with the <code>SessionBean</code> interface.
     * </p>
     *
     * @param ctx The <code>SessionContext</code> to use
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context
     * if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return this.sessionContext;
    }

    /**
     * <p>
     * Returns the project utility implementation.
     * </p>
     *
     * @return the project utility implementation
     *
     * @throws DataAccessException if the project utility implementation is null
     */
    protected ProjectUtility getImpl() throws DataAccessException {
        if (impl == null) {
            throw new DataAccessException("The project utility implementation is null.");
        }

        return impl;
    }
}