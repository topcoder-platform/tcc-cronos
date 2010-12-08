/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import com.topcoder.management.project.ReviewApplication;

/**
 * <p>
 * This class is used to represent the reviewer selection result returned by ReviewSelection, it contains a primary
 * reviewer and a list of secondary reviewers.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class ReviewSelectionResult {
    /**
     * Represents the primary reviewer.
     */
    private final ReviewApplication primaryReviewer;

    /**
     * Represents the secondary reviewers.
     */
    private final ReviewApplication[] secondaryReviewers;

    /**
     * Constructs a new ReviewSelectionResult instance using given parameters.
     *
     * @param primaryReviewer the primary reviewer to be set to the primaryReviewer field.
     * @param secondaryReviewers the secondary reviewers array to be set to the secondaryReviewers field.
     * @throws IllegalArgumentException if primaryReviewer is null, or if secondaryReviewers is null/empty or contains
     *             null element.
     */
    public ReviewSelectionResult(ReviewApplication primaryReviewer, ReviewApplication[] secondaryReviewers) {
        Helper.checkNull(primaryReviewer, "primaryReviewer");
        Helper.checkNull(secondaryReviewers, "secondaryReviewers");
        if (secondaryReviewers.length == 0) {
            throw new IllegalArgumentException("The secondaryReviewers should NOT be empty.");
        }
        for (ReviewApplication secondaryReviewer : secondaryReviewers) {
            Helper.checkNull(secondaryReviewer, "element of secondaryReviewers");
        }

        this.primaryReviewer = primaryReviewer;
        this.secondaryReviewers = secondaryReviewers;
    }

    /**
     * Getter method for primaryReviewer field.
     *
     * @return the primaryReviewer field.
     */
    public ReviewApplication getPrimaryReviewer() {
        return primaryReviewer;
    }

    /**
     * Getter method for secondaryReviewers field.
     *
     * @return the secondaryReviewers field.
     */
    public ReviewApplication[] getSecondaryReviewers() {
        return secondaryReviewers.clone();
    }
}
