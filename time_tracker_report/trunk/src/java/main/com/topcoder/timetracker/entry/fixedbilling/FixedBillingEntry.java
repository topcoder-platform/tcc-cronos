/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.entry.base.BaseEntry;

/**
 * <p>
 * This class holds the information of a fixed billing entry. No parameter checking is provided in
 * this class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class FixedBillingEntry extends BaseEntry {

    /**
     * <p>
     * This is the amount of money that was spent.
     * </p>
     */
    private double amount = 0.0;

    /**
     * <p>
     * This is the status of the entry, which is an indication of how the FixedBillingEntry is being
     * processed by the Time Tracker system..
     * </p>
     */
    private FixedBillingStatus fixedBillingStatus;

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any). If
     * it is 0, it represents that no invoice was associated.
     * </p>
     */
    private long invoiceId;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FixedBillingEntry() {
        // empty
    }

    /**
     * <p>
     * Gets the status of the entry, which is an indication of how the FixedBillingEntry is being
     * processed by the Time Tracker system..
     * </p>
     *
     * @return the status of the entry
     */
    public FixedBillingStatus getFixedBillingStatus() {
        return this.fixedBillingStatus;
    }

    /**
     * <p>
     * Sets the status of the entry, which is an indication of how the FixedBillingEntry is being
     * processed by the Time Tracker system..
     * </p>
     *
     * @param fixedBillingStatus the status of the entry
     */
    public void setFixedBillingStatus(FixedBillingStatus fixedBillingStatus) {
        this.fixedBillingStatus = fixedBillingStatus;
    }

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any). It
     * will be 0 to indicate no invoiceId is currently associated..
     * </p>
     *
     * @return The id of the invoice associated with this entry.
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any). It
     * will be 0 to indicate no invoiceId is currently associated.
     * </p>
     *
     * @param invoiceId the id of the Time Tracker Invoice which is associated with this entry (if
     *        any).
     */
    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * <p>
     * Gets the amount of money that was spent.
     * </p>
     *
     * @return the amount of money that was spent.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * <p>
     * Sets the amount of money that was spent.
     * </p>
     *
     * @param amount the amount of money that was spent.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
