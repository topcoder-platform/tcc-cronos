/*
 * Copyright (C) 2009-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.scoreaggregator.AggregatedSubmission;
import com.topcoder.management.review.scoreaggregator.InconsistentDataException;
import com.topcoder.management.review.scoreaggregator.RankedSubmission;
import com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator;
import com.topcoder.project.phases.Phase;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add
 * extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection and email sending. This class handle the
 * review phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The review phase can start as soon as the dependencies are met and can stop when the following conditions met:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>All active submissions have one review scorecard from each reviewer for the phase;</li>
 * <li>All test case reviewers have one test case upload.</li>
 * </ul>
 * </p>
 * <p>
 * The additional logic for executing this phase is: When Review phase is starting, all submissions failed
 * automated screening must be set to the status &quot;Failed Screening&quot;.
 * </p>
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager,
 * etc).</li>
 * <li>Support for more information in the email generated: for start, Submitter info with review info. for stop,
 * review result.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.4 changes note:
 * <ul>
 * <li>It now will search active submission with contest submission submission type.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6 changes note:
 * <ul>
 * <li>For Studio competitions, there will be no Appeal, Appeal Response, Aggregation and Aggregation Review
 * phases, so we will populate the winner information and set prizes for submissions.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6.1 (Milestone Support Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #updateSubmissionScores(Phase, String, Map)} method to properly map prizes to submissions.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.6.2 (Online Review Replatforming Release 2 ) Change notes:
 * <ol>
 * <li>Change submission.getUploads() to submission.getUpload().</li>
 * <li>Change contest submission prize type name from "Submission Prize" to "Contest Prize".</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.6.1 (Component development) changes note:
 * <ul>
 * <li>canPerform() method was updated to return not only true/false value, but additionally an explanation message
 * in case if operation cannot be performed.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.6.2 (BUGR-4779) changes note:
 * <ul>
 * <li>Updated {@link #updateSubmissionScores(Phase, String, Map)} method to use 
 * <code>PhaseHeleper.setSubmissionPrize</code> to populate contest prizes for studio submissions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 * @author tuenm, bose_java, argolite, waits, saarixx, myxgyy, FireIce, microsky, flexme
 * @version 1.6.2
 */
