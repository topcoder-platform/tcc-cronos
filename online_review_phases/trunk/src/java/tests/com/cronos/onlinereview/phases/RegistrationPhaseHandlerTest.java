/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.Date;

import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * All test cases for RegistrationPhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class RegistrationPhaseHandlerTest extends BaseTest {

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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Registration");
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Registration");
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Registration");
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
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Registration");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests the RegistrationPhaseHandler() constructor and canPerform with Scheduled and Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testRegistrationPhaseHandler() throws Exception {
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase registration = phases[0];
	
	        //test with scheduled status.
	        registration.setPhaseStatus(PhaseStatus.SCHEDULED);
	        assertTrue("can start should return true", handler.canPerform(registration));
	
	        //test with open status, time has not passed.
	        registration.setPhaseStatus(PhaseStatus.OPEN);
	        assertFalse("can stop should return false", handler.canPerform(registration));
	
	        //time has passed, but no registrants
	        registration.setActualEndDate(new Date());
	        registration.setAttribute("Registration Number", "1");
	        assertFalse("can stop should return false", handler.canPerform(registration));
	
	        //time has passed, and enough registrants.
            Connection conn = getConnection();
            Resource resource = super.createResource(1, 101, 1, 1);
            super.insertResources(conn, new Resource[] {resource});
            assertTrue("can stop should return true", handler.canPerform(registration));
        } finally {
            closeConnection();
        	cleanTables();
        }
    }

    /**
     * Tests the RegistrationPhaseHandler() constructor and perform with Scheduled and Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerform() throws Exception {
        RegistrationPhaseHandler handler = new RegistrationPhaseHandler(PHASE_HANDLER_NAMESPACE);

        //test with scheduled status.
        Phase registration = createPhase(1, 1, "Scheduled", 1, "Registration");
        String operator = "operator";
        handler.perform(registration, operator);

        //test with open status
        registration.setPhaseStatus(PhaseStatus.OPEN);
        handler.perform(registration, operator);
    }

}
