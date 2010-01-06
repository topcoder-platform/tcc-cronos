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
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a
 * phase can be executed and to add extra logic to execute a phase. It will be
 * used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection and email
 * sending. This class handle the approval phase. If the input is of other phase
 * types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The approval phase can start as soon as the dependencies are met and can stop
 * when the following conditions met:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>The approval scorecards are committed;</li>
 * <li>All approval scorecards must have passing scores.</li>
 * </ul>
 * </p>
 * <p>
 * There is no additional logic for executing this phase.
 * </p>
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 * Version 1.1 changes note:
 * <li>Adds another criteria in <code>canPerform</code> to judge whether the
 * phase can stop : At least the required number of Approver resources have
 * filled in a scorecard (use the Reviewer Number phase criteria).</li>
 * <li>Modify the method <code>perform</code> to insert another final fix/final
 * review round when the approval scorecard is rejected.</li>
 * </p>
 *
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>
 * Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager, etc).
 * </li>
 * <li>
 * Support for more information in the email generated:
 * for start, add the approver info. for stop, add the result to the value map.
 * </li>
 * </ul>
 * </p>
 *
 * @author tuenm, bose_java, argolite, waits
 * @version 1.2
 */
public class ApprovalPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default name space of this class. It is used in the
     * default constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.ApprovalPhaseHandler";

    /**
     * constant for Approval phase type.
     */
    private static final String PHASE_TYPE_APPROVAL = "Approval";

    /**
     * Create a new instance of ApprovalPhaseHandler using the default namespace
     * for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     */
    public ApprovalPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of ApprovalPhaseHandler using the given namespace
     * for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading
     *         configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public ApprovalPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check
     * the phase status to see what will be executed. This method will be called
     * by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * </p>
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: The dependencies are met.
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be
     * stopped using the following conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>The approval scorecards are committed;</li>
     * <li>All approval scorecards must have passing scores.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * <p>
     * Version 1.1 change notes: Codes modified to check whether at least the
     * required number of Approver resources have filled in a scorecard (use the
     * Reviewer Number phase criteria).
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Approval" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_APPROVAL);

        // Throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            // Return true if all dependencies have stopped and start time has
            // been reached and there are configured number of approvers
            if (!PhasesHelper.canPhaseStart(phase)) {
                return false;
            }

            Connection conn = null;

            try {

                conn = createConnection();

                Resource[] approver = PhasesHelper.searchResourcesForRoleNames(
                                getManagerHelper(), conn,
                                new String[] {"Approver"}, phase.getId());

                // Set the fallback value to 1
                int approverNum = 1;

                // Get the number of required approval (use reviewer number
                // criteria), if it's not set, fallback value is used
                if (phase.getAttribute("Reviewer Number") != null) {
                    approverNum = PhasesHelper.getIntegerAttribute(phase,
                                    "Reviewer Number");
                }

                // version 1.1 : Return true if approver number is met
                return (approver.length == approverNum);

            } finally {
                PhasesHelper.closeConnection(conn);
            }
        } else {
            return (PhasesHelper.arePhaseDependenciesMet(phase, false) && checkScorecardsCommitted(phase));
        }
    }

    /**
     * <p>
     * Provides additional logic to execute a phase. This method will be called
     * by start() and end() methods of PhaseManager implementations in Phase
     * Management component. This method can send email to a group users
     * associated with timeline notification for the project. The email can be
     * send on start phase or end phase base on configuration settings.
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * <p>
     * Version 1.1 changes note: Add logic to insert final review / final fix
     * when there is approval review rejected.
     * </p>
     *
     * <p>
     * Version 1.2 : for start, add the approver info. for stop, add the result to the value map.
     * </p>
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Approval" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty
     *         string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_APPROVAL);
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        Map<String, Object> values = new HashMap<String, Object>();

        if (toStart) {
            //find the number of approvers
            int approvers = getApproverNumbers(phase);
            // Set the fallback value to 1
            int approverNum = 1;
            // Get the number of required approval (use reviewer number
            // criteria), if it's not set, fallback value is used
            if (phase.getAttribute(PhasesHelper.REVIEWER_NUMBER_PROPERTY) != null) {
                approverNum = PhasesHelper.getIntegerAttribute(phase, PhasesHelper.REVIEWER_NUMBER_PROPERTY);
            }
            values.put("N_APPROVERS", approvers);
            values.put("N_REQUIRED_APPROVERS", approverNum);
            values.put("NEED_APPROVER", approverNum <= approvers ? 0 : 1);

        } else {
            // Check whether there is approval review rejected
            boolean rejected = checkScorecardsRejected(phase);
            values.put("RESULT", rejected ? "rejected" : "approved");

            if (rejected) {
                // approval review rejected, insert final review/final fix phases
                Connection conn = null;

                try {
                    conn = createConnection();

                    // find current phase index and also the lengths of 'final fix'
                    // and 'final review' phases.
                    Project currentPrj = phase.getProject();

                    // use helper method to insert final fix/final review phase
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
                } catch (PhaseManagementException e) {
                    throw new PhaseHandlingException(
                                    "Problem when persisting phases", e);
                } finally {
                    PhasesHelper.closeConnection(conn);
                }
            }
        }

        sendEmail(phase, values);
    }
    /**
     * Find the number of 'Approver' of the phase.
     * @param phase the current Phase
     * @return the number of approver
     * @throws PhaseHandlingException if any error occurs
     */
    private int getApproverNumbers(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            return PhasesHelper.searchResourcesForRoleNames(getManagerHelper(), conn,
                    new String[] {"Approver"}, phase.getId()).length;
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * This method checks if all approval scorecards are committed.
     *
     * @param phase the phase instance.
     *
     * @return true if all approval scorecards are committed.
     *
     * @throws PhaseHandlingException if an error occurs when retrieving data.
     * @since 1.1
     */
    private boolean checkScorecardsCommitted(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();

            // Get all approval scorecards
            Review[] approveReviews = PhasesHelper
                            .searchReviewsForResourceRoles(conn,
                                            getManagerHelper(), phase.getId(),
                                            new String[] {"Approver"}, null);

            // No review has been filled, return false
            if (approveReviews.length == 0) {
                return false;
            }

            // approverNum default to 1
            int approverNum = 1;

            if (phase.getAttribute("Reviewer Number") != null) {
                approverNum = PhasesHelper.getIntegerAttribute(phase,
                                "Reviewer Number");
            }

            // counter for committed review
            int commitedCount = 0;

            // Check approval scorecards are committed
            // also Check passing score
            for (int i = 0; i < approveReviews.length; i++) {

                // Return false is not committed
                if (!approveReviews[i].isCommitted()) {
                    return false;
                } else {
                    // counter ++
                    commitedCount++;
                }

                // No need to check score here
                // if (approveReviews[i].getScore().floatValue() < minScore) {
                // return false;
                // }
            }

            // Check whether required number of reviews are all committed

            return commitedCount >= approverNum;

        } catch (SQLException e) {
            throw new PhaseHandlingException("Problem when looking up ids.", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }

    /**
     * Checks whether whether all the approval scorecards are approved. If any
     * approval scorecard is rejected, false will be returned, return true if
     * all are approved.
     *
     * @param phase the input phase.
     * @return true if all approval scorecard are approved, false otherwise.
     * @throws PhaseHandlingException if any error occurs
     * @since 1.1
     */
    private boolean checkScorecardsRejected(Phase phase) throws PhaseHandlingException {
        Connection conn = null;
        try {
            conn = createConnection();
            Review[] approveReviews = PhasesHelper
                            .searchReviewsForResourceRoles(conn,
                                            getManagerHelper(), phase.getId(),
                                            new String[] {"Approver"}, null);

            // check for approved/rejected comments.
            boolean rejected = false;

            for (int reviewIndex = 0; reviewIndex < approveReviews.length; reviewIndex++) {

                Comment[] comments = approveReviews[reviewIndex]
                                .getAllComments();

                for (int i = 0; i < comments.length; i++) {
                    String value = (String) comments[i].getExtraInfo();

                    if (comments[i].getCommentType().getName().equals(
                                    "Approval Review Comment")) {
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
