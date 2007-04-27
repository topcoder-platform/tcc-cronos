/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details involving a
 * <code>FixedBillingStatus</code>. CRUDE and search methods are provided to manage the FixedBillingStatuses inside a
 * persistent store.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for statuses based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingStatusManager {
    /**
     * <p>
     * Defines a FixedBillingStatus to be recognized within the persistent store managed by this utility. A unique
     * status id will automatically be generated and assigned to the status.
     * </p>
     *
     * <p>
     * The implementation will set the status' creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1). Otherwise, the implementation should throw
     * IllegalArgumentException.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void createFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingStatus parameter.
     * </p>
     *
     * <p>
     * The implementation will set the status' modification details to the current date.   These modification details
     * will also reflect in the persistent store. The modification user is the responsibility of the calling
     * application.
     * </p>
     *
     * @param status The status for which the operation should be performed.
     *
     * @throws IllegalArgumentException if status is null.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingStatus(FixedBillingStatus status)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the FixedBillingstatus  with the specified
     * id.
     * </p>
     *
     * @param statusId The id of the status for which the operation should be performed.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException if statusId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void deleteFixedBillingStatus(long statusId)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves a FixedBillingStatus object that reflects the data in the persistent store  with the specified
     * statusId.
     * </p>
     *
     * @param statusId The id of the status to retrieve.
     *
     * @return The status with specified id.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingStatus getFixedBillingStatus(long statusId)
        throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any FixedBillingStatuses that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingStatusFilterFactory returned by getFixedBillingStatusEntryFilterFactory  of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search  Builder component) that
     * combines the filters created using FixedBillingStatusFilterFactory.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     *
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidFilterException if the filter cannot be recognized.
     */
    FixedBillingStatus[] searchFixedBillingStatuses(Filter criteria)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the createFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void createFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the updateFixedBillingStatus method.
     * </p>
     *
     * <p>
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param statuses An array of  statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the deleteFixedBillingStatus method.
     * </p>
     *
     * @param statusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void deleteFixedBillingStatuses(long[] statusIds)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the getFixedBillingStatus  method.
     * </p>
     *
     * @param statusIds An array of statusIds for which time status should be retrieved.
     *
     * @return The statuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingStatus[] getFixedBillingStatuses(long[] statusIds)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching
     * for FixedBillingStatuses.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingStatuses method.
     * </p>
     *
     * @throws DataAccessException if a problem occurs.
     * @return the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching for
     *         FixedBillingStatuses.
     */
    FixedBillingStatusFilterFactory getFixedBillingStatusFilterFactory()
        throws DataAccessException;

    /**
     * <p>
     * Retrieves all the FixedBillingStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of statuses retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingStatus[] getAllFixedBillingStatuses()
        throws DataAccessException;
}
