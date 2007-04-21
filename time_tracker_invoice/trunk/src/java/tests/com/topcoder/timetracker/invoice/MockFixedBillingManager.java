/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;

/**
 * Mock for <code>FixedBillingEntryManager</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class MockFixedBillingManager implements FixedBillingEntryManager {

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
     *             not thrown
     */
    public void addRejectReasonToEntry(FixedBillingEntry entry, long rejectReasonId, boolean audit)
        throws DataAccessException {

    }

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     */
    public void createFixedBillingEntries(FixedBillingEntry[] entries, boolean audit) throws DataAccessException {

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
     *             not thrown
     */
    public void createFixedBillingEntry(FixedBillingEntry entry, boolean audit) throws DataAccessException {

    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     */
    public void deleteFixedBillingEntries(long[] entryIds, boolean audit) throws DataAccessException {

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
     *             not thrown
     */
    public void deleteFixedBillingEntry(long entryId, boolean audit) throws DataAccessException {

    }

    /**
     * Mock method.
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     */
    public FixedBillingEntry[] getAllFixedBillingEntries() throws DataAccessException {

        return null;
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
     *             not thrown
     */
    public long[] getAllRejectReasonsForEntry(FixedBillingEntry entry) throws DataAccessException {

        return null;
    }

    /**
     * Mock method.
     *
     * @param entryIds
     *            not used
     *
     * @return empty array
     *
     * @throws DataAccessException
     *             not thrown
     */
    public FixedBillingEntry[] getFixedBillingEntries(long[] entryIds) throws DataAccessException {
        return new FixedBillingEntry[0];
    }

    /**
     * Mock method.
     *
     * @param entryId
     *            not used
     *
     * @return null
     *
     * @throws DataAccessException
     *             not thrown
     */
    public FixedBillingEntry getFixedBillingEntry(long entryId) throws DataAccessException {

        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory() {
        return new MockFixedBillingEntryFilterFactory();
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
     *             not thrown
     */
    public void removeAllRejectReasonsFromEntry(FixedBillingEntry entry, boolean audit) throws DataAccessException {

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
     *             not thrown
     */
    public void removeRejectReasonFromEntry(FixedBillingEntry entry, long rejectReasonsId, boolean audit)
        throws DataAccessException {

    }

    /**
     * Mock method.
     *
     * @param filter
     *            not used
     *
     * @return an array contains one element
     *
     * @throws DataAccessException
     *             not thrown
     */
    public FixedBillingEntry[] searchFixedBillingEntries(Filter filter) throws DataAccessException {
        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        return new FixedBillingEntry[] {fixedBillingEntry};
    }

    /**
     * Mock method.
     *
     * @param entries
     *            not used
     * @param audit
     *            not used
     *
     * @throws DataAccessException
     *             not thrown
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit) throws DataAccessException {

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
     *             not thrown
     */
    public void updateFixedBillingEntry(FixedBillingEntry entry, boolean audit) throws DataAccessException {

    }

}
