/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the association between Time Tracker
 * FixedBillingEntries and the Reject Reasons. Simple CRUDE methods are specified.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations are requiredto be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingEntryRejectReasonDAO {
    /**
     * <p>
     * This adds RejectReason with the specified id  to the specified entry.
     * </p>
     *
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param entry The id of entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id is &lt;= 0.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void addRejectReasonToEntry(long rejectReasonId, FixedBillingEntry entry, boolean audit)
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
     * @param rejectReasonId the id of the rejectReason for which the operation will be performed.
     * @param entry the id of the entry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id is &lt;= 0.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    void removeRejectReasonFromEntry(long rejectReasonId, FixedBillingEntry entry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This retrieves all RejectReasons for the specified entry.
     * </p>
     *
     * @param entryId the id of the entry for which the operation will be performed.
     *
     * @return The RejectReasons corresponding to given entry.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    long[] getAllRejectReasonsForEntry(long entryId)
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
     * @param entry the id of the entry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException if entryId &lt;= 0.
     */
    void removeAllRejectReasonsFromEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException;
}
