/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeEntryManager;

/**
 * <p>
 * This class implements TimeEntryManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockTimeEntryManager implements TimeEntryManager {
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void createTimeEntry(TimeEntry arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void updateTimeEntry(TimeEntry arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void deleteTimeEntry(long arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @return null array
     */
    public TimeEntry getTimeEntry(long arg0) {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @return null array
     */
    public TimeEntry[] searchTimeEntries(Filter arg0) {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void createTimeEntries(TimeEntry[] arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void updateTimeEntries(TimeEntry[] arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void deleteTimeEntries(long[] arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @return null array
     */
    public TimeEntry[] getTimeEntries(long[] arg0) {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     * @param arg2 not used
     */
    public void addRejectReasonToEntry(TimeEntry arg0, long arg1, boolean arg2) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     * @param arg2 not used
     */
    public void removeRejectReasonFromEntry(TimeEntry arg0, long arg1, boolean arg2) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry arg0, boolean arg1) {
        // empty

    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @return null array
     */
    public long[] getAllRejectReasonsForEntry(TimeEntry arg0) {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null array
     */
    public TimeEntry[] getAllTimeEntries() {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null array
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() {
        return null;
    }

}