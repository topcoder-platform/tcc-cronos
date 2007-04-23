/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeEntryManager;

/**
 * Mock for <code>TimeEntryManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockTimeEntryManager implements TimeEntryManager {

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param rejectReasonId
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void addRejectReasonToEntry(TimeEntry entry, long rejectReasonId, boolean audit)
        throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param timeEntries
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void createTimeEntries(TimeEntry[] timeEntries, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void createTimeEntry(TimeEntry entry, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param timeEntryIds
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void deleteTimeEntries(long[] timeEntryIds, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param entryId
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void deleteTimeEntry(long entryId, boolean audit) throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not happen
     */
    public long[] getAllRejectReasonsForEntry(TimeEntry entry) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws DataAccessException
     *             not happen
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param timeEntryIds
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not happen
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entrytId
     *            not used
     *
     * @return new TimeEntry
     *
     * @throws DataAccessException
     *             not happen
     */
    public TimeEntry getTimeEntry(long entrytId) throws DataAccessException {
        return new TimeEntry();
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry entry, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param rejectReasonsId
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void removeRejectReasonFromEntry(TimeEntry entry, long rejectReasonsId, boolean audit)
        throws DataAccessException {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param filter
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not happen
     */
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException {
        return null;
    }

    /**
     * Mock method.
     *
     * @param timeEntries
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void updateTimeEntries(TimeEntry[] timeEntries, boolean audit) throws DataAccessException {
        // nothing to do

    }

    /**
     * Mock method.
     *
     * @param entry
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not happen
     */
    public void updateTimeEntry(TimeEntry entry, boolean audit) throws DataAccessException {
        // nothing to do

    }

}
