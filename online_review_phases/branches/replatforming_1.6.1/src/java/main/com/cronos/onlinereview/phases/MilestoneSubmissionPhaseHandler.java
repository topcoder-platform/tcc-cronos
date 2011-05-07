/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.phase.OperationCheckResult;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add
 * extra logic to execute a phase. It will be used by <b>Phase Management</b> component. It is configurable using
 * an input namespace. The configurable parameters include database connection, email sending and the required
 * number of submissions that pass screening. This class handle the milestone submission phase. If the input is of
 * other phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The milestone submission phase can be started using when the following conditions:
 * <ul>
 * <li>the dependencies are met</li>
 * <li>Its start time is reached.</li>
 * </ul>
 * </p>
 * <p>
 * The milestone submission phase can stop when the following conditions met:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>The phase's end time is reached.</li>
 * <li>If there are no milestone submissions or if manually milestone screening is required, the number of
 * milestone submissions that have passed manual milestone screening meets the required number.</li>
 * </ul>
 * </p>
 * <p>
 * Note that milestone screening phase can be started during a milestone submission phase.
 * </p>
 * <p>
 * There is no additional logic for executing this phase.
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
 * @author FireIce, saarixx, TCSDEVELOPER, microsky
 * @version 1.6.1
 * @since 1.6
 */
public class MilestoneSubmissionPhaseHandler extends AbstractPhaseHandler {
    /**
     * <p>
     * Represents the default namespace of this class. It is used in the default constructor to load configuration
     * settings.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.MilestoneSubmissionPhaseHandler";

    /**
     * <p>
     * constant for milestone submission phase type.
     * </p>
     */
    private static final String PHASE_TYPE_MILESTONE_SUBMISSION = "Milestone Submission";

    /**
     * Represents the logger for this class. Is initialized during class loading and never changed after that.
     */
    private static final Log LOG = LogFactory.getLog(MilestoneSubmissionPhaseHandler.class.getName());

    /**
     * <p>
     * Create a new instance of MilestoneSubmissionPhaseHandler using the default namespace for loading
     * configuration settings.
     * </p>
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings.
     */
    public MilestoneSubmissionPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Create a new instance of MilestoneSubmissionPhaseHandler using the given namespace for loading configuration
     * settings.
     * </p>
     * @param namespace
     *            the namespace to load configuration settings from.
     * @throws ConfigurationException
     *             if errors occurred while loading configuration settings or required properties missing.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public MilestoneSubmissionPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check the phase status to see what will be
     * executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * </p>
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions:
     * <ul>
     * <li>the dependencies are met</li>
     * <li>Its start time is reached.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>The phase's end time is reached.</li>
     * <li>If there are no milestone submissions or if manually milestone screening is required, the number of
     * milestone submissions that have passed manual milestone screening meets the required number.</li>
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
     *             if the input phase type is not &quot;Milestone Submission&quot; type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input is null.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_SUBMISSION);

        // will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            return PhasesHelper.checkPhaseCanStart(phase);
        } else {
            Connection conn = null;
            try {
                conn = createConnection();
                OperationCheckResult result = PhasesHelper.checkPhaseDependenciesMet(phase, false);
                if (!result.isSuccess()) {
                    return result;
                }
                if (!PhasesHelper.reachedPhaseEndTime(phase)) {
                    return new OperationCheckResult("Phase end time is not yet reached");
                }

                if (!hasAnySubmission(conn, phase, null) || arePassedSubmissionsEnough(
                    conn, phase)) {
                    return OperationCheckResult.SUCCESS;
                }
                return new OperationCheckResult("Not enough milestone submissions for the project");
            } finally {
                PhasesHelper.closeConnection(conn);
            }
        }
    }

    /**
     * Provides additional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group of users
     * associated with timeline notification for the project. The email can be send on start phase or end phase
     * base on
     * configuration settings.
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not &quot;Milestone Submission&quot; type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_MILESTONE_SUBMISSION);

        // will throw exception if phase status is neither "Scheduled" nor "Open"
        PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        Map<String, Object> values = new HashMap<String, Object>();

        Connection conn = null;
        try {
            conn = createConnection();

            // retrieve the submissions information for sending mail.
            hasAnySubmission(conn, phase, values);
        } finally {
            PhasesHelper.closeConnection(conn);
        }

        sendEmail(phase, values);
    }

    /**
     * <p>
     * This method checks whether there is any milestone submission in this phase.
     * </p>
     * @param phase
     *            the phase to check.
     * @param values
     *            the values map to hold the information for email generation
     * @param conn
     *            the database connection
     * @return true if there is at least one submission, false otherwise.
     * @throws PhaseHandlingException
     *             if any error occurs during processing.
     */
    private boolean hasAnySubmission(Connection conn, Phase phase, Map<String, Object> values)
        throws PhaseHandlingException {
        Submission[] subs = PhasesHelper.searchActiveMilestoneSubmissions(getManagerHelper().getUploadManager(),
            conn,
                phase.getProject().getId(), phase.getId(), LOG);

        // for stop phase, we are going to support more information.
        if (values != null) {
            values.put("N_SUBMITTERS", subs.length);
            values.put("SUBMITTER", PhasesHelper.constructSubmitterValues(subs,
                    getManagerHelper().getResourceManager(), false));
        }
        return subs.length > 0;
    }

    /**
     * <p>
     * Returns true if the number of submissions that passed screenings meets the required number.
     * </p>
     * @param conn
     *            the Connection instance
     * @param phase
     *            phase to check.
     * @return true if the number of submissions that passed screenings meets the required number.
     * @throws PhaseHandlingException
     *             in case of any error.
     */
    private boolean arePassedSubmissionsEnough(Connection conn, Phase phase) throws PhaseHandlingException {
        if (PhasesHelper.isScreeningManual(phase)) {
            if (phase.getAttribute("Submission Number") == null) {
                return true;
            }

            int submissionNum = PhasesHelper.getIntegerAttribute(phase, "Submission Number");
            int passedNum = 0;

            // get the next milestone screening phase
            Phase milestoneScreeningPhase = PhasesHelper.locatePhase(phase, "Milestone Screening", true, false);

            // if the milestone screening phase does not exist, simply return true.
            if (milestoneScreeningPhase == null) {
                LOG.log(Level.INFO, "The next milestone screening phase does not exist");
                return true;
            }
            long milestoneScreeningPhaseId = milestoneScreeningPhase.getId();

            // get reviews for the phase
            Review[] milestoneScreenReviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    milestoneScreeningPhaseId, new String[] {"Milestone Screener" }, null);
            if (milestoneScreenReviews.length == 0) {
                LOG.log(Level.INFO, "There is no milestone screening review.");
                return false;
            }
            float minScore = PhasesHelper.getScorecardMinimumScore(getManagerHelper().getScorecardManager(),
                    milestoneScreenReviews[0]);
            for (int i = 0; i < milestoneScreenReviews.length; i++) {
                if (milestoneScreenReviews[i].getScore().floatValue() >= minScore) {
                    passedNum++;
                }
            }

            return passedNum >= submissionNum;
        }

        // manually screening is not required.
        return true;
    }

}