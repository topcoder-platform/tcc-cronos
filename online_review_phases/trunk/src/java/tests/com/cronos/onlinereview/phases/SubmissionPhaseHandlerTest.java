/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.Date;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * All tests for SubmissionPhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class SubmissionPhaseHandlerTest extends BaseTest {

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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 2, "Submission");
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Submission");
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Submission");
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
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Submission");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests the SubmissionPhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase submission = phases[1];
	
	        //test with scheduled status.
	        submission.setPhaseStatus(PhaseStatus.SCHEDULED);
	
	        //time has not passed, nor dependencies met
	        assertFalse("canPerform should have returned false", handler.canPerform(submission));
	
	        //time has passed, but dependency not met.
	        submission.setActualStartDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(submission));
	
	        //time has passed and dependency met.
	        submission.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        assertTrue("canPerform should have returned true", handler.canPerform(submission));
        } finally {
        	cleanTables();
        }
    }


    /**
     * Tests the SubmissionPhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen() throws Exception {
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase submissionPhase = phases[1];
	
	        //test with open status.
	        submissionPhase.setPhaseStatus(PhaseStatus.OPEN);
	
	        //time has not passed, nor dependencies met
	        assertFalse("canPerform should have returned false", handler.canPerform(submissionPhase));
	
	        //time has passed, but dependency not met.
	        submissionPhase.setActualStartDate(new Date(System.currentTimeMillis() - 1000));
	        submissionPhase.setActualEndDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(submissionPhase));
	
	        //time has passed and dependency met, reviews passed.
	        submissionPhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        submissionPhase.setAttribute("Manual Screening", "Yes");
	        submissionPhase.setAttribute("Submission Number", "1");

            Connection conn = getConnection();

            Phase screeningPhase = phases[2];
            long screeningPhaseId = screeningPhase.getId();
            Resource submitter = super.createResource(1, submissionPhase.getId(), 1, 1);
            Resource reviewer1 = super.createResource(2, screeningPhaseId, 1, 2);
            Resource reviewer2 = super.createResource(3, screeningPhaseId, 1, 3);

            Upload upload = createUpload(1, 1, submitter.getId(), 1, 1, "parameter");
            Submission submission = createSubmission(1, 1, 1);
            Scorecard scorecard = createScorecard(1, 1, 1, 1, "name", "1.0", 75.0f, 100.0f);
            Review review = createReview(1, reviewer1.getId(), submission.getId(), scorecard.getId(), true, 80.0f);

            insertResources(conn, new Resource[] {submitter, reviewer1, reviewer2});
            insertUploads(conn, new Upload[] {upload});
            insertSubmissions(conn, new Submission[] {submission});
            insertScorecards(conn, new Scorecard[] {scorecard});
            insertReviews(conn, new Review[] {review});
            assertTrue("canPerform should have returned true", handler.canPerform(submissionPhase));

        } finally {
            closeConnection();
        	cleanTables();
        }
    }

    /**
     * Tests the perform with Scheduled and Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerform() throws Exception {
        SubmissionPhaseHandler handler = new SubmissionPhaseHandler(PHASE_HANDLER_NAMESPACE);

        //test with scheduled status.
        Phase submissionPhase = createPhase(1, 1, "Scheduled", 2, "Submission");
        String operator = "operator";
        handler.perform(submissionPhase, operator);

        //test with open status
        submissionPhase.setPhaseStatus(PhaseStatus.OPEN);
        handler.perform(submissionPhase, operator);
    }
}
