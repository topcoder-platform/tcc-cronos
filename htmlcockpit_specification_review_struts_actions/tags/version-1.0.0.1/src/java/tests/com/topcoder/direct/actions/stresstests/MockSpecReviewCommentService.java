/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions.stresstests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.direct.specreview.services.SpecReviewCommentServiceException;
import com.topcoder.direct.specreview.services.UserComment;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * The mock <code>SpecReviewCommentService</code>.
 * </p>
 *
 * @author moon.river
 * @version 1.0
 */
public class MockSpecReviewCommentService implements SpecReviewCommentService {

    /**
     * Add specification review comment for specific question.
     *
     * @param tcSubject the TC subject
     * @param projectId the project id
     * @param isStudio whether contest is studio
     * @param questionId the question id
     * @param comment the user comment
     * @return the comment id
     *
     * @throws SpecReviewCommentServiceException if some error occurred performing the operation
     */
    public long addSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException {

        return 0;
    }

    /**
     * Gets specification review comments for the given project.
     *
     * @param tcSubject the TC subject
     * @param projectId the project id
     * @param isStudio whether contest is studio
     * @return a list of specification review comments for the project.
     *
     * @throws SpecReviewCommentServiceException if some error occurred performing the operation
     */
    public List<SpecReviewComment> getSpecReviewComments(TCSubject tcSubject, long projectId, boolean isStudio)
        throws SpecReviewCommentServiceException {
        List<SpecReviewComment> comments = new ArrayList<SpecReviewComment>();
        for (int i = 0; i < 1000; i++) {
            SpecReviewComment comment = new SpecReviewComment();
            comments.add(comment);
        }
        return comments;
    }

    /**
     * Updates the specification comment for specific question in the review scorecard.
     *
     * @param tcSubject the TC subject
     * @param projectId the project id
     * @param isStudio whether contest is studio
     * @param questionId the question id
     * @param comment the comment to update
     *
     * @throws SpecReviewCommentServiceException if some error occurred performing the operation
     */
    public void updateSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException {

    }
}
