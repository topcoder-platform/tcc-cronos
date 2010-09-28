/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment.accuracytests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cronos.onlinereview.review.payment.ReviewerPayment;

/**
 * Set of tests for testing ReviewPaymentCalculatorConfigurationException class.
 * @author vilain
 * @version 1.0
 */
public class ReviewerPaymentAccuracyTests {

    /**
     * Reviewer id for testing.
     */
    private long reviewerId = 1;

    /**
     * Project id for testing.
     */
    private long projectId = 2;

    /**
     * Payment amount for testing.
     */
    private double paymentAmount;

    /**
     * Instance of ReviewerPayment for testing.
     */
    private ReviewerPayment reviewerPayment;

    /**
     * Setting up environment.
     * @throws Exception wraps all exceptions
     */
    @Before
    public void setUp() throws Exception {
        reviewerPayment = new ReviewerPayment();
    }

    /**
     * Bean under test ReviewerPayment#reviewerId. Accuracy testing of properly
     * setting/retrieval.
     */
    @Test
    public final void testSetGetReviewerIdAccuracy() {
        reviewerPayment.setReviewerId(reviewerId);
        Assert.assertEquals("reviewerIds must be the equal", reviewerId, reviewerPayment.getReviewerId());
    }

    /**
     * Bean under test ReviewerPayment#projectId. Accuracy testing of properly
     * setting/retrieval.
     */
    @Test
    public final void testSetGetProjectIdAccuracy() {
        reviewerPayment.setProjectId(projectId);
        Assert.assertEquals("projectIds must be the equal", projectId, reviewerPayment.getProjectId());
    }

    /**
     * Bean under test ReviewerPayment#paymentAmount. Accuracy testing of
     * properly setting/retrieval.
     */
    @Test
    public final void testSetGetPaymentAmountAccuracy() {
        reviewerPayment.setPaymentAmount(paymentAmount);
        Assert.assertEquals("paymentAmounts must be the equal", paymentAmount, reviewerPayment
            .getPaymentAmount(), 0.000001);
    }
}