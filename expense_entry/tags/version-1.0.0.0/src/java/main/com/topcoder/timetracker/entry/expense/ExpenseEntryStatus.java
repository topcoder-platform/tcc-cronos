/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

/**
 * <p>
 * Defines the information contained by an expense entry status. It has no more information than the common information.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 * @see CommonInfo
 */
public class ExpenseEntryStatus extends CommonInfo {
    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryStatus</code> class. The unique ID is generated when persisting.
     * </p>
     */
    public ExpenseEntryStatus() {
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryStatus</code> class. The unique ID is given.
     * </p>
     *
     * @param id the unique ID of this expense entry status.
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public ExpenseEntryStatus(int id) {
        super(id);
    }
}






