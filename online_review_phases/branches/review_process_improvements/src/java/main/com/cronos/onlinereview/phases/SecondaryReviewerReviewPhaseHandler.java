/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cronos.onlinereview.phases.logging.LogMessage;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scoreaggregator.AggregatedSubmission;
import com.topcoder.management.review.scoreaggregator.InconsistentDataException;
import com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This handler is responsible for checking whether the Secondary Reviewer Review Phase can be performed and
 * performing the phase. It extends <code>AbstractPhaseHandler</code> to leverage the various services
 * provided by the base class. Logging is done with the Logging Wrapper.
 * </p>
 * <p>
 * Usage: please see "test_files/config/Phase_Handler_1_5.xml". The namespace is
 * "com.cronos.onlinereview.phases.SecondaryReviewerReviewPhaseHandler".
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe because it's immutable.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.5
 * @since 1.5
 */
public class SecondaryReviewerReviewPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * Represents the default namespace of this class.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It is final and won't change
     * once it is initialized as part of variable declaration to:
     * "com.cronos.onlinereview.phases.SecondaryReviewerReviewPhaseHandler".
     * </p>
     * <p>
     * Usage: It is used in {@link #SecondaryReviewerReviewPhaseHandler()} (for initialization).
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.SecondaryReviewerReviewPhaseHandler";

    /**
     * <p>
     * The logger used for logging.
     * </p>
     * <p>
     * LegalValue: It cannot be null. Initialization and Mutability: It is final and won't change once it is
     * initialized as part of variable declaration to:
     * LogFactory.getLog(SecondaryReviewerReviewPhaseHandler.class.getName()).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogFactory.getLog(SecondaryReviewerReviewPhaseHandler.class.getName());

    /**
     * <p>
     * The Constant PHASE_TYPE.
     * </p>
     */
    private static final String PHASE_TYPE = "Secondary Reviewer Review";

    /**
     * <p>
     * The secondary reviewer role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #SecondaryReviewerReviewPhaseHandler()} (for initialization),
     * {@link #allReviewsDone()}.
     * </p>
     */
    private final String secondaryReviewerRoleName;

    /**
     * <p>
     * Create an instance of the class with the default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             if any error occurs
     */
    public SecondaryReviewerReviewPhaseHandler() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create an instance of the class.
     * </p>
     *
     * @param namespace
     *            the configuration namespace.
     * @throws ConfigurationException
     *             if any error occurs
     * @throws IllegalArgumentException
     *             if namespace is null or empty.
     */
    public SecondaryReviewerReviewPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
        secondaryReviewerRoleName = PhasesHelper.getPropertyValue(namespace, "secondaryReviewerRoleName",
            true);
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
     *
     * @param phase
     *            The input phase to check
     * @return True if the input phase can be executed, false otherwise.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Secondary Reviewer Review"
     * @throws PhaseHandlingException
     *             if there is any error occurred
     * @throws IllegalArgumentException
     *             if phase is null;
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            // check if the phase is to start:
            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            if (toStart) {
                return PhasesHelper.canPhaseStart(phase);
            }
            // Check if dependencies are met:
            // Check if all reviews are done by the secondary reviewers:
            return PhasesHelper.arePhaseDependenciesMet(phase, false) && allReviewsDone(phase);
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
     * @param operation
     *            The operator that execute the phase.
     * @param phase
     *            The input phase to check.
     * @throws IllegalArgumentException
     *             if phase is null; operation is null or empty.
     * @throws PhaseNotSupportedException
     *             if the input phase type doesn't equal to "Secondary Reviewer Review"
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public void perform(Phase phase, String operation) throws PhaseHandlingException {

        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operation, "operation");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
            Map<String, Object> values = new HashMap<String, Object>();
            if (toStart) {
                // for start, puts the reviewer/submission info
                putPhaseStartInfoValues(phase, values);
            } else {
                // update the submission's initial score after review and set in the values
                // map
                updateSubmissionScores(phase, operation, values);
            }

            sendEmail(phase, values);
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, operation, phase.getProject().getId());
        }
    }

    /**
     * <p>
     * This method checks if all active submissions have one review scorecard from each secondary reviewer for
     * the phase and returns true if conditions are met,false otherwise.
     * </p>
     *
     * @param phase
     *            the phase instance.
     * @return true if all active submissions have one review from each secondary reviewer, false otherwise.
     * @throws PhaseHandlingException
     *             if there was an error retrieving data.
     */
    private boolean allReviewsDone(Phase phase) throws PhaseHandlingException {

        Connection conn = null;

        try {
            conn = createConnection();

            // Search all "Active" submissions for current project:
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // Search the secondary reviewers' resources:
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                new String[]{secondaryReviewerRoleName}, phase.getId());

            // Search all secondary review scorecards for the current phase:
            Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                phase.getId(), new String[]{secondaryReviewerRoleName}, null);

            for (Submission sub : subs) {
                // Get the submission id from submission:
                long submissionId = sub.getId();

                // This is the number of reviews of this submission:
                int numberOfReviews = 0;

                for (Review review : reviews) {
                    if (!review.isCommitted()) {
                        return false;

                    }
                    if (submissionId == review.getSubmission()) {
                        for (Resource resource : reviewers) {
                            if (review.getAuthor() == resource.getId()) {
                                numberOfReviews++;
                                break;
                            }
                        }

                    }
                }

                if (numberOfReviews != reviewers.length) {
                    return false;
                }
            }

            return true;

        } catch (SQLException ex) {
            throw new PhaseHandlingException("Failed to searching the data retrieval.", ex);
        } finally {
            PhasesHelper.closeConnection(conn);
        }

    }

    /**
     * <p>
     * Puts the start review phase information about submissions and reviewer info to the value map.
     * </p>
     *
     * @param phase
     *            the current Phase, not null
     * @param values
     *            the values map
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    private void putPhaseStartInfoValues(Phase phase, Map<String, Object> values)
        throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            // Search all "Active" submissions for current project with contest submission type
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
            values.put("SUBMITTER",
                PhasesHelper.constructSubmitterValues(subs, getManagerHelper().getResourceManager(), false));
            // Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                PhasesHelper.REVIEWER_ROLE_NAMES, phase.getId());
            // according to discussion here
            // http://forums.topcoder.com/?module=Thread&threadID=659556&start=0
            // if the attribute is not set, default value would be 0
            int reviewerNum = 0;
            if (phase.getAttribute(PhasesHelper.REVIEWER_NUMBER_PROPERTY) != null) {
                reviewerNum = PhasesHelper.getIntegerAttribute(phase, PhasesHelper.REVIEWER_NUMBER_PROPERTY);
            }
            values.put("N_REQUIRED_REVIEWERS", reviewerNum);
            values.put("N_REVIEWERS", reviewers.length);
            values.put("NEED_REVIEWER", reviewers.length >= reviewerNum ? 0 : 1);
        } catch (SQLException ex) {
            throw logAndThrowException(ex, "Problem when looking up submissions for review phase.", null,
                phase.getId());
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Log and throw exception.
     * </p>
     *
     * @param ex
     *            the exception instance
     * @param detail
     *            the detail message
     * @param operation
     *            the operation name
     * @param phaseId
     *            the phase id number
     * @return the phase handling exception
     */
    private PhaseHandlingException logAndThrowException(Exception ex, String detail, String operation,
        Long phaseId) {
        LOG.log(Level.ERROR, new LogMessage(phaseId, operation, detail, ex));
        return new PhaseHandlingException(detail, ex);
    }

    /**
     * <p>
     * This method calculates initial score of all submissions that passed screening and saves it to the
     * submitter's resource properties. It is called from perform method when phase is stopping. for stop, put
     * the result info.
     * </p>
     *
     * @param phase
     *            phase instance.
     * @param operator
     *            the operator name.
     * @param values
     *            map
     * @throws PhaseHandlingException
     *             in case of error when retrieving/updating data.
     */
    private void updateSubmissionScores(Phase phase, String operator, Map<String, Object> values)
        throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            // Search all "Active" submissions with contest submission type for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                PhasesHelper.REVIEWER_ROLE_NAMES, phase.getId());

            // Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResources(conn, getManagerHelper(), reviewers,
                null);

            // create array to hold scores from all reviewers for all submissions
            com.topcoder.management.review.scoreaggregator.Submission[] submissionScores = new com.topcoder.management.review.scoreaggregator.Submission[subs.length];

            // for each submission, populate scores array to use with review score aggregator.
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                int noReviews = 0;
                List<Float> scoresList = new ArrayList<Float>();

                // Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    if (subId == reviews[j].getSubmission()) {
                        noReviews++;
                        // get review score
                        scoresList.add(reviews[j].getScore());
                    }
                }

                // if no. of reviews do not match no. of reviewers return false.
                if (noReviews != reviewers.length) {
                    String detail = "Number of reviews does not match number of reviewers";
                    LOG.log(Level.ERROR, detail);
                    throw new PhaseHandlingException(detail);
                }

                // create float array
                float[] scores = new float[scoresList.size()];

                for (int iScore = 0; iScore < scores.length; iScore++) {
                    scores[iScore] = ((Float) scoresList.get(iScore)).floatValue();
                }

                submissionScores[iSub] = new com.topcoder.management.review.scoreaggregator.Submission(subId,
                    scores);
            }

            // now calculate the aggregated scores
            ReviewScoreAggregator scoreAggregator = getManagerHelper().getScorecardAggregator();

            // this will hold as many elements as submissions
            AggregatedSubmission[] aggregations = scoreAggregator.aggregateScores(submissionScores);

            // again iterate over submissions to set the initial score
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                float aggScore = aggregations[iSub].getAggregatedScore();

                // OrChange - Modified to update the submissions table instead
                // of the resource_info table
                submission.setInitialScore(Double.valueOf(String.valueOf(aggScore)));
                getManagerHelper().getUploadManager().updateSubmission(submission, operator);

            }
            // add the submission result to the values map
            try {
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                for (Submission sub : subs) {
                    Map<String, Object> infos = new HashMap<String, Object>();
                    Resource submitt = getManagerHelper().getResourceManager().getResource(
                        sub.getUpload().getOwner());
                    infos.put("SUBMITTER_HANDLE",
                        PhasesHelper.notNullValue(submitt.getProperty(PhasesHelper.HANDLE)));
                    infos.put("SUBMITTER_SCORE", sub.getInitialScore());
                    result.add(infos);
                }
                values.put("SUBMITTER", result);
            } catch (ResourcePersistenceException ex) {
                throw logAndThrowException(ex, "Problem when looking up resource for the submission.",
                    operator, phase.getId());

            }
        } catch (SQLException ex) {
            throw logAndThrowException(ex, "Problem when looking up id", operator, phase.getId());
        } catch (InconsistentDataException ex) {
            throw logAndThrowException(ex, "Problem when aggregating scores", operator, phase.getId());
        } catch (UploadPersistenceException ex) {
            throw logAndThrowException(ex, "Problem when updating submission", operator, phase.getId());
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
