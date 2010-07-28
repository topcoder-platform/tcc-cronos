/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

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
 * The mock <code>SpecReviewCommentService</code> used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSpecReviewCommentService implements SpecReviewCommentService {

    /**
     * Indicates whether to trigger an exception during unit testing.
     */
    private static boolean triggerException = false;

    /**
     * The SpecReviewComment map used for unit testing.
     */
    private static Map<Long, List<SpecReviewComment>> specReviewComments =
        new HashMap<Long, List<SpecReviewComment>>();

    /**
     * Setter for boolean indicating whether to trigger an exception during unit testing.
     *
     * @param triggerException boolean indicating whether to trigger an exception during unit testing
     */
    public static void setTriggerException(boolean triggerException) {
        MockSpecReviewCommentService.triggerException = triggerException;
    }

    /**
     * Prepares the mock SpecReviewComment data.
     */
    private static void prepareSpecReviewComments() {
        long commentId = 1;

        // load the SpecReviewComment data for each project
        for (int projectId = 1; projectId <= 2; ++projectId) {
            List<SpecReviewComment> list = new ArrayList<SpecReviewComment>();
            for (int questionId = 1; questionId <= 2; ++questionId) {
                SpecReviewComment specReviewComment = new SpecReviewComment();
                specReviewComment.setQuestionId(questionId);

                // prepare the UserComment objects
                List<UserComment> userComments = new ArrayList<UserComment>();
                for (int i = 0; i < 2; ++i) {
                    UserComment userComment = new UserComment();
                    userComment.setCommentId(commentId);
                    userComment.setComment("spec review comment " + commentId++);
                    userComments.add(userComment);
                }
                specReviewComment.setComments(userComments);
                list.add(specReviewComment);
            }
            // store the SpecReviewComment list for the given project id in the map
            specReviewComments.put(Long.valueOf(projectId), list);
        }
    }

    /**
     * Prepares the mock data to use for unit testing.
     */
    public static void initMockData() {
        triggerException = false;
        prepareSpecReviewComments();
    }

    /**
     * Prepares the mock data to use for demo.
     */
    private static void initMockDataForDemo() {
        // initialize mock data if we are running demo and it hasn't already been initialized
        if (TestHelper.getTestingMode().equalsIgnoreCase("demo") && specReviewComments.size() == 0) {
            initMockData();
        }
    }

    /**
     * Clears mock data used for unit testing.
     */
    public static void clearMockData() {
        specReviewComments.clear();
    }

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

        if (triggerException) {
            throw new SpecReviewCommentServiceException("test");
        }

        initMockDataForDemo();

        // get the SpecReviewComment list for this project, or create it if not present
        List<SpecReviewComment> list = specReviewComments.get(projectId);
        if (list == null) {
            list = new ArrayList<SpecReviewComment>();
            specReviewComments.put(Long.valueOf(projectId), list);
        }

        // find the SpecReviewComment for the given question id
        SpecReviewComment specReviewComment = null;
        for (SpecReviewComment item : list) {
            if (item.getQuestionId() == questionId) {
                specReviewComment = item;
                break;
            }
        }
        if (specReviewComment == null) {
            // didn't find it, so create a new SpecReviewComment for the given question id and add it to the
            // list
            specReviewComment = new SpecReviewComment();
            specReviewComment.setQuestionId(questionId);
            specReviewComment.setComments(new ArrayList<UserComment>());
            list.add(specReviewComment);
        }

        // determine the next comment id to use
        long nextCommentId = 1;
        for (UserComment item : specReviewComment.getComments()) {
            nextCommentId = Math.max(nextCommentId, item.getCommentId());
        }
        ++nextCommentId;

        // add the UserComment to the list for the SpecReviewComment
        comment.setCommentId(nextCommentId);
        specReviewComment.getComments().add(comment);

        return nextCommentId;
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

        if (triggerException) {
            throw new SpecReviewCommentServiceException("test");
        }

        initMockDataForDemo();
        if (specReviewComments.containsKey(projectId)) {
            return specReviewComments.get(projectId);
        }
        return null;
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

        // get the SpecReviewComment list for this project
        List<SpecReviewComment> list = specReviewComments.get(projectId);
        if (list == null) {
            throw new SpecReviewCommentServiceException("cannot find project for id = " + projectId);
        }

        // find the SpecReviewComment for the given question id
        SpecReviewComment specReviewComment = null;
        for (SpecReviewComment item : list) {
            if (item.getQuestionId() == questionId) {
                specReviewComment = item;
                break;
            }
        }
        if (specReviewComment == null) {
            throw new SpecReviewCommentServiceException("cannot find SpecReviewComment for questionId = "
                + questionId);
        }

        // search for matching UserComment
        UserComment userComment = null;
        for (UserComment item : specReviewComment.getComments()) {
            if (item.getCommentId() == comment.getCommentId()) {
                userComment = item;
                break;
            }
        }
        if (userComment == null) {
            throw new SpecReviewCommentServiceException("cannot find UserComment for commentId = "
                + comment.getCommentId());
        }

        // update the comment
        userComment.setComment(comment.getComment());
    }
}
