/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.accuracytests;

import com.topcoder.security.TCSubject;

import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

import java.util.Date;
import java.util.List;


/**
 * Mock implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class MockSpecificationReviewService implements SpecificationReviewService {
    /**
     * A mock!
     *
     * @param tcSubject A mock!
     *
     * @return A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject)
        throws SpecificationReviewServiceException {
        return null;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException {
        return new SpecificationReview();
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     *
     * @return A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException {
        return SpecificationReviewStatus.PENDING_REVIEW;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param reviewStartDate A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public void scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)
        throws SpecificationReviewServiceException {
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param filename A mock!
     *
     * @return A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename)
        throws SpecificationReviewServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param content A mock!
     *
     * @return A mock!
     *
     * @throws SpecificationReviewServiceException A mock!
     */
    public long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
        throws SpecificationReviewServiceException {
        return 0;
    }
}
