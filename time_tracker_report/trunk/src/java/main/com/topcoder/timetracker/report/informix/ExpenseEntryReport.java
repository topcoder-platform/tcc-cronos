/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.ExpenseType;

/**
 * <p>
 * This class represents a report of a single expense entry and all information about it. This class
 * extends ReportEntryBean.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> This class isn't thread safe because it's a bean and haven't the
 * setter method synchronized.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class ExpenseEntryReport extends ReportEntryBean {

    /**
	 * Automatically generated unique ID for use qith serialization.
	 */
	private static final long serialVersionUID = 3731679201863501420L;

	/**
     * <p>
     * Represents the expense entry. It's changeable, can be null and it's null at the beginning.
     * </p>
     */
    private ExpenseEntry expenseEntry;

    /**
     * <p>
     * Represents the expense status. It's changeable, can be null and it's null at the beginning.
     * </p>
     */
    private ExpenseStatus expenseStatus;

    /**
     * <p>
     * Represents the expense type. It's changeable, can be null and it's null at the beginning.
     * </p>
     */
    private ExpenseType expenseType;

    /**
     * <p>
     * Constructs a new <code>ExpenseEntryReport</code>.
     * </p>
     */
    public ExpenseEntryReport() {
        // empty
    }

    /**
     * <p>
     * Retrieves the expense entry.
     * </p>
     *
     * @return the expense entry.
     */
    public ExpenseEntry getExpenseEntry() {
        return this.expenseEntry;
    }

    /**
     * <p>
     * Sets the expense entry.
     * </p>
     *
     * @param expenseEntry the expense entry to set
     */
    public void setExpenseEntry(ExpenseEntry expenseEntry) {
        this.expenseEntry = expenseEntry;
    }

    /**
     * <p>
     * Retrieves the expense status.
     * </p>
     *
     * @return the expense status
     */
    public ExpenseStatus getExpenseStatus() {
        return this.expenseStatus;
    }

    /**
     * <p>
     * Sets the expense status.
     * </p>
     *
     * @param expenseStatus the expense status
     */
    public void setExpenseStatus(ExpenseStatus expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    /**
     * <p>
     * Retrieves the expense type.
     * </p>
     *
     * @return the expense type
     */
    public ExpenseType getExpenseType() {
        return this.expenseType;
    }

    /**
     * <p>
     * Sets the expense type.
     * </p>
     *
     * @param expenseType the expense type to set
     */
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
}
