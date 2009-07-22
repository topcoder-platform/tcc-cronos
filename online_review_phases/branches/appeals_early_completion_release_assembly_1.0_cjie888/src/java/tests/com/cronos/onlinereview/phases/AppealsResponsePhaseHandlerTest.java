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
 * All tests for AppealsResponsePhaseHandler class.
 *
 * @author bose_java
 * @version 1.0
 */
public class AppealsResponsePhaseHandlerTest extends BaseTest {

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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 3, "Appeals Response");
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Appeals Response");
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Appeals Response");
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
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Appeals Response");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            //expected.
        }
    }

    /**
     * Tests the AppealsResponsePhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsResponsePhase = phases[5];
	
	        //test with scheduled status.
	        appealsResponsePhase.setPhaseStatus(PhaseStatus.SCHEDULED);
	
	        //time has not passed, nor dependencies met
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsResponsePhase));
	
	        //time has passed, but dependency not met.
	        appealsResponsePhase.setActualStartDate(new Date());
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsResponsePhase));
	
	        //time has passed and dependency met.
	        appealsResponsePhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
	        assertTrue("canPerform should have returned true", handler.canPerform(appealsResponsePhase));
        } finally {
        	cleanTables();
        }
    }


    /**
     * Tests the AppealsResponsePhaseHandler() constructor and canPerform with Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen() throws Exception {
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsResponsePhase = phases[5];
	    	
	        //change dependency type to F2F
	        appealsResponsePhase.getAllDependencies()[0].setDependentStart(false);
	
	        //test with open status.
	        appealsResponsePhase.setPhaseStatus(PhaseStatus.OPEN);
	
	        //time has not passed, dependencies not met
	        assertFalse("canPerform should have returned false", handler.canPerform(appealsResponsePhase));
        } finally {
        	cleanTables();
        }
    }

    /**
     * Tests the perform with Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithScheduled() throws Exception {
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);

        //test with scheduled status.
        Phase appealsResponsePhase = createPhase(1, 1, "Scheduled", 2, "Appeals Response");
        String operator = "operator";
        handler.perform(appealsResponsePhase, operator);
    }

    /**
     * Tests the AppealsResponsePhaseHandler() constructor and perform() with Open status and no winners.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithOpenNoWinner() throws Exception {
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsResponsePhase = phases[5];
	        Phase submissionPhase = phases[1];
	        Phase reviewPhase = phases[3];
	
	        //test with open status.
	        appealsResponsePhase.setPhaseStatus(PhaseStatus.OPEN);
	        
	        //insert database records.
	        Resource submitter1 = createResource(1, submissionPhase.getId(), project.getId(), 1);
	        Resource submitter2 = createResource(2, submissionPhase.getId(), project.getId(), 1);
	        Resource reviewer1 = createResource(3, reviewPhase.getId(), project.getId(), 4);
	        Upload upload1 = createUpload(1, project.getId(), submitter1.getId(), 1, 1, "parameter");
	        Upload upload2 = createUpload(2, project.getId(), submitter2.getId(), 1, 1, "parameter");
	        Submission submission1 = createSubmission(1, upload1.getId(), 1);
	        Submission submission2 = createSubmission(2, upload2.getId(), 1);
	        Scorecard scorecard = createScorecard(1, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
	        //assuming 2 reviewers * 2 submissions, so 4 reviews in all are needed
	        Review review1 = createReview(1, reviewer1.getId(), submission1.getId(), scorecard.getId(), true, 60.0f);
	        Review review2 = createReview(2, reviewer1.getId(), submission1.getId(), scorecard.getId(), true, 70.0f);
	        Review review3 = createReview(3, reviewer1.getId(), submission2.getId(), scorecard.getId(), true, 50.0f);
	        Review review4 = createReview(4, reviewer1.getId(), submission2.getId(), scorecard.getId(), true, 60.0f);
	        
	        Connection conn = getConnection();
	        insertResources(conn, new Resource[] {submitter1, submitter2, reviewer1});
	        insertUploads(conn, new Upload[] {upload1, upload2});
	        insertSubmissions(conn, new Submission[] {submission1, submission2});
	        insertScorecards(conn, new Scorecard[] {scorecard});
	        insertReviews(conn, new Review[] {review1, review2, review3, review4});
	
            //call perform method
            String operator = "operator";
            handler.perform(appealsResponsePhase, operator);
            
            //ensure "Final Score" property of submitter resources has been updated
            submitter1 = handler.getManagerHelper().getResourceManager().getResource(submitter1.getId());
            submitter2 = handler.getManagerHelper().getResourceManager().getResource(submitter2.getId());
            assertEquals("Final Score not set", submitter1.getProperty("Final Score"), "65.0");
            assertEquals("Final Score not set", submitter2.getProperty("Final Score"), "55.0");
            
            //ensure submission status has been updated
            submission1 = handler.getManagerHelper().getUploadManager().getSubmission(submission1.getId());
            submission2 = handler.getManagerHelper().getUploadManager().getSubmission(submission2.getId());
            assertEquals("Submission Status not set", submission1.getSubmissionStatus().getId(), 3);
            assertEquals("Submission Status not set", submission2.getSubmissionStatus().getId(), 3);
            
            //ensure no winners for the project
            com.topcoder.management.project.Project project1 = handler.getManagerHelper().getProjectManager().getProject(1);
            assertNull("No winner expected", project1.getProperty("Winner External Reference ID"));
            assertNull("No runner up expected", project1.getProperty("Runner-up External Reference ID"));
        } finally {
        	closeConnection();
        	cleanTables();
        }
    }

    /**
     * Tests the AppealsResponsePhaseHandler() constructor and perform() with Open status and winners.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithOpenWithWinner() throws Exception {
        AppealsResponsePhaseHandler handler = new AppealsResponsePhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
        	cleanTables();
	        Project project = super.setupPhases();
	        Phase[] phases = project.getAllPhases();
	        Phase appealsResponsePhase = phases[5];
	        Phase submissionPhase = phases[1];
	        Phase reviewPhase = phases[3];
	
	        //test with open status.
	        appealsResponsePhase.setPhaseStatus(PhaseStatus.OPEN);
	        
	        //insert database records.
	        Resource submitter1 = createResource(1, submissionPhase.getId(), project.getId(), 1);
	        Resource submitter2 = createResource(2, submissionPhase.getId(), project.getId(), 1);
	        Resource submitter3 = createResource(3, submissionPhase.getId(), project.getId(), 1);
	        Resource reviewer1 = createResource(4, reviewPhase.getId(), project.getId(), 4);
	        Upload upload1 = createUpload(1, project.getId(), submitter1.getId(), 1, 1, "parameter");
	        Upload upload2 = createUpload(2, project.getId(), submitter2.getId(), 1, 1, "parameter");
	        Upload upload3 = createUpload(3, project.getId(), submitter3.getId(), 1, 1, "parameter");
	        Submission submission1 = createSubmission(1, upload1.getId(), 1);
	        Submission submission2 = createSubmission(2, upload2.getId(), 1);
	        Submission submission3 = createSubmission(3, upload3.getId(), 1);
	        Scorecard scorecard = createScorecard(1, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
	        //assuming 2 reviewers * 3 submissions, so 6 reviews in all are needed
	        Review review1 = createReview(1, reviewer1.getId(), submission1.getId(), scorecard.getId(), true, 90.0f);
	        Review review2 = createReview(2, reviewer1.getId(), submission1.getId(), scorecard.getId(), true, 90.0f);
	        Review review3 = createReview(3, reviewer1.getId(), submission2.getId(), scorecard.getId(), true, 80.0f);
	        Review review4 = createReview(4, reviewer1.getId(), submission2.getId(), scorecard.getId(), true, 84.0f);
	        Review review5 = createReview(5, reviewer1.getId(), submission3.getId(), scorecard.getId(), true, 80.0f);
	        Review review6 = createReview(6, reviewer1.getId(), submission3.getId(), scorecard.getId(), true, 80.0f);
	        
	        Connection conn = getConnection();
	        insertResources(conn, new Resource[] {submitter1, submitter2, submitter3, reviewer1});
	        insertResourceInfo(conn, submitter1.getId(), 1, "" + submitter1.getId());
	        insertResourceInfo(conn, submitter2.getId(), 1, "" + submitter2.getId());
	        insertUploads(conn, new Upload[] {upload1, upload2, upload3});
	        insertSubmissions(conn, new Submission[] {submission1, submission2, submission3});
	        insertScorecards(conn, new Scorecard[] {scorecard});
	        insertReviews(conn, new Review[] {review1, review2, review3, review4, review5, review6});
	
            //call perform method
            String operator = "operator";
            handler.perform(appealsResponsePhase, operator);
            
            //ensure "Final Score" and "Placement" property of submitter resources has been updated
            submitter1 = handler.getManagerHelper().getResourceManager().getResource(submitter1.getId());
            submitter2 = handler.getManagerHelper().getResourceManager().getResource(submitter2.getId());
            submitter3 = handler.getManagerHelper().getResourceManager().getResource(submitter3.getId());
            assertEquals("Final Score not set", submitter1.getProperty("Final Score"), "90.0");
            assertEquals("Placement not set", submitter1.getProperty("Placement"), "1");
            
            assertEquals("Final Score not set", submitter2.getProperty("Final Score"), "82.0");
            assertEquals("Placement not set", submitter2.getProperty("Placement"), "2");
            
            assertEquals("Final Score not set", submitter3.getProperty("Final Score"), "80.0");
            assertEquals("Placement not set", submitter3.getProperty("Placement"), "3");
            
            //ensure submission status has been updated to Completed Without Win
            submission3 = handler.getManagerHelper().getUploadManager().getSubmission(submission3.getId());
            assertEquals("Submission Status not set", submission3.getSubmissionStatus().getId(), 4);
            
            //ensure winner and runner up for the project
            com.topcoder.management.project.Project project1 = handler.getManagerHelper().getProjectManager().getProject(1);
            assertEquals("winner expected", project1.getProperty("Winner External Reference ID"), "" + submitter1.getId());
            assertEquals("runner up expected", project1.getProperty("Runner-up External Reference ID"), "" + submitter2.getId());
        } finally {
        	closeConnection();
        	cleanTables();
        }
    }
}
