/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

import com.topcoder.service.studio.contest.Helper;
import com.topcoder.service.studio.submission.PaymentStatus;

/**
 * <p>
 * Represents the entity class for db table <i>contest_payment</i>.
 * </p>
 * 
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 * 
 * @author superZZ
 * @version 1.0
 */
public class ContestPayment implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2575579887608597669L;

    /**
     * Represents the contest id.
     */
    private long contestId;

    /**
     * Represents the payment status.
     */
    private PaymentStatus status;

    /**
     * Represents the price.
     */
    private Double price;

    /**
     * Represents the paypal order id.
     */
    private long payPalOrderId;

    /**
     * Default constructor.
     */
    public ContestPayment() {
        // empty
    }

    /**
     * Returns the contest id.
     * 
     * @return the contest id.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Updates the contest id with the specified value.
     * 
     * @param contestId
     *            the contest id to set.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the status.
     * 
     * @return the status.
     */
    public PaymentStatus getStatus() {
        return status;
    }

    /**
     * Updates the status with the specified value.
     * 
     * @param status
     *            the status to set.
     */
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    /**
     * Returns the price.
     * 
     * @return the price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Updates the PayPal order id with the specified value.
     * 
     * @param price
     *            the PayPal order id to set.
     */
    public void setPayPalOrderId(long payPalOrderId) {
        this.payPalOrderId = payPalOrderId;
    }

    /**
     * Returns the PayPal order id.
     * 
     * @return the PayPalOrderId.
     */
    public long getPayPalOrderId() {
        return payPalOrderId;
    }

    /**
     * Updates the price with the specified value.
     * 
     * @param price
     *            the price to set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Compares this object with the passed object for equality. Only the id
     * will be compared.
     * 
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestPayment) {
            return (getContestId() == ((ContestPayment) obj).getContestId());
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent
     * with this class's {@link #equals(Object)} method.
     * 
     * @return a hash code for this {@code SubmissionPayment}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(payPalOrderId);
    }
}
