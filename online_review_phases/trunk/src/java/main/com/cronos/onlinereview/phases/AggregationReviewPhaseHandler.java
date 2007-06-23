/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.cronos.onlinereview.phases.logging.LogMessage;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection and email sending. This class handle the
 * aggregation review phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>The aggregation review phase can start as soon as the dependencies are met and can stop when the following
 * conditions met:</p>
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>The aggregation review is performed by two reviewers other than the aggregator, and the winning
 *      submitter.</li>
 *  </ul>
 *  <p>The additional logic for executing this phase is:</p>
 *  <ul>
 *      <li>When Aggregation Review phase is stopping, if the aggregation is rejected by anyone, another
 *      aggregation/aggregation review cycle is inserted.</li>
 *  </ul>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class AggregationReviewPhaseHandler extends AbstractPhaseHandler {
	/**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.AggregationReviewPhaseHandler";

    /** constant for aggregation Review phase type. */
    private static final String PHASE_TYPE_AGGREGATION_REVIEW = "Aggregation Review";

    /**
	 * The logger.
	 */
	private static final Log log = LogFactory.getLog(AggregationReviewPhaseHandler.class.getName());
	
    /**
     * Create a new instance of AggregationReviewPhaseHandler using the default namespace for loading configuration
     * settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public AggregationReviewPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of AggregationReviewPhaseHandler using the given namespace for loading configuration
     * settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public AggregationReviewPhaseHandler(String namespace)
        throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.</p>
     *  <p>If the input phase status is Scheduled, then it will check if the phase can be started using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>The aggregation review is performed by two reviewers other than the aggregator, and the
     *      winning submitter.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Aggregation Review" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
    	log.log(Level.DEBUG, "entering canPerform: phaseId" + phase.getId());
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_AGGREGATION_REVIEW);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        log.log(Level.DEBUG, "toStart: " + toStart);
        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            return PhasesHelper.canPhaseStart(phase);
        } else {
        	boolean met = PhasesHelper.arePhaseDependenciesMet(phase, false);
            if (!met) {
            	log.log(Level.WARN, "Can not execute Aggregation Review phase because the phase dependencies have not been met.");
            }
            boolean done = aggregationReviewDone(phase);
            if (!done) {
            	log.log(Level.WARN, "Can not execute Aggregation Review phase because the aggregation review is not done.");
            }
            return done && met;
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings. This method will perform the following additional logic: If the input phase status
     * is Scheduled, then it will do nothing. If the input phase status is Open, then it will perform the following
     * additional logic to stop the phase:
     *  <ul>
     *      <li>If the aggregation is rejected by anyone, another aggregation/aggregation review cycle is
     *      inserted.</li>
     *  </ul>
     *  If the input phase status is Closed, then PhaseHandlingException will be thrown.
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Aggregation Review" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_AGGREGATION_REVIEW);

        log.log(Level.INFO, new LogMessage(new Long(phase.getId()), operator,
        		"execute Aggregation review phase with some phase operation."));
        
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (!toStart) {
            checkAggregationReview(phase, operator);
        }

        sendEmail(phase);
    }

    /**
     * This method is called by perform method when the phase is stopping. If the aggregation is rejected by
     * anyone, another aggregation/aggregation review cycle is inserted by this method.
     *
     * @param phase phase instance.
     * @param operator operator name.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving/saving data.
     */
    private void checkAggregationReview(Phase phase, String operator)
        throws PhaseHandlingException {
        Review aggregationWorksheet = getAggregationWorksheet(phase);
        Comment[] comments = aggregationWorksheet.getAllComments();

        Connection conn = null;

        //Locate the winning submitter
        try {
            conn = createConnection();

            //check for approved/rejected comments.
            boolean rejected = false;

            for (int i = 0; i < comments.length; i++) {
                String value = (String) comments[i].getExtraInfo();
                String commentType = comments[i].getCommentType().getName();

                if (commentType.equals("Aggregation Review Comment")
                        || commentType.equals("Submitter Comment")) {
                    if ("Approved".equalsIgnoreCase(value) || "Accepted".equalsIgnoreCase(value)) {
                        continue;
                    } else if ("Rejected".equalsIgnoreCase(value)) {
                        rejected = true;

                        break;
                    } else {
                    	log.log(Level.ERROR,
                    			new LogMessage(new Long(phase.getId()), operator, "Comment can either be Approved or Rejected."));
                        throw new PhaseHandlingException("Comment can either be Approved or Rejected.");
                    }
                }
            }

            if (rejected) {
                //Extra info for Aggregation Review Comment and Submitter Comment must be cleared.
                for (int i = 0; i < comments.length; i++) {
                    String commentType = comments[i].getCommentType().getName();

                    if (commentType.equals("Aggregation Review Comment")
                            || commentType.equals("Submitter Comment")) {
                        comments[i].resetExtraInfo();
                    }
                }

                //Insert new agg/agg review cycle
                Project currentPrj = phase.getProject();

                //create phase type and status objects
                PhaseType aggPhaseType = PhasesHelper.getPhaseType(
                        getManagerHelper().getPhaseManager(), "Aggregation");
                PhaseType aggReviewPhaseType = PhasesHelper.getPhaseType(
                        getManagerHelper().getPhaseManager(), "Aggregation Review");
                PhaseStatus phaseStatus = PhasesHelper.getPhaseStatus(
                        getManagerHelper().getPhaseManager(), "Scheduled");

                //use helper method to create and save the new phases
                int currentPhaseIndex = PhasesHelper.createNewPhases(currentPrj, phase,
                        new PhaseType[] {aggPhaseType, aggReviewPhaseType}, phaseStatus,
                        getManagerHelper().getPhaseManager(), operator);

                //save the phases
                getManagerHelper().getPhaseManager().updatePhases(currentPrj, operator);

                Phase[] allPhases = currentPrj.getAllPhases();
                //get the id of the newly created aggregation phase
                long newAggPhaseId = allPhases[currentPhaseIndex + 1].getId();

                log.log(Level.INFO, new LogMessage(new Long(phase.getId()), operator,
                		"The aggregation review phase is rejected and new aggregation/agg review cycle is added."));
                log.log(Level.INFO, new LogMessage(new Long(newAggPhaseId), operator,
                		"new aggregation phase id is created and aggregator and final reviewer will be created."));
                PhasesHelper.createAggregatorOrFinalReviewer(allPhases[currentPhaseIndex - 1],
                        getManagerHelper(), conn, "Aggregator", newAggPhaseId, operator);
            }
        } catch (PhaseManagementException e) {
            throw new PhaseHandlingException("Problem when persisting phases", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if the aggregation review has been performed by two reviewers other than the
     * aggregator, and the winning submitter.
     *
     * @param phase the phase instance.
     *
     * @return true if aggregation review is done.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving data.
     */
    private boolean aggregationReviewDone(Phase phase)
        throws PhaseHandlingException {
        Comment[] comments = getAggregationWorksheet(phase).getAllComments();

        //Locate the nearest Review and Aggregation phase
        Phase reviewPhase = PhasesHelper.locatePhase(phase, "Review", false, false);
        Phase aggregationPhase = PhasesHelper.locatePhase(phase, "Aggregation", false, false);
        if (reviewPhase == null || aggregationPhase == null) {
        	log.log(Level.INFO, "Can't execute aggregation review phase: reviewPhase == null || aggregationPhase == null");
            return false;
        }
        Connection conn = null;

        try {
            conn = createConnection();

            //will hold all reviewers
            Resource[] reviewers = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    PhasesHelper.REVIEWER_ROLE_NAMES, reviewPhase.getId());
            Resource[] aggregators = PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] { "Aggregator" }, aggregationPhase.getId());
            if (aggregators.length == 0) {
            	log.log(Level.ERROR, "No Aggregator resource found for phase: " + aggregationPhase.getId());
                throw new PhaseHandlingException("No Aggregator resource found for phase: " + aggregationPhase.getId());
            }
            String aggregatorUserId = (String) aggregators[0].getProperty("External Reference ID");

            //winning submitter.
            Resource winningSubmitter = PhasesHelper.getWinningSubmitter(getManagerHelper().getResourceManager(), getManagerHelper().getProjectManager(),  
            		conn, phase.getProject().getId());

            //The reviewers that are not aggregator should have a review comment of type
            //"Aggregation Review Comment" with extra info of either "Approved" or "Rejected".
            for (int i = 0; i < reviewers.length; i++) {
                if (reviewers[i].getProperty("External Reference ID") == null ||
                    reviewers[i].getProperty("External Reference ID").equals(aggregatorUserId)) {
                    continue;
                }
                if (!doesCommentExist(comments, reviewers[i].getId(), "Aggregation Review Comment")) {
                	log.log(Level.INFO,
                			"cant't execute aggregation review phase: not exists comment type 'Aggregation Review Comment' for reviewer: "
                			+ reviewers[i].getId());
                    return false;
                }
            }
            if (doesCommentExist(comments, winningSubmitter.getId(), "Submitter Comment")) {
            	return true;
            } 
            log.log(Level.INFO, "cant't execute aggregation review phase: not exists comment type 'Submitter Comment' for reviewer: "
            		+ winningSubmitter.getId());
            return false;
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method returns the aggregation review scorecard.
     *
     * @param phase phase instance.
     *
     * @return aggregation review scorecard.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving data.
     */
    private Review getAggregationWorksheet(Phase phase) throws PhaseHandlingException {
        //Locate the nearest backward Aggregation phase
        Phase aggPhase = PhasesHelper.locatePhase(phase, "Aggregation", false, true);
        Connection conn = null;

        try {
            conn = createConnection();
            //Search the aggregated review scorecard
            Review aggWorksheet = PhasesHelper.getAggregationWorksheet(conn, getManagerHelper(), aggPhase.getId());

            if (aggWorksheet == null) {
            	log.log(Level.ERROR,
            			new LogMessage(new Long(phase.getId()), null, "aggregation worksheet does not exist."));
                throw new PhaseHandlingException("aggregation worksheet does not exist.");
            }
            return aggWorksheet;
        } catch (SQLException e) {
        	log.log(Level.ERROR, new LogMessage(new Long(phase.getId()), null,
        			"Fail to retrieve the aggregation worksheet for review."));
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * Returns true if the specified resourceId is the author of atleast one comment in the given array.
     *
     * @param comments comments array.
     * @param resourceId resource id.
     *
     * @return true if the specified resourceId is the author of atleast one comment in the given array.
     */
    private boolean doesCommentExist(Comment[] comments, long resourceId, String commentType) {
        for (int i = 0; i < comments.length; i++) {
            Comment comment = comments[i];

            //the comment should have "Approved" or "Rejected" extra info
            String extraInfo = (String) comment.getExtraInfo();
            if (comment.getCommentType().getName().equals(commentType)
                    && ("Approved".equalsIgnoreCase(extraInfo)
                            || "Accepted".equalsIgnoreCase(extraInfo)
                            || "Rejected".equalsIgnoreCase(extraInfo))) {
                if (comment.getAuthor() == resourceId) {
                    return true;
                }
            }
        }

        return false;
    }
}
