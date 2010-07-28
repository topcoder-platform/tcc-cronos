/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.management.review.data.Review;
import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.SpecificationReviewStatus;

/**
 * <p>
 * The mock <code>SpecificationReviewService</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSpecificationReviewService implements SpecificationReviewService {

    /**
     * Indicates whether to trigger an exception during unit testing.
     */
    private static boolean triggerException = false;

    /**
     * The SpecificationReview map used for unit testing.
     */
    private static Map<Long, SpecificationReview> specificationReviews = new HashMap<Long, SpecificationReview>();

    /**
     * The SpecificationReviewStatus map used for unit testing.
     */
    private static Map<Long, SpecificationReviewStatus> specificationReviewStatuses =
        new HashMap<Long, SpecificationReviewStatus>();

    /**
     * The map containing review start date for each SpecificationReview id.
     */
    private static Map<Long, Date> specificationReviewStartDates = new HashMap<Long, Date>();

    /**
     * The map containing content data for submitted specification reviews.
     */
    private static Map<Long, String> submittedSpecificationReviews = new HashMap<Long, String>();

    /**
     * Setter for boolean indicating whether to trigger an exception during unit testing.
     *
     * @param triggerException boolean indicating whether to trigger an exception during unit testing
     */
    public static void setTriggerException(boolean triggerException) {
        MockSpecificationReviewService.triggerException = triggerException;
    }

    /**
     * Getter for the map containing review start date for each SpecificationReview id.
     *
     * @return the map containing review start date for each SpecificationReview id
     */
    public static Map<Long, Date> getSpecificationReviewStartDates() {
        return specificationReviewStartDates;
    }

    /**
     * Getter for the map containing content data for submitted specification reviews.
     *
     * @return the map containing content data for submitted specification reviews
     */
    public static Map<Long, String> getSubmittedSpecificationReviews() {
        return submittedSpecificationReviews;
    }

    /**
     * Prepares the mock SpecificationReview data.
     */
    private static void prepareSpecificationReviews() {
        long id = 1;

        // load the SpecificationReview data for each project
        for (int projectId = 1; projectId <= 2; ++projectId) {
            SpecificationReview specificationReview = new SpecificationReview();
            Review review = new Review();
            review.setId(id++);
            specificationReview.setReview(review);

            // store the SpecificationReview for the given project id in the map
            specificationReviews.put(Long.valueOf(projectId), specificationReview);

            // store the SpecificationReviewStatus for the given project id in the map
            specificationReviewStatuses.put(Long.valueOf(projectId),
                projectId == 1 ? SpecificationReviewStatus.PENDING_REVIEW
                    : SpecificationReviewStatus.WAITING_FOR_FIXES);
        }
    }

    /**
     * Prepares the mock data to use for unit testing.
     */
    public static void initMockData() {
        prepareSpecificationReviews();
        triggerException = false;
    }

    /**
     * Prepares the mock data to use for demo.
     */
    private static void initMockDataForDemo() {
        // initialize mock data if we are running demo and it hasn't already been initialized
        if (TestHelper.getTestingMode().equalsIgnoreCase("demo") && specificationReviews.size() == 0) {
            initMockData();
        }
    }

    /**
     * Clears mock data used for unit testing.
     */
    public static void clearMockData() {
        specificationReviews.clear();
        specificationReviewStatuses.clear();
        specificationReviewStartDates.clear();
        submittedSpecificationReviews.clear();
    }

    /**
     * Gets the IDs of all projects whose specification review positions are yet not filled. Will return an
     * empty list if there are no such projects
     *
     * @param tcSubject the user making the request
     * @return the IDs of all projects whose specification review positions are yet not filled (not null,
     *         doesn't contain null)
     */
    public List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets a specification review, including the scorecard structure as well as the review content.
     * Returns null if not found.
     *
     * @param tcSubject the user making the request
     * @param projectId the id of the project
     * @return the entity with scorecard and review (null if not found)
     */
    public SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId) {
        initMockDataForDemo();
        if (specificationReviews.containsKey(projectId)) {
            return specificationReviews.get(projectId);
        }
        return null;
    }

    /**
     * Gets the specification review status of the given project. It can be in specification submission phase,
     * or specification review phase, or null if neither.
     *
     * @param tcSubject the user making the request
     * @param projectId the id of the project
     * @return the status of the specification review (null if not ("waiting for fixes" or "pending review"))
     */
    public SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId) {
        initMockDataForDemo();
        if (specificationReviewStatuses.containsKey(projectId)) {
            return specificationReviewStatuses.get(projectId);
        }
        return null;
    }

    /**
     * Schedules a specification review for the given project on the given date.
     *
     * @param tcSubject the user making the request
     * @param projectId the id of the project
     * @param reviewStartDate the date the review is to begin (immediately if null or in the past)
     *
     * @throws SpecificationReviewServiceException if there are any errors during this operation
     */
    public void scheduleSpecificationReview(TCSubject tcSubject, long projectId, Date reviewStartDate)
        throws SpecificationReviewServiceException {
        SpecificationReview specificationReview = specificationReviews.get(projectId);
        if (specificationReview == null) {
            throw new SpecificationReviewServiceException("couldn't find specification review");
        }
        specificationReviewStartDates.put(projectId, reviewStartDate);
    }

    /**
     * Uploads a specification in the form of a file, for the given project by the given user. This method can
     * be used for an initial submission or an updated submission.
     *
     * @param tcSubject the user making the request
     * @param projectId the id of the project
     * @param filename the name of the file with the submission to upload
     * @return the submission id
     */
    public long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename) {
        throw new UnsupportedOperationException();
    }

    /**
     * Uploads a specification in the form of a String, for the given project by the given user. This method
     * can be used for an initial submission or an updated submission.
     *
     * @param tcSubject the user making the request
     * @param projectId the id of the project
     * @param content the content  to upload
     * @return the submission id
     *
     * @throws SpecificationReviewServiceException if there are any errors during this operation
     */
    public long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
        throws SpecificationReviewServiceException {

        if (triggerException) {
            throw new SpecificationReviewServiceException("test");
        }

        submittedSpecificationReviews.put(projectId, content);
        return 0;
    }

}
