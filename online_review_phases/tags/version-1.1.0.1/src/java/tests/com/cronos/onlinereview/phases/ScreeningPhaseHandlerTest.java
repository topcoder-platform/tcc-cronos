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
 * All tests for ScreeningPhaseHandler class.
 *
 * <p>
 * Version 1.1 change notes: 4 test cases have been added since version 1.1 to
 * test the new added logic: When Screening phase is stopping, if no submissions
 * have passed screening, a Post-Mortem phase is inserted that depends on the
 * end of the finished Screening phase.
 * </p>
 *
 * @author bose_java, TCSDEVELOPER
 * @version 1.1
 */
public class ScreeningPhaseHandlerTest extends BaseTest {

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

        // add the component configurations as well
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
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
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
     * @throws Exception not under test.
     */
    public void testCanPerformWithInvalidStatus() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 3, "Screening");
            handler.canPerform(phase);
            fail("canPerform() did not throw PhaseHandlingException for invalid phase status.");
        } catch (PhaseHandlingException e) {
            // expected.
        }
    }

    /**
     * Tests canPerform(Phase) with invalid phase type.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithInvalidType() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
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
     * @throws Exception not under test.
     */
    public void testPerformWithNullPhase() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
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
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Invalid", 1, "Screening");
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
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
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
     * @throws Exception not under test.
     */
    public void testPerformWithNullOperator() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Screening");
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
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);
        try {
            Phase phase = createPhase(1, 1, "Scheduled", 1, "Screening");
            handler.perform(phase, "   ");
            fail("perform() did not throw IllegalArgumentException for empty operator.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Tests the ScreeningPhaseHandler() constructor and canPerform with
     * Scheduled statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformWithScheduled() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screeningPhase = phases[2];

            // test with scheduled status.
            screeningPhase.setPhaseStatus(PhaseStatus.SCHEDULED);

            // time has not passed, nor dependencies met
            assertFalse("canPerform should have returned false", handler
                            .canPerform(screeningPhase));

            // time has passed, but dependency not met.
            screeningPhase.setActualStartDate(new Date());
            assertFalse("canPerform should have returned false", handler
                            .canPerform(screeningPhase));

            // time has passed and dependency met.
            screeningPhase.getAllDependencies()[0].getDependency()
                            .setPhaseStatus(PhaseStatus.CLOSED);

            // Set up a active submission for screening phase
            super.setupSubmissionForScreening();

            assertTrue("canPerform should have returned true", handler
                            .canPerform(screeningPhase));
        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the ScreeningPhaseHandler() constructor and canPerform with Open
     * statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen1() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screeningPhase = phases[2];

            // test with open status.
            screeningPhase.setPhaseStatus(PhaseStatus.OPEN);

            // change dependency type to false
            screeningPhase.getAllDependencies()[0].setDependentStart(false);

            // time has not passed, dependencies not met
            assertFalse("canPerform should have returned false", handler
                            .canPerform(screeningPhase));

        } finally {
            cleanTables();
        }
    }

    /**
     * Tests the ScreeningPhaseHandler() constructor and canPerform with Open
     * statuses.
     *
     * @throws Exception not under test.
     */
    public void testCanPerformHandlerWithOpen2() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screeningPhase = phases[2];

            // test with open status.
            screeningPhase.setPhaseStatus(PhaseStatus.OPEN);

            // make dependency closed
            screeningPhase.getAllDependencies()[0].getDependency()
                            .setPhaseStatus(PhaseStatus.CLOSED);

            // time has not passed, dependencies not met
            assertFalse("canPerform should have returned false", handler
                            .canPerform(screeningPhase));

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
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        // test with scheduled status.
        Phase screeningPhase = createPhase(1, 1, "Scheduled", 2, "Screening");
        String operator = "operator";
        handler.perform(screeningPhase, operator);
    }

    /**
     * Tests the perform with OPEN statuses.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testPerformWithOpen1() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            // test with scheduled status.
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screenPhase = phases[2];

            screenPhase.setPhaseStatus(PhaseStatus.OPEN);

            // 1. insert submitter
            Resource submitter = createResource(102, 102, project.getId(), 1);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {submitter});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, submitter.getId(), 1, "1002");

            // 2. insert screener
            Resource screener = createResource(101, screenPhase.getId(),
                            project.getId(), 3);

            // insert records
            insertResources(conn, new Resource[] {screener});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, screener.getId(), 1, "1001");

            // 3. insert submission
            Upload submitterUpload = createUpload(102, project.getId(),
                            submitter.getId(), 1, 1, "parameter");
            Submission submission1 = createSubmission(102, submitterUpload
                            .getId(), 1);
            submission1.setUpload(submitterUpload);

            this.insertUploads(conn, new Upload[] {submitterUpload});
            this.insertSubmissions(conn, new Submission[] {submission1});

            // 4. insert a scorecard here
            Scorecard scorecard1 = createScorecard(1, 1, 1, 1, "name", "1.0",
                            75.0f, 100.0f);

            // 5. insert a screening review
            Review screenReview = createReview(11, screener.getId(),
                            submission1.getId(), scorecard1.getId(), true,
                            90.0f);

            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {screenReview});

            handler.perform(screenPhase, "1001");

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the perform with OPEN statuses.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testPerformWithOpen2() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            // test with scheduled status.
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screenPhase = phases[2];

            screenPhase.setPhaseStatus(PhaseStatus.OPEN);

            // 1. insert submitter
            Resource submitter = createResource(102, 102, project.getId(), 1);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {submitter});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, submitter.getId(), 1, "1002");

            // 2. insert primary screener
            Resource primaryScreener = createResource(101, screenPhase.getId(),
                            project.getId(), 2);

            // insert records
            insertResources(conn, new Resource[] {primaryScreener});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, primaryScreener.getId(), 1, "1001");

            // 3. insert submission
            Upload submitterUpload = createUpload(102, project.getId(),
                            submitter.getId(), 1, 1, "parameter");
            Submission submission1 = createSubmission(102, submitterUpload
                            .getId(), 1);
            submission1.setUpload(submitterUpload);

            this.insertUploads(conn, new Upload[] {submitterUpload});
            this.insertSubmissions(conn, new Submission[] {submission1});

            // 4. insert a scorecard here
            Scorecard scorecard1 = createScorecard(1, 1, 1, 1, "name", "1.0",
                            75.0f, 100.0f);

            // 5. insert a screening review
            Review screenReview = createReview(11, primaryScreener.getId(),
                            submission1.getId(), scorecard1.getId(), true,
                            90.0f);

            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {screenReview});

            handler.canPerform(screenPhase);
            handler.perform(screenPhase, "1001");

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the perform with OPEN statuses.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testPerformWithOpen3() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            // test with scheduled status.
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screenPhase = phases[2];

            screenPhase.setPhaseStatus(PhaseStatus.OPEN);

            // 1. insert submitter
            Resource submitter = createResource(102, 102, project.getId(), 1);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {submitter});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, submitter.getId(), 1, "1002");

            // 2. insert primary screener
            Resource primaryScreener = createResource(101, screenPhase.getId(),
                            project.getId(), 2);

            // insert records
            insertResources(conn, new Resource[] {primaryScreener});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, primaryScreener.getId(), 1, "1001");

            // 3. insert submission
            Upload submitterUpload = createUpload(102, project.getId(),
                            submitter.getId(), 1, 1, "parameter");
            Submission submission1 = createSubmission(102, submitterUpload
                            .getId(), 1);
            submission1.setUpload(submitterUpload);

            this.insertUploads(conn, new Upload[] {submitterUpload});
            this.insertSubmissions(conn, new Submission[] {submission1});

            // 4. insert a scorecard here
            Scorecard scorecard1 = createScorecard(1, 1, 1, 1, "name", "1.0",
                            75.0f, 100.0f);

            // 5. insert a screening review
            Review screenReview = createReview(11, primaryScreener.getId(),
                            submission1.getId(), scorecard1.getId(), true,
                            65.0f);

            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {screenReview});

            handler.canPerform(screenPhase);

            // score not passed
            handler.perform(screenPhase, "1001");

            // check whether the post-mortem phase is inserted
            assertTrue("Post-mortem phase should be inserted",
                            havePostMortemPhase(conn));

        } finally {
            cleanTables();
            closeConnection();
        }
    }

    /**
     * Tests the perform with OPEN statuses.
     *
     * @throws Exception to JUnit.
     * @since 1.1
     */
    public void testPerformWithOpen4() throws Exception {
        ScreeningPhaseHandler handler = new ScreeningPhaseHandler(
                        PHASE_HANDLER_NAMESPACE);

        try {
            cleanTables();
            // test with scheduled status.
            Project project = super.setupPhases();
            Phase[] phases = project.getAllPhases();
            Phase screenPhase = phases[2];

            screenPhase.setPhaseStatus(PhaseStatus.OPEN);

            // 1. insert submitter
            Resource submitter = createResource(102, screenPhase.getId(),
                            project.getId(), 1);

            Connection conn = getConnection();

            // insert records
            insertResources(conn, new Resource[] {submitter});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, submitter.getId(), 1, "1002");

            // 2. insert screener
            Resource screener = createResource(101, screenPhase.getId(),
                            project.getId(), 3);

            // insert records
            insertResources(conn, new Resource[] {screener});

            // we need to insert an external reference id
            // which references to resource's user id in resource_info table
            insertResourceInfo(conn, screener.getId(), 1, "1001");

            // 3. insert submission
            Upload submitterUpload = createUpload(102, project.getId(),
                            submitter.getId(), 1, 1, "parameter");
            Submission submission1 = createSubmission(102, submitterUpload
                            .getId(), 1);
            submission1.setUpload(submitterUpload);

            this.insertUploads(conn, new Upload[] {submitterUpload});
            this.insertSubmissions(conn, new Submission[] {submission1});

            // 4. insert a scorecard here
            Scorecard scorecard1 = createScorecard(1, 1, 1, 1, "name", "1.0",
                            75.0f, 100.0f);

            // 5. insert a screening review
            Review screenReview = createReview(11, screener.getId(),
                            submission1.getId(), scorecard1.getId(), true,
                            90.0f);

            this.insertScorecards(conn, new Scorecard[] {scorecard1});
            this.insertReviews(conn, new Review[] {screenReview});

            handler.canPerform(screenPhase);
            handler.perform(screenPhase, "1001");

        } finally {
            cleanTables();
            closeConnection();
        }
    }
}
