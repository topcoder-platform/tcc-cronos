/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.payment;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Unit tests for <code>{@link ReviewerPayment}</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ReviewerPaymentUnitTests {
    /**
     * <p>
     * Represents the <code>ReviewerPayment</code> instance used in tests.
     * </p>
     */
    private ReviewerPayment instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ReviewerPaymentUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     */
    @Before
    public void setUp() {
        instance = new ReviewerPayment();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ReviewerPayment()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new ReviewerPayment();

        assertEquals("'reviewerId' should be correct.", 0L, TestsHelper.getField(instance, "reviewerId"));
        assertEquals("'projectId' should be correct.", 0L, TestsHelper.getField(instance, "projectId"));
        assertEquals("'paymentAmount' should be correct.",
            0.0, (Double) TestsHelper.getField(instance, "paymentAmount"), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReviewerId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getReviewerId() {
        long value = 1;
        instance.setReviewerId(value);

        assertEquals("'getReviewerId' should be correct.", value, instance.getReviewerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReviewerId(long reviewerId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setReviewerId() {
        long value = 1;
        instance.setReviewerId(value);

        assertEquals("'setReviewerId' should be correct.", value, TestsHelper.getField(instance, "reviewerId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getProjectId() {
        long value = 1;
        instance.setProjectId(value);

        assertEquals("'getProjectId' should be correct.", value, instance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProjectId(long projectId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setProjectId() {
        long value = 1;
        instance.setProjectId(value);

        assertEquals("'setProjectId' should be correct.", value, TestsHelper.getField(instance, "projectId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentAmount()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPaymentAmount() {
        double value = 1;
        instance.setPaymentAmount(value);

        assertEquals("'getPaymentAmount' should be correct.", value, instance.getPaymentAmount(), 0.001);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPaymentAmount(double paymentAmount)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPaymentAmount() {
        double value = 1;
        instance.setPaymentAmount(value);

        assertEquals("'setPaymentAmount' should be correct.",
            value, (Double) TestsHelper.getField(instance, "paymentAmount"), 0.001);
    }
}
