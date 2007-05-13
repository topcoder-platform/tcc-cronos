/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
import com.topcoder.timetracker.project.ProjectManagerUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;
import javax.ejb.SessionBean;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage ProjectManagers
 * within the Time Tracker Application.
 * </p>
 *
 * <P>
 * It contains the same methods as <code>ProjectManagerUtility</code>, and delegates to
 * an instance of <code>ProjecManagertUtility</code>.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the transaction
 * level declared in the deployment descriptor for this class will be <tt>REQUIRED</tt>.
 * </p>
 *
 * <p>
 * Thread Safety: This class is managed by the EJB Container, which will ensure that it's accessed
 * by no more than one thread at the same time.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ProjectManagerUtilitySessionBean implements ProjectManagerUtility, SessionBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 8696125191355754836L;

	/**
     * <p>
     * This is the instance of <code>ProjectManagerUtility</code> that this session bean delegates
     * all the work to.
     * </p>
     *
     * <p>
     * It is set in the {@link ProjectManagerUtilitySessionBean#ejbCreate()}.
     * </p>
     */
    private ProjectManagerUtility impl;

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
     * Default Constructor.
     * </p>
     */
    public ProjectManagerUtilitySessionBean() {
        // empty
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().addProjectManager(manager, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().updateProjectManager(manager, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().removeProjectManager(projectId, userId, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * {@link ProjectManagerUtilitySessionBean#getProjectManagerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectManagerFilterFactory</code>.
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
     * @param filter The filter containing the search criteria.
     * @return An array of managers satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] searchProjectManagers(Filter filter) throws DataAccessException {
        try {
            return getImpl().searchProjectManagers(filter);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return An array of managers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] enumerateProjectManagers() throws DataAccessException {
        try {
            return getImpl().enumerateProjectManagers();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the
     * {@link ProjectManagerUtilitySessionBean#addProjectManager(ProjectManager, boolean)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().addProjectManagers(managers, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the
     * {@link ProjectManagerUtilitySessionBean#updateProjectManager(ProjectManager, boolean)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().updateProjectManagers(managers, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectManagerUtilitySessionBean#removeProjectManager(long, long, boolean)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getImpl().removeProjectManagers(projectIds, userIds, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>ProjectManagerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectManagers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectManagerUtilitySessionBean#searchProjectManagers(Filter)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction and <code>null</code> will be returned.
     * </p>
     *
     * @return The <code>ProjectManagerFilterFactory</code> used for building search filters.
     */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {
        try {
            return getImpl().getProjectManagerFilterFactory();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            return null;
        }
    }

    /**
     * <p>
     * This method will instantiate the project manager utility implementation for providing the functionality of
     * this class.
     * </p>
     *
     * <p>
     * The project manager utility implementation is created via object factory.
     * </p>
     *
     * @throws CreateException if there's a problem instantiating the project manager utility implementation
     */
    public void ejbCreate() throws CreateException {
        this.impl = (ProjectManagerUtility) Util.createObject(ProjectManagerUtilitySessionBean.class.getName(),
            ProjectManagerUtility.class);
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
     * Returns the project manager utility implementation.
     * </p>
     *
     * @return the project manager utility implementation
     *
     * @throws DataAccessException if the project manager utility implementation is null
     */
    private ProjectManagerUtility getImpl() throws DataAccessException {
        if (impl == null) {
            throw new DataAccessException("The project manager utility implementation is null.");
        }

        return impl;
    }
}
