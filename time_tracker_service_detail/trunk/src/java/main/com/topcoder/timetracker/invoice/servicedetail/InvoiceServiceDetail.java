/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import java.math.BigDecimal;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.invoice.Invoice;

/**
 * <p>
 * This class extends from TimeTrackerBean and represents object model for invoice_detail table.
 * </p>
 * <p>
 * Class is not thread safe, because it is mutable.
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class InvoiceServiceDetail extends TimeTrackerBean {

    /** Serial version UID. */
    private static final long serialVersionUID = 3791882647860149529L;

    /**
     * <p>
     * This attributes is used to represent invoice of entry. Invoice instance, mutable, may be changed by
     * corresponding setter.
     * </p>
     */
    private Invoice invoice = null;

    /**
     * <p>
     * This attributes is used to represent time entry of detail. Its id corresponds time_entry_id field of
     * service_detail table. This is mutable, may be changed by corresponding setter, null is possible only as
     * default value.
     * </p>
     */
    private TimeEntry timeEntry = null;

    /**
     * <p>
     * This attributes is used to represent invoice id of entry. It correspond rate field of service_detail table.
     * This is mutable, may be changed by corresponding setter, negative value possible only as default
     * </p>
     */
    private double rate = -1.0;

    /**
     * <p>
     * This attributes is used to represent amount of detail. It correspond amount field of service_detail table.
     * This is mutable, may be changed by corresponding setter, null is possible only as default value.
     * </p>
     */
    private BigDecimal amount = null;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     */
    public InvoiceServiceDetail() {
        // nothing to do
    }

    /**
     * <p>
     * This method is used to get invoice attribute.
     * </p>
     *
     * @return this.invoice, null is possible
     */
    public Invoice getInvoice() {
        return this.invoice;
    }

    /**
     * <p>
     * This method is used to set invoice attribute.
     * </p>
     *
     * @param invoice
     *            Invoice instance, null impossible
     */
    public void setInvoice(Invoice invoice) {
        ArgumentCheckUtil.checkNotNull("invoice", invoice);

        this.invoice = invoice;

        setChanged(true);
    }

    /**
     * <p>
     * This method is used to get timeEntry attribute.
     * </p>
     *
     * @return the timeEntry, null is possible if the default value hasn't been changed
     */
    public TimeEntry getTimeEntry() {
        return this.timeEntry;
    }

    /**
     * <p>
     * This method is used to set timeEntry attribute.
     * </p>
     *
     * @param timeEntry
     *            the given <code>TimeEntry</code>
     * @throws IllegalArgumentException
     *             if some parameter is null
     */
    public void setTimeEntry(TimeEntry timeEntry) {
        ArgumentCheckUtil.checkNotNull("timeEntry", timeEntry);

        this.timeEntry = timeEntry;

        setChanged(true);
    }

    /**
     * <p>
     * This method is used to get rate attribute.
     * </p>
     *
     * @return the rate
     */
    public double getRate() {
        return this.rate;
    }

    /**
     * <p>
     * This method is used to set rate attribute.
     * </p>
     *
     * @param rate
     *            the given rate
     *
     * @throws IllegalArgumentException
     *             if parameter is less than 0
     */
    public void setRate(double rate) {
        if (rate < 0.0) {
            throw new IllegalArgumentException("rate can't be less than 0");
        }

        this.rate = rate;
        setChanged(true);
    }

    /**
     * <p>
     * This method is used to get amount attribute.
     * </p>
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * <p>
     * This method is used to set amount attribute.
     * </p>
     *
     * @param amount
     *            the given amount
     *
     * @throws IllegalArgumentException
     *             if some parameter is null
     */
    public void setAmount(BigDecimal amount) {
        ArgumentCheckUtil.checkNotNull("amount", amount);

        this.amount = amount;

        setChanged(true);
    }

}
