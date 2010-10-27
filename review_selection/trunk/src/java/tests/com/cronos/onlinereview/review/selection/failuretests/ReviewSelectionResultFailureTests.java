/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.topcoder.management.project.ReviewApplication;

/**
 * Failure test for ReviewSelectionResult class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewSelectionResultFailureTests extends TestCase {
    /**
     * The array of ReviewApplication instance.
     */
    private ReviewApplication[] secondaryReviewers;

    /**
     * Set up for each test.
     */
    protected void setUp() {
        secondaryReviewers = new ReviewApplication[1];
        secondaryReviewers[0] = new ReviewApplication();
        secondaryReviewers[0].setApplicationDate(new Date());
    }

    /**
     * Test ReviewSelectionResult(ReviewApplication primaryReviewer, ReviewApplication[] secondaryReviewers). When
     * primaryReviewer is null.
     */
    public void testCtor_PrimaryReviewerIsNull() {
        try {
            new ReviewSelectionResult(null, secondaryReviewers);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test ReviewSelectionResult(ReviewApplication primaryReviewer, ReviewApplication[] secondaryReviewers). When
     * secondaryReviewers is null.
     */
    public void testCtor_SecondaryReviewersIsNull() {
        try {
            new ReviewSelectionResult(secondaryReviewers[0], null);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test ReviewSelectionResult(ReviewApplication primaryReviewer, ReviewApplication[] secondaryReviewers). When
     * secondaryReviewers is empty.
     */
    public void testCtor_SecondaryReviewersIsEmpty() {
        try {
            new ReviewSelectionResult(secondaryReviewers[0], new ReviewApplication[0]);
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    /**
     * Test ReviewSelectionResult(ReviewApplication primaryReviewer, ReviewApplication[] secondaryReviewers). When
     * secondaryReviewers contains null.
     */
    public void testCtor_SecondaryReviewersHasNull() {
        try {
            new ReviewSelectionResult(secondaryReviewers[0], new ReviewApplication[] { null });
            fail("Cannot go here.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
