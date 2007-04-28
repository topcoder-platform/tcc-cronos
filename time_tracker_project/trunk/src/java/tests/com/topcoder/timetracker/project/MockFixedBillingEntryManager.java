/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;

/**
 * <p>
 * This class implements FixedBillingEntryManager interface.
 * It is only used for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockFixedBillingEntryManager implements FixedBillingEntryManager {
    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @param arg0 not used
     * @param arg1 not used
     */
    public void createFixedBillingEntry(FixedBillingEntry arg0, boolean arg1) {
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
    public void updateFixedBillingEntry(FixedBillingEntry arg0, boolean arg1) {
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
    public void deleteFixedBillingEntry(long arg0, boolean arg1) {
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
    public FixedBillingEntry getFixedBillingEntry(long arg0) {
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
    public FixedBillingEntry[] searchFixedBillingEntries(Filter arg0) {
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
    public void createFixedBillingEntries(FixedBillingEntry[] arg0, boolean arg1) {
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
    public void updateFixedBillingEntries(FixedBillingEntry[] arg0, boolean arg1) {
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
    public void deleteFixedBillingEntries(long[] arg0, boolean arg1) {
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
    public FixedBillingEntry[] getFixedBillingEntries(long[] arg0) {
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
    public void addRejectReasonToEntry(FixedBillingEntry arg0, long arg1, boolean arg2) {
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
    public void removeRejectReasonFromEntry(FixedBillingEntry arg0, long arg1, boolean arg2) {
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
    public void removeAllRejectReasonsFromEntry(FixedBillingEntry arg0, boolean arg1) {
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
    public long[] getAllRejectReasonsForEntry(FixedBillingEntry arg0) {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null array
     */
    public FixedBillingEntry[] getAllFixedBillingEntries() {
        return null;
    }

    /**
     * <p>
     * Not implemented.
     * </p>
     *
     * @return null array
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory() {
        return null;
    }

}
