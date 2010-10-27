/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.management.project.ReviewApplication;

/**
 * Unit test cases for class ReviewCoefficient.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class ReviewerCoefficientUnitTests extends TestCase {
    /** The coefficient value used for testing. */
    private static final double COEFFICIENT_VALUE = 0.5;

    /**
     * Tests constructor: ReviewerCoefficient(ReviewApplication, double) with valid parameters. The review application
     * and coefficient value can be retrieved correctly later. No exception is expected.
     */
    public void testCtor1() {
        ReviewApplication application = new ReviewApplication();
        application.setApplicationDate(new Date(2000));
        ReviewerCoefficient coefficient = new ReviewerCoefficient(application, COEFFICIENT_VALUE);

        assertEquals("Review application is not properly initialized.", application, coefficient
            .getReviewApplication());
        assertEquals("Coefficient value is not properly initialized.", COEFFICIENT_VALUE, coefficient
            .getCoefficientValue());
    }

    /**
     * Tests constructor: ReviewerCoefficient(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor2() {
        try {
            new ReviewerCoefficient(null, COEFFICIENT_VALUE);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewerCoefficient(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor3() {
        try {
            ReviewApplication application = new ReviewApplication();
            application.setApplicationDate(new Date(2000));
            new ReviewerCoefficient(application, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewerCoefficient(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor4() {
        try {
            ReviewApplication application = new ReviewApplication();
            application.setApplicationDate(new Date(2000));
            new ReviewerCoefficient(application, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests constructor: ReviewerCoefficient(ReviewApplication, ReviewApplication[]) with invalid parameters.
     * IllegalArgumentException should be thrown.
     */
    public void testCtor5() {
        try {
            ReviewApplication application = new ReviewApplication();
            new ReviewerCoefficient(application, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests compareTo(ReviewerCoefficient) with ReviewerCoefficients containing different coefficient value.
     */
    public void testCompareTo1() {
        ReviewApplication application = new ReviewApplication();
        application.setApplicationDate(new Date(2000));
        ReviewerCoefficient coefficient1 = new ReviewerCoefficient(application, 2);
        ReviewerCoefficient coefficient2 = new ReviewerCoefficient(application, 1);
        assertEquals("The result of compare is incorrect.", -1, coefficient1.compareTo(coefficient2));
        assertEquals("The result of compare is incorrect.", 1, coefficient2.compareTo(coefficient1));
    }

    /**
     * Tests compareTo(ReviewerCoefficient) with ReviewerCoefficients containing different application date.
     */
    public void testCompareTo2() {
        ReviewApplication application1 = new ReviewApplication();
        application1.setApplicationDate(new Date(1000));
        ReviewApplication application2 = new ReviewApplication();
        application2.setApplicationDate(new Date(2000));

        ReviewerCoefficient coefficient1 = new ReviewerCoefficient(application1, 1);
        ReviewerCoefficient coefficient2 = new ReviewerCoefficient(application2, 1);
        assertEquals("The result of compare is incorrect.", -1, coefficient1.compareTo(coefficient2));
        assertEquals("The result of compare is incorrect.", 1, coefficient2.compareTo(coefficient1));
    }

    /**
     * Tests compareTo(ReviewerCoefficient) with ReviewerCoefficients containing different reviewer id.
     */
    public void testCompareTo3() {
        ReviewApplication application1 = new ReviewApplication();
        application1.setApplicationDate(new Date(1000));
        application1.setReviewerId(1);
        ReviewApplication application2 = new ReviewApplication();
        application2.setApplicationDate(new Date(1000));
        application2.setReviewerId(2);

        ReviewerCoefficient coefficient1 = new ReviewerCoefficient(application1, 1);
        ReviewerCoefficient coefficient2 = new ReviewerCoefficient(application2, 1);
        assertEquals("The result of compare is incorrect.", -1, coefficient1.compareTo(coefficient2));
        assertEquals("The result of compare is incorrect.", 1, coefficient2.compareTo(coefficient1));
    }

    /**
     * Tests compareTo(ReviewerCoefficient) with equal ReviewerCoefficients.
     */
    public void testCompareTo4() {
        ReviewApplication application1 = new ReviewApplication();
        application1.setApplicationDate(new Date(1000));
        application1.setReviewerId(1);
        ReviewApplication application2 = new ReviewApplication();
        application2.setApplicationDate(new Date(1000));
        application2.setReviewerId(1);

        ReviewerCoefficient coefficient1 = new ReviewerCoefficient(application1, 1);
        ReviewerCoefficient coefficient2 = new ReviewerCoefficient(application2, 1);
        assertEquals("The result of compare is incorrect.", 0, coefficient1.compareTo(coefficient2));
        assertEquals("The result of compare is incorrect.", 0, coefficient2.compareTo(coefficient1));
    }

    /**
     * Tests compareTo(ReviewerCoefficient) with invalid ReviewerCoefficient. IllegalArgumentException should be thrown.
     */
    public void testCompareToWithInvalidReviewerCoefficient() {
        try {
            ReviewApplication application1 = new ReviewApplication();
            application1.setApplicationDate(new Date(1000));
            application1.setReviewerId(1);
            ReviewerCoefficient coefficient1 = new ReviewerCoefficient(application1, 1);
            coefficient1.compareTo(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
