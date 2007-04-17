/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.delegate;

import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeEntryManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.InvalidCompanyException;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.db.Util;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerLocal;
import com.topcoder.timetracker.entry.time.ejb.TimeEntryManagerLocalHome;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.
 * </p>
 *
 * <p>
 * It is responsible for looking up the local interface of the SessionBean for <code>TimeEntryManager</code>,
 * and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TimeEntryManagerDelegate implements TimeEntryManager {
    /**
     * <p>
     * This is the local interface for the <code>TimeEntryManager</code> business services.
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
    private final TimeEntryManagerLocal local;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     * @throws IllegalArgumentException if namespace is null or an empty String.
     *
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public TimeEntryManagerDelegate(String namespace) throws ConfigurationException {
        TimeEntryManagerLocalHome home = (TimeEntryManagerLocalHome) Util.createEJBLocalHome(namespace,
            TimeEntryManagerLocalHome.class);

        this.local = home.create();
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
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has id != -1.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntry(TimeEntry entry, boolean audit) throws InvalidCompanyException, DataAccessException {
        local.createTimeEntry(entry, audit);
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
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null or has an id &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws InvalidCompanyException if the TaskType company id does not match with this one.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntry(TimeEntry entry, boolean audit) throws InvalidCompanyException,
        UnrecognizedEntityException, DataAccessException {
        local.updateTimeEntry(entry, audit);
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
     * @param entryId The entryId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryId &lt; 0.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntry(long entryId, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        local.deleteTimeEntry(entryId, audit);
    }

    /**
     * <p>
     * Retrieves a TimeEntry object that reflects the data in the persistent store on the Time Tracker
     * Time Entry with the specified entryId.
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
        return local.getTimeEntry(entrytId);
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
     * @param filter The filter used to search for TimeEntry.
     * @return The time entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException {
        return local.searchTimeEntries(filter);
    }

    /**
     * <p>
     * This is a batch version of the <code>createTimeEntry</code> method.
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
        local.createTimeEntries(timeEntries, audit);
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
        local.updateTimeEntries(timeEntries, audit);
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
        local.deleteTimeEntries(timeEntryIds, audit);
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
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @return The TimeEntries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws BatchOperationException, DataAccessException {
        return local.getTimeEntries(timeEntryIds);
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
        throws InvalidCompanyException, UnrecognizedEntityException, DataAccessException {
        local.addRejectReasonToEntry(entry, rejectReasonId, audit);
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
        local.removeRejectReasonFromEntry(entry, rejectReasonId, audit);
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
        local.removeAllRejectReasonsFromEntry(entry, audit);
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified <code>TimeEntry</code>.
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
        return local.getAllRejectReasonsForEntry(entry);
    }

    /**
     * <p>
     * Retrieves all the TimeEntries that are currently in the persistent
     * store.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException {
        return local.getAllTimeEntries();
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
     * @return the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for time entries.
     *
     * @throws DataAccessException if unable to get the time entry filter factory for any reason
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() throws DataAccessException {
        return local.getTimeEntryFilterFactory();
    }
}
