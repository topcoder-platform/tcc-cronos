/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.entry.base.BaseEntry;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to access the various properties of a
 * Fixed Billing Entry,  A Fixed Billing Entry is  an amount of money spent by a Project Manager for a specific
 * Project and client.
 * </p>
 *
 * <p>
 * This class extends BaseEntry to retrieve the properties common to a  Time Tracker Entry.  It also inherits from the
 * base TimeTrackerBean  to include the id, creation and modification details.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance, or this class
 * should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingEntry extends BaseEntry {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -9189558790907155274L;

    /**
     * <p>
     * This is the amount of money that was spent.
     * </p>
     */
    private double amount = 0.0;

    /**
     * <p>
     * This is the status of the entry, which is an indication of  how the FixedBillingEntry is being processed by the
     * Time Tracker system..
     * </p>
     */
    private FixedBillingStatus fixedBillingStatus;

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any).  If it is 0, it
     * represents that no invoice was associated.
     * </p>
     */
    private long invoiceId;

    /**
     * This is ID of a user who modified this fixed billing entry. This ID will be used for auditing.
     */
    private long modificationUserId = -1;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public FixedBillingEntry() {
    }

    /**
     * <p>
     * Gets the status of the entry, which is an indication of how the FixedBillingEntry is being processed by the Time
     * Tracker system..
     * </p>
     *
     * @return the status of the entry
     */
    public FixedBillingStatus getFixedBillingStatus() {
        return fixedBillingStatus;
    }

    /**
     * <p>
     * Sets the status of the entry, which is an indication of how the FixedBillingEntry is being processed by the Time
     * Tracker system.
     * </p>
     *
     * @param fixedBillingStatus the status of the entry
     *
     * @throws IllegalArgumentException if status is null.
     */
    public void setFixedBillingStatus(FixedBillingStatus fixedBillingStatus) {
        Helper.checkNull("fixedBillingStatus", fixedBillingStatus);

        if ((this.fixedBillingStatus == null) || (!this.fixedBillingStatus.equals(fixedBillingStatus))) {
            setChanged(true);
        }

        this.fixedBillingStatus = fixedBillingStatus;
    }

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any).  It will be 0 to
     * indicate no invoiceId is currently associated..
     * </p>
     *
     * @return The id of the invoice associated with this entry.
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * <p>
     * This is the id of the Time Tracker Invoice which is associated with this entry (if any).  It will be 0 to
     * indicate no invoiceId is currently associated.
     * </p>
     *
     * @param invoiceId the id of the Time Tracker Invoice which is associated with this entry (if any).
     *
     * @throws IllegalArgumentException if invoiceId is &lt; 0.
     */
    public void setInvoiceId(long invoiceId) {
        if (invoiceId != -1) {
            Helper.checkLongValueLessThan("invoiceId", invoiceId);
        }

        if (invoiceId != this.invoiceId) {
            setChanged(true);
        }

        this.invoiceId = invoiceId;
    }

    /**
     * Gets an ID of the user who modified this fixed billing entry.
     *
     * @return an ID of the user who modified this fixed billing entry.
     */
    public long getModificationUserId() {
        return this.modificationUserId;
    }

    /**
     * Sets ID of the user who modified this fixed billing entry.
     *
     * @param modificationUserId an ID of the user who modified this fixed billing entry.
     * @throws IllegalArgumentException if the <code>modificationUserId</code> is less than or equal to zero.
     */
    public void setModificationUserId(long modificationUserId) {
        if (modificationUserId <= 0) {
            throw new IllegalArgumentException("Modification user's ID is invalid:" + modificationUserId + ".");
        }
        this.modificationUserId = modificationUserId;
    }

    /**
     * <p>
     * Gets the amount of money that was spent.
     * </p>
     *
     * @return the amount of money that was spent.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * <p>
     * Sets the amount of money that was spent.
     * </p>
     *
     * @param amount the amount of money that was spent.
     *
     * @throws IllegalArgumentException if amount is &lt; 0.
     */
    public void setAmount(double amount) {
        Helper.checkDoubleValueLessThan("amount", amount);

        if (amount != this.amount) {
            setChanged(true);
        }

        this.amount = amount;
    }
}
