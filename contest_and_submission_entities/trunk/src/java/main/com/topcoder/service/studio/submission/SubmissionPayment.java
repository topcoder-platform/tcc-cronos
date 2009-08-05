/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.service.studio.contest.Helper;
import com.topcoder.service.studio.PaymentType;

/**
 * <p>
 * Represents the entity class for db table <i>submission_payments</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class SubmissionPayment implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 8719957969441348728L;

    /**
     * Represents the submission.
     */
    private Submission submission;

    /**
     * Represents the payment status.
     */
    private PaymentStatus status;

    /**
     * Represents the price.
     */
    private Double price;

    /**
     * Represents the PayPal order id.
     */
    @Deprecated
    private String PayPalOrderId;

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
    public SubmissionPayment() {
        // empty
    }

    /**
     * Returns the submission.
     *
     * @return the submission.
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Updates the submission with the specified value.
     *
     * @param submission
     *            the submission to set.
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
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
     * @param PayPalOrderId
     *            the PayPal order id to set.
     */
    public void setPayPalOrderId(String PayPalOrderId) {
        this.PayPalOrderId = PayPalOrderId;
    }

    /**
     * Returns the PayPal order  id.
     *
     * @return the PayPalOrderId.
     */
    public String getPayPalOrderId() {
        return PayPalOrderId;
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
        if (obj instanceof SubmissionPayment) {
            return submission != null && getSubmission().equals(((SubmissionPayment) obj).getSubmission());
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
        return Helper.calculateHash(submission != null ? submission.getSubmissionId() : null);
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
}
