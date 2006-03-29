/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

/**
 * <p>
 * Defines the information contained by an expense entry type. It has no more information than the basic information.
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
public class ExpenseEntryType extends BasicInfo {
    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryType</code> class. The unique ID is generated when persisting.
     * </p>
     */
    public ExpenseEntryType() {
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryType</code> class. The unique ID is given.
     * </p>
     *
     * @param id the unique ID of this expense entry type.
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public ExpenseEntryType(int id) {
        super(id);
    }
}
