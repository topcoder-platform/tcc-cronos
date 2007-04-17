/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the association between
 * Time Tracker Entries and the Reject Reasons.
 * </p>
 *
 * <p>
 * Simple CRUDE methods are specified.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TimeEntryRejectReasonDAO {
    /**
     * <p>
     * This adds a <code>RejectReason</code> with the specified id to the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param timeEntry The TimeEntry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void addRejectReasonToEntry(long rejectReasonId, TimeEntry timeEntry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This removes a <code>RejectReason</code> with the specified id from the specified <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param rejectReasonId the id of the rejectReason for which the operation will be performed.
     * @param timeEntry the TimeEntry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeRejectReasonFromEntry(long rejectReasonId, TimeEntry timeEntry, boolean audit)
        throws DataAccessException;

    /**
     * <p>
     * This retrieves all RejectReasons for the specified <code>TimeEntry</code>.
     * </p>
     *
     * @param timeEntryId the id of the TimeEntry for which the operation will be performed.
     * @return The RejectReasons corresponding to given entry.
     *
     * @throws IllegalArgumentException if timeEntryId is &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] getAllRejectReasonsForEntry(long timeEntryId) throws DataAccessException;

    /**
     * <p>
     * This removes all RejectReasons from the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param timeEntry the TimeEntry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry timeEntry, boolean audit) throws DataAccessException;
}
