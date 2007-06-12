/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.InvalidCompanyException;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage TimeEntries
 * within the Time Tracker Application.
 * </p>
 *
 * <P>
 * It contains the same methods as <code>TimeEntryManager</code>, and delegates to
 * an instance of <code>TimeEntryManager</code>. The instance of <code>TimeEntryManager</code>
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
public class TimeEntryManagerSessionBean implements TimeEntryManager, SessionBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 5392094400675078069L;

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
    public TimeEntryManagerSessionBean() {
        // empty
    }

    /**
     * <p>
     * Defines a time entry to be recognized within the persistent store managed by this manager.
     * </p>
     *
     * <p>
     * A unique time entry id will automatically be generated and assigned to the time entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * It will set the TimeEntry's creation and modification date to the current date.
     * These creation/modification details will also reflect in the persistent store.
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has id != -1.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntry(TimeEntry entry, boolean audit) throws InvalidCompanyException, DataAccessException {
        try {
            getTimeEntryManager().createTimeEntry(entry, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeEntry parameter.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * It will set the Entry's modification details to the current date.
     * These modification details will also reflect in the persistent store.
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has an id &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntry(TimeEntry entry, boolean audit) throws UnrecognizedEntityException,
        InvalidCompanyException, DataAccessException {
        try {
            getTimeEntryManager().updateTimeEntry(entry, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the time entry
     * with the specified id.
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
     * @param entryId The entryId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryId &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntry(long entryId, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        try {
            getTimeEntryManager().deleteTimeEntry(entryId, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a TimeEntry object that reflects the data in the persistent store on the Time Tracker
     * Time Entry with the specified entryId.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param entrytId The id of the entry to retrieve.
     * @return The entry with specified id.
     *
     * @throws IllegalArgumentException if entryId &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry getTimeEntry(long entrytId) throws UnrecognizedEntityException, DataAccessException {
        try {
            return getTimeEntryManager().getTimeEntry(entrytId);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any time entries that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TimeEntryFilterFactory</code> returned by <code>getTimeEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TimeEntryFilterFactory</code>.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param filter The filter used to search for TimeEntry.
     * @return The time entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException {
        try {
            return getTimeEntryManager().searchTimeEntries(filter);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>createTimeEntry</code> method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        try {
            getTimeEntryManager().createTimeEntries(timeEntries, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>updateTimeEntry</code> method.
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
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        try {
            getTimeEntryManager().updateTimeEntries(timeEntries, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>deleteTimeEntry</code> method.
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
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntries(long[] timeEntryIds, boolean audit) throws BatchOperationException,
        DataAccessException {
        try {
            getTimeEntryManager().deleteTimeEntries(timeEntryIds, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>getTimeEntry</code> method.
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
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @return The TimeEntries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws BatchOperationException, DataAccessException {
        try {
            return getTimeEntryManager().getTimeEntries(timeEntryIds);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This adds a <code>RejectReason</code> with the specified id to the specified TimeEntry.
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
     * @param entry The TimeEntry for which the operation should be performed.
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if a rejectReason or TimeEntry was not found with specified id.
     * @throws InvalidCompanyException if the TaskType company id does not match with RejectReason company id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void addRejectReasonToEntry(TimeEntry entry, long rejectReasonId, boolean audit)
        throws UnrecognizedEntityException, InvalidCompanyException, DataAccessException {
        try {
            getTimeEntryManager().addRejectReasonToEntry(entry, rejectReasonId, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This removes a <code>RejectReason</code> with the specified id from the specified <code>TimeEntry</code>.
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
     * @param entry The user for which the operation should be performed.
     * @param rejectReasonId The rejectReason to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if a rejectReason or TimeEntry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeRejectReasonFromEntry(TimeEntry entry, long rejectReasonId, boolean audit)
        throws UnrecognizedEntityException, DataAccessException {
        try {
            getTimeEntryManager().removeRejectReasonFromEntry(entry, rejectReasonId, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This removes all RejectReasons from the specified TimeEntry.
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
     * @param entry The TimeEntry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntry is null or has id &lt; 0
     * @throws UnrecognizedEntityException if the given TimeEntry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry entry, boolean audit) throws UnrecognizedEntityException,
        DataAccessException {
        try {
            getTimeEntryManager().removeAllRejectReasonsFromEntry(entry, audit);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param entry The TimeEntry to retrieve RejectReasons from.
     * @return An array of RejectReason ids for the given TimeEntry.
     *
     * @throws IllegalArgumentException if timeEntryId is &lt; 0
     * @throws UnrecognizedEntityException if a TimeEntry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] getAllRejectReasonsForEntry(TimeEntry entry) throws UnrecognizedEntityException, DataAccessException {
        try {
            return getTimeEntryManager().getAllRejectReasonsForEntry(entry);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the TimeEntries that are currently in the persistent
     * store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException {
        try {
            return getTimeEntryManager().getAllTimeEntries();
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for TimeEntries.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTimeEntries</code> method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for time entries.
     *
     * @throws DataAccessException if unable to get the time entry filter factory for any reason
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() throws DataAccessException {
        try {
            return getTimeEntryManager().getTimeEntryFilterFactory();
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
    protected SessionContext getSessionContext() {
        return context;
    }

    /**
     * <p>
     * This method gets the current time entry manager in the factory.
     * </p>
     *
     * @return the current time entry manager in the factory
     *
     * @throws DataAccessException to wrap the <code>ConfigurationException</code> thrown by
     * <code>ManagerFactory</code>
     */
    private TimeEntryManager getTimeEntryManager() throws DataAccessException {
        try {
            return ManagerFactory.getTimeEntryManager();
        } catch (ConfigurationException e) {
            throw new DataAccessException("Unable to get the task type manager.", e);
        }
    }
}
