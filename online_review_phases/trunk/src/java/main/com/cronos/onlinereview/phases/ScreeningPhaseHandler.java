/*
 * Copyright (C) 2009-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add
 * extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection, email sending. This class handle the
 * screening phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The screening phase can start as soon as the dependencies are met and can stop when the following conditions
 * met:
 * <ul>
 * <li>The dependencies are met.</li>
 * <li>All active submissions have one screening scorecard committed.</li>
 * </ul>
 * </p>
 * <p>
 * The additional logic for executing this phase is: When screening is stopping,
 * <ul>
 * <li>all submissions with failed screening scorecard scores should be set to the status &quot;Failed
 * Screening&quot;.</li>
 * <li>Screening score for the all submissions will be calculated and saved to the submitters&rsquo; resource
 * properties named &quot;Screening Score&quot;.</li>
 * </ul>
 * </p>
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 * <p>
 * Version 1.1 change note: Modify the <code>perform</code> method to add post-mortem phase when there is no passed
 * screening.
 * </p>
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager,
 * etc).</li>
 * <li>Support for more information in the email generated: for start, Submitter info with screener needed or not.
 * for stop, screening result.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.3 (Online Review End Of Project Analysis Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #perform(Phase, String)} method to use updated PhasesHelper#insertPostMortemPhase(Project ,
 * Phase, ManagerHelper, String) method for creating <code>Post-Mortem</code> phase.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>canPerform() method was updated to return not only true/false value, but additionally an explanation message
 * in case if operation cannot be performed.</li>
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
 * @author tuenm, bose_java, argolite, waits, saarixx, microsky, lmmortal
 * @version 1.6.2
 */
