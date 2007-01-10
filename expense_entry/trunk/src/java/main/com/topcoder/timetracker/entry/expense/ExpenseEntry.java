/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Defines the information contained by an expense entry. In addition to common information, an expense entry also
 * contains the date, amount of money, the type, the current status and a flag indicating whether the client should be
 * billed.
 * </p>
 *
 * <p>
 * Changes for Version 1.1: a rejectReason map containing <code>ExpenseEntryRejectReason</code> objects is added.
 * </p>
 *
 * <p>
 * Thread safety: Because the class is mutable it is not thread safe. Threads will typically not share instances but if
 * they do, the mutability should be used with care since any change done in one thread will affect the other thread,
 * possibly without even being aware of the change.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @author DanLazar, visualage
 * @version 2.0
 *
 * @since 1.0
 */
public class ExpenseEntry extends BasicInfo {
    /** Represents the date of this expense entry. */
    private Date date = null;

    /** Represents the amount of money of this expense. It cannot be negative. */
    private BigDecimal amount = null;

    /** Represents the mileages in this expense. (if the type is 'auto Mileage' )It cannot be negative. */
    private BigDecimal mileage = null;

        /** Represents the type of this expense. */
    private ExpenseEntryType expenseType = null;

    /** Represents the current status of this expense. */
    private ExpenseEntryStatus status = null;

    /** Represents a flag indicating whether the client should be billed for this expense. */
    private boolean billable = false;

    /**
     * Represetns the company id this expense entry associated to.
     */
    private int companyId = -1;

