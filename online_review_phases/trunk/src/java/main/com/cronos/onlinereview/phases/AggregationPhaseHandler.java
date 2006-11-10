/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection and email sending. This class handle the
 * aggregation phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>If the input phase status is Scheduled, then it will check if the phase can be started using the following
 * conditions:
 *  <ul>
 *      <li>The dependencies are met.</li>
 *  </ul>
 *  </p>
 *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the following
 * conditions:
 *  <ul>
 *      <li>The dependencies are met.</li>
 *      <li>The winning submission have one aggregated review scorecard committed.</li>
 *  </ul>
 *  </p>
 *  <p>The additional logic for executing this phase is:</p>
 *  <p>If the input phase status is Scheduled, then it will perform the following additional logic to start the
 * phase:
 *  <ul>
 *      <li>If Aggregation worksheet is not created, it should be created; otherwise it should be marked
 *      uncommitted, as well as the aggregation review comments.</li>
 *  </ul>
 *  </p>
 *  <p>If the input phase status is Open, then it will do nothing.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class AggregationPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.AggregationPhaseHandler";

    /** constant for aggregation phase type. */
    private static final String PHASE_TYPE_AGGREGATION = "Aggregation";


    /** array of comment types to be copied from individual review scorecards to new aggregation worksheet. */
    private static final String[] COMMENT_TYPES_TO_COPY = new String[] {"Comment",
        "Required", "Recommended", "Appeal", "Appeal Response", "Aggregation Comment", "Aggregation Review Comment",
        "Submitter Comment", "Manager Comment"};

    /**
     * Create a new instance of AggregationPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public AggregationPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of AggregationPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public AggregationPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.<p>If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions:
     *  <ul>
     *      <li>The dependencies are met.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met.</li>
     *      <li>The winning submission have one aggregated review scorecard committed.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Aggregation" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_AGGREGATION);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached and there is a winner
            //and one aggregator.
            if (!PhasesHelper.canPhaseStart(phase)) {
                return false;
            }

            Connection conn = null;
            try {
                conn = createConnection();
                Resource winner = PhasesHelper.getWinningSubmitter(getManagerHelper().getResourceManager(),
                        conn, phase.getProject().getId());

                Resource[] aggregator = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(),
                        conn, new String[] {"Aggregator"}, phase.getId());

                //return true if there is a winner and an aggregator
                return (winner != null) && (aggregator.length == 1);
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            //return true if all dependencies have stopped and aggregation worksheet exists.
            return (PhasesHelper.arePhaseDependenciesMet(phase, false)
                    && isAggregationWorksheetPresent(phase));
        }
    }

    /**
     * <p>Provides addtional logic to execute a phase. This method will be called by start() and end() methods
     * of PhaseManager implementations in Phase Management component. This method can send email to a group of users
     * associated with timeline notification for the project.The email can be send on start phase or end phase base on
     * configuration settings.</p>
     *  <p>If the input phase status is Scheduled, then it will perform the following additional logic to start
     * the phase:
     *  <ul>
     *      <li>If Aggregation worksheet is not created, it should be created; otherwise it should be
     *      marked uncommitted, as well as the aggregation review comments.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Open, then it will do nothing.</p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Aggregation" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_AGGREGATION);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            checkAggregationWorksheet(phase, operator);
        }

        sendEmail(phase);
    }

    /**
     * This method is called from perform method when phase is starting. If the Aggregation worksheet is not
     * created, it is created; otherwise it is marked uncommitted, as well as the aggregation review comments.
     *
     * @param phase the phase.
     * @param operator operator name.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving/saving data.
     */
    private void checkAggregationWorksheet(Phase phase, String operator)
        throws PhaseHandlingException {
        //Check if the Aggregation worksheet is created
        Phase previousAggregationPhase = PhasesHelper.locatePhase(phase, "Aggregation", false, false);

        Connection conn = null;

        try {
            conn = createConnection();

            //Search for id of the Aggregator
            Resource[] aggregators = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] { "Aggregator" }, phase.getId());
            if (aggregators.length == 0) {
                throw new PhaseHandlingException("No Aggregator resource found for phase: " + phase.getId());
            }
            String aggregatorUserId = (String) aggregators[0].getProperty("External Reference ID");

            Review aggWorksheet = null;
            if (previousAggregationPhase != null) {
                aggWorksheet = PhasesHelper.getAggregationWorksheet(conn, getManagerHelper(),
                        previousAggregationPhase.getId());
            }

            if (aggWorksheet == null) {
                //create the worksheet
                aggWorksheet = new Review();
                aggWorksheet.setAuthor(aggregators[0].getId());

                //copy the comments from review scorecards
                Phase reviewPhase = PhasesHelper.locatePhase(phase, "Review", false, true);
                Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                        PhasesHelper.REVIEWER_ROLE_NAMES, reviewPhase.getId());

                //find winning submitter.
                Resource winningSubmitter = PhasesHelper.getWinningSubmitter(getManagerHelper().getResourceManager(),
                        conn, phase.getProject().getId());
                if (winningSubmitter == null) {
                    throw new PhaseHandlingException("No winner for project with id" + phase.getProject().getId());
                }

                // find the winning submission
                Filter filter = SubmissionFilterBuilder.createResourceIdFilter(winningSubmitter.getId());
                Submission[] submissions = getManagerHelper().getUploadManager().searchSubmissions(filter);
                if (submissions == null || submissions.length != 1) {
                    throw new PhaseHandlingException("No winning submission for project with id"
                            + phase.getProject().getId());
                }
                Long winningSubmissionId = new Long(submissions[0].getId());

                //Search all review scorecard from review phase for the winning submitter
                Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    reviewPhase.getId(), PhasesHelper.REVIEWER_ROLE_NAMES, winningSubmissionId);

                for (int r = 0; r < reviews.length; r++) {
                    aggWorksheet.setScorecard(reviews[r].getScorecard());
                    aggWorksheet.setSubmission(reviews[r].getSubmission());
                    PhasesHelper.copyComments(reviews[r], aggWorksheet, COMMENT_TYPES_TO_COPY, null);
                    PhasesHelper.copyReviewItems(reviews[r], aggWorksheet, COMMENT_TYPES_TO_COPY);
                }

                //Adding empty comments
                CommentType aggregationReviewCommentType = PhasesHelper.getCommentType(
                        getManagerHelper().getReviewManager(), "Aggregation Review Comment");
                CommentType submitterCommentType = PhasesHelper.getCommentType(
                        getManagerHelper().getReviewManager(), "Submitter Comment");
                for (int i = 0; i < reviewers.length; i++) {
                    if (reviewers[i].getProperty("External Reference ID") == null ||
                        reviewers[i].getProperty("External Reference ID").equals(aggregatorUserId)) {
                        continue;
                    }
                    Comment comment = new Comment();
                    comment.setAuthor(reviewers[i].getId());
                    comment.setComment("");
                    comment.setCommentType(aggregationReviewCommentType);
                    aggWorksheet.addComment(comment);
                }
                Comment comment = new Comment();
                comment.setAuthor(winningSubmitter.getId());
                comment.setComment("");
                comment.setCommentType(submitterCommentType);
                aggWorksheet.addComment(comment);

                getManagerHelper().getReviewManager().createReview(aggWorksheet, operator);
            } else {
                //Mark uncommitted for the worksheet:
                aggWorksheet.setAuthor(aggregators[0].getId());
                aggWorksheet.setCommitted(false);

                //Mark uncommitted for comments:
                Comment[] comments = aggWorksheet.getAllComments();

                //For each comment, reset comment extra info
                for (int i = 0; i < comments.length; i++) {
                    Comment comment = comments[i];
                    if ("Approved".equalsIgnoreCase((String) comment.getExtraInfo()) ||
                        "Accepted".equalsIgnoreCase((String) comment.getExtraInfo())) {
                        comment.setExtraInfo("Approving");
                    } else if ("Rejected".equalsIgnoreCase((String) comment.getExtraInfo())) {
                        comment.setExtraInfo(null);
                    }
               }

                //persist the copy
                aggWorksheet = PhasesHelper.cloneReview(aggWorksheet);
                getManagerHelper().getReviewManager().createReview(aggWorksheet, operator);
            }
        } catch (ReviewManagementException e) {
            throw new PhaseHandlingException("Problem when persisting review", e);
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem when retrieving winning submission.", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem when retrieving winning submission.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * returns true if aggregation worksheets exists.
     *
     * @param phase phase to check.
     *
     * @return whether aggregation worksheets exists.
     * @throws PhaseHandlingException
     */
    private boolean isAggregationWorksheetPresent(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            Review review = PhasesHelper.getAggregationWorksheet(conn, getManagerHelper(), phase.getId());
            return (review != null && review.isCommitted());
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }

    }
}
