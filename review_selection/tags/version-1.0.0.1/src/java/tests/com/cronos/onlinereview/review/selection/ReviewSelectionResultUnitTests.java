/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import junit.framework.TestCase;

import com.topcoder.management.project.ReviewApplication;

/**
 * Unit test cases for class ReviewSelectionResult.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class ReviewSelectionResultUnitTests extends TestCase {
    /** The primary reviewer used for testing. */
    private static final ReviewApplication PRIMARY_REVIEWER = new ReviewApplication();

    /** The secondary reviewer array used for testing. */
    private static final ReviewApplication[] SECONDARY_REVIEWER = {new ReviewApplication(), new ReviewApplication()};

    /** The empty secondary reviewer array used for testing. */
    private static final ReviewApplication[] EMPTY_SECONDARY_REVIEWER = {};

    /** The containing null secondary reviewer array used for testing. */
    private static final ReviewApplication[] CONTAINING_NULL_SECONDARY_REVIEWER = {new ReviewApplication(), null};

    /**
     * Tests constructor: ReviewSelectionResult(ReviewApplication, ReviewApplication[]) with valid parameters. The
     * primary reviewer and secondary reviewer array can be retrieved correctly later. No exception is expected.
     */
    public void testCtor1() {
        ReviewSelectionResult result = new ReviewSelectionResult(PRIMARY_REVIEWER, SECONDARY_REVIEWER);

        assertEquals("Primary reviewer is not properly initialized.", PRIMARY_REVIEWER, result
            .getPrimaryReviewer());
        assertEquals("Secondary reviewer is not properly initialized.", SECONDARY_REVIEWER.length, result
            .getSecondaryReviewers().length);
        for (int i = 0; i < SECONDARY_REVIEWER.length; ++i) {
            assertEquals("Secondary reviewer is not properly initialized.", SECONDARY_REVIEWER[i], result
                .getSecondaryReviewers()[i]);
        }
    }

    /**
     * Tests constructor: ReviewSelectionResult(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor2() {
        try {
            new ReviewSelectionResult(null, SECONDARY_REVIEWER);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewSelectionResult(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor3() {
        try {
            new ReviewSelectionResult(PRIMARY_REVIEWER, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewSelectionResult(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor4() {
        try {
            new ReviewSelectionResult(PRIMARY_REVIEWER, EMPTY_SECONDARY_REVIEWER);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewSelectionResult(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor5() {
        try {
            new ReviewSelectionResult(PRIMARY_REVIEWER, CONTAINING_NULL_SECONDARY_REVIEWER);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
