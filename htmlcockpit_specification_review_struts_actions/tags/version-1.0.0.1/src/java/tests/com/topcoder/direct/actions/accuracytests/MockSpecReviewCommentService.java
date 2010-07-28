/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.accuracytests;

import com.topcoder.direct.specreview.services.SpecReviewComment;
import com.topcoder.direct.specreview.services.SpecReviewCommentService;
import com.topcoder.direct.specreview.services.SpecReviewCommentServiceException;
import com.topcoder.direct.specreview.services.UserComment;

import com.topcoder.security.TCSubject;

import java.util.ArrayList;
import java.util.List;


/**
 * Mock implementation.
 *
 * @author onsky
 * @version 1.0
 */
public class MockSpecReviewCommentService implements SpecReviewCommentService {
    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param isStudio A mock!
     * @param questionId A mock!
     * @param comment A mock!
     *
     * @return A mock!
     *
     * @throws SpecReviewCommentServiceException A mock!
     */
    public long addSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException {
        return 0;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param isStudio A mock!
     *
     * @return A mock!
     *
     * @throws SpecReviewCommentServiceException A mock!
     */
    public List<SpecReviewComment> getSpecReviewComments(TCSubject tcSubject, long projectId, boolean isStudio)
        throws SpecReviewCommentServiceException {
    	List<SpecReviewComment> comments = new ArrayList<SpecReviewComment>();
    	comments.add(new SpecReviewComment());
        return comments;
    }

    /**
     * A mock!
     *
     * @param tcSubject A mock!
     * @param projectId A mock!
     * @param isStudio A mock!
     * @param questionId A mock!
     * @param comment A mock!
     *
     * @throws SpecReviewCommentServiceException A mock!
     */
    public void updateSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException {
    }
}
