/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.math.BigDecimal;

import java.util.Date;

/**
 * <p>
 * Defines the information contained by an expense entry. In addition to common information, an expense entry also
 * contains the date, amount of money, the type, the current status and a flag indicating whether the client should be
 * billed.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 * @see CommonInfo
 */
public class ExpenseEntry extends CommonInfo {
    /** Represents the date of this expense entry. */
    private Date date = null;

    /** Represents the amount of money of this expense. It cannot be negative. */
    private BigDecimal amount = null;

    /** Represents the type of this expense. */
    private ExpenseEntryType expenseType = null;

    /** Represents the current status of this expense. */
    private ExpenseEntryStatus status = null;

    /** Represents a flag indicating whether the client should be billed for this expense. */
    private boolean billable = false;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntry</code> class. The unique ID is generated when persisting.
     * </p>
     */
    public ExpenseEntry() {
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntry</code> class. The unique ID is given.
     * </p>
     *
     * @param id the unique ID of this expense entry.
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public ExpenseEntry(int id) {
        super(id);
    }

    /**
     * <p>
     * Sets the date of this expense.
     * </p>
     *
     * @param date the date of this expense.
     * @throws NullPointerException if <code>date</code> is <code>null</code>.
     */
    public void setDate(Date date) {
        ExpenseEntryHelper.validateNotNull(date, "date");

        this.date = date;
    }

    /**
     * <p>
     * Sets the amount of money of this expense.
     * </p>
     *
     * @param amount the amount of money of this expense.
     * @throws NullPointerException if <code>amount</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>amount</code> is negative.
     */
    public void setAmount(BigDecimal amount) {
        ExpenseEntryHelper.validateNotNull(amount, "amount");

        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount cannot be negative.");
        }

        this.amount = amount;
    }

    /**
     * <p>
     * Sets the type of this expense.
     * </p>
     *
     * @param expenseType the type of this expense.
     * @throws NullPointerException if <code>expenseType</code> is <code>null</code>.
     */
    public void setExpenseType(ExpenseEntryType expenseType) {
        ExpenseEntryHelper.validateNotNull(expenseType, "expenseType");

        this.expenseType = expenseType;
    }

    /**
     * <p>
     * Sets the current status of this expense.
     * </p>
     *
     * @param status the current status of this expense.
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     */
    public void setStatus(ExpenseEntryStatus status) {
        ExpenseEntryHelper.validateNotNull(status, "status");

        this.status = status;
    }

    /**
     * <p>
     * Sets a flag indicating whether the client should be billed for this expense.
     * </p>
     *
     * @param billable a flag indicating whether the client should be billed for this expense.
     */
    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    /**
     * <p>
     * Gets the date of this expense.
     * </p>
     *
     * @return the date of this expense.
     */
    public Date getDate() {
        return date;
    }

    /**
     * <p>
     * Gets the amount of money of this expense.
     * </p>
     *
     * @return the amount of money of this expense.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * <p>
     * Gets the type of this expense.
     * </p>
     *
     * @return the type of this expense.
     */
    public ExpenseEntryType getExpenseType() {
        return expenseType;
    }

    /**
     * <p>
     * Gets the current status of this expense.
     * </p>
     *
     * @return the current status of this expense.
     */
    public ExpenseEntryStatus getStatus() {
        return status;
    }

    /**
     * <p>
     * Gets a flag indicating whether the client should be billed for this expense.
     * </p>
     *
     * @return <code>true</code> if the client should be billed for this expense; <code>false</code> otherwise.
     */
    public boolean isBillable() {
        return billable;
    }
}






