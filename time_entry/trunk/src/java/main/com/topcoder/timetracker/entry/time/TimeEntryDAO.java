/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of Time Tracker TimeEntry data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TimeEntryDAO {
    /**
     * <p>
     * Adds the specified time entries into the persistent store.
     * </p>
     *
     * <p>
     * Unique time entry ids will automatically be generated and assigned to the time entries.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
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
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeEntries array.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
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
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the TimeEntry with the
     * specified timeEntryIds.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
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
        DataAccessException;

    /**
     * <p>
     * Retrieves an array of TimeEntry objects that reflects the data in the persistent store on the
     * Time Tracker Time Entry with the specified Ids.
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
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws BatchOperationException,
        DataAccessException;

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
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException;

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
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory();

    /**
     * <p>
     * Retrieves all the TimeEntries that are currently in the persistent store.
     * </p>
     *
     * @return An array of all time entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException;
}
