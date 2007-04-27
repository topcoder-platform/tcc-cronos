/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage, and searching
 * of Time Tracker FixedBillingEntry data from a persistent store. It is also responsible for generating ids for any
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
public interface FixedBillingEntryDAO {
    /**
     * <p>
     * Adds the specified entries into the persistent store.  Unique  entry ids will automatically be generated and
     * assigned to  the entries.   There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1).  Otherwise, the implementation should throw
     * IllegalArgumentException.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void createFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided  entries array.  There is also
     * the option to perform an audit.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null or contains null values.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the  entry with the specified entryIds.
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entryIds An array of entryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void deleteFixedBillingEntries(long[] entryIds, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves an array of FixedBillingEntry objects that reflects the data in the persistent  store on the Time
     * Tracker FixedBilling Entry with the specified Ids.
     * </p>
     *
     * @param entryIds An array of timeEntryIds for which the operation should be performed.
     *
     * @return The TimeEntries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeEntryIds is null or contains values &lt;= 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingEntry[] getFixedBillingEntries(long[] entryIds)
        throws DataAccessException;

    /**
     * <p>
     * Searches the persistent store for any fixed billing entries  that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingEntryFilterFactory returned by getFixedBillingEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingEntryFilterFactory.
     * </p>
     *
     * @param filter The filter used to search for entries.
     *
     * @return The entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidFilterException if the filter cannot be recognized.
     */
    FixedBillingEntry[] searchFixedBillingEntries(Filter filter)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory();

    /**
     * <p>
     * Retrieves all the FixedBillingEntries that are currently in the persistent store.
     * </p>
     *
     * @return An array of all entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingEntry[] getAllFixedBillingEntries()
        throws DataAccessException;
}
