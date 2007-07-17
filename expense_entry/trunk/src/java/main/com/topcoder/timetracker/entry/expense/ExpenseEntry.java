/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.math.BigDecimal;

import com.topcoder.timetracker.entry.base.BaseEntry;

/**
 * <p>
 * Defines the information contained by an expense entry. In addition to common information, an expense entry also
 * contains the invoice, amount of money, the type, the current status and a flag indicating whether the client should
 * be billed.
 * </p>
 *
 * <p>
 * When creating an instance of this class the user has two options: 1) Use the default constructor and allow the GUID
 * Generator component to generate a unique id 2) Use the parameterized constructor and provide an id for the
 * ExpenseEntry instance; if the id already is contained by another entry from the ExpenseEntries table, then the
 * newly created entry will not be added to the ExpenseEntries table. Also the user should not populate the
 * creationDate and modificationDate fields, because if he does, the entry will not be added to the database. These
 * fields will be handled automatically by the component(the current date will be used). When loading from the
 * persistence, all the fields will be properly populated.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>Because the class is mutable it is not thread safe. Threads will typically not share instances
 * but if they do, the mutability should be used with care since any change done in one thread will affect the other
 * thread, possibly without even being aware of the change.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntry extends BaseEntry {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -7321093032974434117L;

    /**
     * Represents the amount of money of this expense. Default is null. It cannot be negative once set.
     */
    private BigDecimal amount = null;

    /**
     * Represents the mileages in this expense(if the type is 'auto Mileage'). Default is 0. It cannot be negative once
     * set.
     */
    private int mileage = 0;

    /**
      * Represents the type of this expense. Default is null. It cannot be null once set.
      */
    private ExpenseType expenseType = null;

    /**
      * Represents the status of this expense. Default is null. It cannot be null once set.
      */
    private ExpenseStatus status = null;

    /**
      * Represents a flag indicating whether the client should be billed for this expense. Default is false.
      */
    private boolean billable = false;

    /**
      * Represents the invoice ID for of this expense. Can be -1 if the expense has not yet been invoiced.
      */
    private long invoiceId = -1L;

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
     *
     * @throws IllegalArgumentException if <code>id</code> is not positive.
     */
    public ExpenseEntry(long id) {
        ExpenseEntryHelper.validatePositive(id, "id");
        super.setId(id);
    }

    /**
     * <p>
     * Sets the amount of money of this expense.
     * </p>
     *
     * @param amount the amount of money of this expense.
     *
     * @throws IllegalArgumentException if <code>amount</code> is <code>null</code> or negative.
     */
    public void setAmount(BigDecimal amount) {
        ExpenseEntryHelper.validateNotNull(amount, "amount");

        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount cannot be negative.");
        }

        if (!amount.equals(this.amount)) {
            setChanged(true);
        }

        this.amount = amount;
    }

    /**
     * <p>
     * Sets the mileage of money of this expense.
     * </p>
     *
     * @param mileage the mileage of money of this expense.
     *
     * @throws IllegalArgumentException if <code>mileage</code> negative.
     */
    public void setMileage(int mileage) {
        if (mileage < 0) {
            throw new IllegalArgumentException("mileage cannot be negative.");
        }

        if (this.mileage != mileage) {
            setChanged(true);
        }

        this.mileage = mileage;
    }

    /**
     * <p>
     * Sets the type of this expense.
     * </p>
     *
     * @param expenseType the type of this expense.
     *
     * @throws IllegalArgumentException if <code>expenseType</code> is <code>null</code>.
     */
    public void setExpenseType(ExpenseType expenseType) {
        ExpenseEntryHelper.validateNotNull(expenseType, "expenseType");

        if (!expenseType.equals(this.expenseType)) {
            setChanged(true);
        }

        this.expenseType = expenseType;
    }

    /**
     * <p>
     * Sets the current status of this expense.
     * </p>
     *
     * @param status the current status of this expense.
     *
     * @throws IllegalArgumentException if <code>status</code> is <code>null</code>.
     */
    public void setStatus(ExpenseStatus status) {
        ExpenseEntryHelper.validateNotNull(status, "status");

        if (!status.equals(this.status)) {
            setChanged(true);
        }

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
        if (this.billable != billable) {
            setChanged(true);
        }

        this.billable = billable;
    }

    /**
     * <p>
     * Gets the amount of money of this expense.
     * </p>
     *
     * @return the amount of money of this expense.
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * <p>
     * Gets the mileage in this expense.
     * </p>
     *
     * @return the mileage.
     */
    public int getMileage() {
        return this.mileage;
    }

    /**
     * <p>
     * Gets the type of this expense.
     * </p>
     *
     * @return the type of this expense.
     */
    public ExpenseType getExpenseType() {
        return this.expenseType;
    }

    /**
     * <p>
     * Gets the current status of this expense.
     * </p>
     *
     * @return the current status of this expense.
     */
    public ExpenseStatus getStatus() {
        return this.status;
    }

    /**
     * <p>
     * Gets a flag indicating whether the client should be billed for this expense.
     * </p>
     *
     * @return <code>true</code> if the client should be billed for this expense; <code>false</code> otherwise.
     */
    public boolean isBillable() {
        return this.billable;
    }

    /**
     * <p>
     * Sets the current invoice of this expense. Can accept null, which would mean un-invoiced expense.
     * </p>
     *
     * @param invoiceId the current invoice ID for this expense.
     */
    public void setInvoiceId(long invoiceId) {
        if (invoiceId <= 0) {
            invoiceId = -1;
        }
        if (getInvoiceId() != invoiceId) {
            setChanged(true);
        }

        this.invoiceId = invoiceId;
    }

    /**
     * <p>
     * Gets the current invoice ID for this expense. Could possibly return a null if the invoice is not set.
     * </p>
     *
     * @return the current invoice ID for this expense.
     */
    public long getInvoiceId() {
        return this.invoiceId;
    }
}
