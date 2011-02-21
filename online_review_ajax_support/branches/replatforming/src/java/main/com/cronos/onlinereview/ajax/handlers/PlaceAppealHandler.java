/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;

import com.cronos.onlinereview.ajax.AjaxRequest;
import com.cronos.onlinereview.ajax.AjaxResponse;
import com.cronos.onlinereview.ajax.AjaxSupportHelper;
import com.cronos.onlinereview.ajax.ConfigurationException;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Ajax request handler class which handles the "Place Appeal" Ajax operation.
 * This class extends the ReviewCommonHandler abstract class,
 * and uses the managers defined in its parents classes,
 * plus an instance of the UploadManager class in order to implement the "Place Appeal" operation.
 *
 * Any successful or failed operation is logged using the Logging Wrapper component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>
 * This class is immutable an thread safe. any manager class used by this handler is supposed to be thread safe.
 * </p>
 *
 * <p>
 * Version 1.0.2 (Milestone Support Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Made the class compatible with latest version of <code>Online Review Deliverables</code> component.</li>
 *   </ol>
 * </p>
 *
 * @author topgear
 * @author assistant
 * @author George1, TCSDEVELOPER
 * @version 1.0.2
 */
public class PlaceAppealHandler extends ReviewCommonHandler {
	private static final com.topcoder.util.log.Log log = com.topcoder.util.log.LogFactory
			.getLog(PlaceAppealHandler.class.getName());
	
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
    private static final String TYPE_APPEALS = "Appeals";

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
     * Represents the status of invalid parameter error.
     */
    private static final String POSSIBLE_TEXT_CUTOFF = "Possible text cutoff error";

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
     * The upload manager used to get submission data
     * This variable is immutable, it is initialized by the constructor to a not null UploadManager object,
     * and used by the service method.
     * </p>
     */
    private final UploadManager uploadManager;

    /**
     * <p>
     * Represents comment type with name "Appeal", it is used to create an appeal comment.
     * This variable is immutable, it is initialized by the constructor to a not null CommentType obtained
     * from the ReviewManager of the parent class, and used by the service method.
     * </p>
     */
    private final CommentType appealCommentType;

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
     * The id of the phase type with name "Appeals". it is used to check that a phase has the "Appeals" phase type.
     * This variable is immutable, it is initialized by the constructor to a negative/0/positive long number,
     * and used by the service method.
     * </p>
     */
    private final long appealsPhaseTypeId;

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
     * Creates an instance of this class and initialize its internal state.
     * </p>
     *
     * @throws ConfigurationException if there is a configuration exception
     */
    public PlaceAppealHandler() throws ConfigurationException {

        try {
            uploadManager = (UploadManager) AjaxSupportHelper.createObject(UploadManager.class);

            // get all comment types and find the one with name Appeal
            CommentType[] commentTypes = getReviewManager().getAllCommentTypes();
            CommentType type = null;
            for (int i = 0; i < commentTypes.length; i++) {
                if (commentTypes[i].getName().equals("Appeal")) {
                    type = commentTypes[i];
                    break;
                }
            }
            if (type == null) {
                throw new ConfigurationException("No appeal comment type found.");
            }
            this.appealCommentType = type;

            // get all phase types and find the review phase type id and appeals phase type id
            PhaseType[] phaseTypes = getPhaseManager().getAllPhaseTypes();
            long reviewTypeId = 0;
            long appealTypeId = 0;
            boolean foundReview = false;
            boolean foundAppeal = false;
            for (int i = 0; i < phaseTypes.length; i++) {
                if (phaseTypes[i].getName().equals(TYPE_REVIEW)) {
                    reviewTypeId = phaseTypes[i].getId();
                    foundReview = true;
                } else if (phaseTypes[i].getName().equals(TYPE_APPEALS)) {
                    appealTypeId = phaseTypes[i].getId();
                    foundAppeal = true;
                }
            }

            if (!foundReview) {
                throw new ConfigurationException("The review phase type id can't be found.");
            }
            if (!foundAppeal) {
                throw new ConfigurationException("The appeal phase type id can't be found.");
            }
            this.reviewPhaseTypeId = reviewTypeId;
            this.appealsPhaseTypeId = appealTypeId;

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
     * Performs the "Place Appeal" operation and return the success or failure Ajax response.
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

        // Check the userId for validation
        if (userId == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), LOGIN_ERROR,
                    "Doesn't login or expired.", "PlaceAppeal. " + "User id : " + userId);
        }

        // Variables to hold parsed parameters' values
        long reviewId = 0;
        long itemId = 0;
        String text = null;
        long textLength = 0;

        // ReviewID parameter
        try {
            reviewId = request.getParameterAsLong("ReviewId");
        } catch (NumberFormatException nfe) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", "PlaceAppeal. User id : " + userId, nfe);
        }
        // ItemId parameter
        try {
            itemId = request.getParameterAsLong("ItemId");
        } catch (NumberFormatException nfe) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The review id should be a long value.", "PlaceAppeal. User id : " + userId, nfe);
        }
        // Text parameter
        text = request.getParameter("Text");
        if (text == null || text.trim().length() == 0) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "The appeal text must be provided.", "PlaceAppeal. User id : " + userId);
        }
        // TextLength parameter
        try {
            textLength = request.getParameterAsLong("TextLength");
        } catch (NumberFormatException nfe) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_PARAMETER_ERROR,
                    "Text length parameter must be specified and parsable to long.",
                    "PlaceAppeal. User id : " + userId, nfe);
        }

        // Verify text's length is exactly the same as specified by TextLength parameter
        if (text.length() != textLength) {
//            AjaxResponse response = AjaxSupportHelper.createAndLogError(request.getType(), POSSIBLE_TEXT_CUTOFF,
//                    "Received Appeal's text had incorrect length. Received text was "
//                            + text.length() + " characters length, but should have been " + textLength + ".",
//                    "PlaceAppeal. User id : " + userId);
            
            log.log(Level.WARN, request.getType()
                    + " Additional info. The text of Appeal received:\n----- begin of appeal text -----\n"
                    + text
                    + "\n----- end of appeal -----");
//            return response;
        }

        // check whether this user has the right to appeal
        // Get the review using the reviewManager
        Review review = null;
        try {
            review = getReviewManager().getReview(reviewId);
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get the review : " + e.getMessage(),
                    "PlaceAppeal. User id : " + userId + "\treview id : " + reviewId, e);
        }
        if (review == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(), INVALID_REVIEW_ERROR,
                    "Can't get the review",
                    "PlaceAppeal. User id : " + userId + "\treview id : " + reviewId);
        }


        // Get the submission id
        long submissionId = review.getSubmission();

        // Get the submission
        Submission submission = null;
        try {
            submission = uploadManager.getSubmission(submissionId);
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get the submission : " + e.getMessage(),
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }
        if (submission == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get the submission.",
                    "User id : " + userId + "\tsubmission id : " + submissionId);
        }

        // Get the upload
        Upload upload = submission.getUploads().get(0);

        // get the submission resource
        Resource submitterResource = null;
        try {
            submitterResource = getResourceManager().getResource(upload.getOwner());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Error when finding the resource.",
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }
        if (submitterResource == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Error when finding the resource.",
                    "User id : " + userId + "\tsubmission id : " + submissionId);
        }

        try {
            if (!checkResourceAssignedToUser(submitterResource, userId.longValue())) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        ROLE_ERROR, "The user should be a submitter.",
                        "User id : " + userId + "\tsubmission id : " + submissionId);
            }
        } catch (ResourceException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't check the user role.",
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }

        // check the user has submitter role
        try {
            if (!checkResourceHasRole(submitterResource, "Submitter")) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        ROLE_ERROR, "The user should be a submitter.",
                        "User id : " + userId + "\tsubmission id : " + submissionId);
            }
        } catch (ResourceException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't check the user role.",
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }

        // ISV : Get the user ID for the submitter resource
        Object extRefId = submitterResource.getProperty(EXTERNAL_REFERENCE_ID_PROPERTY);
        if (extRefId == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "The resource matching the resource ID [" + submitterResource.getId() + "] does not"
                                         + " provide the 'External Reference ID' property",
                    "User id : " + userId + "\tsubmission id : " + submissionId);
        } else {
            // ISV : Convert to string as at this point the exact type of the property is not clarified
            String extRefIdString = String.valueOf(extRefId);
            if (userId.longValue() != Long.parseLong(extRefIdString)) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                                                           ROLE_ERROR, "The user is not right.",
                                                           "User id : " + userId + "\tsubmission id : " + submissionId);
            }
        }


        // get the reviewer resource
        Resource reviewerResource = null;
        try {
            reviewerResource = getResourceManager().getResource(review.getAuthor());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Error when finding the resource.",
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }
        if (reviewerResource == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Error when finding the resource.",
                    "User id : " + userId + "\tsubmission id : " + submissionId);
        }

        // check the review's author has reviewer role
        try {
            if (!(checkResourceHasRole(reviewerResource, "Reviewer") ||
                  checkResourceHasRole(reviewerResource, "Accuracy Reviewer") ||
                  checkResourceHasRole(reviewerResource, "Failure Reviewer") ||
                  checkResourceHasRole(reviewerResource, "Stress Reviewer"))) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        ROLE_ERROR, "The author should be a reviewer.",
                        "User id : " + userId + "\tsubmission id : " + submissionId
                        + "\treviewer : " + review.getAuthor());
            }
        } catch (ResourceException e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't check the user role.",
                    "User id : " + userId + "\tsubmission id : " + submissionId, e);
        }


        // get all the phases
        Phase[] phases = null;
        try {
            phases = getPhaseManager().getPhases(upload.getProject()).getAllPhases();
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get phases.",
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\tupload id :" + upload.getId(), e);
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
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\tupload id :" + upload.getId());
        }

        // validate the review resource
        if (reviewerResource.getPhase() == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    ROLE_ERROR, "The reviewerResource should have a phase.",
                    "User id : " + userId + "\tsubmission id : " + submissionId
                    + "\treviwer id :" + review.getAuthor());
        }
        if (reviewPhase.getId() != reviewerResource.getPhase().longValue()) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    ROLE_ERROR, "The reviewerResource should have a phase the same with the review phase.",
                    "User id : " + userId + "\tsubmission id : " + submissionId
                    + "\texpected phase id :" + reviewPhase.getId() + "\tactual id : " + reviewerResource.getPhase());
        }

        // get the appeal phase
        Phase appealPhase = null;
        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getPhaseType().getId() == appealsPhaseTypeId) {
                appealPhase = phases[i];
                break;
            }
        }

        // validate the phase
        if (appealPhase == null) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't get appeal phase.",
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\tupload id :" + upload.getId());
        }

        if (appealPhase.getPhaseStatus().getId() != openPhaseStatusId) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "The phase should be open.",
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\tphase id :" + appealPhase.getId());
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
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\titem id :" + itemId);
        }

        // get all the comments
        Comment[] comments = item.getAllComments();

        // check whether there is already appeal comment
        for (int i = 0; i < comments.length; i++) {
            if (comments[i].getCommentType() != null
                    && appealCommentType.getName().equals(comments[i].getCommentType().getName())
                    && appealCommentType.getId() == comments[i].getCommentType().getId()) {
                return AjaxSupportHelper.createAndLogError(request.getType(),
                        PHASE_ERROR, "The appeal comment exists.",
                        "User id : " + userId + "\tsubmission id : " + submissionId + "\titem id :" + itemId);
            }
        }

        // create a new comment, set the text to text parameter
        Comment comment = new Comment();
        // ISV : The appeal should be associated with resource but not external reference ID
//        comment.setAuthor(userId.longValue());
        comment.setAuthor(submitterResource.getId());
        comment.setComment(text);
        comment.setCommentType(appealCommentType);

        // add the comment to the item
        item.addComment(comment);

        // update the review
        try {
            getReviewManager().updateReview(review, userId.toString());
        } catch (Exception e) {
            return AjaxSupportHelper.createAndLogError(request.getType(),
                    BUSINESS_ERROR, "Can't update review.",
                    "User id : " + userId + "\tsubmission id : " + submissionId + "\treview id :" + review.getId(), e);
        }

        // succeed
        return AjaxSupportHelper.createAndLogSucceess(request.getType(), SUCCESS,
                "Suceeded to place appeal.", null, "PlaceAppeal."
                + "\tuser id : " + userId + "review id :" + review.getId()
                + "\titem id : " + item.getId());
    }
}

