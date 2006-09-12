/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockReviewManager implements ReviewManager {
    public void createReview(Review review, String operator) {
    }

    public void updateReview(Review review, String operator) {
    }

    public Review getReview(long id) {
        if (id == -1) {
            return null;
        }
        return new MockReview();
    }

    public Review[] searchReviews(Filter filter, boolean complete) {
        return null;
    }

    public CommentType[] getAllCommentTypes() {
        return new CommentType[]{new MockCommentType(), new MockCommentType(1)};
    }

    public void addReviewComment(long review, Comment comment, String operator) {
    }

    public void addItemComment(long item, Comment comment, String operator) {
    }
}
