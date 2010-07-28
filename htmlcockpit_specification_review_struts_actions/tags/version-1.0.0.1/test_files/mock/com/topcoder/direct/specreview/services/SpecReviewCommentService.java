package com.topcoder.direct.specreview.services;

import java.util.List;

import com.topcoder.direct.specreview.services.*;
import com.topcoder.security.*;

/**
 * This interface defines the contact to get spec review comments, add spec review comment and update spec
 * review comment.
 * 
 * Thread-safety: Implementation needs to be thread-safe.
 */
public interface SpecReviewCommentService {

    /**
     * Get specification review comments for the given project.
     * 
     * #Param
     * tcSubject - the tc subject.
     * projectId - the project id.
     * return - a list of specification review comment for the project.
     * 
     * #Exception
     * throw IllegalArgumentException if the tcSubject is null.
     * throw SpecReviewCommentServiceException if any error occurs.
     * 
     * @param tcSubject
     * @param Return
     * @param isStudio
     * @param projectId
     * @return
     */
    public List<SpecReviewComment> getSpecReviewComments(TCSubject tcSubject, long projectId, boolean isStudio)
        throws SpecReviewCommentServiceException;

    /**
     * Add specification review comment for specific question.
     * 
     * #Param
     * tcSubject - tc subject.
     * projectId - the project id.
     * questionId - the question id.
     * comment - the user comment.
     * return - the comment id.
     * 
     * #Exception
     * throw IllegalArgumentException if the tcSubject or comment argument is null.
     * throw SpecReviewCommentServiceException if any error occurs.
     * 
     * @param questionId
     * @param tcSubject
     * @param Return
     * @param isStudio
     * @param projectId
     * @param comment
     * @return
     */
    public long addSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException;

    /**
     * Update the specification comment for specific question in the review scorecard.
     * 
     * #Param
     * tcSubject - the tc subject.
     * projectId - the project id.
     * questionId - the question id.
     * comment - the comment to update.
     * 
     * #Exception
     * throw IllegalArgumentException - if the tcSubject or comment is null.
     * throw SpecReviewCommentServiceException if any error occurs.
     * 
     * @param questionId
     * @param tcSubject
     * @param Return
     * @param isStudio
     * @param projectId
     * @param comment
     */
    public void updateSpecReviewComment(TCSubject tcSubject, long projectId, boolean isStudio, long questionId,
        UserComment comment) throws SpecReviewCommentServiceException;

}
