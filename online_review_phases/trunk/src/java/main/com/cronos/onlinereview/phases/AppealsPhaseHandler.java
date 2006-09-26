/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.topcoder.management.phase.PhaseHandlingException;

import com.topcoder.project.phases.Phase;


/**
 * <p>This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to
 * add extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an
 * input namespace. The configurable parameters include database connection and email sending. This class handles the
 * appeals phase. If the input is of other phase types, PhaseNotSupportedException will be thrown.</p>
 *  <p>The appeals phase can start as soon as the dependencies are met and can stop when the following conditions
 * met:</p>
 *  <ul>
 *      <li>The dependencies are met</li>
 *      <li>The period has passed.</li>
 *  </ul>
 *  <p>There is no additional logic for executing this phase.</p>
 *  <p>Thread safety: This class is thread safe because it is immutable.</p>
 *
 * @author tuenm, bose_java
 * @version 1.0
 */
public class AppealsPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default constructor to load
     * configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.AppealsPhaseHandler";

    /** constant for appeals phase type. */
    private static final String PHASE_TYPE_APPEALS = "Appeals";

    /**
     * Create a new instance of AppealsPhaseHandler using the default namespace for loading configuration settings.
     *
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     */
    public AppealsPhaseHandler() throws ConfigurationException {
        super(DEFAULT_NAMESPACE);
    }

    /**
     * Create a new instance of AppealsPhaseHandler using the given namespace for loading configuration settings.
     *
     * @param namespace the namespace to load configuration settings from.
     * @throws ConfigurationException if errors occurred while loading configuration settings.
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public AppealsPhaseHandler(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>Check if the input phase can be executed or not. This method will check the phase status to see what
     * will be executed. This method will be called by canStart() and canEnd() methods of PhaseManager implementations
     * in Phase Management component.</p>
     *  <p>If the input phase status is Scheduled, then it will check if the phase can be started using the
     * following conditions:</p>
     *  <ul>
     *      <li>The dependencies are met.</li>
     *  </ul>
     *  <p>If the input phase status is Open, then it will check if the phase can be stopped using the
     * following conditions:</p>
     *  <ul>
     *      <li>The dependencies are met</li>
     *      <li>The period has passed.</li>
     *  </ul>
     *  <p>If the input phase status is Closed, then PhaseHandlingException will be thrown.</p>
     *
     * @param phase The input phase to check.
     *
     * @return True if the input phase can be executed, false otherwise.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Appeals" type.
     * @throws PhaseHandlingException if there is any error while processing the phase.
     * @throws IllegalArgumentException if the input is null.
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_APPEALS);

        //will throw exception if phase status is neither "Scheduled" nor "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (toStart) {
            //return true if all dependencies have stopped and start time has been reached.
            return PhasesHelper.canPhaseStart(phase);
        } else {
            return (PhasesHelper.havePhaseDependenciesStopped(phase)
                    && PhasesHelper.reachedPhaseEndTime(phase));
        }
    }

    /**
     * Provides addtional logic to execute a phase. This method will be called by start() and end() methods of
     * PhaseManager implementations in Phase Management component. This method can send email to a group of users
     * associated with timeline notification for the project. The email can be send on start phase or end phase based
     * on configuration settings.
     *
     * @param phase The input phase to check.
     * @param operator The operator that execute the phase.
     *
     * @throws PhaseNotSupportedException if the input phase type is not "Appeals" type.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, PHASE_TYPE_APPEALS);
        PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        sendEmail(phase);
    }
}
