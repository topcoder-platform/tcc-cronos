/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection, email sending. This class handle the
 * screening phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>The screening phase can start as soon as the dependencies are met and can stop when the following conditions
 * met:
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>If manual screening is not required, all submissions have been auto-screened;</li>
 *      <li>If manual screening is required, all active submissions have one screening scorecard committed.</li>
 *  </ul>
 *  </p>
 *  <p>The additional logic for executing this phase is: When screening is stopping,
 *  <ul>
 *      <li>all submissions with failed screening scorecard scores should be set to the status &quot;Failed
 *      Screening&quot;.</li>
 *      <li>Screening score for the all submissions will be calculated and saved to the submitters&rsquo;
 *      resource properties named &quot;Screening Score&quot;.</li>
 *  </ul>
 *  </p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class ScreeningPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ScreeningPhaseHandler";

    /** constant for "Submitter" role name. */
    private static final String ROLE_SUBMITTER = "Submitter";

    /** constant for "Screener" role name. */
    private static final String ROLE_SCREENER = "Screener";

    /** constant for "Primary Screener" role name. */
    private static final String ROLE_PRIMARY_SCREENER = "Primary Screener";

    /** constant for "Failed Screening" submission status. */
    private static final String SUBMISSION_STATUS_FAILED_SCREENING = "Failed Screening";

    /** constant for "Screening Score" property. */
    private static final String PROP_SCREENING_SCORE = "Screening Score";

    /** constant for screening phase type. */
    private static final String PHASE_TYPE_SCREENING = "Screening";

    /**
     * Create a new instance of ScreeningPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public ScreeningPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of ScreeningPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public ScreeningPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.<p>If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: The dependencies are met</p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>If manual screening is not required, all submissions have been auto-screened;</li>
     *      <li>If manual screening is required, all active submissions have one screening scorecard
     *      committed.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Screening" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SCREENING);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            return PhasesHelper.canPhaseStart(phase);
        } else {
            if (!PhasesHelper.arePhaseDependenciesMet(phase, false)) {
                return false;
            }

            Boolean bPrimaryScreening = isPrimaryScreening(phase);
            if (bPrimaryScreening == null) {
                return false;
            }

            if (bPrimaryScreening.booleanValue()) {
                return arePrimaryScorecardsCommitted(phase);
            } else {
                return areScorecardsCommitted(phase);
            }
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings.<p>If the input phase status is Scheduled, then it will do nothing.</p>
     *  <p>If the input phase status is Open, then it will perform the following additional logic to stop the
     * phase:
     *  <ul>
     *      <li>all submissions with failed screening scorecard scores should be set to the status
     *      &quot;Failed Screening&quot;.</li>
     *      <li>Screening score for the all submissions will be calculated and saved to the
     *      submitters&rsquo; resource properties named &quot;Screening Score&quot;.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Screening" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SCREENING);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        //when phase is stopping
        if (!toStart) {
            Connection conn = null;
            try {
                conn = createConnection();

                //Search all submissions for current project
                Submission[] submissions = PhasesHelper.searchSubmissionsForProject(
                        getManagerHelper().getUploadManager(), phase.getProject().getId());

                //Search all screening scorecard for the current phase
                Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                        phase.getId(), new String[] { ROLE_PRIMARY_SCREENER, ROLE_SCREENER }, null);

                if (submissions.length != screenReviews.length) {
                    throw new PhaseHandlingException("Submission count does not match screening count for project:"
                            + phase.getProject().getId());
                }

                if (screenReviews.length > 0) {
                    //get minimum score
                    float minScore = PhasesHelper.getScorecardMinimumScore(getManagerHelper().getScorecardManager(),
                            screenReviews[0]);

                    //for each submission...
                    for (int iSub = 0; iSub < submissions.length; iSub++) {
                        Submission submission = submissions[iSub];
                        processAndUpdateSubmission(submission, screenReviews, minScore, operator, conn);
                    }
                }
            } catch (SQLException e) {
                throw new PhaseHandlingException("Problem when looking up ids.", e);
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        }

        //send mail for start/stop phase
        sendEmail(phase);
    }

    /**
     * This method is called from perform method when phase is stopping. It does the following :
     *  <ul>
     *      <li>All submissions with failed screening scorecard scores are set to the status "Failed
     *      Screening".</li>
     *      <li>Screening score for the all submissions will be saved to the submitters' resource
     *      properties named "Screening Score".</li>
     *  </ul>
     *
     * @param submission the submission to process and update.
     * @param screenReviews screening reviews.
     * @param minScore minimum scorecard score.
     * @param operator operator name.
     * @param conn connection to use when looking up ids.
     *
     * @throws PhaseHandlingException if there was a problem retrieving/updating data.
     */
    private void processAndUpdateSubmission(Submission submission, Review[] screenReviews, float minScore,
        String operator, Connection conn) throws PhaseHandlingException {
        //Find the matching screening review
        Review review = null;
        long subId = submission.getId();

        for (int j = 0; j < screenReviews.length; j++) {
            if (subId == screenReviews[j].getSubmission()) {
                review = screenReviews[j];

                break;
            }
        }

        //get screening score
        Float screeningScore = review.getScore();

        //Store the screening score for the submission
        long submitterId = submission.getUpload().getOwner();

        try {
            //get submitter
            Resource submitter = getManagerHelper().getResourceManager().getResource(submitterId);
            //Set "Screening Score" property
            submitter.setProperty(PROP_SCREENING_SCORE, screeningScore.toString());
            //Save the submitter to the persistence
            getManagerHelper().getResourceManager().updateResource(submitter, operator);

            //If screeningScore < screening minimum score, Set submission status to "Failed Screening"
            if (screeningScore.floatValue() < minScore) {

                SubmissionStatus subStatus = PhasesHelper.getSubmissionStatus(getManagerHelper().getUploadManager(),
                    SUBMISSION_STATUS_FAILED_SCREENING);
                submission.setSubmissionStatus(subStatus);
                getManagerHelper().getUploadManager().updateSubmission(submission, operator);
            }
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("There was a resource persistence error", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a upload persistence error", e);
        }
    }

    /**
     * Checks if the submission that passed review has one scorecard committed.
     *
     * @param phase phase instance.
     *
     * @return true if the submission that passed review has one scorecard committed.
     *
     * @throws PhaseHandlingException if there was an error retrieving data.
     */
    private boolean areScorecardsCommitted(Phase phase)
        throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();

            //get the submitter for this phase
            Resource[] resources = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] { ROLE_SUBMITTER }, phase.getId());

            if (resources.length != 1) {
                throw new PhaseHandlingException("Inconsistent data: Multiple resources");
            }

            Resource submitter = resources[0];

            //search submission based on submitter
            Submission submission = null;

            Filter projectIdFilter = SubmissionFilterBuilder.createProjectIdFilter(phase.getProject().getId());
            Filter resourceIdFilter = SubmissionFilterBuilder.createResourceIdFilter(submitter.getId());
            Filter fullFilter = SearchBundle.buildAndFilter(projectIdFilter, resourceIdFilter);
            Submission[] submissions = getManagerHelper().getUploadManager().searchSubmissions(fullFilter);

            if (submissions.length != 1) {
                throw new PhaseHandlingException("Inconsistent data: Multiple submissions");
            }

            submission = submissions[0];

            //search for review
            Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(), phase.getId(),
                    new String[] { ROLE_SCREENER }, null);

            if (screenReviews.length != 1) {
                throw new PhaseHandlingException("Inconsistent data: Multiple reviews");
            }

            Review screenReview = screenReviews[0];

            //Check if the screening review is associated with the submission
            if (submission.getId() != screenReview.getSubmission()) {
                return false;
            }

            //Check if the screening scorecards are committed
            return screenReview.isCommitted();
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("There was a submission retrieval error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a search builder error", e);
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * returns true if all the submissions for the given project have one scorecard committed, false otherwise.
     *
     * @param phase the phase to check.
     *
     * @return true if all the submissions for the given project have one scorecard committed, false otherwise.
     *
     * @throws PhaseHandlingException if there was an error retrieving data.
     */
    private boolean arePrimaryScorecardsCommitted(Phase phase)
        throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            //get all screening scorecards for the phase
            Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(), phase.getId(),
                    new String[] { ROLE_PRIMARY_SCREENER }, null);

            //get the submissions for the project
            Submission[] submissions = PhasesHelper.searchSubmissionsForProject(getManagerHelper().getUploadManager(),
                    phase.getProject().getId());

            // If the number of reviews doesn't match submission number - not all reviews are commited for sure
            if (screenReviews.length != submissions.length) {
                return false;
            }

            for (int i = 0; i < submissions.length; i++) {
                long subId = submissions[i].getId();

                //check if each submission has a review...
                boolean foundReview = false;

                for (int j = 0; j < screenReviews.length; j++) {
                    if (subId == screenReviews[j].getSubmission()) {
                        foundReview = true;

                        //also check if each review is committed
                        if (!screenReviews[j].isCommitted()) {
                            return false;
                        }

                        break;
                    }
                }

                //if a review was not found for a particular submission, return false.
                if (!foundReview) {
                    return false;
                }
            }

            return true;

        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * returns true if the screening mode is Primary, false if it is Individual.
     *
     * @param phase the phase instance.
     *
     * @return true if the screening mode is Primary, false if it is Individual.
     *
     * @throws PhaseHandlingException if an error occurs during data retrieval or if data is inconsistent.
     */
    private Boolean isPrimaryScreening(Phase phase) throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            Resource[] screeners = null;

            //search for primary screeners first
            Resource[] primaryScreeners = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] { ROLE_PRIMARY_SCREENER }, phase.getId());

            //if no row is returned, search for screeners
            if (primaryScreeners.length == 0) {
                screeners = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                        new String[] { ROLE_SCREENER }, phase.getId());
            } else {
                screeners = new Resource[0];
            }

            //if both searches returned more than one row
            if ((primaryScreeners.length > 0) && (screeners.length > 0)) {
                return null;
            }

            if (primaryScreeners.length > 0) {
                return Boolean.TRUE;
            } else if (screeners.length > 0) {
                return Boolean.FALSE;
            } else {
                return null;
            }
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
