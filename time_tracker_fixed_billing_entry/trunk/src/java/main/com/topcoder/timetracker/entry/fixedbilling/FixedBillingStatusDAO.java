/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and searching
 * of <code>FixedBillingStatus</code> data from a persistent store. It is also responsible for generating ids for any
 * entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingStatusDAO {
    /**
     * <p>
     * Defines a set of FixedBillingStatuses to be recognized within the persistent store managed by this utility.   A
     * unique status id will automatically be generated and assigned to the statuses.
     * </p>
     *
     * <p>
     * The implementation will set the status' creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1).  Otherwise, the implementation should throw
     * IllegalArgumentException.
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
     * Defines a set of FixedBillingStatuses to be recognized within the persistent store managed by this utility.   A
     * unique status id will automatically be generated and assigned to the statuses.
     * </p>
     *
     * <p>
     * The implementation will set the status' modification date to the current date.   These modification details will
     * also reflect in the persistent store. The modification user is the responsibility of the calling application.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1).  Otherwise, the implementation should throw
     * IllegalArgumentException.
     * </p>
     *
     * @param statuses An array of  statuses for which the operation should be performed.
     *
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws IllegalArgumentException if statuses is null or contains null values.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingStatuses(FixedBillingStatus[] statuses)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data  in the provided FixedBillingStatuses array
     * parameter.
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
     * Retrieves an array of FixedBillingStatus objects that reflects the data  in the persistent  store on the status
     * with the specified Ids.
     * </p>
     *
     * @param statusIds An array of statusIds for which status should be retrieved.
     *
     * @return The statuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if statusIds is null or contains values &lt;= 0.
     * @throws UnrecognizedEntityException if a status with the provided id was not found in the data store.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs.
     */
    FixedBillingStatus[] getFixedBillingStatuses(long[] statusIds)
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
     * Retrieves the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching
     * for FixedBillingStatuses.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingStatuses method.
     * </p>
     *
     * @return the FixedBillingStatusFilterFactory that is capable of creating SearchFilters to use when searching for
     *         FixedBillingStatuses.
     */
    FixedBillingStatusFilterFactory getFixedBillingStatusFilterFactory();

    /**
     * <p>
     * Retrieves all the FixedBillingStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of fixed billing status retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingStatus[] getAllFixedBillingStatuses()
        throws DataAccessException;
}
