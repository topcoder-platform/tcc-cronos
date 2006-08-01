/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;
import com.cronos.onlinereview.ajax.AjaxRequest;
import com.cronos.onlinereview.ajax.AjaxResponse;
import com.cronos.onlinereview.ajax.AjaxSupportHelper;
import com.cronos.onlinereview.ajax.ConfigurationException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scorecalculator.CalculationManager;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;

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
 * <strong>Thread Safety : </strong>
 * This class is immutable an thread safe. any manager class used by this handler is supposed to be thread safe.
 * </p>
 *
 * @author topgear, TCSDEVELOPER
 * @version 1.0
 */
public class ResolveAppealHandler extends ReviewCommonHandler {

    /**
     * The magic string for status open.
     */
    private static final String STATUS_OPEN = "Open";

    /**
     * The magic string for type review.
     */
    private static final String TYPE_REVIEW = "Review";

    /**
     * The magic string for type appeals response.
     */
    private static final String TYPE_APPEALS_RESPONSE = "Appeals Response";

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
     * Represents the status of invalid review error.
     */
    private static final String INVALID_REVIEW_ERROR = "Invalid review error";

    /**
     * <p>
     * Represents comment type with name "Appeal Response", it is used to create an appeal response comment.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType
     * obtained from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType appealResponseCommentType;

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
     * The id of the phase type with name "Appeals Response".
     * It is used to check that a phase has the "Appeals Response" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long appealsResponsePhaseTypeId;

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
            CommentType type = null;
            for (int i = 0; i < commentTypes.length; i++) {
                if (commentTypes[i].getName().equals("Appeal Response")) {
                    type = commentTypes[i];
                    break;
                }
            }
            if (type == null) {
                throw new ConfigurationException("No appeal found.");
            }
            this.appealResponseCommentType = type;

            // get all phase types and find the review phase type id and appeals phase type id
            PhaseType[] phaseTypes = getPhaseManager().getAllPhaseTypes();
            long reviewTypeId = 0;
            long appealResponseTypeId = 0;
            boolean foundReview = false;
            boolean foundAppeal = false;
            for (int i = 0; i < phaseTypes.length; i++) {
                if (phaseTypes[i].getName().equals(TYPE_REVIEW)) {
                    reviewTypeId = phaseTypes[i].getId();
                    foundReview = true;
                }
                if (phaseTypes[i].getName().equals(TYPE_APPEALS_RESPONSE)) {
                    appealResponseTypeId = phaseTypes[i].getId();
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
            this.appealsResponsePhaseTypeId = appealResponseTypeId;

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
     * if the operation failse this method returns a failure Ajax response.
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
        String status = null;
        String answer = null;
        String text = null;

        // ReviewID
        try {
            reviewId = request.getParameterAsLong("ReviewId");
        } catch (NumberFormatException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", userId);
        }
        // ItemId
        try {
            itemId = request.getParameterAsLong("ItemId");
        } catch (NumberFormatException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", userId);
        }

        // status
        status = request.getParameter("Status");
        if (status == null || (!status.equals("Succeeded") && !status.equals("Failed"))) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The status must be Succeeded or Failed.", status);
        }
        // answer and text
        answer = request.getParameter("Answer");
        text = request.getParameter("text");

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
                    "User id : " + userId + "\treview id : " + reviewId);
        }
        if (review == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_REVIEW_ERROR,
                    "Can't get the review",
                    "User id : " + userId + "\treview id : " + reviewId);
        }
        if (review.getAuthor() != userId.longValue()) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    ROLE_ERROR, "The user should be the author of the review.",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        // check the user has the role of "Reviewer"
        try {
            if (!checkUserHasRole(userId.longValue(), "Reviewer")) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        ROLE_ERROR, "The user should be a reviewer.",
                        "User id : " + userId + "\treview id : " + reviewId);
            }
        } catch (RoleResolutionException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't check the user role.",
                    "User id : " + userId + "\tsubmission id : " + reviewId);
        }

        // get the reviewer resource
        Resource reviewerResource = null;
        try {
            reviewerResource = getResourceManager().getResource(review.getAuthor());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get reviewer resource.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\treviwer id :" + review.getAuthor());
        }
        // validate the review resource
        if (reviewerResource.getPhase() == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    PHASE_ERROR, "The reviewerResource should have a phase.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\treviwer id :" + review.getAuthor());
        }

        Phase[] phases = null;
        try {
            phases = getPhaseManager().getPhases(reviewerResource.getProject().longValue()).getAllPhases();
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get phases.",
                    "User id : " + userId + "\treview id : " + reviewId
                    + "\tproject id :" + reviewerResource.getProject().longValue());
        }

        // get the review phase
        Phase reviewPhase = null;
        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getPhaseType().getId() == reviewPhaseTypeId) {
                reviewPhase = phases[i];
                break;
            }
        }
        if (reviewPhase == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get review phase.",
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
            }
        }

        // valiate the phase
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
        Comment comment = null;

        // find the one with type appealResponseCommentType
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType().getId() == this.appealResponseCommentType.getId()) {
                comment = comments[i];
                break;
            }
        }

        // if the comment doesn't exist
        if (comment == null) {
            // create a new one and add it to the item
            comment = new Comment();
            comment.setCommentType(appealResponseCommentType);
            comment.setComment(text);
            comment.setExtraInfo(item.getAnswer());
            comment.setAuthor(userId.longValue());
            item.addComment(comment);
        } else {
            // just set the comment text
            comment.setComment(text);
        }

        // find the comment with appeal comment type
        Comment appealComment = null;
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType().getName().equals("Appeal")) {
                appealComment = comments[i];
                break;
            }
        }
        if (appealComment == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    PHASE_ERROR, "There should be an appeal comment.",
                    "User id : " + userId + "\treview id : " + reviewId + "\titem id :" + itemId);
        }

        appealComment.setExtraInfo(status);

        // update the item
        item.setAnswer(answer);

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
                    "User id : " + userId + "\treview id : " + reviewId);
        }
        // update the item
        try {
            getReviewManager().updateReview(review, userId.toString());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't update review.",
                    "User id : " + userId + "\treview id : " + reviewId);
        }

        // succeed
        return AjaxSupportHelper.createAndLogSucceess(request.getType(), SUCCESS,
                "Suceeded to response appeal.", review.getScore(), "ResponseAppeal."
                + "\tuser id : " + userId + "review id :" + review.getId()
                + "\titem id : " + item.getId());
    }
}
