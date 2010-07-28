package com.topcoder.service.review.specification;

/**
 * This is an enumeration for specification review statuses.
 * 
 * Thread Safety:
 * This class is immutable and thread safe.
 */
public enum SpecificationReviewStatus {

    /**
     * Represents the "waiting for fixes" specification review status.
     */
    WAITING_FOR_FIXES,

    /**
     * Represents the "pending review" specification review status.
     */
    PENDING_REVIEW;
}
