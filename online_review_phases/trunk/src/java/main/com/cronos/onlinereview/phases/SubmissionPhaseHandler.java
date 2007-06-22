/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;

import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.phases.logging.LogMessage;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection, email sending and the required number of
 * submissions that pass screening. This class handle the submission phase. If the input is of other phase types,
 * PhaseNotSupportedException will be thrown.</p>
 *  <p>The submission phase can start as soon as the dependencies are met and start time is reached, and can stop
 * when the following conditions met:
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>The phase's end time is reached.</li>
 *      <li>If manual screening is absent, the number of submissions that have passed auto-screening meets the
 *      required number;</li>
 *      <li>If manual screening is required, the number of submissions that have passed manual screening
 *      meets the required number.</li>
 *  </ul>
 *  </p>
 *  <p>Note that screening phase can be started during a submission phase.</p>
 *  <p>There is no additional logic for executing this phase.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class SubmissionPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.SubmissionPhaseHandler";

    /** constant for appeals phase type. */
    private static final String PHASE_TYPE_SUBMISSION = "Submission";

    /**
     * The logger instance.
     */
    private static final Log logger = LogFactory.getLog(SubmissionPhaseHandler.class.getName());
    
    /**
     * Create a new instance of SubmissionPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public SubmissionPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of SubmissionPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings or required properties
     * missing.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public SubmissionPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.<p>If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: the dependencies are met and Its start time is reached.</p>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>The phase's end time is reached.</li>
     *      <li>If manual screening is absent, the number of submissions that have passed auto-screening
     *      meets the required number;</li>
     *      <li>If manual screening is required, the number of submissions that have passed manual
     *      screening meets the required number.</li>
     *  </ul>
     *  </p>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Submission" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SUBMISSION);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            return PhasesHelper.canPhaseStart(phase);
        } else {
            boolean met = PhasesHelper.arePhaseDependenciesMet(phase, false);
            if (!met) {
            	logger.log(Level.WARN, "Can not execute submission phase because the phase dependencies have not been met.");
            }
            boolean reached = PhasesHelper.reachedPhaseEndTime(phase);
            if (!reached) {
            	logger.log(Level.WARN, "Can not execute submission phase because the phase end time is not reached.");
            }
            boolean enough = arePassedSubmissionsEnough(phase);
            if (!enough) {
            	logger.log(Level.WARN, "Can not execute submission phase because there is not enough passed submissions.");
            }
            return met&&reached&&enough;
        }
    }

    /**
     * Provides additional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be send on start phase or end phase base
     * on configuration settings.<p>If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.</p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Submission" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_SUBMISSION);
        PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        logger.log(Level.INFO, new LogMessage(new Long(phase.getId()), operator, 
        		"execute submission phase with some phase operation."));
        sendEmail(phase);
    }

    /**
     * This method returns true if the number of submissions that passed screenings meets the
     * required number.
     *
     * @param phase phase to check.
     *
     * @return true if the number of submissions that passed screenings meets the required number.
     * @throws PhaseHandlingException in case of any error.
     */
    private boolean arePassedSubmissionsEnough(Phase phase) throws PhaseHandlingException {
        boolean bManual = PhasesHelper.isScreeningManual(phase);

        if (phase.getAttribute("Submission Number") == null) {
            return true;
        }

        int submissionNum = PhasesHelper.getIntegerAttribute(phase, "Submission Number");
        int passedNum = 0;

        if (bManual) {
            passedNum = getManualScreeningPasses(phase);
            logger.log(Level.INFO,
            		passedNum + " submissions pass the manual screening phase .");
        } else {
            passedNum = getAutoScreeningPasses(phase);
            logger.log(Level.INFO,
            		passedNum + " submissions pass the auto screening phase .");
        }
        return (passedNum >= submissionNum);
    }

    /**
     * Returns the number of submissions which passed manual screening.
     *
     * @param phase phase instance.
     *
     * @return the number of submissions which passed manual screening.
     *
     * @throws PhaseHandlingException in case of retrieval error.
     */
    private int getManualScreeningPasses(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();

            //get the next screening phase
            Phase screeningPhase = PhasesHelper.locatePhase(phase, "Screening", true, false);
            if (screeningPhase == null) {
                return 0;
            }
            long screeningPhaseId = screeningPhase.getId();

            //get reviews for the phase
            Review[] screenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    screeningPhaseId, new String[] { "Primary Screener", "Screener" }, null);
            if (screenReviews.length == 0) {
                return 0;
            }
            float minScore = PhasesHelper.getScorecardMinimumScore(getManagerHelper().getScorecardManager(),
                    screenReviews[0]);
            int passedNum = 0;

            for (int i = 0; i < screenReviews.length; i++) {
                if (screenReviews[i].getScore().floatValue() >= minScore) {
                    passedNum++;
                }
            }

            return passedNum;
        } catch (SQLException e) {
        	logger.log(Level.ERROR, new LogMessage(new Long(phase.getId()), null,
        			"Fail to get mannual screening phases passed number.", e));
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * Returns the number of submissions which passed automatic screening.
     *
     * @param phase phase instance.
     *
     * @return the number of submissions which passed automatic screening.
     *
     * @throws PhaseHandlingException in case of retrieval error.
     */
    private int getAutoScreeningPasses(Phase phase) throws PhaseHandlingException {
        ScreeningTask[] screeningTasks = PhasesHelper.getScreeningTasks(getManagerHelper(), phase);
        int passedNum = 0;

        for (int i = 0; i < screeningTasks.length; i++) {
            String status = screeningTasks[i].getScreeningStatus().getName();

            if ("Passed".equals(status) || "Passed with Warning".equals(status)) {
                passedNum++;
            }
        }

        return passedNum;
    }
}
