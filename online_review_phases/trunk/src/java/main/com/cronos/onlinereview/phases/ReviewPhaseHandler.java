/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scoreaggregator.AggregatedSubmission;
import com.topcoder.management.review.scoreaggregator.InconsistentDataException;
import com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator;

import com.topcoder.project.phases.Phase;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection and email sending. This class handle the
 * review phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>The review phase can start as soon as the dependencies are met and can stop when the following conditions
 * met:
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>All active submissions have one review scorecard from each reviewer for the phase;</li>
 *      <li>All test case reviewers have one test case upload.</li>
 *  </ul>
 *  </p>
 *  <p>The additional logic for executing this phase is: When Review phase is starting, all submissions failed
 * automated screening must be set to the status &quot;Failed Screening&quot;.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class ReviewPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ReviewPhaseHandler";

    /** constant for Review phase type. */
    private static final String PHASE_TYPE_REVIEW = "Review";

    /**
     * Create a new instance of ReviewPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public ReviewPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of ReviewPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public ReviewPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.<p>If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: The dependencies are met.</p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>All active submissions have one review scorecard from each reviewer for the phase;</li>
     *      <li>All test case reviewers have one test case upload.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Review" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REVIEW);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            if (!PhasesHelper.canPhaseStart(phase)) {
                return false;
            }

            Connection conn = null;
            try {
                conn = createConnection();
                //Search all "Active" submissions for current project
                Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(), conn,
                        phase.getProject().getId());
                return (subs.length > 0);
            } catch (SQLException sqle) {
                throw new PhaseHandlingException("Failed to search submissions.", sqle);
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            return (PhasesHelper.arePhaseDependenciesMet(phase, false)
                    && allReviewsDone(phase)
                    && allTestCasesUploaded(phase));
        }
    }

    /**
     * <p>Provides addtional logic to execute a phase. This method will be called by start() and end() methods
     * of PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings.</p>
     *  <p>If the input phase status is Scheduled, then it will perform the following additional logic to start
     * the phase: All submissions failed automated screening must be set to the status &quot;Failed Screening&quot;.</p>
     *  <p>If the input phase status is Open, then it will perform the following additional logic to stop the
     * phase: Initial score for the all passed screening submissions will be calculated and saved to the submitters's
     * resource properties named &quot;Initial Score&quot;.</p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Review" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_REVIEW);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (!toStart) {
            updateSubmissionScores(phase, operator);
        }

        sendEmail(phase);
    }

    /**
     * This method calculates initial score of all submissions that passed screening and saves it to the
     * submitter's resource properties. It is called from perform method when phase is stopping.
     *
     * @param phase phase instance.
     * @param operator the operator name.
     *
     * @throws PhaseHandlingException in case of error when retrieving/updating data.
     */
    private void updateSubmissionScores(Phase phase, String operator)
        throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            //Search all "Active" submissions for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(), conn,
                    phase.getProject().getId());

            //Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    PhasesHelper.REVIEWER_ROLE_NAMES, phase.getId());

            //Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(), phase.getId(),
                    PhasesHelper.REVIEWER_ROLE_NAMES, null);

            //create array to hold scores from all reviewers for all submissions
            com.topcoder.management.review.scoreaggregator.Submission[] submissionScores =
                new com.topcoder.management.review.scoreaggregator.Submission[subs.length];

            //for each submission, populate scores array to use with review score aggregator.
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                int noReviews = 0;
                List scoresList = new ArrayList();

                //Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    if (subId == reviews[j].getSubmission()) {
                        noReviews++;
                        //get review score
                        scoresList.add(reviews[j].getScore());
                    }
                }

                //if no. of reviews do not match no. of reviewers return false.
                if (noReviews != reviewers.length) {
                    throw new PhaseHandlingException("Number of reviews does not match number of reviewers");
                }

                //create float array
                float[] scores = new float[scoresList.size()];

                for (int iScore = 0; iScore < scores.length; iScore++) {
                    scores[iScore] = ((Float) scoresList.get(iScore)).floatValue();
                }

                submissionScores[iSub] = new com.topcoder.management.review.scoreaggregator.Submission(subId, scores);
            }

            //now calculate the aggregated scores
            ReviewScoreAggregator scoreAggregator = getManagerHelper().getScorecardAggregator();

            //this will hold as many elements as submissions
            AggregatedSubmission[] aggregations = scoreAggregator.aggregateScores(submissionScores);

            //again iterate over submissions to set the initial score
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                float aggScore = aggregations[iSub].getAggregatedScore();

                //update submitter's initial score
                long submitterId = submission.getUpload().getOwner();
                Resource submitter = getManagerHelper().getResourceManager().getResource(submitterId);
                submitter.setProperty("Initial Score", String.valueOf(aggScore));
                getManagerHelper().getResourceManager().updateResource(submitter, operator);
            }
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up id", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem when retrieving/updating resource", e);
        } catch (InconsistentDataException e) {
            throw new PhaseHandlingException("Problem when aggregating scores", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if all active submissions have one review scorecard from each reviewer for the phase
     * and returns true if conditions are met, false otherwise.
     *
     * @param phase the phase instance.
     *
     * @return true if all active submissions have one review from each reviewer, false otherwise.
     *
     * @throws PhaseHandlingException if there was an error retrieving data.
     */
    private boolean allReviewsDone(Phase phase) throws PhaseHandlingException {
        Connection conn = null;

        try {
            //Search all "Active" submissions for current project
            conn = createConnection();

            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(), conn,
                    phase.getProject().getId());

            //Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    PhasesHelper.REVIEWER_ROLE_NAMES, phase.getId());

            //Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(), phase.getId(),
                    PhasesHelper.REVIEWER_ROLE_NAMES, null);

            if (reviewers.length == 0) {
                return false;
            }

            if (phase.getAttribute("Reviewer Number") != null) {
                int reviewerNum = PhasesHelper.getIntegerAttribute(phase, "Reviewer Number");

                if (reviewers.length < reviewerNum) {
                    return false;
                }
            }

            //for each submission
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                int noReviews = 0;

                //Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    // check if review is committed
                    if (!reviews[j].isCommitted()) {
                        return false;
                    }
                    if (subId == reviews[j].getSubmission()) {
                        //this author of review should also match reviewer id
                        for (int r = 0; r < reviewers.length; r++) {
                            if (reviews[j].getAuthor() == reviewers[r].getId()) {
                                noReviews++;

                                break;
                            }
                        }
                    }
                }

                //if no. of reviews do not match no. of reviews return false.
                if (noReviews != reviewers.length) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            throw new PhaseHandlingException("Error retrieving submission status id", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if all test case reviewers have one test case uploaded.
     *
     * @param phase the phase instance.
     *
     * @return true if all test case reviewers have one test case uploaded, or if
     *       there are no test case reviewers; false otherwise.
     *
     * @throws PhaseHandlingException if any error occured when retrieving data.
     */
    private boolean allTestCasesUploaded(Phase phase) throws PhaseHandlingException {
        // get test case reviewers for phase id
        Resource[] reviewers = getReviewers(phase);

        // if there are no test case reviewers,
        // no need to check if all uploads have been uploaded
        if (reviewers.length == 0) {
            return true;
        }

        List reviewerIds = new ArrayList();

        for (int i = 0; i < reviewers.length; i++) {
            reviewerIds.add(new Long(reviewers[i].getId()));
        }

        try {
            Filter resourceIdFilter = SearchBundle.buildInFilter("resource_id", reviewerIds);
            Upload[] uploads = getManagerHelper().getUploadManager().searchUploads(resourceIdFilter);

            //match reviewer with test case upload
            boolean found = false;

            for (int i = 0; i < reviewers.length; i++) {
                for (int u = 0; u < uploads.length; u++) {
                    if (reviewers[i].getId() == uploads[u].getOwner()) {
                        found = true;

                        break;
                    }
                }

                //if a test case upload is not found, return false
                if (!found) {
                    return false;
                }
            }
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem retrieving uploads", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        }

        return true;
    }

    /**
     * This method retrievies the reviewer ids for Accuracy, Failure and Stress reviews for the given phase id.
     *
     * @param phase the phase instance.
     *
     * @return reviewers matching search criteria.
     *
     * @throws PhaseHandlingException if anyb error occured when retrieving data.
     */
    private Resource[] getReviewers(Phase phase) throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();
            long accuracyReviewerId = ResourceRoleLookupUtility.lookUpId(conn, "Accuracy Reviewer");
            long failureReviewerId = ResourceRoleLookupUtility.lookUpId(conn, "Failure Reviewer");
            long stressReviewerId = ResourceRoleLookupUtility.lookUpId(conn, "Stress Reviewer");

            List resourceRoleIds = new ArrayList();
            resourceRoleIds.add(new Long(accuracyReviewerId));
            resourceRoleIds.add(new Long(failureReviewerId));
            resourceRoleIds.add(new Long(stressReviewerId));

            //prepare filters
            Filter resourceRoleFilter = SearchBundle.buildInFilter(
                ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, resourceRoleIds);
            Filter phaseIdFilter = ResourceFilterBuilder.createPhaseIdFilter(phase.getId());
            Filter fullFilter = SearchBundle.buildAndFilter(resourceRoleFilter, phaseIdFilter);

            return getManagerHelper().getResourceManager().searchResources(fullFilter);
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem connecting to database", e);
        } catch (SearchBuilderConfigurationException e) {
            throw new PhaseHandlingException("Problem with search builder configuration", e);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem retrieving resource", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
