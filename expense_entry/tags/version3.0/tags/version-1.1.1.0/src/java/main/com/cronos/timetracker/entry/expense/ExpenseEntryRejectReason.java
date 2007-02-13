/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

/**
 * <p>
 * Defines the information contained by a reject reason type. It has no more information than the common information.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @version 1.1
 */
public class ExpenseEntryRejectReason extends CommonInfo {
    /** Represents the id of the reject reason. */
    private int rejectReasonId = 0;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryRejectReason</code> class with given id.
     * </p>
     *
     * @param rejectReasonId the reject reason id.
     */
    public ExpenseEntryRejectReason(int rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    /**
     * Sets the reject reason id.
     *
     * @param rejectReasonId the reject reason id to set
     */
    public void setRejectReasonId(int rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    /**
     * Gets the reject reason id.
     *
     * @return the reject reason id.
     */
    public int getRejectReasonId() {
        return rejectReasonId;
    }
}
