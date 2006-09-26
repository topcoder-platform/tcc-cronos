/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.util.Date;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * All tests for FinalReviewPhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class FinalReviewPhaseHandlerTest extends BaseTest {

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
     * Tests canPerform(Phase) with null phase.
     *
     * @throws Exception not under test.
     */
    public void testCanPerform() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            handler.canPerform(null);
            fail("canPerform() did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase status.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithInvalidStatus() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 3, "Final Review");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            //expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase type.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithInvalidType() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "INVALID");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            //expected.
        }
    }

    /**
     * Tests perform(Phase) with null phase.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithNullPhase() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            handler.perform(null, "operator");
            fail("perform() did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests perform(Phase) with invalid phase status.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithInvalidStatus() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Final Review");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            //expected.
        }
    }

    /**
     * Tests perform(Phase) with invalid phase type.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithInvalidType() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "INVALID");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            //expected.
        }
    }

    /**
     * Tests perform(Phase) with null operator.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithNullOperator() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Final Review");
            handler.perform(phase, null);
            fail("perform() did not throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests perform(Phase) with empty operator.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithEmptyOperator() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Final Review");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests the FinalReviewPhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase finalReviewPhase = phases[9];
	
	        //test with scheduled status.
	        finalReviewPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
	
	        //time has not passed, nor dependencies met
	        assertFalse("canPerform should have returned false", handler.canPerform(finalReviewPhase));
	
	        //time has passed, but dependency not met.
	        finalReviewPhase.setActualStartDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(finalReviewPhase));
	
	        //time has passed and dependency met.
	        finalReviewPhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        assertTrue("canPerform should have returned true", handler.canPerform(finalReviewPhase));
        } finally {
        	cleanTables();
        }
    }


    /**
     * Tests the FinalReviewPhaseHandler() constructor and canPerform with Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase finalReviewPhase = phases[9];
	
	        //test with open status.
	        finalReviewPhase.setPhaseStatus(PhaseStatus.OPEN);
	
	        //time has not passed, dependencies not met
	        assertFalse("canPerform should have returned false", handler.canPerform(finalReviewPhase));
        } finally {
        	cleanTables();
        }
    }

    /**
     * Tests the perform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerform() throws Exception {
        FinalReviewPhaseHandler handler = new FinalReviewPhaseHandler(PHASE_HANDLER_NAMESPACE);

        //test with scheduled status.
        Phase finalReviewPhase = createPhase(1, 1, "Scheduled", 2, "Final Review");
        String operator = "operator";
        handler.perform(finalReviewPhase, operator);
    }
}
