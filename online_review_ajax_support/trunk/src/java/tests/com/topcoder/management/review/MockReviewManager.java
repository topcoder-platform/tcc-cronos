/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.MockCommentType;
import com.topcoder.management.review.data.MockReview;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock implementation of <code>ReviewManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockReviewManager implements ReviewManager {

    /**
     * Add item comment.
     * @param item the item id
     * @param comment the comment
     * @param operator the operator
     */
    public void addItemComment(long item, Comment comment, String operator) {
    }

    /**
     * Add review comment.
     * @param review the review id
     * @param comment the comment
     * @param operator the operator
     */
    public void addReviewComment(long review, Comment comment, String operator) {
    }

    /**
     * Create review.
     * @param review the review to create
     * @param operator the operator
     */
    public void createReview(Review review, String operator) {
    }

    /**
     * Get all the comment types.
     * @return all the comment types
     */
    public CommentType[] getAllCommentTypes() {

        CommentType review = new MockCommentType();
        review.setId(1);
        review.setName("Appeal");

        CommentType response = new MockCommentType();
        response.setId(2);
        response.setName("Appeal Response");

        return new CommentType[] {review, response};
    }

    /**
     * Get the review.
     * @param id the review id
     * @return the review got
     */
    public Review getReview(long id) {
        if (id < 10) {
            Review review = new MockReview();
            review.setId(1);
            review.setSubmission(1);
            review.setAuthor(3);
            return review;
        }
        return null;
    }

    /**
     * Search reviews by filter.
     * @param filter the filter
     * @param complete whether complete or not
     * @return the reviews got
     */
    public Review[] searchReviews(Filter filter, boolean complete) {
        return null;
    }

    /**
     * Update the review.
     * @param review the review to update
     * @param operator the operator
     */
    public void updateReview(Review review, String operator) {
    }

}
