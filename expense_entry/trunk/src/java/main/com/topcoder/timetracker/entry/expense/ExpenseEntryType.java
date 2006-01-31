/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

/**
 * <p>
 * Defines the information contained by an expense entry type. It has no more information than the common information.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 * @see CommonInfo
 */
public class ExpenseEntryType extends CommonInfo {
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
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public ExpenseEntryType(int id) {
        super(id);
    }
}






