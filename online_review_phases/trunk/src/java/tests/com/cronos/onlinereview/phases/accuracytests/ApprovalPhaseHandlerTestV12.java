/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.accuracytests;

import java.sql.Connection;

import com.cronos.onlinereview.phases.ApprovalPhaseHandler;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;


/**
 * Accuracy tests for ApprovalPhaseHandler class.
 *
 * @author assistant
 * @version 1.2
 */
public class ApprovalPhaseHandlerTestV12 extends BaseTestCase {
    /**
     * sets up the environment required for test cases for this class.
     *
     * @throws Exception not under test.
     */
    protected void setUp() throws Exception {
        super.setUp();
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
     * Tests the perform with Scheduled statuses.
     *
     * @throws Exception not under test.
     *
     * @since 1.2
     */
    public void testPerform_1() throws Exception {
        ApprovalPhaseHandler handler = new ApprovalPhaseHandler(ApprovalPhaseHandler.DEFAULT_NAMESPACE);
        Project project = super.setupProjectResourcesNotification("Approval", true);
        Phase[] phases = project.getAllPhases();
        Phase approvalPhase = phases[10];
        handler.perform(approvalPhase, "1001");
    }

    /**
     * Tests the perform with Scheduled statuses.
     *
     * @throws Exception not under test.
     *
     * @since 1.2
     */
    public void testPerform_2() throws Exception {
        ApprovalPhaseHandler handler = new ApprovalPhaseHandler(ApprovalPhaseHandler.DEFAULT_NAMESPACE);

        Project project = super.setupProjectResourcesNotification("Approval", true);
        Phase[] phases = project.getAllPhases();
        Phase approvalPhase = phases[10];

        Resource approval = createResource(100233, approvalPhase.getId(), project.getId(), 10);
        Connection conn = getConnection();
        insertResources(conn, new Resource[] {approval});
        insertResourceInfo(conn, approval.getId(), 1, "2");

        handler.perform(approvalPhase, "1001");

    }

    /**
     * Test the method perform with a rejected approval review, the new final review / final fix phases should be
     * inserted.
     *
     * @throws Exception to JUnit
     *
     * @since 1.1
     */
    public void testPerform_3() throws Exception {
        ApprovalPhaseHandler handler = new ApprovalPhaseHandler(ApprovalPhaseHandler.DEFAULT_NAMESPACE);
        Project project = super.setupProjectResourcesNotification("Approval", true);
        Phase[] phases = project.getAllPhases();
        Phase approvalPhase = phases[10];
        approvalPhase.setPhaseStatus(PhaseStatus.OPEN);

        Connection conn = getConnection();

        // populate db with required data final reviewer resource
        Resource finalReviewer = createResource(102, 111L, project.getId(), 9);

        insertResources(conn, new Resource[] {finalReviewer});
        insertResourceInfo(conn, finalReviewer.getId(), 1, "2");

        // populate db with required data for approver resource
        Resource approver = createResource(101, approvalPhase.getId(), project.getId(), 10);
        Upload appUpload = createUpload(1, project.getId(), approver.getId(), 4, 1, "parameter");
        Submission appSubmission = createSubmission(1, appUpload.getId(), 1);

        // reviewer resource and related review
        Scorecard scorecard1 = createScorecard(1001, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
        Review frWorksheet = createReview(1111, approver.getId(), appSubmission.getId(), scorecard1.getId(), true,
                90.0f);

        // add a rejected comment
        frWorksheet.addComment(createComment(11, approver.getId(), "Rejected", 12, "Approval Review Comment"));

        // insert records
        insertResources(conn, new Resource[] {approver});
        insertResourceInfo(conn, approver.getId(), 1, "2");
        insertUploads(conn, new Upload[] {appUpload});
        insertSubmissions(conn, new Submission[] {appSubmission});
        insertResourceSubmission(conn, approver.getId(), appSubmission.getId());
        insertScorecards(conn, new Scorecard[] {scorecard1});
        insertReviews(conn, new Review[] {frWorksheet});
        insertCommentsWithExtraInfo(conn, new long[] {1}, new long[] {approver.getId()},
            new long[] {frWorksheet.getId()}, new String[] {"Rejected Comment"}, new long[] {12},
            new String[] {"Rejected"});
        insertScorecardQuestion(conn, 1, scorecard1.getId());

        // no exception should be thrown.
        String operator = "1001";

        handler.canPerform(approvalPhase);
        handler.perform(approvalPhase, operator);
    }

    /**
     * Test the method perform with an approved approval review, the final review / final fix phases should NOT be
     * inserted.
     *
     * @throws Exception to JUnit
     *
     * @since 1.1
     */
    public void testPerform_4() throws Exception {
        ApprovalPhaseHandler handler = new ApprovalPhaseHandler(ApprovalPhaseHandler.DEFAULT_NAMESPACE);
        Project project = super.setupProjectResourcesNotification("Approval", true);
        Phase[] phases = project.getAllPhases();
        Phase approvalPhase = phases[10];
        approvalPhase.setPhaseStatus(PhaseStatus.OPEN);

        Connection conn = getConnection();

        // populate db with required data final reviewer resource
        Resource finalReviewer = createResource(102, 111L, project.getId(), 9);

        insertResources(conn, new Resource[] {finalReviewer});
        insertResourceInfo(conn, finalReviewer.getId(), 1, "100002");

        // populate db with required data for approver resource
        Resource approver = createResource(101, approvalPhase.getId(), project.getId(), 10);
        Upload appUpload = createUpload(1, project.getId(), approver.getId(), 4, 1, "parameter");
        Submission appSubmission = createSubmission(1, appUpload.getId(), 1);

        // reviewer resource and related review
        Scorecard scorecard1 = createScorecard(1, 1, 2, 1, "name", "1.0", 75.0f, 100.0f);
        Review frWorksheet = createReview(11, approver.getId(), appSubmission.getId(), scorecard1.getId(), true,
                90.0f);

        // add a rejected comment
        frWorksheet.addComment(createComment(1, approver.getId(), "Approved", 12, "Approval Review Comment"));

        // insert records
        insertResources(conn, new Resource[] {approver});
        insertResourceInfo(conn, approver.getId(), 1, "100001");
        insertUploads(conn, new Upload[] {appUpload});
        insertSubmissions(conn, new Submission[] {appSubmission});
        insertResourceSubmission(conn, approver.getId(), appSubmission.getId());
        insertScorecards(conn, new Scorecard[] {scorecard1});
        insertReviews(conn, new Review[] {frWorksheet});
        insertCommentsWithExtraInfo(conn, new long[] {1}, new long[] {approver.getId()},
            new long[] {frWorksheet.getId()}, new String[] {"Approved Comment"}, new long[] {12},
            new String[] {"Approved"});
        insertScorecardQuestion(conn, 1, 1);

        // no exception should be thrown.
        String operator = "1001";

        handler.canPerform(approvalPhase);
        handler.perform(approvalPhase, operator);

    }
}
