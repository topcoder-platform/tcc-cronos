/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.PaymentType;

/**
 * <p>
 * Represents the entity class for db table <i>contest_payment</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author superZZ, TCSDEVELOPER
 * @version 1.2
 */
public class ContestPayment implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 2575579887608597669L;

    /**
     * Represents the contest payment id.
     *
     * @since BUGR-1363
     */
    private long contestPaymentId = -1;

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
    @Deprecated
    private String payPalOrderId;

    /**
     * Represents the create date.
     */
    private Date createDate;

    /**
     * Represents the payment type.
     *
     * @since BUGR-1076
     */
    private PaymentType paymentType;

    /**
     * Represents the payment reference id.
     *
     * @since BUGR-1076
     */
    private String paymentReferenceId;

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
     * @param payPalOrderId
     *            the PayPal order id to set.
     */
    public void setPayPalOrderId(String payPalOrderId) {
        this.payPalOrderId = payPalOrderId;
    }

    /**
     * Returns the PayPal order id.
     *
     * @return the PayPalOrderId.
     */
    public String getPayPalOrderId() {
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
     * Returns create date.
     *
     * @return the create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set create date.
     *
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        return payPalOrderId != null ? payPalOrderId.hashCode() : super.hashCode();
    }

    /**
     * Updates the payment type with the specified value.
     *
     * @since BUGR-1076
     * @param paymentType
     *            the payment order id to set.
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Returns the paymentType.
     *
     * @since BUGR-1076
     * @return payment type
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Updates the payment reference id with the specified value.
     *
     * @since BUGR-1076
     * @param paymentReferenceId
     *            the payment order id to set.
     */
    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    /**
     * Returns the paymentOrderId.
     *
     * @since BUGR-1076
     * @return payment reference id
     */
    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    /**
     * Returns the contestPaymentId.
     *
     * @since BUGR-1363
     * @return contest payment id
     */
    public long getContestPaymentId() {
        return contestPaymentId;
    }

    /**
     * Updates the contest payment id with the specified value.
     *
     * @since BUGR-1363
     * @param contestPaymentId
     *            the contest payment id to set.
     */
    public void setContestPaymentId(long contestPaymentId) {
        this.contestPaymentId = contestPaymentId;
    }
}
