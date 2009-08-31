/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.util.List;

import javax.ejb.Remote;

/**
 * The Interface SpecReviewService.
 * 
 * @author snow01
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 * @version 1.0
 */
@Remote
public interface SpecReviewService {

    /**
     * Gets the spec reviews for specified contest id.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * 
     * @return the list of spec reviews that matches the specified contest id.
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    List<SpecReview> getSpecReviews(long contestId, boolean studio) throws SpecReviewServiceException;

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void saveReviewStatus(long contestId, boolean studio, String sectionName, String comment, boolean isPass, String role)
            throws SpecReviewServiceException;

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void saveReviewComment(long contestId, boolean studio, String sectionName, String comment, String role)
            throws SpecReviewServiceException;

    /**
     * Mark review comment with specified comment id as seen.
     * 
     * @param commentId
     *            the comment id
     * 
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    void markReviewCommentSeen(long commentId) throws SpecReviewServiceException;

}
