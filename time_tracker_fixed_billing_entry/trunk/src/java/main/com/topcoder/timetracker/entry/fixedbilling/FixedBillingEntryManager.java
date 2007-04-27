/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details involving a Time
 * Tracker Fixed Billing Entry. CRUDE and search methods are provided to manage the Entries inside a persistent store.
 * There are also methods for the manipulation of RejectReasons associated with the <code>FixedBillingEntry</code>.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for entries based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingEntryManager {
    /**
     * <p>
     * Defines a fixed billing entry to be recognized within the persistent store managed by this utility.   A unique
     * fixed billing entry id will automatically be generated and assigned to the entry.   There is also the option to
     * perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Entry's creation and modification date to the current date.   These
     * creation/modification details will also reflect in the persistent store. The creation and modification user is
     * the responsibility of the calling application.
     * </p>
     *
     * <p>
     * The entity should be provided with no id set (id = -1).  Otherwise, the implementation should throw
     * IllegalArgumentException.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null, or has an invalid entry provided.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void createFixedBillingEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided FixedBillingEntry parameter.
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * The implementation will set the Entry's modification details to the current date.   These modification details
     * will also reflect in the persistent store. The modification user is the responsibility of the calling
     * application.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entry is null.
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the fixed billing  entry with the specified
     * id.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit. The creation and modification user is the responsibility of the
     * calling application.
     * </p>
     *
     * @param entryId The entryId for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws IllegalArgumentException if entryId &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void deleteFixedBillingEntry(long entryId, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves a FixedBillingEntry object that reflects the data in the persistent store  with the specified entryId.
     * </p>
     *
     * @param entrytId The id of the entry to retrieve.
     *
     * @return The entry with specified id.
     *
     * @throws UnrecognizedEntityException if a entry with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingEntry getFixedBillingEntry(long entrytId)
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
     * This is a batch version of the createFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
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
     * This is a batch version of the updateFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the deleteFixedBillingEntry method.
     * </p>
     *
     * <p>
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param entryIds An array of entryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void deleteFixedBillingEntries(long[] entryIds, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This is a batch version of the getFixedBillingEntry method.
     * </p>
     *
     * @param entryIds An array of entryIds for which entries should be retrieved.
     *
     * @return The entries corresponding to the provided ids.
     *
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws UnrecognizedEntityException if an entry  with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingEntry[] getFixedBillingEntries(long[] entryIds)
        throws DataAccessException;

    /**
     * <p>
     * This adds RejectReason with the specified id  to the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any parameter is null.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidCompanyException if the companyId of the entry and RejectReason do not match.
     */
    void addRejectReasonToEntry(FixedBillingEntry entry, long rejectReasonId, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This removes a RejectReason with the specified id  from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param rejectReasonsId The rejectReason to remove.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException if either argument is null.
     */
    void removeRejectReasonFromEntry(FixedBillingEntry entry, long rejectReasonsId, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This removes all RejectReasons from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry The entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the entry is null.
     * @throws UnrecognizedEntityException if the given entry was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void removeAllRejectReasonsFromEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This retrieves all RejectReasons for the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.  If an audit is specified, then the audit must be rolled back in
     * the case that the operation fails.
     * </p>
     *
     * @param entry The entry to retrieve RejectReasons from.
     *
     * @return An array of RejectReason ids for the given entry.
     *
     * @throws IllegalArgumentException if the entry is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws UnrecognizedEntityException if a entry was not found in the data store.
     */
    long[] getAllRejectReasonsForEntry(FixedBillingEntry entry)
        throws DataAccessException;

    /**
     * <p>
     * Retrieves all the FixedBillingEntries that are currently in the persistent store.
     * </p>
     *
     * @return An array of fixed billing entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    FixedBillingEntry[] getAllFixedBillingEntries()
        throws DataAccessException;

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @throws DataAccessException if a problem occurs.
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory()
        throws DataAccessException;
}
