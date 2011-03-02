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

import com.topcoder.management.phase.PhaseHandlingException;
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
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This handler is responsible for checking whether the Primary Review Evaluation Phase can be performed and
 * performing the phase. It extends <code>AbstractPhaseHandle</code>r to leverage the various services
 * provided by the base class. Logging is done with the Logging Wrapper.
 * </p>
 * <p>
 * Usage: please see "test_files/config/Phase_Handler_1_5.xml". The namespace is
 * "com.cronos.onlinereview.phases.PrimaryReviewEvaluationPhaseHandler".
 * </p>
 * <p>
 * Thread Safety: This class is thread-safe because it's immutable.
 * </p>
 * <p>
 * Version 1.6 (Online Review Update Review Management Process assembly 1 version 1.0) Change notes:
 * <ol>
 * <li>Modified {@link #canPerform(Phase)} </li>
 * <li>Modified {@link #perform(Phase, String)} </li>
 * <li>Added {@link #updateScores(Phase, String, Map<String, Object>)} </li>
 * </ol>
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.6
 * @since 1.5
 */
public class PrimaryReviewEvaluationPhaseHandler extends AbstractPhaseHandler {

    /**
     * <p>
     * Represents the default namespace of this class.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. Initialization and Mutability: It is final and won't change
     * once it is initialized as part of variable declaration to:
     * "com.cronos.onlinereview.phases.PrimaryReviewEvaluationPhaseHandler".
     * </p>
     * <p>
     * Usage: It is used in {@link #PrimaryReviewEvaluationPhaseHandler()} (for initialization).
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.PrimaryReviewEvaluationPhaseHandler";

    /**
     * <p>
     * The logger used for logging.
     * </p>
     * <p>
     * LegalValue: It cannot be null. Initialization and Mutability: It is final and won't change once it is
     * initialized as part of variable declaration to:
     * LogFactory.getLog(PrimaryReviewEvaluationPhaseHandler.class.getName()).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogFactory.getLog(PrimaryReviewEvaluationPhaseHandler.class.getName());

    /**
     * <p>
     * The Constant PHASE_TYPE.
     * </p>
     */
    private static final String PHASE_TYPE = "Primary Review Evaluation";

    /**
     * <p>
     * The primary reviewer role name.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. Initialization and Mutability: It's initialized within
     * constructor, won't change afterwards.
     * </p>
     * <p>
     * Usage: It is used in {@link #PrimaryReviewEvaluationPhaseHandler()} (for initialization),
     * {@link #canPerform()}.
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
    public PrimaryReviewEvaluationPhaseHandler() throws ConfigurationException {
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
     */
    public PrimaryReviewEvaluationPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
        primaryReviewerRoleName = PhasesHelper.getPropertyValue(namespace, "primaryReviewerRoleName", true);
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
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);

            // Check if the phase is to start:
            boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            if (toStart) {
                return PhasesHelper.canPhaseStart(phase);
            }

            // check if all dependencies are met:
            boolean dependenciesMet = PhasesHelper.arePhaseDependenciesMet(phase, false);

            Connection conn = null;
            try {
                conn = createConnection();
                // Search all "Active" submissions with contest submission type for current project
                Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper().getUploadManager(),
                    conn, phase.getProject().getId(), PhasesHelper.CONTEST_SUBMISSION_TYPE);
                // 	Locate the nearest Review  phase
                Phase reviewPhase = PhasesHelper.locatePhase(phase, PhasesHelper.SECONDARY_REVIEWER_REVIEW, false,
                                false);
                // Search the reviewIds
                Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,new String[]{PhasesHelper.SECONDARY_REVIEWER_ROLE_NAME}, reviewPhase.getId());

                // Search all review scorecard for the current phase
                Review[] reviews = PhasesHelper.searchReviewsForResources(conn, getManagerHelper(),
                    reviewers, null);
                boolean allReviewsCommitted = true;
                // Search the reviewers
    			Resource[] reviewEvaluators = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn, new String[]{primaryReviewerRoleName},phase.getId());
    			if (reviewEvaluators.length == 0) {
    				LOG.log(Level.INFO, "no reviewers for project: "
    						+ phase.getProject().getId());
    				return false;
    			}
                Review[] reviewEvaluations = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),phase.getId(), new String[]{primaryReviewerRoleName}, null);
                
                if (reviewEvaluations != null && reviewEvaluations.length > 0) {
                	for ( Review review : reviewEvaluations ){
                		allReviewsCommitted = allReviewsCommitted & review.isCommitted();
                	}
                } else {
                	LOG.log(Level.ERROR, "Can't end phase. No Evaluation reviews found" );
                    return false;        
                }
                // There will be one review evaluation per review 
                if (reviews.length != reviewEvaluations.length){
                	LOG.log(Level.ERROR, "Can't end phase. Not all reviews has been evaluated. There are "+reviews.length+" reviews and only "+reviewEvaluations.length+" evaluation completed" );
                    return false;
                }
                // return true if all dependencies are met and review evaluation is submitted:
                return dependenciesMet && allReviewsCommitted;
            } catch (SQLException sqle) {
                throw new PhaseHandlingException("Failed to search submissions.", sqle);
            } finally {
                PhasesHelper.closeConnection(conn);
            }
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
     * @throws PhaseHandlingException
     *             if there is any error occurred
     */
    public void perform(Phase phase, String operation) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operation, "operation");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE);
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();
        if (!toStart) {
        	updateScores(phase,operation,values);
        }
        sendEmail(phase, values);
    }
    
    /**
     * This method calculates final score of all submissions that passed evaluation
     * It is called from perform method when phase is stopping.
     *
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
    private void updateScores(Phase phase, String operator, Map<String, Object> values)
        throws PhaseHandlingException {
        Connection conn = null;

        try {
            conn = createConnection();

            // Search all "Active" submissions with contest submission type for current project
            Submission[] subs = PhasesHelper.searchActiveSubmissions(getManagerHelper()
                .getUploadManager(), conn, phase.getProject().getId(),
                PhasesHelper.CONTEST_SUBMISSION_TYPE);

            // Search the reviewIds
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,new String[]{PhasesHelper.SECONDARY_REVIEWER_ROLE_NAME}, phase.getId());

            // Search all review scorecard for the current phase
            Review[] reviews = PhasesHelper.searchReviewsForResources(conn, getManagerHelper(),
                reviewers, null);

            // create array to hold scores from all reviewers for all
            // submissions
            com.topcoder.management.review.scoreaggregator.Submission[] submissionScores
                = new com.topcoder.management.review.scoreaggregator.Submission[subs.length];

            // for each submission, populate scores array to use with review
            // score aggregator.
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

                // create float array
                float[] scores = new float[scoresList.size()];

                for (int iScore = 0; iScore < scores.length; iScore++) {
                    scores[iScore] = ((Float) scoresList.get(iScore)).floatValue();
                }

                submissionScores[iSub] = new com.topcoder.management.review.scoreaggregator.Submission(
                    subId, scores);
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
                    infos.put("SUBMITTER_HANDLE", PhasesHelper.notNullValue(submitt
                        .getProperty(PhasesHelper.HANDLE)));
                    infos.put("SUBMITTER_SCORE", sub.getInitialScore());
                    result.add(infos);
                }
                values.put("SUBMITTER", result);
            } catch (ResourcePersistenceException e) {
                throw new PhaseHandlingException("Problem when looking up resource for the submission.",
                    e);
            }
        } catch (SQLException e) {
            throw new PhaseHandlingException("Error retrieving submission status id", e);
        } catch (InconsistentDataException e) {
            throw new PhaseHandlingException("Problem when aggregating scores", e);
        } catch (UploadPersistenceException e) {
            throw new PhaseHandlingException("Problem when updating submission", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
