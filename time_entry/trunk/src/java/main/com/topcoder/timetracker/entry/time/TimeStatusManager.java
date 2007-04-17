/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a TimeStatus.
 * </p>
 *
 * <p>
 * CRUDE and search methods are provided to manage the TimeStatuses inside a persistent store.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for statuses based on different
 * search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TimeStatusManager {
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
     * @param timeStatus The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null or has id != -1.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeStatus(TimeStatus timeStatus) throws DataAccessException;

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
     * @param timeStatus The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null or has id &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeStatus(TimeStatus timeStatus) throws UnrecognizedEntityException,
        DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the time status
     * with the specified id.
     * </p>
     *
     * @param timeStatusId The id of the status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatusId &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeStatus(long timeStatusId) throws UnrecognizedEntityException,
        DataAccessException;

    /**
     * <p>
     * Retrieves a TimeStatus object that reflects the data in the persistent store on the Time Tracker
     * TimeStatus with the specified timeStatusId.
     * </p>
     *
     * @param timeStatusId The id of the status to retrieve.
     * @return The status with specified id.
     *
     * @throws IllegalArgumentException if timeStatusId &lt; 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus getTimeStatus(long timeStatusId) throws UnrecognizedEntityException,
        DataAccessException;

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
     * This is a batch version of the <code>createTimeStatus</code> method.
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
     * This is a batch version of the <code>updateTimeStatus</code> method.
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
     * This is a batch version of the <code>deleteTimeStatus</code> method.
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
     * This is a batch version of the <code>getTimeStatus</code> method.
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
    public TimeStatus[] getTimeStatuses(long[] timeStatusIds) throws BatchOperationException,
        DataAccessException;

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
     *
     * @throws DataAccessException if unable to get the time status filter factory for any reason
     */
    public TimeStatusFilterFactory getTimeStatusFilterFactory() throws DataAccessException;

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
