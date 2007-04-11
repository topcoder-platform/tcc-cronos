/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.entry.base.BaseEntry;

/**
 * <p>
 * This class holds the information of an ExpenseEntry. No parameter checking is provided in this
 * class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class ExpenseEntry extends BaseEntry {
    /**
     * Represents the amount of money the employee spent. Must be initialized by the user, using the
     * setAmount method. Valid values: non-negative.
     */
    private java.math.BigDecimal amount = null;

    /**
     * Represents the type of this entry. Initial value is null. Must be initialized by the user
     * using the setExpenseType method. Valid values: non-null.
     */
    private ExpenseType expenseType = null;

    /**
     * Represents the status of this entry. Initial value is null. Must be initialized by the user
     * using the setStatus method. Valid values: non-null.
     */
    private ExpenseStatus status = null;

    /**
     * Represents a flag to indicate whether the entry is billable to client. True means that the
     * entry is billable, false means that is not. If the billable field is true then the
     * corresponding column in the ExpenseEntries table will be 1. If the billable field is false
     * then the corresponding column in the ExpenseEntries table will be 0.
     */
    private boolean billable = false;

    //
    // /**
    // * <p>
    // * <strong>Purpose:</strong>
    // * </p>
    // * <p>
    // * This variable represents the invoice for this ExpenseEntry. It is set/initialized in the
    // * dedictaed setter and can also be accessed through a dedicated getter.
    // * </p>
    // * <p>
    // * Can be null if the expense has not yet been invoiced.
    // * </p>
    // * <p>
    // * </p>
    // *
    // */
    // private com.topcoder.timetracker.invoice.Invoice invoice;

    /**
     * Empty constructor.
     *
     */
    public ExpenseEntry() {
        // empty
    }

    /**
     * Create a new instance. Simply initialize the id field.
     *
     * @param id this value will be assigned to the id field
     */
    public ExpenseEntry(int id) {
        this.setId(id);
    }

    /**
     * Setter for the date field. If the argument is null, NullPointerException will be thrown.
     *
     * @param amount this value will be assigned to the amount field
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Setter for the expenseType field.
     *
     * @param expenseType this value will be assigned to the expenseType field
     */
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    /**
     * Setter for the status field.
     *
     * @param status this value will be assigned to the status field
     */
    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    /**
     * Setter for the billable field.
     *
     * @param billable this value will be assigned to the billable field
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * Getter for the amount field. Simply return the amount field.
     *
     * @return the amount field
     */
    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * Getter for the expenseType field. Simply return the expenseType field.
     *
     * @return the amount field
     */
    public ExpenseType getExpenseType() {
        return this.expenseType;
    }

    /**
     * Getter for the status field. Simply return the status field.
     *
     * @return the status field
     */
    public ExpenseStatus getStatus() {
        return this.status;
    }

    /**
     * This method simply returns the billable field. Using this method a user can find out if the
     * entry is billable or not.
     *
     * @return the billable field
     */
    public boolean isBillable() {
        return this.billable;
    }

    // /**
    // * <p>
    // * <strong>Purpose:</strong>
    // * </p>
    // * <p>
    // * Set the invoice for this expense entry. Can accept null, which would mean un-invoiced
    // * expense.
    // * </p>
    // * <p>
    // * </p>
    // *
    // * @poseidon-object-id [Im302568b8m110ec7791aamm140]
    // * @param invoice invoice //
    // */
    // // public void setInvoice(Invoice invoice) {
    // // // your code here
    // // return null;
    // // }
    // /**
    // * <p>
    // * <strong>Purpose:</strong>
    // * </p>
    // * <p>
    // * Return the current instance of Invoice associatied with this ExpenseEntry. Could possibly
    // * return a null if the invoice is not set.
    // * </p>
    // *
    // * @poseidon-object-id [Im302568b8m110ec7791aammc6]
    // * @return current invoice
    // */
    // // public com.topcoder.timetracker.invoice.Invoice getInvoice() {
    // // // your code here
    // // return null;
    // // }
}