public class ReviewPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load configuration
     * settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ReviewPhaseHandler";

    /**
     * Logger instance for this class.
     * @since 1.1
     */
    private static final Log LOG = LogFactory.getLog(ReviewPhaseHandler.class.getName());

    /**
     * Create a new instance of ReviewPhaseHandler using the default namespace for loading configuration settings.
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     */
    public ReviewPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of ReviewPhaseHandler using the given namespace for loading configuration settings.
     * @param namespace
     *            the namespace to load configuration settings from.
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public ReviewPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what will be
     * executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase
     * Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions: The dependencies are met.
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>All active submissions have one review scorecard from each reviewer for the phase;</li>
     * <li>All test case reviewers have one test case upload.</li>
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
     *             if the input phase type is not "Review" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input is null.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PhasesHelper.REVIEW);

        // will throw exception if phase status is neither "Scheduled" nor
        // "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        OperationCheckResult result;
        if (toStart) {
            // return true if all dependencies have stopped and start time has
            // been reached.
            result = PhasesHelper.checkPhaseCanStart(phase);
            if (!result.isSuccess()) {
                return result;
            }

            Connection conn = createConnection();
            try {
                // change in version 1.4
                // Search all "Active" submissions for current project with contest submission type
                Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                    conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
                if (subs.length > 0) {
                    return OperationCheckResult.SUCCESS;
                } else {
                    return new OperationCheckResult("No submissions that passed screening");
                }
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            result = PhasesHelper.checkPhaseDependenciesMet(phase, false);
            LOG.log(Level.INFO, "pid: " + phase.getProject().getId()
                + " - PhasesHelper.arePhaseDependenciesMet(phase, false): " + result.isSuccess());
            if (!result.isSuccess()) {
                return result;
            }
            result = allReviewsDone(phase);
            LOG.log(Level.INFO,
                "pid: " + phase.getProject().getId() + " - allReviewsDone(phase): " + result.isSuccess());
            if (!result.isSuccess()) {
                return result;
            }
            result = allTestCasesUploaded(phase);
            LOG.log(Level.INFO,
                "pid: " + phase.getProject().getId() + " - allTestCasesUploaded(phase): " + result.isSuccess());
            return result;
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
     * If the input phase status is Scheduled, then it will perform the following additional logic to start the
     * phase: All submissions failed automated screening must be set to the status &quot;Failed Screening&quot;.
     * </p>
     * <p>
     * If the input phase status is Open, then it will perform the following additional logic to stop the phase:
     * Initial score for the all passed screening submissions will be calculated and saved to the submitters's
     * resource properties named &quot;Initial Score&quot;.
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * <p>
     * Update for version 1.2, for start, put the reviewer/submission info for stop, put the result info.
     * </p>
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Review" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PhasesHelper.REVIEW);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();
        if (toStart) {
            // for start, puts the reviewer/submission info
            putPhaseStartInfoValues(phase, values);
        } else {
            // update the submission's initial score after review and set in the values map
            updateSubmissionScores(phase, operator, values);
        }

        sendEmail(phase, values);
    }

    /**
     * <p>
     * Puts the start review phase information about submissions and reviewer info to the value map.
     * </p>
     * @param phase
     *            the current Phase, not null
     * @param values
     *            the values map
     * @throws PhaseHandlingException
     *             if any error occurs
     * @since 1.2
     */
    private void putPhaseStartInfoValues(Phase phase, Map<String, Object> values) throws PhaseHandlingException {
        Connection conn = createConnection();

        try {
            // change in version 1.4
            // Search all "Active" submissions for current project with contest submission type
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(), conn,
                phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
            values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(subs,
                    getManagerHelper().getResourceManager(), false));
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
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method calculates initial score of all submissions that passed screening and saves it to the
     * submitter's
     * resource properties. It is called from perform method when phase is stopping.
     * <p>
     * Update for version 1.2, for stop, put the result info.
     * </p>
     * <p>
     * Changes in version 1.6: For studio competitions, there will be no Appeal, Appeal Response, Aggregation, and
     * Aggregation Review Phase, so the ranking and score will be calculated here, and no change later.
     * </p>
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
        Connection conn = createConnection();

        try {
            Project project = getManagerHelper().getProjectManager().getProject(phase.getProject().getId());
            boolean isStudioProject = project.getProjectCategory().getProjectType().getId() == PhasesHelper.STUDIO_PROJECT_TYPE_ID;
            Phase appealsResponsePhase = PhasesHelper.locatePhase(phase, "Appeals Response", true, false);

            Submission[] subs = PhasesHelper.updateSubmissionsResults(getManagerHelper(), conn, phase, operator,
                false, true, isStudioProject || appealsResponsePhase == null);

            // add the submission result to the values map
			DecimalFormat df = new DecimalFormat("#.##");
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Submission sub : subs) {
                Map<String, Object> infos = new HashMap<String, Object>();
                Resource submitter = getManagerHelper().getResourceManager().getResource(sub.getUpload().getOwner());
                infos.put("SUBMITTER_HANDLE", PhasesHelper.notNullValue(submitter.getProperty(PhasesHelper.HANDLE)));
                infos.put("SUBMITTER_SCORE", df.format(sub.getInitialScore()));
                result.add(infos);
            }
            values.put("SUBMITTER", result);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("Problem when looking up resource for the submission.", e);
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Fail to retrieve the corresponding project.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if all active submissions have one review scorecard from each reviewer for the phase and
     * returns true if conditions are met, false otherwise.
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase
     *            the phase instance.
     * @return the validation result indicating whether all the reviews are done, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseHandlingException
     *             if there was an error retrieving data.
     */
    private OperationCheckResult allReviewsDone(Phase phase) throws PhaseHandlingException {
        Connection conn = createConnection();

        try {
            // Search all "Active" submissions for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(), conn,
                phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                PhasesHelper.REVIEWER_ROLE_NAMES, phase.getId());

            // Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResources(conn, getManagerHelper(), reviewers, null);

            if (phase.getAttribute(PhasesHelper.REVIEWER_NUMBER_PROPERTY) != null) {
                int reviewerNum = PhasesHelper.getIntegerAttribute(phase, PhasesHelper.REVIEWER_NUMBER_PROPERTY);

                if (reviewers.length < reviewerNum) {
                    LOG.log(Level.INFO, "can't end phase because: reviewers.length < reviewerNum, projectId: "
                        + phase.getProject().getId());
                    return new OperationCheckResult("Not enough reviewers for project: " + phase.getProject().getId());
                }
            }

            // for each submission
            for (int iSub = 0; iSub < subs.length; iSub++) {
                Submission submission = subs[iSub];
                long subId = submission.getId();
                int nunmberOfReviews = 0;

                // Match the submission with its reviews
                for (int j = 0; j < reviews.length; j++) {
                    // check if review is committed
                    if (!reviews[j].isCommitted()) {
                        return new OperationCheckResult("There are uncommitted reviews.");
                    }
                    if (subId == reviews[j].getSubmission()) {
                        nunmberOfReviews++;
                    }
                }

                // if number of reviews does not match number of reviewers
                if (nunmberOfReviews != reviewers.length) {
                    LOG.log(Level.INFO, "can't end phase because: numReviews != reviewers.length, projectId: "
                        + phase.getProject().getId());
                    return new OperationCheckResult("Not all reviewers submitted their reviews.");
                }
            }

            return OperationCheckResult.SUCCESS;
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if all test case reviewers have one test case uploaded.
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase
     *            the phase instance.
     * @return the validation result indicating whether all the test cases are uploaded, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseHandlingException
     *             if any error occurred when retrieving data.
     */
    private OperationCheckResult allTestCasesUploaded(Phase phase) throws PhaseHandlingException {
        // get test case reviewers for phase id
        Resource[] reviewers = getDevelopmentReviewers(phase);

        // if there are no test case reviewers, no need to check if all uploads have been uploaded
        if (reviewers.length == 0) {
            return OperationCheckResult.SUCCESS;
        }

        List<Long> reviewerIds = new ArrayList<Long>();

        for (int i = 0; i < reviewers.length; i++) {
            reviewerIds.add(new Long(reviewers[i].getId()));
        }

        try {
            Filter resourceIdFilter = new InFilter("resource_id", reviewerIds);
            Upload[] uploads = getManagerHelper().getUploadManager().searchUploads(resourceIdFilter);

            for (int i = 0; i < reviewers.length; i++) {
                // match reviewer with test case upload
                boolean found = false;

                for (int u = 0; u < uploads.length; u++) {
                    if (reviewers[i].getId() == uploads[u].getOwner()) {
                        found = true;
                        break;
                    }
                }

                // if a test case upload is not found, return false
                if (!found) {
                    LOG.log(Level.INFO, "can't end phase. cant find upload for reviewer: " + reviewers[i].getId()
                            + ", " + reviewers[i].getProperty("Handle") + ", "
                            + reviewers[i].getResourceRole().getName());
                    return new OperationCheckResult("Not all test cases are uploaded (see reviewer with "
                        + reviewers[i].getResourceRole().getName() + " role)");
                }
            }
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem retrieving uploads", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("Problem with search builder", e);
        }

        return OperationCheckResult.SUCCESS;
    }

    /**
     * This method retrieves the reviewer ids for Accuracy, Failure and Stress reviews for the given phase id.
     * @param phase
     *            the phase instance.
     * @return reviewers matching search criteria.
     * @throws PhaseHandlingException
     *             if any error occurred when retrieving data.
     */
    private Resource[] getDevelopmentReviewers(Phase phase) throws PhaseHandlingException {
        Connection conn = createConnection();

        try {
            String[] roleNames = new String[] {
                PhasesHelper.ACCURACY_REVIEWER_ROLE_NAME,
                PhasesHelper.FAILURE_REVIEWER_ROLE_NAME,
                PhasesHelper.STRESS_REVIEWER_ROLE_NAME};

            return PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn, roleNames, phase.getId());
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