    /**
     * Represents the mapping with the reject reasons (ids to <code>ExpenseEntryRejectReason</code> objects).
     *
     * @since 1.1
     */
    private Map rejectReasons = new HashMap();

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
     *
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
     *
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
     * Sets the amount of money of this expense.
     * </p>
     *
     * @param amount the amount of money of this expense.
     *
     * @throws NullPointerException if <code>amount</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>amount</code> is negative.
     */
    public void setMileage(BigDecimal mileage) {
        ExpenseEntryHelper.validateNotNull(mileage, "mileage");

        if (mileage.signum() < 0) {
            throw new IllegalArgumentException("mileage cannot be negative.");
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
     *
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
     * Gets the mileage in this expense.
     * </p>
     *
     * @return the mileage.
     */
    public BigDecimal getMileage() {
    	if (mileage == null)	{
    		mileage = new BigDecimal(0);
		}
        return mileage;
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

    /**
     * <p>
     * Gets a flag indicating whether the client should be billed for this expense.
     * </p>
     *
     * @return <code>true</code> if the client should be billed for this expense; <code>false</code> otherwise.
     */
    public boolean getBillable() {
        return isBillable();
    }

    /**
     * <p>
     * Adds a new reject reason to the entry. Duplicate ids are not accepted.
     * </p>
     *
     * @param rejectReason the reject reason to add into the entry.
     *
     * @return <code>true</code> if added, <code>false</code> if not.
     *
     * @throws IllegalArgumentException if argument is <code>null</code>.
     */
    public boolean addRejectReason(ExpenseEntryRejectReason rejectReason) {
        if (rejectReason == null) {
            throw new IllegalArgumentException("rejectReason can not be null.");
        }

        // A duplicate id is not accepted
        Integer key = new Integer(rejectReason.getRejectReasonId());

        if (this.rejectReasons.containsKey(key)) {
            return false;
        }

        // add the new ExpenseEntryRejectReason to the map.
        this.rejectReasons.put(key, rejectReason);

        return true;
    }

    /**
     * <p>
     * Adds more new reject reasons to the entry. Duplicate ids are not accepted.
     * </p>
     *
     * @param rejectReasons the reject reasons.
     *
     * @return null if added, an array with duplicates if not.
     *
     * @throws IllegalArgumentException if the array is <code>null</code> or empty or has <code>null</code> elements.
     */
    public ExpenseEntryRejectReason[] addRejectReasons(ExpenseEntryRejectReason[] rejectReasons) {
        // arguments validation
        if (rejectReasons == null) {
            throw new IllegalArgumentException("rejectReasons can not be null.");
        }

        if (rejectReasons.length == 0) {
            throw new IllegalArgumentException("rejectReasons can not be empty.");
        }

        for (int i = 0; i < rejectReasons.length; ++i) {
            if (rejectReasons[i] == null) {
                throw new IllegalArgumentException("rejectReasons can not contain null element.");
            }
        }

        Map exists = new HashMap();
        boolean[] duplicates = new boolean[rejectReasons.length];

        for (int i = 0; i < duplicates.length; i++) {
            duplicates[i] = false;
        }

        boolean hasDuplicate = false;

        for (int i = 0; i < rejectReasons.length; ++i) {
            Integer key = new Integer(rejectReasons[i].getRejectReasonId());

            if (this.rejectReasons.containsKey(key)) {
                // duplicates occur in the rejectReasons map
                duplicates[i] = true;
                hasDuplicate = true;
            } else if (exists.containsKey(key)) {
                // duplicates occur in the input collection
                duplicates[i] = true;
                duplicates[((Integer) exists.get(key)).intValue()] = true;
                hasDuplicate = true;
            }

            exists.put(key, new Integer(i));
        }

        if (!hasDuplicate) {
            // add all the rejectReasons since no duplicates exist
            for (int i = 0; i < rejectReasons.length; ++i) {
                this.rejectReasons.put(new Integer(rejectReasons[i].getRejectReasonId()), rejectReasons[i]);
            }

            return null;
        }

        // nothing will be added since the duplicates exist, return the duplicates
        List ret = new ArrayList();

        for (int i = 0; i < duplicates.length; i++) {
            if (duplicates[i]) {
                ret.add(rejectReasons[i]);
            }
        }

        return (ExpenseEntryRejectReason[]) ret.toArray(new ExpenseEntryRejectReason[ret.size()]);
    }

    /**
     * <p>
     * Deletes a reject reason id from the expense entry.
     * </p>
     *
     * @param rejectReasonId the reject reason id to delete.
     *
     * @return <code>true</code> if deleted, <code>false</code> if not.
     */
    public boolean deleteRejectReason(int rejectReasonId) {
        return this.rejectReasons.remove(new Integer(rejectReasonId)) != null;
    }

    /**
     * <p>
     * Deletes more reject reason ids from the expense entry. A non existing value will cause the method to do nothing
     * and the resulting array will contain all non existing values.
     * </p>
     *
     * @param rejectReasonIds the reject reason ids.
     *
     * @return null if deleted, or an array with non existing entries otherwise.
     *
     * @throws IllegalArgumentException if the array is <code>null</code> or empty.
     */
    public int[] deleteRejectReasons(int[] rejectReasonIds) {
        // arguments validation
        if (rejectReasonIds == null) {
            throw new IllegalArgumentException("rejectReasonIds can not be null.");
        }

        if (rejectReasonIds.length == 0) {
            throw new IllegalArgumentException("rejectReasonIds can not be empty.");
        }

        Map exists = new HashMap();
        boolean[] nonExisting = new boolean[rejectReasonIds.length];

        for (int i = 0; i < rejectReasonIds.length; i++) {
            nonExisting[i] = false;
        }

        boolean hasNonExisting = false;

        for (int i = 0; i < rejectReasonIds.length; ++i) {
            Integer key = new Integer(rejectReasonIds[i]);

            if (!this.rejectReasons.containsKey(key)) {
                // nonExisting exists
                nonExisting[i] = true;
                hasNonExisting = true;
            } else if (exists.containsKey(key)) {
                // duplicates occur in the input collection
                nonExisting[i] = true;
                nonExisting[((Integer) exists.get(key)).intValue()] = true;
                hasNonExisting = true;
            }

            exists.put(key, new Integer(i));
        }

        if (!hasNonExisting) {
            // delete all the rejectReasons since no nonExisting exists
            for (int i = 0; i < rejectReasonIds.length; ++i) {
                rejectReasons.remove(new Integer(rejectReasonIds[i]));
            }

            return null;
        }

        // nothing will be deleted since the nonExisting exists, return the nonExistings
        int count = 0;

        for (int i = 0; i < nonExisting.length; i++) {
            if (nonExisting[i]) {
                count++;
            }
        }

        int[] ret = new int[count];
        int index = 0;

        for (int i = 0; i < nonExisting.length; i++) {
            if (nonExisting[i]) {
                ret[index++] = rejectReasonIds[i];
            }
        }

        return ret;
    }

    /**
     * <p>
     * Updates a reject reason from the expense entry. A non existing value will return false.
     * </p>
     * Implementation details: Replaces the object from the mapping having the same id as the param.
     *
     * @param rejectReason the reject reason to update.
     *
     * @return <code>true if updated</code>, <code>false</code> if not.
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>.
     */
    public boolean updateRejectReason(ExpenseEntryRejectReason rejectReason) {
        // arguments validation
        if (rejectReason == null) {
            throw new IllegalArgumentException("rejectReason can not be null.");
        }

        Integer key = new Integer(rejectReason.getRejectReasonId());

        if (!this.rejectReasons.containsKey(key)) {
            // A non existing value will return false.
            return false;
        }

        // replace the previous value
        this.rejectReasons.put(key, rejectReason);

        return true;
    }

    /**
     * <p>
     * Updates more reject reasons from the expense entry. A non existing value will cause the method to do nothing and
     * the resulting array will contain all non existing values.
     * </p>
     *
     * @param rejectReasons the reject reasons to update.
     *
     * @return <code>null</code> if updated, an array with non-existing entries if not
     *
     * @throws IllegalArgumentException if the array is <code>null</code> or empty or has <code>null</code> elements.
     */
    public ExpenseEntryRejectReason[] updateRejectReasons(ExpenseEntryRejectReason[] rejectReasons) {
        // arguments validation
        if (rejectReasons == null) {
            throw new IllegalArgumentException("rejectReasons can not be null.");
        }

        if (rejectReasons.length == 0) {
            throw new IllegalArgumentException("rejectReasons can not be empty.");
        }

        for (int i = 0; i < rejectReasons.length; ++i) {
            if (rejectReasons[i] == null) {
                throw new IllegalArgumentException("rejectReasons can not contain null element.");
            }
        }

        Map exists = new HashMap();
        boolean[] nonExisting = new boolean[rejectReasons.length];

        for (int i = 0; i < rejectReasons.length; i++) {
            nonExisting[i] = false;
        }

        boolean hasNonExisting = false;

        for (int i = 0; i < rejectReasons.length; ++i) {
            Integer key = new Integer(rejectReasons[i].getRejectReasonId());

            if (!this.rejectReasons.containsKey(key)) {
                // nonExisting exists
                nonExisting[i] = true;
                hasNonExisting = true;
            } else if (exists.containsKey(key)) {
                // duplicates occur in the input collection
                nonExisting[i] = true;
                nonExisting[((Integer) exists.get(key)).intValue()] = true;
                hasNonExisting = true;
            }

            exists.put(key, new Integer(i));
        }

        if (!hasNonExisting) {
            // update all the rejectReasons since no nonExisting exists
            for (int i = 0; i < rejectReasons.length; ++i) {
                this.rejectReasons.put(new Integer(rejectReasons[i].getRejectReasonId()), rejectReasons[i]);
            }

            return null;
        }

        // nothing will be updated since the nonExisting exists, return the nonExistings
        List ret = new ArrayList();

        for (int i = 0; i < nonExisting.length; i++) {
            if (nonExisting[i]) {
                ret.add(rejectReasons[i]);
            }
        }

        return (ExpenseEntryRejectReason[]) ret.toArray(new ExpenseEntryRejectReason[ret.size()]);
    }

    /**
     * Gets the reject reason objects.
     *
     * @return the reject reasons.
     */
    public ExpenseEntryRejectReason[] getRejectReasons() {
        return (ExpenseEntryRejectReason[]) this.rejectReasons.values().toArray(new ExpenseEntryRejectReason[0]);
    }

    /**
     * Gets the reject reason ids directly (shortcut).
     *
     * @return the reject reason ids.
     */
    public int[] getRejectReasonIds() {
        int[] ret = new int[this.rejectReasons.keySet().size()];
        int index = 0;

        for (Iterator iter = this.rejectReasons.keySet().iterator(); iter.hasNext();) {
            ret[index++] = ((Integer) iter.next()).intValue();
        }
        return ret;
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
}
