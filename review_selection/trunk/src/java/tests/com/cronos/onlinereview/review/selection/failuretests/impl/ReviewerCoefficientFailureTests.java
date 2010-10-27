/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.impl;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.impl.ReviewerCoefficient;
import com.topcoder.management.project.ReviewApplication;

/**
 * Failure test for ReviewerCoefficient class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewerCoefficientFailureTests extends TestCase {
    /**
     * The ReviewApplication instance used in test.
     */
    private ReviewApplication reviewApplication;

    /**
     * Set up for each test.
     */
    protected void setUp() {
        reviewApplication = new ReviewApplication();
        reviewApplication.setApplicationDate(new Date());
    }

    /**
     * Test ReviewerCoefficient(ReviewApplication reviewApplication, double coefficientValue). When reviewApplication is
     * null.
     */
    public void testCtor_ReviewApplicationIsNull() {
        try {
            new ReviewerCoefficient(null, 1);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test ReviewerCoefficient(ReviewApplication reviewApplication, double coefficientValue). When
     * reviewApplication.getApplicationDate is null.
     */
    public void testCtor_ReviewApplicationDateIsNull() {
        try {
            new ReviewerCoefficient(new ReviewApplication(), 1);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test ReviewerCoefficient(ReviewApplication reviewApplication, double coefficientValue). When coefficientValue is
     * negative.
     */
    public void testCtor_CoefficientValueIsNegative() {
        try {
            new ReviewerCoefficient(reviewApplication, -0.01);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test compareTo(ReviewerCoefficient reviewerCoefficient). When reviewerCoefficient is null.
     */
    public void testCompareTo_ReviewerCoefficientIsNull() {
        ReviewerCoefficient tested = new ReviewerCoefficient(reviewApplication, 0.01);
        try {
            tested.compareTo(null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}