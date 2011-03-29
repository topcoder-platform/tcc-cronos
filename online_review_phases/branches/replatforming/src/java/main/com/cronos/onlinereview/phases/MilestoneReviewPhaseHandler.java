/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
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
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add extra
 * logic to execute a phase. It will be used by Phase Management component. It is configurable using an input namespace.
 * The configurable parameters include database connection and email sending. This class handle the milestone review
 * phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The milestone review phase can start as soon as
 * <ul>
 * <li>the dependencies are met</li>
 * <li>Phase start date/time is reached (if specified)</li>
 * <li>There is at least one active submission with "Milestone Submission".</li>
 * </ul>
 * and can stop when the following conditions met:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>All active submissions have one review scorecard for the phase;</li>
 * </ul>
 * </p>
 * <p>
 * The additional logic for executing this phase is: When Review phase is starting, all submissions failed automated
 * screening must be set to the status &quot;Failed Milestone Screening&quot;.
 * </p>
 *
 * <p>
 * Version 1.6.1 (Milestone Support Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #updateSubmissionScores(Connection, Phase, String, Map)} method to properly map prizes to 
 *     submissions.</li>
 *     <li>Updated {@link #updateSubmissionScores(Connection, Phase, String, Map)} method to rank submissions based on
 *     scores and update the status in case submission fails to pass review.</li>
 *   </ol>
 * </p>
 * <p>
 * 
 * <p>
 * Version 1.6.2 (Online Review Replatforming Release 2 ) Change notes:
 *   <ol>
 *     <li>Change submission.getUploads() to submission.getUpload().</li>
 *   </ol>
 * </p>
 * 
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * @author FireIce, TCSDEVELOPER
 * @version 1.6.2
 * @since 1.6
 */
public class MilestoneReviewPhaseHandler extends AbstractPhaseHandler {
    /**
     * <p>
     * Represents the default namespace of this class. It is used in the default constructor to load configuration
     * settings.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.MilestoneReviewPhaseHandler";

    /**
     * <p>
     * constant for milestone review phase type.
     * </p>
     */
    private static final String PHASE_TYPE_MILESTONE_REVIEW = "Milestone Review";

    /**
     * <p>
     * Represents the constant for milestone reviewer.
     * </p>
     */
    private static final String MILESTONE_REVIEWER = "Milestone Reviewer";

    /**
     * Logger instance for this class.
     */
    private static final Log LOG = LogFactory.getLog(MilestoneReviewPhaseHandler.class.getName());

    /**
     * <p>
     * Create a new instance of MilestoneReviewPhaseHandler using the default namespace for loading configuration
     * settings.
     * </p>
     *
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     */
    public MilestoneReviewPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create a new instance of MilestoneReviewPhaseHandler using the given namespace for loading configuration
     * settings.
     * </p>
     *
     * @param namespace
     *            the namespace to load configuration settings from.
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public MilestoneReviewPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what will be
     * executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations in Phase
     * Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions:
     * <ul>
     * <li>the dependencies are met</li>
     * <li>Phase start date/time is reached (if specified)</li>
     * <li>There is at least one active submission with "Milestone Submission".</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>All active submissions have one review scorecard for the phase;</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     *
     * @param phase
     *            The input phase to check.
     * @return True if the input phase can be executed, false otherwise.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Milestone Review" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_REVIEW);

        // will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        Connection conn = null;
        try {
            if (toStart) {
                // return true if all dependencies have stopped and start time has been reached.
                if (!PhasesHelper.canPhaseStart(phase)) {
                    return false;
                }

                conn = createConnection();

                // Search all "Active" milestone submissions for current project with milestone submission type
                Submission[] subs = PhasesHelper.searchActiveMilestoneSubmissions(
                        getManagerHelper().getUploadManager(), conn, phase.getProject().getId(), phase.getId(), LOG);
                return subs.length > 0;
            } else {
                boolean deps = PhasesHelper.arePhaseDependenciesMet(phase, false);

                conn = createConnection();
                boolean reviews = allReviewsDone(conn, phase);
                return deps && reviews;
            }
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group or users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base on
     * configuration settings.
     * </p>
     * <p>
     * If the input phase status is Scheduled, then it will perform the following additional logic to start the phase:
     * All submissions failed automated screening must be set to the status &quot;Failed Milestone Screening&quot;.
     * </p>
     * <p>
     * If the input phase status is Open, then it will perform the following additional logic to stop the phase: Initial
     * score for the all passed screening submissions will be calculated and saved to the submitters's resource
     * properties named &quot;Initial Score&quot;.
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     *
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Milestone Review" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_REVIEW);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        Map<String, Object> values = new HashMap<String, Object>();
        Connection conn = null;

        try {
            conn = createConnection();
            if (toStart) {
                // for start, puts the reviewer/submission info
                putPhaseStartInfoValues(conn, phase, values);
            } else {
                // update the submission's initial score after review and set in the values map
                updateSubmissionScores(conn, phase, operator, values);
            }

            sendEmail(phase, values);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Puts the start review phase information about submissions and reviewer info to the value map.
     * </p>
     *
     * @param conn
     *            the database connection
     * @param phase
     *            the current Phase, not null
     * @param values
     *            the values map
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    private void putPhaseStartInfoValues(Connection conn, Phase phase, Map<String, Object> values)
        throws PhaseHandlingException {
        // Search all "Active" milestone submissions for current project with milestone submission type
        Submission[] subs = PhasesHelper.searchActiveMilestoneSubmissions(getManagerHelper().getUploadManager(), conn,
                phase.getProject().getId(), phase.getId(), LOG);
        values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(subs, getManagerHelper().getResourceManager(),
                false));
        // Search the milestone reviewer
        Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                new String[] {MILESTONE_REVIEWER}, phase.getId());
        values.put("NEED_MILESTONE_REVIEWER", reviewers.length == 0 ? 1 : 0);

    }

    /**
     * This method calculates initial score of all submissions that passed screening and saves it to the submitter's
     * resource properties. It is called from perform method when phase is stopping.
     *
     * @param conn
     *            the database connection
     * @param phase
     *            phase instance.
     * @param operator
     *            the operator name.
     * @param values
     *            map
     * @throws PhaseHandlingException
     *             in case of error when retrieving/updating data.
     */
    private void updateSubmissionScores(Connection conn, Phase phase, String operator, Map<String, Object> values)
        throws PhaseHandlingException {
        try {
            // Search all "Active" submissions with milestone submission type for current project
            Submission[] subs = PhasesHelper.searchActiveMilestoneSubmissions(getManagerHelper().getUploadManager(),
                    conn, phase.getProject().getId(), phase.getId(), LOG);

            // Search the milestone reviewer
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] {MILESTONE_REVIEWER}, phase.getId());

            // Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResources(conn, getManagerHelper(), reviewers, null);

            // for each submission, populate scores array to use with review score aggregator.
            com.topcoder.management.review.scoreaggregator.Submission[] submissionScores
                = new com.topcoder.management.review.scoreaggregator.Submission[subs.length];
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                Float score = null;

                // Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    if (subId == reviews[j].getSubmission()) {
                        // get review score
                        score = reviews[j].getScore();
                    }
                }

                if (score == null) {
                    LOG.log(Level.ERROR, "There is no score for the given submission with id - " + subId);
                    throw new PhaseHandlingException("There is no score for the given submission with id - " + subId);
                }

                submission.setInitialScore(score.doubleValue());
                submission.setFinalScore(score.doubleValue());
                submissionScores[iSub] 
                    = new com.topcoder.management.review.scoreaggregator.Submission(subId, 
                                                                                    new float[] {score.floatValue()});
            }
            
            // Calculate placements for submissions based on review score
            ReviewScoreAggregator scoreAggregator = getManagerHelper().getScorecardAggregator();
            AggregatedSubmission[] aggregations = scoreAggregator.aggregateScores(submissionScores);
            RankedSubmission[] placements = scoreAggregator.calcPlacements(aggregations);
            
            // Get minimum passing score for review
            float minScore = PhasesHelper
                    .getScorecardMinimumScore(getManagerHelper().getScorecardManager(), reviews[0]);
            SubmissionStatus failedStatus = PhasesHelper.getSubmissionStatus(getManagerHelper().getUploadManager(),
                                                                             "Failed Milestone Review");

            // Assign placements to submissions based on scores
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                for (int i = 0; i < placements.length; i++) {
                    RankedSubmission rankedSubmission = placements[i];
                    if (rankedSubmission.getId() == submission.getId()) {
                        submission.setPlacement(rankedSubmission.getRank() * 1L);
                        float aggScore = rankedSubmission.getAggregatedScore();
                        if (aggScore < minScore) {
                            submission.setSubmissionStatus(failedStatus);
                        }
                        break;
                    }
                }
            }

            // set the milestone prize.
            Project project = getManagerHelper().getProjectManager().getProject(phase.getProject().getId());

            List<Prize> prizes = new ArrayList<Prize>(project.getPrizes());

            if (prizes != null && !prizes.isEmpty()) {
                for (Iterator<Prize> iter = prizes.iterator(); iter.hasNext();) {
                    Prize prize = iter.next();

                    if (!"Milestone Prize".equals(prize.getPrizeType().getDescription())) {
                        iter.remove();
                    }
                }

                if (!prizes.isEmpty()) {
                    // sort the milestone prizes
                    Collections.sort(prizes, new Comparator<Prize>() {
                        public int compare(Prize o1, Prize o2) {
                            return o1.getPlace() - o2.getPlace();
                        }
                    });

                    // sort the submissions.
                    Arrays.sort(subs, new Comparator<Submission>() {
                        public int compare(Submission o1, Submission o2) {
                            int placementResult = o1.getPlacement().compareTo(o2.getPlacement());
                            if (placementResult == 0) {
                                return o1.getCreationTimestamp().compareTo(o2.getCreationTimestamp());
                            } else {
                                return placementResult;
                            }
                        }
                    });

                    Prize currentPrize = prizes.get(0);
                    int currentPrizeIndex = 0;
                    int currentPrizeNumLeft = currentPrize.getNumberOfSubmissions();
                    for (int i = 0; i < subs.length; i++) {
                        Submission submission = subs[i];
                        if (submission.getPlacement() == currentPrize.getPlace()) {
                            if (currentPrizeNumLeft > 0) {
                                currentPrizeNumLeft--;
                            } else {
                                continue; // no more prizes left for awarding for current place
                            }
                        } else {
                            currentPrizeIndex++;
                            if (currentPrizeIndex >= prizes.size()) {
                                break; // No more prizes left for awarding
                            } else {
                                currentPrize = prizes.get(currentPrizeIndex);
                                currentPrizeNumLeft = currentPrize.getNumberOfSubmissions();
                            }
                        }
                        submission.setPrize(currentPrize);
                    }
                }
            }

            // update the submissions
            for (int iSub = 0; iSub < subs.length; iSub++) {
                getManagerHelper().getUploadManager().updateSubmission(subs[iSub], operator);
            }

            // add the submission result to the values map
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Submission sub : subs) {
                Map<String, Object> infos = new HashMap<String, Object>();
                Resource submitt = getManagerHelper().getResourceManager().getResource(
                        sub.getUpload().getOwner());
                infos.put("SUBMITTER_HANDLE", PhasesHelper.notNullValue(submitt.getProperty(PhasesHelper.HANDLE)));
                infos.put("SUBMITTER_SCORE", sub.getInitialScore());
                result.add(infos);
            }
            values.put("SUBMITTER", result);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem when looking up resource for the submission.", e);
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Problem to get the project.", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem when updating submission", e);
        } catch (InconsistentDataException e) {
            throw new PhaseHandlingException("Problem when updating submission", e);
        }
    }

    /**
     * This method checks if all active submissions have one review scorecard from the reviewer for the phase and
     * returns true if conditions are met, false otherwise.
     *
     * @param conn
     *            the database connection
     * @param phase
     *            the phase instance.
     * @return true if all active submissions have one review from the reviewer, false otherwise.
     * @throws PhaseHandlingException
     *             if there was an error retrieving data.
     */
    private boolean allReviewsDone(Connection conn, Phase phase) throws PhaseHandlingException {
        // Search all "Active" milestone submissions for current project
        Submission[] subs = PhasesHelper.searchActiveMilestoneSubmissions(getManagerHelper().getUploadManager(), conn,
                phase.getProject().getId(), phase.getId(), LOG);

        // Search the reviewIds
        Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                new String[] {MILESTONE_REVIEWER}, phase.getId());

        if (reviewers.length == 0) {
            LOG.log(Level.INFO, "no milestone reviewer for project: " + phase.getProject().getId());
            return false;
        }

        if (reviewers.length != 1) {
            LOG.log(Level.ERROR, "There should be exactly one milestone reviewer.");
            throw new PhaseHandlingException("There should be exactly one milestone reviewer.");
        }

        // Search all review scorecard for the current phase
        Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(), phase.getId(),
                new String[] {MILESTONE_REVIEWER}, null);

        if (LOG.isEnabled(Level.DEBUG)) {
            for (int i = 0; i < subs.length; i++) {
                LOG.log(Level.DEBUG, "submission: " + subs[i].getId());
            }

            for (int i = 0; i < reviewers.length; i++) {
                LOG.log(Level.DEBUG, "reviewer: " + reviewers[i].getId() + ", "
                        + reviewers[i].getProperty(PhasesHelper.HANDLE) + ", "
                        + reviewers[i].getResourceRole().getName());
            }

            for (int i = 0; i < reviews.length; i++) {
                LOG.log(Level.DEBUG, "review: " + reviews[i].getId() + ", " + reviews[i].getSubmission() + ", "
                        + reviews[i].getAuthor() + ", " + reviews[i].isCommitted());
            }
        }

        if (subs.length != reviews.length) {
            LOG.log(Level.ERROR, "The number of reviews don't match the number of submissions." 
                                 + subs.length + " vs "+ reviews.length);
            return false;
        }

        // make sure that all reviews are committed.
        for (int i = 0; i < reviews.length; i++) {
            // check if review is committed
            if (!reviews[i].isCommitted()) {
                return false;
            }
        }

        // for each submission
        for (int iSub = 0; iSub < subs.length; iSub++) {
            Submission submission = subs[iSub];
            long subId = submission.getId();
            boolean reviewFound = false;

            // Match the submission with its reviews
            for (int j = 0; j < reviews.length; j++) {
                if (subId == reviews[j].getSubmission()) {
                    reviewFound = true;

                    break;
                }
            }

            if (!reviewFound) {
                LOG.log(Level.ERROR, "There is no review for the given submission " + subId);
                return false;
            }
        }

        return true;
    }
}
