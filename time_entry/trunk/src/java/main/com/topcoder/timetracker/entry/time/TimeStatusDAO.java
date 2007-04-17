/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of TimeStatus data from a persistent store.
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
public interface TimeStatusDAO {
    /**
     * <p>
     * Defines a set of time statuses to be recognized within the persistent store managed by this DAO.
     * </p>
     *
     * <p>
     * A unique time status id will automatically be generated and assigned to the time statuses.
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
    public void createTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException,
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeStatuses parameter.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
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
    public void updateTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException,
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the time status
     * with the specified ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeStatusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeStatuses(long[] timeStatusIds) throws BatchOperationException,
        DataAccessException;

    /**
     * <p>
     * Retrieves an array of TimeStatus objects that reflects the data in the persistent
     * store on the TimeStatus with the specified Ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeStatusIds An array of timeStatusIds for which time status should be retrieved.
     * @return The TimeStatuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getTimeStatuses(long[] timeStatusIds) throws DataAccessException;

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
     * @param criteria The filter used to search for statuses.
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] searchTimeStatuses(Filter criteria) throws DataAccessException;

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
     * @return the <code>TimeStatusFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for time statuses.
     */
    public TimeStatusFilterFactory getTimeStatusFilterFactory();

    /**
     * <p>
     * Retrieves all the TimeStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getAllTimeStatuses() throws DataAccessException;
}
