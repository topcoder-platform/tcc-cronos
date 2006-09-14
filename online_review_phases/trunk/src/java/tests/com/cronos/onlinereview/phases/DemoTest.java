/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

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
 * Shows a demo of how to use this component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends BaseTest {

    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager configManager = ConfigManager.getInstance();

        configManager.add(PHASE_HANDLER_CONFIG_FILE);

        configManager.add(MANAGER_HELPER_CONFIG_FILE);

        //add the component configurations as well
        for (int i = 0; i < COMPONENT_FILE_NAMES.length; i++) {
            configManager.add(COMPONENT_FILE_NAMES[i]);
        }

    }

    /**
     * cleans up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This method shows a demo of how to use the RegistrationPhaseHandler with Phase Management component.<br/>
     * Use of other Phase Handlers is similar to the Registration Phase Handler as the same set of APIs are
     * to be used in those cases as well.
     *
     * @throws Exception not under test.
     */
    public void testDemo() throws Exception {
        //init the phase management component.
        PhaseManager phaseManager = new DefaultPhaseManager(PHASE_MANAGER_NAMESPACE);

        //init the phase handler class.
        PhaseHandler phaseHandler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);

        // register a phase handler for dealing with canStart() and canEnd()
        PhaseType phaseType = new PhaseType(1, "Registration");
        phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.START);
        phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.END);

        //get the phase instance.
        try {
        	cleanTables();
	        Project project = setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase phase = phases[0];
	
	        //canStart method will call canPerform() and perform() methods of the phaseHandler.
	        if (phaseManager.canStart(phase)) {
	            phaseManager.start(phase, "ivern");
	        }
	
	        //canEnd method will call canPerform() and perform() methods of the phaseHandler.
	        phase.setPhaseStatus(PhaseStatus.OPEN);
	        if (phaseManager.canEnd(phase)) {
	            phaseManager.end(phase, "ivern");
	        }
        } finally {
        	cleanTables();
        }
    }
}
