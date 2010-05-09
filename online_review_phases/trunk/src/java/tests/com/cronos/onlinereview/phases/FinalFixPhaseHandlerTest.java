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
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * All tests for FinalFixPhaseHandler class.
 * <p>
 * version 1.1 changes note : Fixed test issues according to the new logic and requirement:
 * <li>Check final reviewer requirement in canPerform</li>
 * </p>
 *
 * @author bose_java, waits
 * @version 1.1
 */
public class FinalFixPhaseHandlerTest extends BaseTest {

    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception
     *             not under test.
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
     * @throws Exception
     *             not under test.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests canPerform(Phase) with null phase.
     *
     * @throws Exception
     *             not under test.
     */
    public void testCanPerform() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
     * @throws Exception
     *             not under test.
     */
    public void testCanPerformWithInvalidStatus() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 3, "Final Fix");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase type.
     *
     * @throws Exception
     *             not under test.
     */
    public void testCanPerformWithInvalidType() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "INVALID");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with null phase.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithNullPhase() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
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
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithInvalidStatus() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Final Fix");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with invalid phase type.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithInvalidType() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "INVALID");
            handler.perform(phase, "operator");
            fail("perform() did not throw PhaseHandlingException for invalid phase type.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with null operator.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithNullOperator() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Final Fix");
            handler.perform(phase, null);
            fail("perform() did not throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests perform(Phase) with empty operator.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithEmptyOperator() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Final Fix");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests the FinalFixPhaseHandler() constructor and canPerform with Scheduled statuses.
     *
     * @throws Exception
     *             not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase finalFixPhase = phases[8];

            // test with scheduled status.
            finalFixPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

            // time has not passed, nor dependencies met
            assertFalse("canPerform should have returned false", handler.canPerform(finalFixPhase));

            // time has passed, but dependency not met.
            finalFixPhase.setActualStartDate(new Date());
            assertFalse("canPerform should have returned false", handler.canPerform(finalFixPhase));

            // time has passed and dependency met, but there is no final
            // reviewer assigned
            // version 1.1 should return false here
            finalFixPhase.getAllDependencies()[0].getDependency().setPhaseStatus(PhaseStatus.CLOSED);
            assertFalse("canPerform should have returned false when there is no final reviewer", handler
                    .canPerform(finalFixPhase));

            // Let's add a final reviewer here
            Resource finalReviewer = createResource(101, finalFixPhase.getId(), project.getId(), 9);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {finalReviewer});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, finalReviewer.getId(), 1, "1001");
            assertFalse("canPerform should have returned true when final reviewer is assigned.", handler
                    .canPerform(finalFixPhase));

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the FinalFixPhaseHandler() constructor and canPerform with Open statuses.
     *
     * @throws Exception
     *             not under test.
     */
    public void testCanPerformHandlerWithOpen() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase finalFixPhase = phases[8];

            // change dependency type to F2F
            finalFixPhase.getAllDependencies()[0].setDependentStart(false);

            // test with open status.
            finalFixPhase.setPhaseStatus(PhaseStatus.OPEN);

            // time has not passed, dependencies not met
            assertFalse("canPerform should have returned false", handler.canPerform(finalFixPhase));
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the perform with Open statuses.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerform() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler();

        // test with open status
        Phase finalFixPhase = createPhase(1, 1, "Open", 2, "Final Fix");
        String operator = "operator";
        handler.perform(finalFixPhase, operator);
    }

    /**
     * Tests the perform with Scheduled status.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerformWithScheduled() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase aggregationPhase = phases[6];
            Phase finalFixPhase = phases[8];
            Phase finalReviewPhase = phases[9];
            finalFixPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

            // populate db with required data
            // aggregator resource
            Resource finalReviewer = createResource(11, finalReviewPhase.getId(), project.getId(), 9);
            Resource aggregator = createResource(1, aggregationPhase.getId(), project.getId(), 8);

            // reviewer resource and related review
            Upload upload1 = createUpload(1, project.getId(), aggregator.getId(), 4, 1, "parameter");
            Submission submission1 = createSubmission(1, upload1.getId(), 1);
            Scorecard scorecard1 = createScorecard(1, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
            Review review1 = createReview(1111, aggregator.getId(), submission1.getId(), scorecard1.getId(), true,
                    80.0f);
            review1.addComment(createComment(21, aggregator.getId(), "Good Design", 1, "Comment"));

            Item[] reviewItems = new Item[2];
            reviewItems[0] = createReviewItem(11, "Answer 1", review1.getId(), 1);
            reviewItems[1] = createReviewItem(12, "Answer 2", review1.getId(), 1);

            Comment[] reviewItemComments = new Comment[2];
            reviewItemComments[0] = createComment(11, aggregator.getId(), "Item 1", 1, "Comment");
            reviewItemComments[1] = createComment(12, aggregator.getId(), "Item 2", 1, "Comment");

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {finalReviewer, aggregator});
            insertResourceInfo(conn, finalReviewer.getId(), 1, "1001");
            insertResourceInfo(conn, aggregator.getId(), 1, "1002");
            insertUploads(conn, new Upload[] {upload1});
            insertSubmissions(conn, new Submission[] {submission1});
            insertScorecards(conn, new Scorecard[] {scorecard1});
            insertReviews(conn, new Review[] {review1});

            insertComments(conn, new long[] {103}, new long[] {aggregator.getId()}, new long[] {review1.getId()},
                    new String[] {"comment 1"}, new long[] {1});
            insertScorecardQuestion(conn, 1, 1);
            insertReviewItems(conn, reviewItems);
            insertReviewItemComments(conn, reviewItemComments, new long[] {11, 12});
            insertWinningSubmitter(conn, 12, project.getId());

            // check no final worksheet exists before calling perform method
            Review finalWorksheet = PhasesHelper.getFinalReviewWorksheet(conn, handler.getManagerHelper(),
                    finalReviewPhase.getId());
            assertNull("No final worksheet should exist before this test", finalWorksheet);


        } finally {
            closeConnection();
            cleanTables();
        }
    }

    /**
     * Tests the perform with Scheduled status.
     *
     * @throws Exception
     *             not under test.
     */
    public void testPerform_exception1() throws Exception {
        FinalFixPhaseHandler handler = new FinalFixPhaseHandler(PHASE_HANDLER_NAMESPACE);
        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase aggregationPhase = phases[6];
            Phase finalFixPhase = phases[8];
            Phase finalReviewPhase = phases[9];
            finalFixPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

            // populate db with required data
            // aggregator resource
            Resource aggregator = createResource(1, aggregationPhase.getId(), project.getId(), 8);

            // reviewer resource and related review
            Upload upload1 = createUpload(1, project.getId(), aggregator.getId(), 4, 1, "parameter");
            Submission submission1 = createSubmission(1, upload1.getId(), 1);
            Scorecard scorecard1 = createScorecard(1, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
            Review review1 = createReview(1111, aggregator.getId(), submission1.getId(), scorecard1.getId(), true,
                    80.0f);
            review1.addComment(createComment(21, aggregator.getId(), "Good Design", 1, "Comment"));

            Item[] reviewItems = new Item[2];
            reviewItems[0] = createReviewItem(11, "Answer 1", review1.getId(), 1);
            reviewItems[1] = createReviewItem(12, "Answer 2", review1.getId(), 1);

            Comment[] reviewItemComments = new Comment[2];
            reviewItemComments[0] = createComment(11, aggregator.getId(), "Item 1", 1, "Comment");
            reviewItemComments[1] = createComment(12, aggregator.getId(), "Item 2", 1, "Comment");

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {aggregator});
            insertResourceInfo(conn, aggregator.getId(), 1, "1002");
            insertUploads(conn, new Upload[] {upload1});
            insertSubmissions(conn, new Submission[] {submission1});
            insertScorecards(conn, new Scorecard[] {scorecard1});
            insertReviews(conn, new Review[] {review1});

            insertComments(conn, new long[] {103}, new long[] {aggregator.getId()}, new long[] {review1.getId()},
                    new String[] {"comment 1"}, new long[] {1});
            insertScorecardQuestion(conn, 1, 1);
            insertReviewItems(conn, reviewItems);
            insertReviewItemComments(conn, reviewItemComments, new long[] {11, 12});
            insertWinningSubmitter(conn, 12, project.getId());

            // check no final worksheet exists before calling perform method
            Review finalWorksheet = PhasesHelper.getFinalReviewWorksheet(conn, handler.getManagerHelper(),
                    finalReviewPhase.getId());
            assertNull("No final worksheet should exist before this test", finalWorksheet);

            // call perform method
            String operator = "1001";
            handler.perform(finalFixPhase, operator);

            fail("expect PhaseHandlingException");
        } catch (PhaseHandlingException e) {
            // expected
        } finally {
            closeConnection();
            cleanTables();
        }
    }

}
