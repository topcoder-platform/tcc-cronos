/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a
 * phase can be executed and to add extra logic to execute a phase. It will be
 * used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection, email
 * sending. This class handle the final review phase. If the input is of other
 * phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The final review phase can start as soon as the dependencies are met and can
 * stop when the following conditions met: the dependencies are met and the
 * final review is committed by the final reviewer.
 * </p>
 * <p>
 * The additional logic for executing this phase is: when Final Review phase is
 * stopping, if the final review is rejected, another final fix/review cycle is
 * inserted.
 * </p>
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 * version 1.1 change notes: Add an approval phase when the final review is
 * approved. The logic is modified in method <code>checkFinalReview</code>
 * </p>
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>
 * Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager, etc).
 * </li>
 * <li>
 * Support for more information in the email generated:
 * for start, find the number of final reviewers. for stop, add the result..
 * </li>
 * </ul>
 * </p>
 *
 * @author tuenm, bose_java, argolite, waits
 * @version 1.2
 */
public class FinalReviewPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default
     * constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.FinalReviewPhaseHandler";

    /** constant for final review phase type. */
    private static final String PHASE_TYPE_FINAL_REVIEW = "Final Review";

    /**
     * Create a new instance of FinalReviewPhaseHandler using the default
     * namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     */
    public FinalReviewPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of FinalReviewPhaseHandler using the given
     * namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public FinalReviewPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Check if the input phase can be executed or not. This method will check
     * the phase status to see what will be executed. This method will be called
     * by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: the dependencies are met
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be
     * stopped using the following conditions: The dependencies are met and the
     * final review is committed by the final reviewer.
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Final Review" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_FINAL_REVIEW);

        // will throw exception if phase status is neither "Scheduled" nor
        // "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            // return true if all dependencies have stopped and start time has
            // been reached
            return PhasesHelper.canPhaseStart(phase);
        } else {
            return (PhasesHelper.arePhaseDependenciesMet(phase, false) && isFinalWorksheetCommitted(phase));
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called
     * by start() and end() methods of PhaseManager implementations in Phase
     * Management component. This method can send email to a group os users
     * associated with timeline notification for the project. The email can be
     * send on start phase or end phase base on configuration settings.
     * <p>
     * If the input phase status is Scheduled, then it will do nothing.
     * </p>
     * <p>
     * If the input phase status is Open, then it will perform the following
     * additional logic to stop the phase: if the final review is rejected,
     * another final fix/review cycle is inserted.
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     * <p>
     * Version 1.2: for start, find the number of final reviewers.
     *              for stop, add the result.
     * </p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Final Review" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty
     *         string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_FINAL_REVIEW);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        Map<String, Object> values = new HashMap<String, Object>();
        if (toStart) {
            //for start, put the final reviewer number
            values.put("N_FINAL_REVIEWERS", getFinalReviewerNumber(phase));
        } else {
            // checkFinalReview is changed in version 1.1 to add
            // an approval phase after final review is approved
            values.put("RESULT", checkFinalReview(phase, operator) ? "rejected" : "approved");
        }

        sendEmail(phase, values);
    }
    /**
     * <p>
     * Get the final reviewer number of the project.
     * </p>
     * @param phase the current Phase
     * @return the number of final reviewer
     * @throws PhaseHandlingException if any error occurs
     */
    private int getFinalReviewerNumber(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            return PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                                                           new String[] {"Final Reviewer"}, phase.getId()).length;
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if final worksheet exists and has been committed.
     *
     * @param phase phase to check.
     *
     * @return true if final worksheet exists and has been committed, false
     *         otherwise.
     *
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     */
    private boolean isFinalWorksheetCommitted(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            Review finalWorksheet = PhasesHelper.getFinalReviewWorksheet(conn,
                            getManagerHelper(), phase.getId());
            return ((finalWorksheet != null) && finalWorksheet.isCommitted());
        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method is called from perform method when the phase is stopping. It
     * checks if the final review is rejected, and inserts another final
     * fix/review cycle.
     *
     * <p>
     * Version 1.1 change notes: Add an approval phase if the final review is
     * approved.
     * </p>
     *
     * <p>
     * Version 1.2: add the return value.
     * </p>
     *
     * @param phase phase instance.
     * @param operator operator name
     * @return if pass the final review of not
     *
     * @throws PhaseHandlingException if an error occurs when retrieving/saving
     *         data.
     *
     * @version 1.2
     */
    private boolean checkFinalReview(Phase phase, String operator) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            Review finalWorksheet = PhasesHelper.getFinalReviewWorksheet(conn,
                            getManagerHelper(), phase.getId());

            // check for approved/rejected comments.
            Comment[] comments = finalWorksheet.getAllComments();
            boolean rejected = false;

            for (int i = 0; i < comments.length; i++) {
                String value = (String) comments[i].getExtraInfo();

                if (comments[i].getCommentType().getName().equals(
                                "Final Review Comment")) {
                    if ("Approved".equalsIgnoreCase(value)
                                    || "Accepted".equalsIgnoreCase(value)) {
                        continue;
                    } else if ("Rejected".equalsIgnoreCase(value)) {
                        rejected = true;

                        break;
                    } else {
                        throw new PhaseHandlingException(
                                        "Comment can either be Approved or Rejected.");
                    }
                }
            }

            if (rejected) {
                // Extra info for Final Review Comment must be cleared.
                for (int i = 0; i < comments.length; i++) {

                    if (comments[i].getCommentType().getName().equals(
                                    "Final Review Comment")) {
                        comments[i].resetExtraInfo();
                    }
                }

                Project currentPrj = phase.getProject();

                // use helper method to insert final fix/final review phases
                int currentPhaseIndex = PhasesHelper
                                .insertFinalFixAndFinalReview(
                                                phase,
                                                getManagerHelper()
                                                                .getPhaseManager(),
                                                operator);

                // get the id of the newly created final review phase
                long finalReviewPhaseId = currentPrj.getAllPhases()[currentPhaseIndex + 2]
                                .getId();

                PhasesHelper.createAggregatorOrFinalReviewer(phase,
                                getManagerHelper(), conn, "Final Reviewer",
                                finalReviewPhaseId, operator);
            } else {
                // Newly added in version 1.1
                // the final review is approved, add approval phase
                PhaseType approvalPhaseType = PhasesHelper.getPhaseType(
                                getManagerHelper().getPhaseManager(),
                                "Approval");
                PhaseStatus phaseStatus = PhasesHelper.getPhaseStatus(
                                getManagerHelper().getPhaseManager(),
                                "Scheduled");

                // use helper method to create and save the new phases
                PhasesHelper.createNewPhases(phase.getProject(), phase,
                                new PhaseType[] {approvalPhaseType},
                                phaseStatus, getManagerHelper()
                                                .getPhaseManager(), operator);

                getManagerHelper().getPhaseManager().updatePhases(
                                phase.getProject(), operator);
            }
            return rejected;

        } catch (SQLException e) {
            throw new PhaseHandlingException(
                            "Problem when connecting to database", e);
        } catch (PhaseManagementException e) {
            throw new PhaseHandlingException("Problem when persisting phases",
                            e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
