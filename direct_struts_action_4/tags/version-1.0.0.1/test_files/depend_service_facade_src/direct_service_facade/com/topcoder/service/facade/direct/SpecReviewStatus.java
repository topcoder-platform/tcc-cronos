/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

/**
 * <p>
 * This is an enumeration for spec review statuses.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public enum SpecReviewStatus {
    /**
     * <p>
     * Represents the "nobody registered" status. It is assigned when the specification review competition just posted
     * and no user registered to it yet.
     * </p>
     */
    NOBODY_REGISTERED,

    /**
     * <p>
     * Represents the "review started" status. It is assigned when the TC Member registered for the specification
     * review competition.
     * </p>
     */
    REVIEW_STARTED,

    /**
     * <p>
     * Represents the "review scorecard provided" status. It is assigned when the TC Member finished reviewing of the
     * contest specification.
     * </p>
     */
    REVIEW_SCORECARD_PROVIDED,

    /**
     * <p>
     * Represents the "final fix review" status. It is assigned when the user updated specification according to the
     * TC Member review and send the final fix.
     * </p>
     */
    FINAL_FIX_REVIEW,

    /**
     * <p>
     * Represents the "final fix rejected" status. It is assigned if the TC Member rejected the final fix from the
     * user.
     * </p>
     */
    FINAL_FIX_REJECTED,

    /**
     * <p>
     * Represents the "final fix accepted" status. It is assigned if the TC Member agreed with the user's final fix.
     * </p>
     */
    FINAL_FIX_ACCEPTED,

    /**
     * <p>
     * Represents the "review completed" status. It is assigned when the user finished the specification review
     * competition.
     * </p>
     */
    REVIEW_COMPLETED;
}
