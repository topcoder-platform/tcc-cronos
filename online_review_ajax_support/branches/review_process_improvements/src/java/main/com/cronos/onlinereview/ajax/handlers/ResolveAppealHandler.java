/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cronos.onlinereview.ajax.AjaxRequest;
import com.cronos.onlinereview.ajax.AjaxResponse;
import com.cronos.onlinereview.ajax.AjaxSupportHelper;
import com.cronos.onlinereview.ajax.ConfigurationException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scorecalculator.CalculationManager;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Ajax request handler class which handles the "Resolve Appeal" Ajax operation.
 * this class extends the ReviewCommonHandler abstract class,
 * and uses the managers defined in its parents classes in order to implement the "Resolve Appeal" operation.
 *
 * Any successful or failed operation is logged using the Logging Wrapper component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is immutable an thread safe. Any manager class used by this handler is supposed to be thread safe.
 * </p>
 *
 * <p>
 * Version 1.0.2 (Online Review Update Review Management Process assembly 2) Change notes:
 * <ol>
 * <li>Added {@link #EXTERNAL_REFERENCE_ID_PROPERTY} constant.</li>
 * <li>Added {@link #TYPE_SECONDARY_REVIEWER_REVIEW} constant.</li>
 * <li>Added {@link #TYPE_PRIMARY_APPEALS_RESPONSE} constant.</li>
 * <li>Update {@link #ResolveAppealHandler()} method to work for the new <code>Primary Review Appeals Response</code> phase.</li>
 * <li>Update {@link #service(AjaxRequest, Long)} method to work for the new <code>Primary Review Appeals Response</code> phase. It
 * allows <code>Primary Review Evaluator</code> to submit appeals response in <code>Primary Review Appeals Response</code> phase. Also
 * <code>Primary Review Evaluator</code> can submit multiply appeals response and multiply evaluation types for a review item.</li>
 * </ol>
 * </p>
 * 
 * @author topgear
 * @author assistant, TCSASSEMBER
 * @version 1.0.2
 */
public class ResolveAppealHandler extends ReviewCommonHandler {

    /**
     * <p>A <code>String</code> providing the name of the resource property which is expected to provide the user ID.
     * </p>
     */
    protected static final String EXTERNAL_REFERENCE_ID_PROPERTY = "External Reference ID";

    /**
     * The magic string for status open.
     */
    private static final String STATUS_OPEN = "Open";

    /**
     * The magic string for type review.
     */
    private static final String TYPE_REVIEW = "Review";

    /**
     * The magic string for type Secondary Reviewer Review.
     */
    private static final String TYPE_SECONDARY_REVIEWER_REVIEW = "Secondary Reviewer Review";

    /**
     * The magic string for type appeals response.
     */
    private static final String TYPE_APPEALS_RESPONSE = "Appeals Response";

    /**
     * The magic string for type primary review appeals response.
     */
    private static final String TYPE_PRIMARY_APPEALS_RESPONSE = "Primary Review Appeals Response";

    /**
     * The magic string for type comment.
     */
    private static final String COMMENT_TYPE_COMMENT = "Comment";

    /**
     * The magic string for type recommended.
     */
    private static final String COMMENT_TYPE_RECOMMENDED = "Recommended";

    /**
     * The magic string for type required.
     */
    private static final String COMMENT_TYPE_REQUIRED = "Required";

    /**
     * The magic string for type appeal response.
     */
    private static final String COMMENT_TYPE_APPEAL_RESPONSE = "Appeal Response";

    /**
     * Represents the status of success.
     */
    private static final String SUCCESS = "Success";

    /**
     * Represents the status of business error.
     */
    private static final String BUSINESS_ERROR = "Business error";

    /**
     * Represents the status of role error.
     */
    private static final String ROLE_ERROR = "Role error";

    /**
     * Represents the status of login error.
     */
    private static final String LOGIN_ERROR = "Login error";

    /**
     * Represents the status of invalid parameter error.
     */
    private static final String INVALID_PARAMETER_ERROR = "Invalid parameter error";

    /**
     * Represents the status of phase error.
     */
    private static final String PHASE_ERROR = "Phase error";

    /**
     * Represents the status of invalid item error.
     */
    private static final String INVALID_ITEM_ERROR = "Invalid item error";

    /**
     * Represents the status of invalid comment error.
     */
    private static final String INVALID_COMMENT_ERROR = "Invalid comment error";

    /**
     * Represents the status of invalid review error.
     */
    private static final String INVALID_REVIEW_ERROR = "Invalid review error";

    /**
     * <p>
     * Represents comment type with name "Comment", it is used to modify the original comment type.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType
     * obtained from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType commentCommentType;

    /**
     * <p>
     * Represents comment type with name "Recommended", it is used to modify the original comment type.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType
     * obtained from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType recommendedCommentType;

    /**
     * <p>
     * Represents comment type with name "Required", it is used to modify the original comment type.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType
     * obtained from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType requiredCommentType;

    /**
     * <p>
     * Represents comment type with name "Appeal Response", it is used to create an appeal response comment.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType
     * obtained from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType appealResponseCommentType;

    private final Map<String, EvaluationType> evaluationTypes;

    /**
     * <p>
     * The id of the phase type with name "Review". it is used to check that a phase has the "Review" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long reviewPhaseTypeId;

    /**
     * <p>
     * The id of the phase type with name "Secondary Reviewer Review". it is used to check that a phase has the "Secondary Reviewer Review" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long secondaryReviewerReviewPhaseTypeId;

    /**
     * <p>
     * The id of the phase type with name "Appeals Response".
     * It is used to check that a phase has the "Appeals Response" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long appealsResponsePhaseTypeId;

    /**
     * <p>
     * The id of the phase type with name "Primary Review Appeals Response".
     * It is used to check that a phase has the "Primary Review Appeals Response" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long primaryAppealsResponsePhaseTypeId;
    
    /**
     * <p>
     * The id of the phase status with name "Open". it is used to check that a phase has the "Open" status.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long openPhaseStatusId;

    /**
     * <p>
     * The calculation manager used to calculate review score.
     * </p>
     */
    private final CalculationManager calculationManager;

    /**
     * <p>
     * The score card manager used to calculate review score.
     * </p>
     */
    private final ScorecardManager scorecardManager;

    /**
     * <p>
     * <strong>Description : </strong>
     * Creates an instance of this class and initialize its internal state.
     * </p>
     *
     * @throws ConfigurationException if there is a configuration exception
     */
    public ResolveAppealHandler() throws ConfigurationException {

        try {
            calculationManager = (CalculationManager) AjaxSupportHelper.createObject(CalculationManager.class);
            scorecardManager = (ScorecardManager) AjaxSupportHelper.createObject(ScorecardManager.class);

            // get all comment types and find the one with name "Appeal Response"
            CommentType[] commentTypes = getReviewManager().getAllCommentTypes();
            CommentType comment = null;
            CommentType recommended = null;
            CommentType required = null;
            CommentType appealResponse = null;
            for (int i = 0; i < commentTypes.length; i++) {
                if (commentTypes[i].getName().equals(COMMENT_TYPE_COMMENT)) {
                    comment = commentTypes[i];
                } else if (commentTypes[i].getName().equals(COMMENT_TYPE_RECOMMENDED)) {
                    recommended = commentTypes[i];
                } else if (commentTypes[i].getName().equals(COMMENT_TYPE_REQUIRED)) {
                    required = commentTypes[i];
                } else if (commentTypes[i].getName().equals(COMMENT_TYPE_APPEAL_RESPONSE)) {
                    appealResponse = commentTypes[i];
                }
            }
            if (comment != null) {
                this.commentCommentType = comment;
            } else {
                throw new ConfigurationException("No comment type Comment found.");
            }
            if (recommended != null) {
                this.recommendedCommentType = recommended;
            } else {
                throw new ConfigurationException("No comment type Recommended found.");
            }
            if (required != null) {
                this.requiredCommentType = required;
            } else {
                throw new ConfigurationException("No comment type Required found.");
            }
            if (appealResponse != null) {
                this.appealResponseCommentType = appealResponse;
            } else {
                throw new ConfigurationException("No comment type Appeal Response found.");
            }

            // get all phase types and find the review phase type id and appeals phase type id
            PhaseType[] phaseTypes = getPhaseManager().getAllPhaseTypes();
            long reviewTypeId = 0;
            long secondaryReviewerReviewTypeId = 0;
            long appealResponseTypeId = 0;
            long primaryAppealsResponseTypeId = 0;
            boolean foundReview = false;
            boolean foundAppeal = false;
            for (int i = 0; i < phaseTypes.length; i++) {
                if (phaseTypes[i].getName().equals(TYPE_REVIEW)) {
                    reviewTypeId = phaseTypes[i].getId();
                    foundReview = true;
                }
                if (phaseTypes[i].getName().equals(TYPE_SECONDARY_REVIEWER_REVIEW)) {
                    secondaryReviewerReviewTypeId = phaseTypes[i].getId();
                    foundReview = true;
                }
                if (phaseTypes[i].getName().equals(TYPE_APPEALS_RESPONSE)) {
                    appealResponseTypeId = phaseTypes[i].getId();
                    foundAppeal = true;
                }
                if (phaseTypes[i].getName().equals(TYPE_PRIMARY_APPEALS_RESPONSE)) {
                    primaryAppealsResponseTypeId = phaseTypes[i].getId();
                    foundAppeal = true;
                }
            }

            if (!foundReview) {
                throw new ConfigurationException("The review phase type id can't be found.");
            }
            if (!foundAppeal) {
                throw new ConfigurationException("The appeal response phase type id can't be found.");
            }
            this.reviewPhaseTypeId = reviewTypeId;
            this.secondaryReviewerReviewPhaseTypeId = secondaryReviewerReviewTypeId;
            this.appealsResponsePhaseTypeId = appealResponseTypeId;
            this.primaryAppealsResponsePhaseTypeId = primaryAppealsResponseTypeId;

            // get the open phase status id
            PhaseStatus[] statuses = getPhaseManager().getAllPhaseStatuses();
            boolean found = false;
            long id = 0;
            for (int i = 0; i < statuses.length; i++) {
                if (statuses[i].getName().equals(STATUS_OPEN)) {
                    id = statuses[i].getId();
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new ConfigurationException("The open phase status id can't be found.");
            }
            this.openPhaseStatusId = id;
            
            Map<String, EvaluationType> etypes = new HashMap<String, EvaluationType>();
            for (EvaluationType et : getReviewManager().getAllEvaluationTypes()) {
                etypes.put(String.valueOf(et.getId()), et);
            }
            evaluationTypes = etypes;
        } catch (Exception e) {
            if (e instanceof ConfigurationException) {
                throw (ConfigurationException) e;
            }
            throw new ConfigurationException("Something error when loading configurations.", e);
        }
    }

    /**
     * <p>
     * Performs the "Resolve Appeal" operation and returns the score of the review
     * if the operation is successful wrapped in an AjaxResponse,
     * if the operation fails this method returns a failure Ajax response.
     * </p>
     *
     * @return the response to the request
     * @param request the request to service
     * @param userId the id of user who issued this request
     * @throws IllegalArgumentException if the request is null
     */
    public AjaxResponse service(AjaxRequest request, Long userId) {

        if (request == null) {
            throw new IllegalArgumentException("The request should not be null.");
        }
        // check all the required parameters
        long reviewId = 0;
        long itemId = 0;
        String answer = null;

        // ReviewID
        try {
            reviewId = request.getParameterAsLong("ReviewId");
        } catch (NumberFormatException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", userId, e);
        }
        // ItemId
        try {
            itemId = request.getParameterAsLong("ItemId");
        } catch (NumberFormatException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", userId, e);
        }

        // status
        String[] statuses = getStringArray(request, "Status");
        boolean statusOk = true;
        if (statuses.length == 0) {
            statusOk = false;
        }
        if (!statusOk) {
            for (String status : statuses) {
                if (!status.equals("Succeeded") && !status.equals("Failed")) {
                    statusOk = false;
                    break;
                }
            }
        }
        if (!statusOk) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The status must be Succeeded or Failed.", statuses);
        }
        // answer and text
        answer = request.getParameter("Answer");
        // ISV : Appeal text is required and must be provided by "Text" parameter but not "text"
        String[] textes = getStringArray(request, "Text");
        boolean textOk = true;
        if (textes.length == 0) {
            textOk = false;
        }
        if (!textOk) {
            for (String text : textes) {
                if (text.trim().length() == 0) {
                    textOk = false;
                    break;
                }
            }
        }
        if (!textOk) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The appeal text must be provided.", "ResolveAppeal. " + "User id : " + userId);
        }

        if (textes.length != statuses.length) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The size of text should be the same with the size of status.", "ResolveAppeal. " + "User id : " + userId);
        }

        // check the userId for validation
        if (userId == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), LOGIN_ERROR,
                    "Doesn't login or expired.", userId);
        }

        // check the user is the author of the review
        Review review = null;
        try {
            review = getReviewManager().getReview(reviewId);
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get the review : " + e.getMessage(),
                    "User id : " + userId + "\treview id : " + reviewId, e);
        }
        if (review == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_REVIEW_ERROR,
                    "Can't get the review",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        // get the reviewer resource
        Resource reviewerResource = null;
        try {
            reviewerResource = getResourceManager().getResource(review.getAuthor());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get reviewer resource.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\treviwer id :" + review.getAuthor(), e);
        }
        // validate the review resource
        if (reviewerResource.getPhase() == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    PHASE_ERROR, "The reviewerResource should have a phase.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\treviwer id :" + review.getAuthor());
        }

        // check the user has the role of "Reviewer"
        try {
            if (!(checkResourceHasRole(reviewerResource, "Reviewer")
                  || checkResourceHasRole(reviewerResource, "Accuracy Reviewer")
                  || checkResourceHasRole(reviewerResource, "Failure Reviewer")
                  || checkResourceHasRole(reviewerResource, "Stress Reviewer")
                  || checkResourceHasRole(reviewerResource, "Secondary Reviewer"))) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        ROLE_ERROR, "The user should be a reviewer.",
                        "User id : " + userId + "\treview id : " + reviewId);
            }
        } catch (ResourceException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't check the user role.",
                    "User id : " + userId + "\tsubmission id : " + reviewId, e);
        }


        Phase[] phases = null;
        try {
            phases = getPhaseManager().getPhases(reviewerResource.getProject().longValue()).getAllPhases();
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get phases.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\tproject id :" + reviewerResource.getProject().longValue(), e);
        }

        // get the review phase
        Phase reviewPhase = null;
        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getPhaseType().getId() == reviewPhaseTypeId) {
                reviewPhase = phases[i];
                break;
            } else if (phases[i].getPhaseType().getId() == secondaryReviewerReviewPhaseTypeId) {
                reviewPhase = phases[i];
                break;
            }
        }
        if (reviewPhase == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get review phase.",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        Resource authorResource = null;
        if (reviewPhase.getPhaseType().getId() == reviewPhaseTypeId) {
            authorResource = reviewerResource;
        } else {
            // In Primary Review Appeals Response phase, the primary review evaluator do the response 
            try {
                Filter filter = ResourceFilterBuilder.createProjectIdFilter(reviewerResource.getProject().longValue());
                Resource[] allResources = getResourceManager().searchResources(filter);
                for (Resource res : allResources) {
                    if (res.getResourceRole().getName().equals("Primary Review Evaluator")
                            && Long.parseLong(res.getProperty(EXTERNAL_REFERENCE_ID_PROPERTY)) == userId) {
                        authorResource = res;
                    }
                }
            } catch (Exception e) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        BUSINESS_ERROR, "Can't get project resources.",
                        "User id : " + userId + "\treview id : " + reviewId);
            }
        }
        if (authorResource == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "The user role cannot perform appeals response.",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        // validate the phase
        if (reviewPhase.getId() != reviewerResource.getPhase().longValue()) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    ROLE_ERROR, "The reviewerResource should have a phase the same with the review phase.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\texpected phase id :" + reviewPhase.getId() + "\tactual id : " + reviewerResource.getPhase());
        }

        // get the appeal response phase
        Phase appealResponsePhase = null;
        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getPhaseType().getId() == appealsResponsePhaseTypeId) {
                appealResponsePhase = phases[i];
                break;
            } else if (phases[i].getPhaseType().getId() == primaryAppealsResponsePhaseTypeId) {
                appealResponsePhase = phases[i];
                break;
            }
        }

        // validate the phase
        if (appealResponsePhase == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get appeal response phase.",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        if (appealResponsePhase.getPhaseStatus().getId() != openPhaseStatusId) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    PHASE_ERROR, "The phase should be open.",
                    "User id : " + userId + "\treview id : " + reviewId + "\tphase id :" + appealResponsePhase.getId());
        }

        // get all the review items
        Item[] items = review.getAllItems();

        // find the item with itemId
        Item item = null;
        for (int i = 0; i < items.length; i++) {
            if (items[i].getId() == itemId) {
                item = items[i];
                break;
            }
        }
        if (item == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    INVALID_ITEM_ERROR, "The item can't be found.",
                    "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
        }

        // get all the comments
        Comment[] comments = item.getAllComments();
        Comment appealResponseComment = null;

        // find the one with type appealResponseCommentType
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType().getId() == this.appealResponseCommentType.getId()) {
                appealResponseComment = comments[i];
                break;
            }
        }

        // find the comment with appeal comment type
        List<Comment> appealComment = new ArrayList<Comment>();
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType().getName().equals("Appeal")) {
                appealComment.add(comments[i]);
            }
        }
        if (appealComment.size() != textes.length) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    PHASE_ERROR, "The size of text should be the same with the size of appeal comment.",
                    "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
        }
        Map<Long, Comment> appealsResponse = new HashMap<Long, Comment>();
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType().getName().equals("Appeal Response")) {
                appealsResponse.put(Long.parseLong(String.valueOf(comments[i].getExtraInfo())), comments[i]);
            }
        }
        for (int i = 0; i < appealComment.size(); i++) {
            appealComment.get(i).setExtraInfo(statuses[i]);
            Long appealCommentId = appealComment.get(i).getId();
            // if the comment doesn't exist
            if (appealsResponse.containsKey(new Long(appealCommentId))) {
                // just set the comment text
                appealsResponse.get(appealCommentId).setComment(textes[i]);
            } else {
                // create a new one and add it to the item
                appealResponseComment = new Comment();
                appealResponseComment.setCommentType(appealResponseCommentType);
                appealResponseComment.setComment(textes[i]);
                // appealResponseComment.setExtraInfo(item.getAnswer());
                appealResponseComment.setExtraInfo(String.valueOf(appealCommentId));
                appealResponseComment.setAuthor(authorResource.getId());
                item.addComment(appealResponseComment);
            }
        }

        // update the item
        item.setAnswer(answer);

        // WB - allow modify the type of the original comments
        for (Iterator itr = request.getAllParameterNames().iterator(); itr.hasNext();) {
            String paramName = (String) itr.next();
            if (!paramName.startsWith("CommentType")) {
                continue;
            }
            String commentId = paramName.substring("CommentType".length());
            String newType = request.getParameter(paramName);

            // find the original comment
            Comment originalComment = null;
            for (int i = 0; i < comments.length; i++) {
                if (commentId.equals(String.valueOf(comments[i].getId()))) {
                    originalComment = comments[i];
                    break;
                }
            }
            if (originalComment == null) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The original comment with id " + commentId + " can not be found.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }

            // verify the comment is of one of the three types
            if (originalComment.getCommentType().getId() != commentCommentType.getId()
                    && originalComment.getCommentType().getId() != recommendedCommentType.getId()
                    && originalComment.getCommentType().getId() != requiredCommentType.getId()) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The original comment with id " + commentId + " can not be modified.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }

            // verify the new comment can be set
            if (COMMENT_TYPE_COMMENT.equals(newType)) {
                originalComment.setCommentType(commentCommentType);
            } else if (COMMENT_TYPE_RECOMMENDED.equals(newType)) {
                originalComment.setCommentType(recommendedCommentType);
            } else if (COMMENT_TYPE_REQUIRED.equals(newType)) {
                originalComment.setCommentType(requiredCommentType);
            } else {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The new comment type " + newType + " can not be recognized.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }
        }

        // WB - allow modify the type of the original comment's evaluation type
        for (Iterator itr = request.getAllParameterNames().iterator(); itr.hasNext();) {
            String paramName = (String) itr.next();
            if (!paramName.startsWith("EvaluationType")) {
                continue;
            }
            String commentId = paramName.substring("EvaluationType".length());
            String newType = request.getParameter(paramName);

            // find the original comment
            Comment originalComment = null;
            for (int i = 0; i < comments.length; i++) {
                if (commentId.equals(String.valueOf(comments[i].getId()))) {
                    originalComment = comments[i];
                    break;
                }
            }
            if (originalComment == null) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The original comment with id " + commentId + " can not be found.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }

            // verify the comment is of one of the three types
            if (originalComment.getCommentType().getId() != commentCommentType.getId()
                    && originalComment.getCommentType().getId() != recommendedCommentType.getId()
                    && originalComment.getCommentType().getId() != requiredCommentType.getId()) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The original comment with id " + commentId + " can not be modified.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }

            // if the original evaluation type is null, then we cannot set the evaluation type now
            if (originalComment.getEvaluationType() == null) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The original comment with id " + commentId + " can not have evaluation.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }

            EvaluationType evaluationType = evaluationTypes.get(newType);
            if (evaluationType == null) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        INVALID_COMMENT_ERROR, "The evaluation type " + newType + " can not be found.",
                        "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
            }
            originalComment.setEvaluationType(evaluationType);
        }

        // calculate the review score
        // get the scorecard id from the review
        try {
            long scorecardId = review.getScorecard();
            // get scorecard
            Scorecard card = scorecardManager.getScorecard(scorecardId);
            // calculate the score
            float score = calculationManager.getScore(card, review);

            review.setScore(new Float(score));
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Error in calculating score.",
                    "User id : " + userId + "\treview id : " + reviewId, e);
        }
        // update the item
        try {
            getReviewManager().updateReview(review, userId.toString());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't update review.",
                    "User id : " + userId + "\treview id : " + reviewId, e);
        }

        // succeed
        return AjaxSupportHelper.createAndLogSucceess(request.getType(), SUCCESS,
                "Suceeded to response appeal.", review.getScore(), "ResponseAppeal."
                + "\tuser id : " + userId + "review id :" + review.getId()
                + "\titem id : " + item.getId());
    }
    
    private static String[] getStringArray(AjaxRequest request, String paramPrefix) {
        List<String> values = new ArrayList<String>();
        int index = 0;
        while (true) {
            String v = request.getParameter(paramPrefix + index);
            if (v == null) {
                break;
            }
            values.add(v);
            index++;
        }
        return values.toArray(new String[0]);
    }
}
