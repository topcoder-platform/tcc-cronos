/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add
 * extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection, email sending. This class handle the
 * milestone screening phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The milestone screening phase can start as soon as the following conditions met:
 * <ul>
 * <li>the dependencies are met</li>
 * <li>Phase start date/time is reached (if specified)</li>
 * <li>There are submissions with "Milestone Submission" type.</li>
 * </ul>
 * and can stop when the following conditions met:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>All active milestone submissions have one screening scorecard committed.</li>
 * </ul>
 * </p>
 * <p>
 * The additional logic for executing this phase is: When screening is stopping,
 * <ul>
 * <li>all submissions with failed screening scorecard scores should be set to the status &quot;Failed Milestone
 * Screening&quot;.</li>
 * <li>Screening score for the all submissions will be calculated and saved to the submitters&rsquo; resource
 * properties named &quot;Screening Score&quot;.</li>
 * </ul>
 * </p>
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>The return changes from boolean to OperationCheckResult.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.6.2 (TCCC-3631) Change notes:
 * <ol>
 * <li>Updated {@link #canPerform(Phase)} method to delete studio submission 
 * if they have user rank more than configured for the project.</li>
 * </ol>
 * </p>

 * @author FireIce, saarixx, microsky, lmmortal
 * @version 1.6.2
 * @since 1.6
 */
public class MilestoneScreeningPhaseHandler extends AbstractPhaseHandler {
    /**
     * <p>
     * Represents the default namespace of this class. It is used in the default constructor to load configuration
     * settings.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.MilestoneScreeningPhaseHandler";

    /**
     * <p>
     * constant for "Milestone Screener" role name.
     * </p>
     */
    private static final String ROLE_MILESTONE_SCREENER = "Milestone Screener";

    /**
     * <p>
     * Represents the logger for this class. Is initialized during class loading and never changed after that.
     * </p>
     */
    private static final Log LOG = LogFactory.getLog(MilestoneScreeningPhaseHandler.class.getName());

    /**
     * <p>
     * constant for "Failed Milestone Screening" submission status.
     * </p>
     */
    private static final String SUBMISSION_STATUS_FAILED_MILESTONE_SCREENING = "Failed Milestone Screening";

    /**
     * <p>
     * constant for milestone screening phase type.
     * </p>
     */
    private static final String PHASE_TYPE_MILESTONE_SCREENING = "Milestone Screening";

    /**
     * <p>
     * Create a new instance of MilestoneScreeningPhaseHandler using the default namespace for loading
     * configuration settings.
     * </p>
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     */
    public MilestoneScreeningPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create a new instance of MilestoneScreeningPhaseHandler using the given namespace for loading configuration
     * settings.
     * </p>
     * @param namespace
     *            the namespace to load configuration settings from.
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public MilestoneScreeningPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what will be
     * executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase
     * Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions:
     * <ul>
     * <li>the dependencies are met</li>
     * <li>Phase start date/time is reached (if specified)</li>
     * <li>There are submissions with "Milestone Submission" type.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>All active milestone submissions have one screening scorecard committed.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase
     *            The input phase to check.
     * @return the validation result indicating whether the associated operation can be performed, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Milestone Screening" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input is null.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_SCREENING);

        // will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        OperationCheckResult result;
        if (toStart) {
            // return true if all dependencies have stopped and start time has been reached.
            result = PhasesHelper.checkPhaseCanStart(phase);
            if (!result.isSuccess()) {
                return result;
            }

            Connection conn = createConnection();
            try {
                // Search all "Active" milestone submissions for current project
                Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                    conn, phase.getProject().getId(), PhasesHelper.MILESTONE_SUBMISSION_TYPE);
                if (subs.length > 0) {
                    return OperationCheckResult.SUCCESS;
                } else {
                    return new OperationCheckResult("There are no milestone submissions for the project");
                }
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            result = PhasesHelper.checkPhaseDependenciesMet(phase, false);
            if (!result.isSuccess()) {
                return result;
            }

            return areScorecardsCommitted(phase);
        }
    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group of users
     * associated with timeline notification for the project. The email can be send on start phase or end phase
     * base on configuration settings.
     * </p>
     * <p>
     * If the input phase status is Scheduled, then it will do nothing.
     * </p>
     * <p>
     * If the input phase status is Open, then it will perform the following additional logic to stop the phase:
     * <ul>
     * <li>all submissions with failed screening scorecard scores should be set to the status &quot;Failed
     * Milestone Screening&quot;.</li>
     * <li>Screening score for the all submissions will be calculated and saved to the submitters&rsquo; resource
     * properties named &quot;Screening Score&quot;.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not &quot;Milestone Screening&quot; type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_SCREENING);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();

        // Database connection
        Connection conn = createConnection();

        try {
            if (toStart) {
                com.topcoder.management.project.Project project = getManagerHelper().getProjectManager().getProject(phase.getProject().getId());
                if (PhasesHelper.isStudio(project)) {
                    PhasesHelper.autoScreenStudioSubmissions(project, getManagerHelper(), PhasesHelper.MILESTONE_SUBMISSION_TYPE, conn, operator);
                }
                    // for start, put the submission information with need_milestone_screener or not
                    putPhaseStartInfos(conn, phase, values);
            } else {
                // flag to indicate whether all submissions do not pass screening
                boolean noScreeningPass = false;

                // Search all submissions for current project
                Submission[] submissions = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                    conn, phase.getProject().getId(), PhasesHelper.MILESTONE_SUBMISSION_TYPE);

                // Search all screening scorecard for the current phase
                Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    phase.getId(), new String[] {ROLE_MILESTONE_SCREENER }, null);

                if (submissions.length != screenReviews.length) {
                    LOG.log(Level.ERROR, "Submission count does not match screening count for project:"
                            + phase.getProject().getId());
                    throw new PhaseHandlingException(
                        "Submission count does not match screening count for project:" + phase.getProject().getId());
                }

                if (screenReviews.length > 0) {
                    // There is screen reviews, start check screening passes
                    noScreeningPass = true;

                    // get minimum score
                    float minScore = PhasesHelper.getScorecardMinimumScore(
                        getManagerHelper().getScorecardManager(), screenReviews[0]);

                    // for each submission...
                    for (int iSub = 0; iSub < submissions.length; iSub++) {
                        Submission submission = submissions[iSub];

                        if (!processAndUpdateSubmission(submission, screenReviews, minScore, operator, conn)) {
                            // if there is one passed screening, set noScreeningPass to false
                            noScreeningPass = false;
                        }
                    }
                }
                // put the submission screening result
                values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(submissions,
                    getManagerHelper().getResourceManager(), true));
                values.put("NO_SCREENING_PASS", noScreeningPass ? 1 : 0);
            }
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("There was problem with project persistence.", e);
        } finally {
            // close the connection after get the submissions
            PhasesHelper.closeConnection(conn);
        }

        // send mail for start/stop phase
        sendEmail(phase, values);
    }

    /**
     * <p>
     * Puts the milestone screening start phase info for email generation.
     * </p>
     * @param phase
     *            the current Phase
     * @param values
     *            the value map to hold the info
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    private void putPhaseStartInfos(Connection conn, Phase phase, Map<String, Object> values)
        throws PhaseHandlingException {
        // Database connection
        Submission[] subs = null;
        // get the screener
        Resource[] screeners = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
            new String[] {ROLE_MILESTONE_SCREENER }, phase.getId());
        values.put("NEED_MILESTONE_SCREENER", screeners.length == 0 ? 1 : 0);
        // get submissions
        subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
            conn, phase.getProject().getId(), PhasesHelper.MILESTONE_SUBMISSION_TYPE);
        // put the submitter info into the map
        values.put("SUBMITTER",
            PhasesHelper.constructSubmitterValues(subs, getManagerHelper().getResourceManager(), false));
    }

    /**
     * <p>
     * This method is called from perform method when phase is stopping. It does the following:
     * <ul>
     * <li>All submissions with failed screening scorecard scores are set to the status &quot;Failed Milestone
     * Screening&quot;.</li>
     * <li>Screening score for the all submissions will be saved to the submitters' resource properties named
     * &quot;Screening Score&quot;.</li>
     * </ul>
     * </p>
     * @param submission
     *            the submission to process and update.
     * @param screenReviews
     *            screening reviews.
     * @param minScore
     *            minimum scorecard score.
     * @param operator
     *            operator name.
     * @param conn
     *            connection to use when looking up ids.
     * @return true if the submission failed screen, false otherwise.
     * @throws PhaseHandlingException
     *             if there was a problem retrieving/updating data.
     */
    private boolean processAndUpdateSubmission(Submission submission, Review[] screenReviews, float minScore,
            String operator, Connection conn) throws PhaseHandlingException {

        // boolean flag to indicate whether the submission failed screen
        boolean failedScreening = false;

        // Find the matching screening review
        Review review = null;
        long subId = submission.getId();

        for (int j = 0; j < screenReviews.length; j++) {
            if (subId == screenReviews[j].getSubmission()) {
                review = screenReviews[j];

                break;
            }
        }

        // get screening score
        Float screeningScore = review.getScore();

        try {

            // Set the screening score to the submission instead of the resource
            submission.setScreeningScore(Double.valueOf(String.valueOf(screeningScore)));

            // If screeningScore < screening minimum score, Set submission status to "Failed Milestone Screening"
            if (screeningScore < minScore) {

                SubmissionStatus subStatus = PhasesHelper.getSubmissionStatus(
                    getManagerHelper().getUploadManager(), SUBMISSION_STATUS_FAILED_MILESTONE_SCREENING);
                submission.setSubmissionStatus(subStatus);

                failedScreening = true;
            }
            getManagerHelper().getUploadManager().updateSubmission(submission, operator);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a upload persistence error", e);
        }

        return failedScreening;
    }

    /**
     * <p>
     * Checks if all the submissions for the given project have one scorecard committed, false otherwise.
     * </p>
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase
     *            the phase to check.
     * @return the validation result indicating whether the scorecards are committed, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseHandlingException
     *             if there was an error retrieving data.
     */
    private OperationCheckResult areScorecardsCommitted(Phase phase) throws PhaseHandlingException {
        Connection conn = createConnection();

        try {
            // get all screening scorecards for the phase
            Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                phase.getId(), new String[] {ROLE_MILESTONE_SCREENER }, null);

            // get the submissions for the project
            Submission[] submissions = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                conn, phase.getProject().getId(), PhasesHelper.MILESTONE_SUBMISSION_TYPE);

            // If the number of reviews doesn't match submission number, mean there is not exactly one milestone
            // screener.
            if (screenReviews.length != submissions.length) {
                LOG.log(Level.INFO, "The number of screening reviews is't matched with the submissions");
                return  new OperationCheckResult("Not all milestone screening scorecards are committed");
            }

            for (int i = 0; i < submissions.length; i++) {
                long subId = submissions[i].getId();

                // check if each submission has a review...
                boolean foundReview = false;

                for (int j = 0; j < screenReviews.length; j++) {
                    if (subId == screenReviews[j].getSubmission()) {
                        foundReview = true;

                        // also check if each review is committed
                        if (!screenReviews[j].isCommitted()) {
                            LOG.log(Level.INFO, "Some screening scorecards are not committed.");
                            return new OperationCheckResult(
                                "Not all milestone screening scorecards are committed (see submission with ID="
                                    + subId + ")");
                        }

                        break;
                    }
                }

                // if a review was not found for a particular submission, return false.
                if (!foundReview) {
                    LOG.log(Level.INFO, "There is no screening review for submission with id - " + subId);
                    return new OperationCheckResult(
                        "Not all milestone screening scorecards are committed (see submission with ID="
                            + subId + ")");
                }
            }

            return OperationCheckResult.SUCCESS;
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