public class ScreeningPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default
     * constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ScreeningPhaseHandler";

    /** constant for "Primary Screener" role name. */
    private static final String ROLE_PRIMARY_SCREENER = "Primary Screener";

    /** constant for "Failed Screening" submission status. */
    private static final String SUBMISSION_STATUS_FAILED_SCREENING = "Failed Screening";

    /** constant for screening phase type. */
    private static final String PHASE_TYPE_SCREENING = "Screening";

    /**
     * Create a new instance of ScreeningPhaseHandler using the default
     * namespace for loading configuration settings.
     * @throws ConfigurationException if errors occurred while loading
     *             configuration settings.
     */
    public ScreeningPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of ScreeningPhaseHandler using the given namespace
     * for loading configuration settings.
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading
     *             configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public ScreeningPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check
     * the phase status to see what will be executed. This method will be called
     * by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions: The dependencies are met
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>If manual screening is not required, all submissions have been auto-screened;</li>
     * <li>If manual screening is required, all active submissions have one screening scorecard committed.</li>
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
     * @param phase The input phase to check.
     * @return the validation result indicating whether the associated operation can be performed, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseNotSupportedException if the input phase type is not
     *             "Screening" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *             processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SCREENING);

        // will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        OperationCheckResult result;
        if (toStart) {
            // return true if all dependencies have stopped and start time has been reached.
            result = PhasesHelper.checkPhaseCanStart(phase);
            if (!result.isSuccess()) {
                return result;
            }

            // Search all "Active" submissions for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
            if (subs.length > 0) {
                return OperationCheckResult.SUCCESS;
            } else {
                return new OperationCheckResult("There are no submissions for the project");
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
     * Provides additional logic to execute a phase. This method will be called
     * by start() and end() methods of PhaseManager implementations in Phase
     * Management component. This method can send email to a group of users
     * associated with timeline notification for the project. The email can be
     * send on start phase or end phase base on configuration settings.
     * <p>
     * If the input phase status is Scheduled, then it will do nothing.
     * </p>
     * <p>
     * If the input phase status is Open, then it will perform the following additional logic to stop the phase:
     * <ul>
     * <li>all submissions with failed screening scorecard scores should be set to the status &quot;Failed
     * Screening&quot;.</li>
     * <li>Screening score for the all submissions will be calculated and saved to the submitters&rsquo; resource
     * properties named &quot;Screening Score&quot;.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * <p>
     * Version 1.2 update: if it is start, get the submission's info. if it is stop, get the screening result info.
     * </p>
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     * @throws PhaseNotSupportedException if the input phase type is not
     *             &quot;Screening&quot; type.
     * @throws PhaseHandlingException if there is any error occurred while
     *             processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty
     *             string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SCREENING);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();

        if (toStart) {
            try {
                com.topcoder.management.project.Project project = getManagerHelper().getProjectManager().getProject(phase.getProject().getId());

                if (PhasesHelper.isStudio(project)) {
                    PhasesHelper.autoScreenStudioSubmissions(project, getManagerHelper(), PhasesHelper.CONTEST_SUBMISSION_TYPE, operator);
                }
            } catch (PersistenceException e) {
                throw new PhaseHandlingException("There was an error with project persistence", e);
            }

            // for start, put the submission information with need_primary_screener or not
            putPhaseStartInfos(phase, values);
        } else {
            // flag to indicate whether all submissions do not pass screening
            boolean noScreeningPass = false;

            // Search all submissions for current project
            Submission[] submissions = PhasesHelper.searchActiveSubmissions(
                getManagerHelper().getUploadManager(), phase.getProject().getId(),
                PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // Search all screening scorecard for the current phase
            Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(
                getManagerHelper(), phase.getId(), new String[] {ROLE_PRIMARY_SCREENER}, null);

            if (submissions.length != screenReviews.length) {
                throw new PhaseHandlingException(
                    "Submission count does not match screening count for project:" + phase.getProject().getId());
            }

            if (screenReviews.length > 0) {
                // There is screen reviews, start check screening passes
                noScreeningPass = true;

                // get minimum score
                float minScore = PhasesHelper.getScorecardMinimumScore(
                    getManagerHelper().getScorecardManager(),screenReviews[0]);

                // for each submission...
                for (int iSub = 0; iSub < submissions.length; iSub++) {
                    Submission submission = submissions[iSub];

                    if (!processAndUpdateSubmission(submission, screenReviews, minScore, operator)) {
                        // if there is one passed screening, set noScreeningPass to false
                        noScreeningPass = false;
                    }
                }
            }
            // put the submission screening result
            values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(submissions,
                getManagerHelper().getResourceManager(), true));
            values.put("NO_SCREENING_PASS", noScreeningPass ? 1 : 0);

            // if there is no passed screening, insert post-mortem phase
            if (noScreeningPass) {
                PhasesHelper.insertPostMortemPhase(phase.getProject(), phase, getManagerHelper(), operator);
            }
        }

        // send mail for start/stop phase
        sendEmail(phase, values);
    }

    /**
     * <p>
     * Puts the screening start phase info for email generation.
     * </p>
     * @param phase the current Phase
     * @param values the value map to hold the info
     * @throws PhaseHandlingException if any error occurs
     */
    private void putPhaseStartInfos(Phase phase, Map<String, Object> values) throws PhaseHandlingException {
        // get the screeners
        Resource[] screeners = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(),
            new String[] {ROLE_PRIMARY_SCREENER}, phase.getId());
        values.put("NEED_PRIMARY_SCREENER", screeners.length == 0 ? 1 : 0);
        // get submissions
        Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
            phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
        // put the submitter info into the map
        values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(subs,
            getManagerHelper().getResourceManager(), false));
    }

    /**
     * This method is called from perform method when phase is stopping. It does
     * the following :
     * <ul>
     * <li>All submissions with failed screening scorecard scores are set to the status "Failed Screening".</li>
     * <li>Screening score for the all submissions will be saved to the submitters' resource properties named
     * "Screening Score".</li>
     * </ul>
     * <p>
     * Change notes: Change return value from void to boolean to indicate whether the checked submission failed
     * screen. True if failed, false otherwise.
     * </p>
     * @param submission the submission to process and update.
     * @param screenReviews screening reviews.
     * @param minScore minimum scorecard score.
     * @param operator operator name.
     * @return true if the submission failed screen, false otherwise.
     * @throws PhaseHandlingException if there was a problem retrieving/updating data.
     */
    private boolean processAndUpdateSubmission(Submission submission,
                    Review[] screenReviews, float minScore, String operator) throws PhaseHandlingException {

        // boolean flag to indicate whether the submission failed screen
        boolean failedScreening = false;

        // Find the matching screening review
        Review review = null;
        long subId = submission.getId();

        for (Review screenReview : screenReviews) {
            if (subId == screenReview.getSubmission()) {
                review = screenReview;
                break;
            }
        }

        if (review == null) {
            throw new PhaseHandlingException("Unable to find screening review for the submission " + subId);
        }

        Float screeningScore = review.getScore();
        UploadManager uploadManager = getManagerHelper().getUploadManager();

        try {
            // Set the screening score to the submission instead of the resource
            submission.setScreeningScore(Double.valueOf(String.valueOf(screeningScore)));

            // If screeningScore < screening minimum score, Set submission status to "Failed Screening"
            if (screeningScore < minScore) {
                SubmissionStatus subStatus = LookupHelper.getSubmissionStatus(
                    uploadManager, SUBMISSION_STATUS_FAILED_SCREENING);

                submission.setSubmissionStatus(subStatus);
                failedScreening = true;
            }
            uploadManager.updateSubmission(submission, operator);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a upload persistence error", e);
        }

        return failedScreening;
    }

    /**
     * returns true if all the submissions for the given project have one
     * scorecard committed, false otherwise.
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase the phase to check.
     * @return the validation result indicating whether the primary scorecards are committed, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseHandlingException if there was an error retrieving data.
     */
    private OperationCheckResult areScorecardsCommitted(Phase phase) throws PhaseHandlingException {
        // get all screening scorecards for the phase
        Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(getManagerHelper(),
            phase.getId(), new String[] {ROLE_PRIMARY_SCREENER }, null);

        // get the submissions for the project
        Submission[] submissions = PhasesHelper.searchActiveSubmissions(
            getManagerHelper().getUploadManager(), phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);

        // If the number of reviews doesn't match the number of submissions it means that
        // not all screening scorecards have been committed yet.
        if (screenReviews.length != submissions.length) {
            return new OperationCheckResult("Not all screening scorecards are committed");
        }

        for (int i = 0; i < submissions.length; i++) {
            long subId = submissions[i].getId();

            // check if each submission has a review
            boolean foundReview = false;

            for (int j = 0; j < screenReviews.length; j++) {
                if (subId == screenReviews[j].getSubmission()) {
                    foundReview = true;

                    // check if each review is committed
                    if (!screenReviews[j].isCommitted()) {
                        return new OperationCheckResult("Screening for " + subId + " has not been committed yet.");
                    }

                    break;
                }
            }

            // if a review was not found for a particular submission.
            if (!foundReview) {
                return new OperationCheckResult("Screening for " + subId + " has not been committed yet.");
            }
        }

        return OperationCheckResult.SUCCESS;
    }
}
