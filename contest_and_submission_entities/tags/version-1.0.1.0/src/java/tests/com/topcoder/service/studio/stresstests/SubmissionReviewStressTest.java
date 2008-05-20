/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.util.Date;

import com.topcoder.service.studio.submission.ReviewStatus;
import com.topcoder.service.studio.submission.SubmissionReview;


/**
 * <p>
 * Stress test for the class <code>SubmissionReview</code>.
 * </P>
 *
 * @author WN
 * @version 1.0
 *
 */
public class SubmissionReviewStressTest extends AbstractStressTest {
    /**
     * <p>
     * Sets up the necessary environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the environment.
     * </P>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Stress test for the class SubmissionReview using database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSubmissionReviewPersistence() throws Exception {
        SubmissionReview[] submissionReviews = new SubmissionReview[10];

        for (int i = 0; i < submissionReviews.length; i++) {
            submissionReviews[i] = new SubmissionReview();
            submissionReviews[i].setReviewerId(i + 0L);
            submissionReviews[i].setSubmission(createSubmission(i));
            submissionReviews[i].setText("text" + i);

            // set ReviewStatus
            ReviewStatus reviewStatuse = new ReviewStatus();
            reviewStatuse.setReviewStatusId(i + 0L);
            reviewStatuse.setDescription("description" + i);
            submissionReviews[i].setStatus(reviewStatuse);
            submissionReviews[i].setModifyDate(new Date());
        }

        long time = System.currentTimeMillis();

        for (int i = 0; i < submissionReviews.length; i++) {
            session.save(submissionReviews[i]);
        }

        time = System.currentTimeMillis() - time;
        System.out.println("Stress test for SubmissionReview took " + time + "ms");
    }
}
