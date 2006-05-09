/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

/**
 * <p>
 * Defines the information contained by an expense entry status. It has no more information than the basic information.
 * </p>
 *
 * <p>
 * Changes in 1.1: this class will extend <code>BasicInfo</code> instead of <code>CommonInfo</code>.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @author DanLazar, visualage
 * @version 1.1
 *
 * @since 1.0
 */
public class ExpenseEntryStatus extends BasicInfo {
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
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public ExpenseEntryStatus(int id) {
        super(id);
    }
}
