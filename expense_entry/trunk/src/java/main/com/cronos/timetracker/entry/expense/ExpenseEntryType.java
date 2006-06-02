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
 * @author arylio
 *
 * @version 2.0
 *
 * @since 1.0
 */
public class ExpenseEntryType extends BasicInfo {
    /**
     * Represetns the company id this expense entry type associated to.
     * @since 2.0
     */
    private int companyId = -1;

    /**
     * Represents the active status of this expense type.
     * @since 2.0
     */
    private boolean active = false;

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

    /**
     * <p>
     * The company id this expense entry associated to.
     * </p>
     *
     * @return the company id.
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Set the company id.
     * </p>
     *
     * @param companyId the company id to set.
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public void setCompanyId(int companyId) {
        ExpenseEntryHelper.validateId(companyId);

        this.companyId = companyId;
    }

    /**
     * <p>
     * The active this expense entry associated to.
     * </p>
     *
     * @return the active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Set the active.
     * </p>
     *
     * @param active the active to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
