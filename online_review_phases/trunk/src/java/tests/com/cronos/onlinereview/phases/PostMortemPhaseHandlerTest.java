/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import java.sql.Connection;
import java.util.Date;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit tests for the new added phase handler
 * <code>PostMortemPhaseHandler</code> in version 1.1.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class PostMortemPhaseHandlerTest extends BaseTest {
    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception to JUnit.
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
     * cleans up the environment required for test cases for this class.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the default constructor of PostMortemPhaseHandler.
     *
     * @throws Exception to JUnit.
     */
    public void testDefaultConstructor() throws Exception {
        PhaseHandler handler = new PostMortemPhaseHandler();

        assertNotNull("PostMortemPhaseHandler should be correctly created.",
                        handler);
    }

    /**
     * Tests canPerform(Phase) with null phase.
     *
     * @throws Exception not under test.
     */
    public void testCanPerform() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            handler.canPerform(null);
            fail("canPerform() did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase status.
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerformWithInvalidStatus() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 12, "Post-Mortem");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase type.
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerformWithInvalidType() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 12, "INVALID");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with null phase.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithNullPhase() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            handler.perform(null, "operator");
            fail("perform() did not throw IllegalArgumentException for null argument.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with invalid phase status.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithInvalidStatus() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 12, "Post-Mortem");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with invalid phase type.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithInvalidType() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 12, "INVALID");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with null operator.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithNullOperator() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 12, "Post-Mortem");
            handler.perform(phase, null);
            fail("perform() did not throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with empty operator.
     *
     * @throws Exception not under test.
     */
    public void testPerformWithEmptyOperator() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 12, "Post-Mortem");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests the PostMortemPhaseHandler() constructor and canPerform with
     * Scheduled statuses.
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerformWithScheduled() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhasesWithPostMortem();
            Phase[] phases = project.getAllPhases();
            Phase postMortemPhase = phases[11];

            // test with scheduled status.
            postMortemPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

            // time has not passed
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

            // time has passed, but dependency not met.
            postMortemPhase.setActualStartDate(new Date());
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

            // set the number of required post-mortem reviewer to 1
            postMortemPhase.setAttribute("Reviewer Number", "1");

            // time has passed and dependency met
            postMortemPhase.getAllDependencies()[0].getDependency()
                            .setPhaseStatus(PhaseStatus.CLOSED);
            assertTrue(
                            "canPerform should return true when dependencies met and time is up",
                            handler.canPerform(postMortemPhase));

        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the PostMortemPhaseHandler() constructor and canPerform with Open
     * statuses.
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerformWithOpen1() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhasesWithPostMortem();
            Phase[] phases = project.getAllPhases();
            Phase postMortemPhase = phases[11];

            // change dependency type to false
            postMortemPhase.getAllDependencies()[0].setDependentStart(false);

            // test with open status.
            postMortemPhase.setPhaseStatus(PhaseStatus.OPEN);

            // dependencies not met
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

            // remove all dependencies
            for (int i = 0; i < postMortemPhase.getAllDependencies().length; ++i) {
                postMortemPhase.removeDependency(postMortemPhase
                                .getAllDependencies()[i]);
            }

            // set the number of required post-mortem reviewer to 1
            postMortemPhase.setAttribute("Reviewer Number", "1");

            // dependencies met but without post-mortem reviewer
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

            // insert post-mortem reviewer
            Resource postMortemReviewer = createResource(101, postMortemPhase
                            .getId(), project.getId(), 14);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {postMortemReviewer});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, postMortemReviewer.getId(), 1, "1001");

            // there is not scorecard filled
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

            // insert a scorecard here
            Upload upload = createUpload(101, project.getId(),
                            postMortemReviewer.getId(), 4, 1, "parameter");
            Submission submission = createSubmission(101, upload.getId(), 1);
            submission.setUpload(upload);

            Scorecard scorecard1 = createScorecard(1, 1, 2, 1, "name", "1.0",
                            75.0f, 100.0f);

            // scorecard not committed
            Review postMortemScorecard = createReview(11, postMortemReviewer
                            .getId(), submission.getId(), scorecard1.getId(),
                            false, 90.0f);

            this.insertUploads(conn, new Upload[] {upload});
            this.insertSubmissions(conn, new Submission[] {submission});
            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {postMortemScorecard});

            // scorecard not committed return false
            assertFalse("canPerform should have returned false", handler
                            .canPerform(postMortemPhase));

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the PostMortemPhaseHandler() constructor and canPerform with Open
     * statuses.
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerformWithOpen2() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhasesWithPostMortem();
            Phase[] phases = project.getAllPhases();
            Phase postMortemPhase = phases[11];

            // test with open status.
            postMortemPhase.setPhaseStatus(PhaseStatus.OPEN);

            // remove all dependencies
            for (int i = 0; i < postMortemPhase.getAllDependencies().length; ++i) {
                postMortemPhase.removeDependency(postMortemPhase
                                .getAllDependencies()[i]);
            }

            // set the number of required post-mortem reviewer to 1
            postMortemPhase.setAttribute("Reviewer Number", "1");

            // insert post-mortem reviewer
            Resource postMortemReviewer = createResource(101, postMortemPhase
                            .getId(), project.getId(), 14);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {postMortemReviewer});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, postMortemReviewer.getId(), 1, "1001");

            // insert a scorecard here
            Upload upload = createUpload(101, project.getId(),
                            postMortemReviewer.getId(), 4, 1, "parameter");
            Submission submission = createSubmission(101, upload.getId(), 1);
            submission.setUpload(upload);

            Scorecard scorecard1 = createScorecard(1, 1, 2, 1, "name", "1.0",
                            75.0f, 100.0f);

            // insert a committed scorecard
            Review postMortemScorecard = createReview(11, postMortemReviewer
                            .getId(), submission.getId(), scorecard1.getId(),
                            true, 90.0f);

            this.insertUploads(conn, new Upload[] {upload});
            this.insertSubmissions(conn, new Submission[] {submission});
            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {postMortemScorecard});

            // scorecard committed
            assertTrue("canPerform should have returned true", handler
                            .canPerform(postMortemPhase));

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the perform with Scheduled and Open statuses.
     *
     * @throws Exception not under test.
     */
    public void testPerform() throws Exception {
        PostMortemPhaseHandler handler = new PostMortemPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            cleanTables();
            Project project = super.setupPhasesWithPostMortem();
            Phase[] phases = project.getAllPhases();
            Phase postMortemPhase = phases[11];

            // test perform, it should do nothing
            handler.perform(postMortemPhase, "1001");

        } finally {
            cleanTables();
        }
    }
}
