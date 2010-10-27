/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import com.cronos.onlinereview.review.selection.Helper;
import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * This class implements the Comparable interface, so that it can be sort base
 * on coefficient value and application date. It has the coefficientValue and
 * reviewApplication fields. The coefficientValue represents the result
 * calculated based on the reviewer's past performance statistics and current
 * workload. Once the best coefficientValue is found, we can retrieve the
 * corresponding reviewApplication.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class ReviewerCoefficient implements Comparable<ReviewerCoefficient> {
    /**
     * Represents the review application.
     */
    private final ReviewApplication reviewApplication;

    /**
     * Represents the coefficient value, which is the result calculated based on
     * the reviewer's past performance statistics and current workload.
     */
    private final double coefficientValue;

    /**
     * Constructs a new ReviewerCoefficient instance using given parameters.
     *
     * @param reviewApplication the review application used to set to the
     *            reviewApplication field.
     * @param coefficientValue the coefficient value used to set to the
     *            coefficientValue field.
     * @throws IllegalArgumentException if reviewApplication is null, or
     *             contains null application date, or coefficientValue is not
     *             positive.
     */
    public ReviewerCoefficient(ReviewApplication reviewApplication,
        double coefficientValue) {
        Helper.checkNull(reviewApplication, "reviewApplication");
        if (reviewApplication.getApplicationDate() == null) {
            throw new IllegalArgumentException(
                "The applicationDate of reviewApplication from given reviewerCoefficient is null.");
        }
        if (coefficientValue <= 0) {
            throw new IllegalArgumentException(
                "The coefficientValue is not positive.");
        }

        this.reviewApplication = reviewApplication;
        this.coefficientValue = coefficientValue;
    }

    /**
     * Getter method for reviewerApplication field.
     *
     * @return the reviewerApplication field.
     */
    public ReviewApplication getReviewApplication() {
        return reviewApplication;
    }

    /**
     * Getter method for coefficientValue field.
     *
     * @return the coefficientValue field.
     */
    public double getCoefficientValue() {
        return coefficientValue;
    }

    /**
     * Compare the given reviewerCoefficient object to current object. This
     * method will first compare coefficient value, and then the application
     * date, and finally the reviewer id. Parameters:
     *
     * @param reviewerCoefficient the reviewerCoefficient object used to compare
     *            to current object
     * @return -1 if current object has larger coefficient, or both object has
     *         the same coefficient, but the current object's application date
     *         is earlier, or coefficient and application date are the same, but
     *         the current reviewer id is smaller. 0 if given
     *         reviewerCoefficient object and current object has the same
     *         coefficient, application date and reviewer id (same reviewer id
     *         will not happen in real environment, because
     *         RatingBasedSelectionAlgorithm will make sure that no duplicate
     *         reviewer id exists) 1 otherwise.
     * @throws IllegalArgumentException is given reviewerCoefficient is null.
     */
    public int compareTo(ReviewerCoefficient reviewerCoefficient) {
        Helper.checkNull(reviewerCoefficient, "reviewerCoefficient");

        // check coefficient value
        if (reviewerCoefficient.coefficientValue < coefficientValue) {
            return -1;
        } else if (reviewerCoefficient.coefficientValue == coefficientValue) {
            // check application date
            if (reviewApplication.getApplicationDate().getTime() < reviewerCoefficient.reviewApplication
                .getApplicationDate().getTime()) {
                return -1;
            } else if (reviewApplication.getApplicationDate().getTime() == reviewerCoefficient.reviewApplication
                .getApplicationDate().getTime()) {
                // check reviewer id
                if (reviewApplication.getReviewerId() < reviewerCoefficient.reviewApplication
                    .getReviewerId()) {
                    return -1;
                } else if (reviewApplication.getReviewerId() == reviewerCoefficient.reviewApplication
                    .getReviewerId()) {
                    return 0;
                }
            }
        }
        return 1;
    }
}
