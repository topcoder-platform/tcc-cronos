/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionContext;

import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorkerUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.timetracker.project.db.Util;
import com.topcoder.search.builder.filter.Filter;
import javax.ejb.SessionBean;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage
 * Project Workers within the Time Tracker Application.
 * </p>
 *
 * <p>
 * It contains the same methods as <code>ProjectWorkerUtility</code>, and delegates to
 * an instance of <code>ProjectWorkerUtility</code>.
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
public class ProjectWorkerUtilitySessionBean implements ProjectWorkerUtility, SessionBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -8316667274618940218L;

	/**
     * <p>
     * This is the instance of <code>ProjectWorkerUtility</code> that this session bean delegates
     * all the work to.
     * </p>
     *
     * <p>
     * It is set in the {@link ProjectWorkerUtilitySessionBean#ejbCreate()}.
     * </p>
     */
    private ProjectWorkerUtility impl;

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
    public ProjectWorkerUtilitySessionBean() {
        // empty
    }

    /**
     * <p>
     * Defines a <code>ProjectWorker</code> to be recognized within the persistent store managed by this utility.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectWorker's creation and modification date and user to the current
     * date. These creation/modification details will also reflect in the persistent store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param worker The worker for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if worker is null, or or the worker contains null property which
     * is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorker(ProjectWorker worker, boolean audit) throws DataAccessException {
        try {
            getImpl().addProjectWorker(worker, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided ProjectWorker parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the ProjectWorker's modification date and user to the current
     * date. These modification details will also reflect in the persistent store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param worker The worker for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if worker is null or or the worker contains null property
     * which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorker(ProjectWorker worker, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        try {
            getImpl().updateProjectWorker(worker, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectWorker with the specified
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
     * @param projectId The project id of the worker to remove.
     * @param userId the user id of the worker to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentExceptino if the id is &lt;= 0.
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the datastore.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectWorker(long projectId, long userId, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        try {
            getImpl().removeProjectWorker(projectId, userId, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectWorkerFilterFactory</code> returned by
     * {@link ProjectWorkerUtilitySessionBean#getProjectWorkerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectWorkerFilterFactory</code>.
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
     * @return An array of workers satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] searchProjectWorkers(Filter filter) throws DataAccessException {
        try {
            return getImpl().searchProjectWorkers(filter);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the project workers that are currently in the persistent store.
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
     * @return An array of workers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] enumerateProjectWorkers() throws DataAccessException {
        try {
            return getImpl().enumerateProjectWorkers();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtilitySessionBean#addProjectWorker(ProjectWorker, boolean)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException {
        try {
            getImpl().addProjectWorkers(workers, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the
     * {@link ProjectWorkerUtilitySessionBean#updateProjectWorker(ProjectWorker, boolean)} method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        try {
            getImpl().updateProjectWorkers(workers, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the {@link ProjectWorkerUtilitySessionBean#removeProjectWorker(long, long, boolean)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param projectIds An array of project id for which this operation should be performed.
     * @param userIds An array of user id for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws DataAccessException if a problem occurs while accessing the data store.
     * @throws UnrecognizedEntityException if a worker with the given project id and user id was not found in the
     * data store.
     */
    public void removeProjectWorkers(long[] projectIds, long[] userIds, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        try {
            getImpl().removeProjectWorkers(projectIds, userIds, audit);
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>ProjectWorkerFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for ProjectWorkers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectWorkerUtilitySessionBean#searchProjectWorkers(Filter)}
     * method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction and <code>null</code> will be returned.
     * </p>
     *
     * @return The <code>ProjectWorkerFilterFactory</code> used for building search filters.
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {
        try {
            return getImpl().getProjectWorkerFilterFactory();
        } catch (DataAccessException e) {
            this.sessionContext.setRollbackOnly();
            return null;
        }
    }

    /**
     * <p>
     * This method will instantiate the project worker utility implementation for providing the functionality of
     * this class.
     * </p>
     *
     * <p>
     * The project worker utility implementation is created via object factory.
     * </p>
     *
     * @throws CreateException if there's a problem instantiating the project worker utility implementation
     */
    public void ejbCreate() throws CreateException {
        this.impl = (ProjectWorkerUtility) Util.createObject(ProjectWorkerUtilitySessionBean.class.getName(),
            ProjectWorkerUtility.class);
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
     * Returns the project worker utility implementation.
     * </p>
     *
     * @return the project worker utility implementation
     *
     * @throws DataAccessException if the project worker utility implementation is null
     */
    protected ProjectWorkerUtility getImpl() throws DataAccessException {
        if (impl == null) {
            throw new DataAccessException("The project worker utility implementation is null.");
        }

        return impl;
    }
}
