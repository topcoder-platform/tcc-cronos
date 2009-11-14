/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a
 * phase can be executed and to add extra logic to execute a phase. It will be
 * used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection and email
 * sending. This class handle the Post-Mortem phase. If the input is of other
 * phase types, PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The Post-Mortem phase can start as soon as the dependencies are met and can
 * stop when the following conditions met: - The dependencies are met; - The
 * post-mortem scorecards are committed. - At least the required number of
 * Post-Mortem Reviewer resources have filled in a scorecard
 * </p>
 *
 * <p>
 * There is no additional logic for executing this phase.
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class PostMortemPhaseHandler extends AbstractPhaseHandler {

    /**
     * The default namespace of PostMortemPhaseHandler.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.PostMortemPhaseHandler";
    
    /**
     * Constant for post-mortem reviewer role name.
     */
    private static final String POST_MORTEM_REVIEWER_ROLE_NAME = "Post-Mortem Reviewer";

    /**
     * The name for the post-mortem phase.
     */
    private static final String PHASE_TYPE_POST_MORTEM = "Post-Mortem";

    /**
     * Creates a PostMortemPhaseHandler with default namespace.
     *
     * @throws ConfigurationException if there is configuration error.
     */
    public PostMortemPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Creates a PostMortemPhaseHandler with the given namespace.
     *
     * @param namespace the namespace of PostMortemPhaseHandler.
     * @throws ConfigurationException if there is configuration error.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public PostMortemPhaseHandler(String namespace)
                    throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * Check if the input phase can be executed or not. This method will check
     * the phase status to see what will be executed. This method will be called
     * by canStart() and canEnd() methods of PhaseManager implementations in
     * Phase Management component.
     * </p>
     *
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase
     * can be started using the following conditions: The dependencies are met.
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be
     * stopped using the following conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>All post-mortem scorecards are committed.</li>
     * <li>At least the required number of Post-Mortem Reviewer resources have
     * filled in a scorecard (use the Reviewer Number phase criteria).</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be
     * thrown.
     * </p>
     *
     * @param phase The input phase to check.
     * @return True if the input phase can be executed, false otherwise.
     * @throws PhaseNotSupportedException if the input phase type is not
     *         "Post-Mortem Review" type.
     * @throws PhaseHandlingException if there is any error occurred while
     *         processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_POST_MORTEM);

        // will throw exception if phase status is
        // neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            // return true if all dependencies have stopped
            // and start time has been reached.
            return PhasesHelper.canPhaseStart(phase);

        } else {

            // Check phase dependencies
            boolean depsMeet = PhasesHelper.arePhaseDependenciesMet(phase,
                            false);

            // Return true is dependencies are met and reviews committed.
            return depsMeet && allPostMortemReviewsDone(phase);
        }
    }

    /**
     * Provides additional logic to execute a phase. Current implementation does
     * nothing.
     *
     * @param phase the input phase.
     * @param operator the operator name.
     * @throws PhaseHandlingException if there is any error occurs.
     */
    public void perform(Phase phase, String operator)
                    throws PhaseHandlingException {
        // perform parameters checking
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_POST_MORTEM);
        PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        // do nothing
    }

    /**
     * Checks whether all post-mortem reviews are committed, and whether the
     * reviews number meets the requirement.
     *
     * @param phase the phase to check.
     * @return true if all post-mortem reviews are committed and meet
     *         requirement, false otherwise.
     * @throws PhaseHandlingException if there was an error retrieving data.
     */
    private boolean allPostMortemReviewsDone(Phase phase)
                    throws PhaseHandlingException {
        Connection conn = null;

        try {
            // Search all "Active" submissions for current project
            conn = createConnection();

            // Search all post-mortem review scorecard for the current phase
            Review[] reviews = PhasesHelper
                            .searchReviewsForResourceRoles(
                                            conn,
                                            getManagerHelper(),
                                            phase.getId(),
                                            new String[] {POST_MORTEM_REVIEWER_ROLE_NAME},
                                            null);

            // Check whether all the reviews are committed
            for (int i = 0; i < reviews.length; ++i) {
                if (!reviews[i].isCommitted()) {
                    // not committed, return false
                    return false;
                }
            }

            if (phase.getAttribute("Reviewer Number") != null) {
                int reviewerNum = PhasesHelper.getIntegerAttribute(phase,
                                "Reviewer Number");

                if (reviews.length < reviewerNum) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            throw new PhaseHandlingException(
                            "Error retrieving data from persistence", e);
        } finally {
            PhasesHelper.closeConnection(conn);
        }
    }
}
