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
 * All tests for AppealsPhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class AppealsPhaseHandlerTest extends BaseTest {

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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 3, "Appeals");
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Appeals");
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Appeals");
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
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Appeals");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests the AppealsPhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsPhase = phases[4];
	
	        //test with scheduled status.
	        appealsPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
	
	        //time has not passed, nor dependencies met
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsPhase));
	
	        //time has passed, but dependency not met.
	        appealsPhase.setActualStartDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsPhase));
	
	        //time has passed and dependency met.
	        appealsPhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        assertTrue("canPerform should have returned true", handler.canPerform(appealsPhase));
        } finally {
        	cleanTables();
        }
    }


    /**
     * Tests the AppealsPhaseHandler() constructor and canPerform with Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen() throws Exception {
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsPhase = phases[4];
	
	        //test with open status.
	        appealsPhase.setPhaseStatus(PhaseStatus.OPEN);
	    	
	        //change dependency type to F2F
	        appealsPhase.getAllDependencies()[0].setDependentStart(false);
	
	        //time has not passed, dependencies not met
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsPhase));
	
	        //time has passed, but dependency not met.
	        appealsPhase.setActualStartDate(new Date(System.currentTimeMillis() - 1000));
	        appealsPhase.setActualEndDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsPhase));
	
	        //time has passed and dependency met
	        appealsPhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        assertTrue("canPerform should have returned true", handler.canPerform(appealsPhase));
        } finally {
        	cleanTables();
        }
    }

    /**
     * Tests the perform with Scheduled and Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerform() throws Exception {
        AppealsPhaseHandler handler = new AppealsPhaseHandler(PHASE_HANDLER_NAMESPACE);

        //test with scheduled status.
        Phase appealsPhase = createPhase(1, 1, "Scheduled", 2, "Appeals");
        String operator = "operator";
        handler.perform(appealsPhase, operator);

        //test with open status
        appealsPhase.setPhaseStatus(PhaseStatus.OPEN);
        handler.perform(appealsPhase, operator);
    }
}
