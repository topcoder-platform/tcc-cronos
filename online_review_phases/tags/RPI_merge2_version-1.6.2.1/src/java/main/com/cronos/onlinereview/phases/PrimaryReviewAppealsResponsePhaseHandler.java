/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scoreaggregator.AggregatedSubmission;
import com.topcoder.management.review.scoreaggregator.InconsistentDataException;
import com.topcoder.management.review.scoreaggregator.RankedSubmission;
import com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This handler is responsible for checking whether the Primary Review Appeals Response Phase can be performed
 * and performing the phase. It extends <code>AbstractPhaseHandler</code> to leverage the various services
 * provided by the base class. Logging is done with the Logging Wrapper.
 * </p>
 * <p>
 * Usage: please see "test_files/config/Phase_Handler_1_5.xml". The namespace is
 * "com.cronos.onlinereview.phases.PrimaryReviewAppealsResponsePhaseHandle".
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe because it's immutable.
 * </p>
 *
 * <p>
 * Version 1.6 (Online Review Update Review Management Process assembly 2) Change notes:
 * <ol>
 * <li>Update {@link #canPerform(Phase)} method. The appeal response phase can be closed
 * only when all the appeals have been responsed.</li>
 * <li>Update {@link #perform(Phase, String)} method. The <code>Aggregation</code> phase should be after appeals
 * response phase.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>Changed return type of canPerform() methods from boolean to OperationCheckResult.</li>
 * <li>fix some check-style if's and cast warning.</li>
 * <li>removed unused return tag in method calCommentType.</li>
 * </ol>
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER, TMALBONPH
 * @version 1.6.2
 * @since 1.5
 */
public class PrimaryReviewAppealsResponsePhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * Represents the default namespace of this class.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It is final and won't change
     * once it is initialized as part of variable declaration to:
     * "com.cronos.onlinereview.phases.PrimaryReviewAppealsResponsePhaseHandler" .
     * </p>
     * <p>
     * Usage: It is used in {@link #PrimaryReviewAppealsResponsePhaseHandler()} (for initialization).
     * </p>
     */
    public static final String DEFAULT_NAMESPACE =
        "com.cronos.onlinereview.phases.PrimaryReviewAppealsResponsePhaseHandler";

    /**
     * <p>
     * The logger used for logging.
     * </p>
     * <p>
     * LegalValue: It cannot be null. Initialization and Mutability: It is final and won't change once it is
     * initialized as part of variable declaration to:
     * LogFactory.getLog(PrimaryReviewAppealsResponsePhaseHandler.class.getName( )).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogFactory
        .getLog(PrimaryReviewAppealsResponsePhaseHandler.class.getName());

    /**
     * <p>
     * The Constant PHASE_TYPE.
     * </p>
     */
    private static final String PHASE_TYPE = "Primary Review Appeals Response";

    /**
     * <p>
     * The names of the phases that involve appeal.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. The contained values can't be null or empty.
     * Initialization and Mutability: It's initialized within constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #allAppealsResolved()}, {@link #PrimaryReviewAppealsResponsePhaseHandler()}
     * (for initialization).
     * </p>
     */
    private final String[] phasesWithAppeal;

    /**
     * <p>
     * The secondary reviewer role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #allAppealsResolved()}, {@link #PrimaryReviewAppealsResponsePhaseHandler()}
     * (for initialization).
     * </p>
     */
    private final String secondaryReviewerRoleName;

    /**
     * <p>
     * The primary reviewer role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #allAppealsResolved()}, {@link #PrimaryReviewAppealsResponsePhaseHandler()}
     * (for initialization).
     * </p>
     */
    private final String primaryReviewerRoleName;

    /**
     * <p>
     * Create an instance of the class with the default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             if any error occurs
     */
    public PrimaryReviewAppealsResponsePhaseHandler() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create an instance of the class.
     * </p>
     *
     * @param namespace
     *            the configuration namespace.
     * @throws IllegalArgumentException
     *             if namespace is null or empty.
     * @throws ConfigurationException
     *             if any error occurs
     */
    public PrimaryReviewAppealsResponsePhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);

        phasesWithAppeal = PhasesHelper.getPropertyValue(namespace, "phasesWithAppeal", true).split(",");

        primaryReviewerRoleName = PhasesHelper.getPropertyValue(namespace, "primaryReviewerRoleName", true);

        secondaryReviewerRoleName = PhasesHelper.getPropertyValue(namespace, "secondaryReviewerRoleName",
            true);

        // check phasesWithAppeal string array. if this array contains the empty
        // value then throw exception.
        for (String str : phasesWithAppeal) {
            if (str.trim().length() == 0) {
                String detail = "Property \"phasesWithAppeal\" string must " + "not have double comma, "
                    + "and the comma must not be first character.";
                LOG.log(Level.ERROR, detail);
                throw new ConfigurationException(detail);
            }
        }
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed.
     * </p>
     * <p>
     * This method will be called by <code>canStart()</code> and <code>canEnd()</code> methods of
     * <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     * <p>
     * Version 1.6.2 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     *
     * @param phase
     *            The input phase to check
     * @return the validation result indicating whether the associated operation can be performed, and if not,
     *         providing a reasoning message (not null)
     * @throws IllegalArgumentException
     *             if phase is null;
     * @throws PhaseNotSupportedException
     *             if the input phase type is not
     *             "Primary Review Appeals Response"
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            // Check if the phase is to start:
            if (PhasesHelper.checkPhaseStatus(phase.getPhaseStatus())) {
                return PhasesHelper.checkPhaseCanStart(phase);
            }

            OperationCheckResult ocrSuccess = PhasesHelper.checkPhaseDependenciesMet(phase, false);
            if (ocrSuccess.isSuccess() && allAppealsResolved(phase)) {
                return ocrSuccess;
            }
            return new OperationCheckResult(false, "Appeals not Resolved");

        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, null, phase.getProject().getId());
        }
    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called by <code>start()</code> and
     * <code>end()</code> methods of <code>PhaseManager</code> implementations in Phase Management component.
     * </p>
     * <p>
     * This method can send email to a group of users associated with timeline notification for the project.
     * The email can be send on start phase or end phase base on configuration settings.
     * </p>
     *
     * @param phase
     *            The input phase to check.
     * @param operation
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type doesn't equal to
     *             "Primary Review Appeals Response"
     * @throws PhaseHandlingException
     *             if any error occurs.
     * @throws IllegalArgumentException
     *             if phase is null; operation is null or empty.
     */
    public void perform(Phase phase, String operation) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operation, "operation");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            Map<String, Object> values = new HashMap<String, Object>();

            if (toStart) {
                // for start phase, puts the submission info/initial score
                values.put("SUBMITTER", PhasesHelper.getSubmitterValueArray(createConnection(),
                    getManagerHelper(), phase.getProject().getId(), false));
            } else {
                // it is going to calculate the final score for every submission
                // and puts the scores into the values after calculation
                boolean passedReview = updateSubmissions(phase, operation, values);
                if (!passedReview) {
                    // if there is no submission passes review after appeal
                    // response
                    // insert the post-mortem phase
                    PhasesHelper.insertPostMortemPhase(phase.getProject(), phase, getManagerHelper(),
                        operation);
                }
                Resource[] aggregators = getAggregators(PhasesHelper.locatePhase(phase, "Aggregation", true,
                    true));
                values.put("N_AGGREGATOR", aggregators.length);
            }

            sendEmail(phase, values);
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, operation, phase.getProject().getId());
        }

    }

    /**
     * <p>
     * This method returns true if all the appeals from submitters and secondary reviewers have been responded
     * to.
     * </p>
     *
     * @param phase
     *            phase instance.
     * @return true if all appeals are resolved, false otherwise.
     * @throws PhaseHandlingException
     *             if an error occurs when retrieving data.
     */
    private boolean allAppealsResolved(Phase phase) throws PhaseHandlingException {
        ArrayList<Review> allReviews = new ArrayList<Review>();

        Connection conn = null;

        try {
            conn = createConnection();

            for (String phaseName : phasesWithAppeal) {
                // get the current phase
                Phase currentPhase = PhasesHelper.locatePhase(phase, phaseName, false, false);

                if (currentPhase == null) {
                    String detail = "The phase type(" + phaseName + ")  can not be found in the project id("
                        + phase.getProject().getId() + "). The exception in the configuration file";
                    throw new PhaseHandlingException(detail);
                }

                // get all reviews made by primary and secondary reviewers:
                Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    currentPhase.getId(), new String[]{primaryReviewerRoleName, secondaryReviewerRoleName},
                    null);

                // add the reviews to allReviews:
                allReviews.addAll(Arrays.asList(reviews));
            }
        } finally {
            PhasesHelper.closeConnection(conn);
        }

        for (Review review : allReviews) {
            int[] counts = new int[2];
            // this is the appeal count of the current review:
            counts[0] = 0;
            // this is the appeal response count of the current review:
            counts[1] = 0;
            // get all comments:
            Comment[] comments = review.getAllComments();
            // calculate the review comment type
            calCommentType(comments, counts);

            // get all items of the review:
            Item[] items = review.getAllItems();
            // for items
            for (Item item : items) {
                // get the comments of the item:
                comments = item.getAllComments();
                // calculate the item comment type
                calCommentType(comments, counts);
            }

            if (counts[0] != counts[1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Calculating the comment types counts.
     * </p>
     *
     * @param comments
     *            the comments
     * @param counts
     *            the counts array
     */
    private void calCommentType(Comment[] comments, int[] counts) {
        // for comments
        for (Comment comment : comments) {
            // get comment type
            String commentType = comment.getCommentType().getName();
            if ("Appeal".equals(commentType)) {
                counts[0]++;
            } else if ("Appeal Response".equals(commentType)) {
                counts[1]++;
            }
        }

    }

    /**
     * <p>
     * Gets the list of resources assigned <code>Aggregator</code> role.
     * </p>
     *
     * @param aggregationPhase
     *            a <code>Phase</code> providing the details for <code>Aggregation</code> phase.
     * @return a <code>Resource</code> array listing the resources granted <code>Aggregator</code> role.
     * @throws PhaseHandlingException
     *             if an unexpected error occurs while accessing the data store.
     */
    private Resource[] getAggregators(Phase aggregationPhase) throws PhaseHandlingException {
        Resource[] aggregators;
        Connection connection = null;
        try {
            connection = createConnection();

            aggregators = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), connection,
                new String[]{"Aggregator"}, aggregationPhase.getId());
        } finally {
            PhasesHelper.closeConnection(connection);
        }
        return aggregators;
    }

    /**
     * <p>
     * This method is called from perform method when phase is stopping. It does the following:
     * <ul>
     * <li>all submissions with failed review scores are set to the status Failed Review.</li>
     * <li>Overall score for the passing submissions are calculated and saved.</li>
     * <li>The winner and runner-up are populated in the project properties.</li>
     * <li>Submissions that do not win are set to the status Completed Without Win.</li>
     * </ul>
     * </p>
     *
     * @param phase
     *            the phase instance.
     * @param operator
     *            operator name.
     * @param values
     *            the values map
     * @return true if there is at least one submission passed review after
     *         appeal response, false otherwise.
     * @throws PhaseHandlingException
     *             if there was an error updating data.
     */
    private boolean updateSubmissions(Phase phase, String operator, Map<String, Object> values)
        throws PhaseHandlingException {
        // exception detail
        String detail;

        // A : Update failed review submission status to "Failed Review"
        Connection conn = null;

        // boolean flag to indicate whether all submissions fail review
        boolean allSubmissionFailed = true;

        try {
            conn = createConnection();

            // Search all "Active" submissions for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // locate previous review phase
            Phase reviewPhase = PhasesHelper.locatePhase(phase, PhasesHelper.SECONDARY_REVIEWER_REVIEW, false, true);
            long reviewPhaseId = reviewPhase.getId();

            // Search all review score card for the review phase
            Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                reviewPhaseId, PhasesHelper.REVIEWER_ROLE_NAMES, null);

            if (reviews.length == 0) {
                detail = "No reviews found for phase: " + reviewPhaseId;
                throw new PhaseHandlingException(detail);
            }

            // Get minimum review score
            float minScore = PhasesHelper.getScorecardMinimumScore(getManagerHelper().getScorecardManager(),
                reviews[0]);

            // create array to hold scores from all reviewers for all
            // submissions
            com.topcoder.management.review.scoreaggregator.Submission[] submissionScores;
            submissionScores = new com.topcoder.management.review.scoreaggregator.Submission[subs.length];

            // for each submission, populate scores array to use with review
            // score aggregator.
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                List<Float> scoresList = new ArrayList<Float>();

                // Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    if (subId == reviews[j].getSubmission()) {
                        // get review score
                        scoresList.add(reviews[j].getScore());
                    }
                }

                // create float array
                float[] scores = new float[scoresList.size()];

                for (int iScore = 0; iScore < scores.length; iScore++) {
                    scores[iScore] = ((Float) scoresList.get(iScore)).floatValue();
                }

                submissionScores[iSub] = new com.topcoder.management.review.scoreaggregator.Submission(subId,
                    scores);
            }

            // now calculate the aggregated scores and placements
            ReviewScoreAggregator scoreAggregator = getManagerHelper().getScorecardAggregator();

            // this will hold as many elements as submissions
            AggregatedSubmission[] aggregations = scoreAggregator.aggregateScores(submissionScores);
            RankedSubmission[] placements = scoreAggregator.calcPlacements(aggregations);

            // status objects
            SubmissionStatus failedStatus = PhasesHelper.getSubmissionStatus(getManagerHelper()
                .getUploadManager(), "Failed Review");
            SubmissionStatus noWinStatus = PhasesHelper.getSubmissionStatus(getManagerHelper()
                .getUploadManager(), "Completed Without Win");

            Resource winningSubmitter = null;
            Resource runnerUpSubmitter = null;

            // again iterate over submissions to set the initial score and
            // placement
            for (int iSub = 0; iSub < placements.length; iSub++) {
                RankedSubmission rankedSubmission = placements[iSub];
                rankedSubmission = breakTies(rankedSubmission, subs, placements);
                Submission submission = getSubmissionById(subs, rankedSubmission.getId());
                float aggScore = rankedSubmission.getAggregatedScore();
                int placement = rankedSubmission.getRank();

                // update submitter's final score
                long submitterId = submission.getUpload().getOwner();
                Resource submitter = getManagerHelper().getResourceManager().getResource(submitterId);

                // OrChange - update the final score and the placement into the
                // submission table.
                submission.setFinalScore(Double.valueOf(aggScore + ""));
                submission.setPlacement(new Long(placement));

                // if failed review, then update the status
                // Only if the status of the current submission is "Active",
                // since "Failed Screening" should not be updated.
                if (aggScore < minScore && submission.getSubmissionStatus().getDescription().equals("Active")) {
                    submission.setSubmissionStatus(failedStatus);
                } else {

                    // there is submission passed review, set flag
                    // allSubmissionFailed to false
                    allSubmissionFailed = false;

                    // cache winning submitter.
                    if (placement == 1) {
                        winningSubmitter = submitter;
                    } else {
                        // cache runner up submitter.
                        if (placement == 2) {
                            runnerUpSubmitter = submitter;
                        }
                        submission.setSubmissionStatus(noWinStatus);
                    }
                }
                // persist the change
                getManagerHelper().getUploadManager().updateSubmission(submission, operator);

            } // end for

            // cannot be the case where there is a runner up but no winner
            if (runnerUpSubmitter != null && winningSubmitter == null) {
                detail = "Runner up present, but no winner for project with id:" + phase.getProject().getId();
                throw new PhaseHandlingException(detail);
            }

            // if there is a winner
            if (winningSubmitter != null) {
                // Set project properties to store the winner and the runner up
                // Get the project instance
                Project project = getManagerHelper().getProjectManager().getProject(
                    phase.getProject().getId());

                Object winnerExtId = winningSubmitter.getProperty(PhasesHelper.EXTERNAL_REFERENCE_ID);
                project.setProperty("Winner External Reference ID", winnerExtId);

                // if there is a runner up
                if (runnerUpSubmitter != null) {
                    Object runnerExtId = runnerUpSubmitter.getProperty(PhasesHelper.EXTERNAL_REFERENCE_ID);
                    project.setProperty("Runner-up External Reference ID", runnerExtId);
                }

                // update the project
                getManagerHelper().getProjectManager().updateProject(project,
                    "Update the winner and runner up.", operator);
            }

            // for each submission, get the submitter and its scores
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Submission submission : subs) {
                Map<String, Object> infos = new HashMap<String, Object>();
                infos.put(
                    "SUBMITTER_HANDLE",
                    PhasesHelper.notNullValue(getManagerHelper().getResourceManager()
                        .getResource(submission.getUpload().getOwner()).getProperty(PhasesHelper.HANDLE)));
                infos.put("SUBMITTER_PRE_APPEALS_SCORE", submission.getInitialScore());
                infos.put("SUBMITTER_POST_APPEALS_SCORE", submission.getFinalScore());
                infos.put("SUBMITTER_RESULT", submission.getPlacement());
                result.add(infos);
            }
            values.put("SUBMITTER", result);
        } catch (SQLException ex) {
            throw new PhaseHandlingException("Problem when looking up id.", ex);
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("Problem with resource persistence.", ex);
        } catch (UploadPersistenceException ex) {
            throw new PhaseHandlingException("Problem with upload persistence.", ex);
        } catch (PersistenceException ex) {
            throw new PhaseHandlingException("Problem with project persistence.", ex);
        } catch (ValidationException ex) {
            throw new PhaseHandlingException("Problem with project persistence", ex);
        } catch (InconsistentDataException ex) {
            throw new PhaseHandlingException("Problem when aggregating scores.", ex);
        } finally {
            PhasesHelper.closeConnection(conn);
        }

        // return whether there is at least one submission passes review after
        // appeal response
        return !allSubmissionFailed;
    }

    /**
     * <p>
     * Break ties by submission timestamp.
     * </p>
     *
     * @param submission
     *            the submission to calculate
     * @param submissions
     *            all the submission records
     * @param placements
     *            all the ranked submission records
     * @return the submission with fixed placement
     * @throws PhaseHandlingException
     *             if an error occurs when retrieving data.
     */
    private RankedSubmission breakTies(RankedSubmission submission, Submission[] submissions,
        RankedSubmission[] placements) throws PhaseHandlingException {
        int rank = submission.getRank();
        Date timestamp1 = getSubmissionById(submissions, submission.getId()).getUpload()
            .getCreationTimestamp();
        for (int i = 0; i < placements.length; ++i) {
            if (placements[i].getRank() == submission.getRank()) {
                Submission tie = getSubmissionById(submissions, placements[i].getId());
                Date timestamp2 = tie.getUpload().getCreationTimestamp();

                if (timestamp1 != null && timestamp2 != null && timestamp2.before(timestamp1)) {
                    ++rank;
                }
            }
        }
        return new RankedSubmission(submission, rank);
    }

    /**
     * <p>
     * Return suitable submission for given submissionId.
     * </p>
     *
     * @param submissions
     *            the submission array
     * @param submissionId
     *            the submissionId
     * @return submission instance
     * @throws PhaseHandlingException
     *             if no submission found
     */
    private Submission getSubmissionById(Submission[] submissions, long submissionId)
        throws PhaseHandlingException {
        for (int i = 0; i < submissions.length; i++) {
            if (submissions[i].getId() == submissionId) {
                return submissions[i];
            }
        }
        // exception detail
        String detail = "submissions not found for submissionId: " + submissionId;

        LOG.log(Level.ERROR, detail);

        throw new PhaseHandlingException(detail);
    }

}
