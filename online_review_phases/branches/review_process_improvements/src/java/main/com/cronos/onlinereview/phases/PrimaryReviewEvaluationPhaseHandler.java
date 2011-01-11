/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.HashMap;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.Phase;
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
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.5
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

                // search the review evaluation score card:
                Review reviewEvaluation = null;
                Review[] reviews = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                    phase.getId(), new String[]{primaryReviewerRoleName}, null);
                if (reviews != null && reviews.length > 0) {
                    reviewEvaluation = PhasesHelper.searchReviewsForResourceRoles(conn, getManagerHelper(),
                        phase.getId(), new String[]{primaryReviewerRoleName}, null)[0];
                }

                // return true if all dependencies are met and review evaluation is submitted:
                return dependenciesMet && (reviewEvaluation != null && reviewEvaluation.isCommitted());
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
        try {
            PhasesHelper.checkPhaseType(phase, PHASE_TYPE);
            PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

            sendEmail(phase, new HashMap<String, Object>());
        } catch (PhaseHandlingException ex) {
            throw PhasesHelper.logPhaseHandlingException(LOG, ex, operation, phase.getProject().getId());
        }
    }
}
