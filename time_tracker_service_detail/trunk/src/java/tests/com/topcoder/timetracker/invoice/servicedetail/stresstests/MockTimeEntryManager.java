/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeEntryManager;

/**
 * This is a mock implementation of <code>TimeEntryManager</code>. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockTimeEntryManager implements TimeEntryManager {

    /**
     * The default constructor.
     */
    public MockTimeEntryManager() {
    }

    /**
     * Returns directly.
     *
     * @param entry
     *            ignored
     * @param audit
     *            ignored
     */
    public void createTimeEntry(TimeEntry entry, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param entry
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateTimeEntry(TimeEntry entry, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param entryId
     *            ignored
     * @param audit
     *            ignored
     */
    public void deleteTimeEntry(long entryId, boolean audit) {
    }

    /**
     * Returns a <code>TimeEntry</code>.
     *
     * @param entryId
     *            the id of the <code>TimeEntry</code>
     * @return a <code>TimeEntry</code>
     */
    public TimeEntry getTimeEntry(long entryId) {
        TimeEntry entry = new TimeEntry();
        entry.setId(entryId);
        return entry;
    }

    /**
     * Returns null.
     *
     * @param filter
     *             ignored
     * @return null
     */
    public TimeEntry[] searchTimeEntries(Filter filter) {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param timeEntries
     *            ignored
     * @param audit
     *            ignored
     */
    public void createTimeEntries(TimeEntry[] timeEntries, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param timeEntries
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateTimeEntries(TimeEntry[] timeEntries, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param timeEntryIds
     *            ignored
     * @param audit
     *            ignored
     */
    public void deleteTimeEntries(long[] timeEntryIds, boolean audit) {
    }

    /**
     * Returns null.
     *
     * @param timeEntryIds
     *             ignored
     * @return null
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param entry
     *            ignored
     * @param rejectReasonId
     *            ignored
     * @param audit
     *            ignored
     */
    public void addRejectReasonToEntry(TimeEntry entry, long rejectReasonId, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param entry
     *            ignored
     * @param rejectReasonId
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeRejectReasonFromEntry(TimeEntry entry, long rejectReasonId, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param entry
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry entry, boolean audit) {
    }

    /**
     * Returns null.
     *
     * @param entry
     *             ignored
     * @return null
     */
    public long[] getAllRejectReasonsForEntry(TimeEntry entry) {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null
     */
    public TimeEntry[] getAllTimeEntries() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() {
        return null;
    }
}
