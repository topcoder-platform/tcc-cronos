/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import com.topcoder.timetracker.entry.time.TimeStatusManager;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeStatusFilterFactory;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage TimeStatuses
 * within the Time Tracker Application.
 * </p>
 *
 * <P>
 * It contains the same methods as <code>TimeStatusManager</code>, and delegates to
 * an instance of <code>TimeStatusManager</code>. The instance of <code>TimeStatusManager</code>
 * to use is retrieved from <code>ManagerFactory</code>.
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
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TimeStatusManagerSessionBean implements TimeStatusManager, SessionBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -3665155517599216079L;

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
    private SessionContext context;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public TimeStatusManagerSessionBean() {
        // empty
    }

    /**
     * <p>
     * Defines a time status to be recognized within the persistent store managed by this manager.
     * </p>
     *
     * <p>
     * A unique time status id will automatically be generated and assigned to the time status.
     * </p>
     *
     * <p>
     * It will set the TimeStatus' creation and modification date to the current date.
     * These creation/modification details will also reflect in the persistent store.
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatus The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null or has id != -1.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeStatus(TimeStatus timeStatus) throws DataAccessException {
        try {
            getTimeStatusManager().createTimeStatus(timeStatus);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeStatus parameter.
     * </p>
     *
     * <p>
     * It will set the TimeStatus' modification details to the current date.
     * These modification details will also reflect in the persistent store.
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatus The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null or has id &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeStatus(TimeStatus timeStatus) throws UnrecognizedEntityException, DataAccessException {
        try {
            getTimeStatusManager().updateTimeStatus(timeStatus);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the time status
     * with the specified id.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatusId The id of the status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatusId &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeStatus(long timeStatusId) throws UnrecognizedEntityException, DataAccessException {
        try {
            getTimeStatusManager().deleteTimeStatus(timeStatusId);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a TimeStatus object that reflects the data in the persistent store on the Time Tracker
     * TimeStatus with the specified timeStatusId.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatusId The id of the status to retrieve.
     * @return The status with specified id.
     *
     * @throws IllegalArgumentException if timeStatusId &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus getTimeStatus(long timeStatusId) throws UnrecognizedEntityException, DataAccessException {
        try {
            return getTimeStatusManager().getTimeStatus(timeStatusId);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any time statuses that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TimeStatusFilterFactory</code> returned by <code>getTimeStatusEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TimeStatusFilterFactory</code>.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] searchTimeStatuses(Filter criteria) throws DataAccessException {
        try {
            return getTimeStatusManager().searchTimeStatuses(criteria);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>createTimeStatus</code> method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatuses An array of time statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatuses is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException, DataAccessException {
        try {
            getTimeStatusManager().createTimeStatuses(timeStatuses);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>updateTimeStatus</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatuses An array of time statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatuses is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException, DataAccessException {
        try {
            getTimeStatusManager().updateTimeStatuses(timeStatuses);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>deleteTimeStatus</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeStatuses(long[] timeStatusIds) throws BatchOperationException, DataAccessException {
        try {
            getTimeStatusManager().deleteTimeStatuses(timeStatusIds);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>getTimeStatus</code> method.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeStatusIds An array of timeStatusIds for which time status should be retrieved.
     * @return The TimeStatuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getTimeStatuses(long[] timeStatusIds) throws BatchOperationException, DataAccessException {
        try {
            return getTimeStatusManager().getTimeStatuses(timeStatusIds);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>TimeStatusFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for TimeEntries.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTimeStatuses</code> method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return the <code>TimeStatusFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for time statuses.
     *
     * @throws DataAccessException if unable to get the time status filter factory for any reason
     */
    public TimeStatusFilterFactory getTimeStatusFilterFactory() throws DataAccessException {
        try {
            return getTimeStatusManager().getTimeStatusFilterFactory();
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the TimeStatuses that are currently in the persistent store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getAllTimeStatuses() throws DataAccessException {
        try {
            return getTimeStatusManager().getAllTimeStatuses();
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
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
    public void ejbCreate() {
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
        this.context = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context
     * if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    public SessionContext getSessionContext() {
        return this.context;
    }

    /**
     * <p>
     * This method gets the current time status manager in the factory.
     * </p>
     *
     * @return the current time status manager in the factory
     *
     * @throws DataAccessException to wrap the <code>ConfigurationException</code> thrown by
     * <code>ManagerFactory</code>
     */
    private TimeStatusManager getTimeStatusManager() throws DataAccessException {
        try {
            return ManagerFactory.getTimeStatusManager();
        } catch (ConfigurationException e) {
            throw new DataAccessException("Unable to get the task type manager.", e);
        }
    }
}
