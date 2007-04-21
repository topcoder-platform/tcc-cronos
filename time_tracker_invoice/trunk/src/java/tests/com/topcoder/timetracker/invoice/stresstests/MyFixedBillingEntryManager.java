/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;

/**
 * A mocked implementation for testing.
 *
 * @author Chenhong
 * @version 1.0
 */
public class MyFixedBillingEntryManager implements FixedBillingEntryManager {

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#createFixedBillingEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry,
     *      boolean)
     */
    public void createFixedBillingEntry(FixedBillingEntry arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#updateFixedBillingEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry,
     *      boolean)
     */
    public void updateFixedBillingEntry(FixedBillingEntry arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#deleteFixedBillingEntry(long, boolean)
     */
    public void deleteFixedBillingEntry(long arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#getFixedBillingEntry(long)
     */
    public FixedBillingEntry getFixedBillingEntry(long arg0) throws DataAccessException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#searchFixedBillingEntries(com.topcoder.search.builder.filter.Filter)
     */
    public FixedBillingEntry[] searchFixedBillingEntries(Filter arg0) throws DataAccessException {
        return new FixedBillingEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#createFixedBillingEntries(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry[],
     *      boolean)
     */
    public void createFixedBillingEntries(FixedBillingEntry[] arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#updateFixedBillingEntries(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry[],
     *      boolean)
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] arg0, boolean arg1) throws DataAccessException {

    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#deleteFixedBillingEntries(long[],
     *      boolean)
     */
    public void deleteFixedBillingEntries(long[] arg0, boolean arg1) throws DataAccessException {

    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#getFixedBillingEntries(long[])
     */
    public FixedBillingEntry[] getFixedBillingEntries(long[] arg0) throws DataAccessException {

        return new FixedBillingEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#addRejectReasonToEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry,
     *      long, boolean)
     */
    public void addRejectReasonToEntry(FixedBillingEntry arg0, long arg1, boolean arg2) throws DataAccessException {

    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#removeRejectReasonFromEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry,
     *      long, boolean)
     */
    public void removeRejectReasonFromEntry(FixedBillingEntry arg0, long arg1, boolean arg2)
            throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#removeAllRejectReasonsFromEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry,
     *      boolean)
     */
    public void removeAllRejectReasonsFromEntry(FixedBillingEntry arg0, boolean arg1) throws DataAccessException {
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#getAllRejectReasonsForEntry(com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry)
     */
    public long[] getAllRejectReasonsForEntry(FixedBillingEntry arg0) throws DataAccessException {
        return null;
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#getAllFixedBillingEntries()
     */
    public FixedBillingEntry[] getAllFixedBillingEntries() throws DataAccessException {
        return new FixedBillingEntry[0];
    }

    /**
     * @see com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager#getFixedBillingEntryFilterFactory()
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory() {
        return new MyFixedBillingEntryFilterFactory();
    }

}