/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import com.cronos.onlinereview.phases.BaseTest;
import com.cronos.onlinereview.phases.RegistrationPhaseHandler;

import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;


/**
 * The stress tests for this components.
 *
 * @author KLW
 * @version 1.1
 */
public class OnlineReviewStressTests extends BaseTest {
    /**
     * sets up the test environment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(PHASE_HANDLER_CONFIG_FILE);

        configManager.add(MANAGER_HELPER_CONFIG_FILE);

        // add the component configurations as well
        for (int i = 0; i < COMPONENT_FILE_NAMES.length; i++) {
            configManager.add(COMPONENT_FILE_NAMES[i]);
        }
    }

    /**
     * tears down the test environment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * stress test for this componment.
     *
     * @throws Exception
     *             if any exception occurs.
     */
    public void testOnlineReview() throws Exception {
        for (int i = 0; i < 10; i++) {
            // init the phase management component.
            PhaseManager phaseManager = new DefaultPhaseManager(PHASE_MANAGER_NAMESPACE);

            // init the phase handler class.
            PhaseHandler phaseHandler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);

            // register a phase handler for dealing with canStart() and canEnd()
            PhaseType phaseType = new PhaseType(1, "Registration");
            phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.START);
            phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.END);

            // get the phase instance.
            try {
                cleanTables();

                Project project = setupPhases();
                Phase[] phases = project.getAllPhases();
                Phase phase = phases[1];

                // canStart method will call canPerform() and perform() methods
                // of
                // the phaseHandler.
                if (phaseManager.canStart(phase)) {
                    phaseManager.start(phase, "1001");
                }

                // canEnd method will call canPerform() and perform() methods of
                // the
                // phaseHandler.
                phase.setPhaseStatus(PhaseStatus.OPEN);

                if (phaseManager.canEnd(phase)) {
                    phaseManager.end(phase, "1001");
                }
            } finally {
                cleanTables();
            }
        }
    }
}
