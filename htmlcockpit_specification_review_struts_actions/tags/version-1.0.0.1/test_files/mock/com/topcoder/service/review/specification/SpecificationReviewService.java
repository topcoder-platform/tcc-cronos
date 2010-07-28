package com.topcoder.service.review.specification;

import java.util.Date;
import java.util.List;

import com.topcoder.security.TCSubject;

public interface SpecificationReviewService {

    void scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)
        throws SpecificationReviewServiceException;

    long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename)
        throws SpecificationReviewServiceException;

    long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
        throws SpecificationReviewServiceException;

    SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;

    SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;

    List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject) throws SpecificationReviewServiceException;
}
