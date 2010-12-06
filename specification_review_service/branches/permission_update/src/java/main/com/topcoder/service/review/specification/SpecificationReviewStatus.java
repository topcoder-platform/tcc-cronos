/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

/**
 * <p>
 * This is an enumeration for specification review statuses.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public enum SpecificationReviewStatus {
    /**
     * Represents the &quot;waiting for fixes&quot; specification review status.
     */
    WAITING_FOR_FIXES,
    /**
     * Represents the &quot;pending review&quot; specification review status.
     */
    PENDING_REVIEW;
}
