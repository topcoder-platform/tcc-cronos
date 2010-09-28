/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for information about a single reviewer payment. It holds reviewer ID, project ID and
 * payment amount.
 * </p>
 *
 * <p>
 * <em>NOTE: </em> It is a simple JavaBean (POJO) that provides getters and setters for all private attributes and
 * performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ReviewerPayment implements Serializable {
    /**
     * <p>
     * The ID of the reviewer.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long reviewerId;

    /**
     * <p>
     * The ID of the project.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * The payment amount (expected to be in US dollars).
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private double paymentAmount;

    /**
     * <p>
     * Creates an instance of ReviewerPayment.
     * </p>
     */
    public ReviewerPayment() {
        // Empty
    }

    /**
     * <p>
     * Gets the ID of the reviewer.
     * </p>
     *
     * @return the ID of the reviewer.
     */
    public long getReviewerId() {
        return reviewerId;
    }

    /**
     * <p>
     * Sets the ID of the reviewer.
     * </p>
     *
     * @param reviewerId
     *            the ID of the reviewer.
     */
    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * <p>
     * Gets the ID of the project.
     * </p>
     *
     * @return the ID of the project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of the project.
     * </p>
     *
     * @param projectId
     *            the ID of the project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the payment amount.
     * </p>
     *
     * @return the payment amount.
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * <p>
     * Sets the payment amount.
     * </p>
     *
     * @param paymentAmount
     *            the payment amount.
     */
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
