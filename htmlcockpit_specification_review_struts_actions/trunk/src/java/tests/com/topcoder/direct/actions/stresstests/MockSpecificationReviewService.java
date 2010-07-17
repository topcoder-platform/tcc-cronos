package com.topcoder.direct.actions.stresstests;

import java.util.Date;
import java.util.List;

import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

/**
 * Mock service.
 * @author moon.river
 * @version 1.0
 */
public class MockSpecificationReviewService implements SpecificationReviewService {

    /**
     * The content.
     */
    private String content;

    /**
     * Gets the review positions.
     * @param tcSubject the subject
     * @return null
     * @throws SpecificationReviewServiceException never
     */
    public List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject)
            throws SpecificationReviewServiceException {
        return null;
    }

    /**
     * Gets the spec review.
     * @param tcSubject the subject
     * @param projectId the project id
     * @return not null
     * @throws SpecificationReviewServiceException never
     */
    
    public SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId)
            throws SpecificationReviewServiceException {
        return new SpecificationReview();
    }

    /**
     * Gets the spec review status.
     * @param tcSubject the subject
     * @param projectId the project id
     * @return not null
     * @throws SpecificationReviewServiceException never
     */
    
    public SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId)
            throws SpecificationReviewServiceException {
        return SpecificationReviewStatus.PENDING_REVIEW;
    }

    /**
     * Schedules the spec review.
     * @param tcSubject the subject
     * @param projectId the project id
     * @param reviewStartDate the start date
     * @throws SpecificationReviewServiceException never
     */
    
    public void scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)
            throws SpecificationReviewServiceException {
    }

    /**
     * Submit the spec review.
     * @param tcSubject the subject
     * @param projectId the project id
     * @param filename the file name
     * @return 0
     * @throws SpecificationReviewServiceException never
     */
    
    public long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename)
            throws SpecificationReviewServiceException {
        return 0;
    }

    /**
     * Submit the spec review.
     * @param tcSubject the subject
     * @param projectId the project id
     * @param filename the file name
     * @return 0
     * @throws SpecificationReviewServiceException never
     */
    
    public long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
            throws SpecificationReviewServiceException {
        this.content = content;
        return 0;
    }

    /**
     * Gets the submitted content.
     * @return the content
     */
    public String getContent() {
        return content;
    }

}
