/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.phase.DefaultPhaseManager;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;


/**
 * Shows a demo of how to use this component.
 *
 * <p>
 * For version 1.2, the email templates and email options for different role has been enhanced.
 * </p>
 *
 * @author bose_java, waits
 * @version 1.2
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
        configManager.add(DOC_GENERATOR_CONFIG_FILE);
        configManager.add(EMAIL_CONFIG_FILE);
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
     * This method shows a demo of how to use the RegistrationPhaseHandler with Phase Management component. Use of
     * other Phase Handlers is similar to the Registration Phase Handler as the same set of APIs are to be used in
     * those cases as well.
     *
     * @throws Exception not under test.
     */
    public void testDemo() throws Exception {
        // init the phase management component.
        PhaseManager phaseManager = new DefaultPhaseManager(PHASE_MANAGER_NAMESPACE);

        // init the phase handler class.
        PhaseHandler phaseHandler = new ScreeningPhaseHandler(ScreeningPhaseHandler.DEFAULT_NAMESPACE);

        // register a phase handler for dealing with canStart() and canEnd()
        PhaseType phaseType = new PhaseType(3, "Screening");
        phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.START);
        phaseManager.registerHandler(phaseHandler, phaseType, PhaseOperationEnum.END);

        // get the phase instance.
        try {
            cleanTables();

            Project project = setupProjectResourcesNotification("Screening");
            Phase[] phases = project.getAllPhases();
            Phase screeningPhase = phases[2];

            Connection conn = getConnection();

            //since it is in screening, we need setup some data for screening
            // create a registration and add its submission
            Resource resource = createResource(4, 101L, 1, 1);
            super.insertResources(conn, new Resource[] {resource});
            insertResourceInfo(conn, resource.getId(), 1, "4");
            insertResourceInfo(conn, resource.getId(), 2, "prunthaban");
            insertResourceInfo(conn, resource.getId(), 4, "3808");
            insertResourceInfo(conn, resource.getId(), 5, "100");

            //create submission/upload
            Upload upload = super.createUpload(132, project.getId(), resource.getId(), 1, 1, "Paramter");
            super.insertUploads(conn, new Upload[] {upload});

            Submission submission1 = super.createSubmission(112, upload.getId(), 1);
            super.insertSubmissions(conn, new Submission[] {submission1});

            //another register
            resource = createResource(5, 101L, 1, 1);
            super.insertResources(conn, new Resource[] {resource});
            insertResourceInfo(conn, resource.getId(), 1, "5");
            insertResourceInfo(conn, resource.getId(), 2, "fastprogrammer");
            insertResourceInfo(conn, resource.getId(), 4, "3338");
            insertResourceInfo(conn, resource.getId(), 5, "90");
            upload = super.createUpload(232, project.getId(), resource.getId(), 1, 1, "Paramter");
            super.insertUploads(conn, new Upload[] {upload});

            Submission submission2 = super.createSubmission(233, upload.getId(), 1);
            super.insertSubmissions(conn, new Submission[] {submission2});

            //insert screener for screening
            Resource screener = createResource(633, screeningPhase.getId(), 1, 2);
            super.insertResources(conn, new Resource[] {screener});
            insertResourceInfo(conn, screener.getId(), 1, "2");

            //close the submission phase first
            phases[1].setPhaseStatus(PhaseStatus.CLOSED);
            // canStart method will call canPerform() and perform() methods of
            // the phaseHandler.
            if (phaseManager.canStart(screeningPhase)) {
                phaseManager.start(screeningPhase, "1001");
            }

            //to stop
            Scorecard scorecard1 = createScorecard(1, 1, 1, 1, "name", "1.0", 75.0f, 100.0f);

            //insert a screening review result for submission one
            Review screenReview =
                createReview(11, screener.getId(), submission1.getId(), scorecard1.getId(), true, 90.0f);
            Scorecard scorecard2 = createScorecard(2, 1, 1, 1, "name", "1.0", 75.0f, 100.0f);
            //insert a screening review result for submission two
            Review screenReview2 = createReview(13, screener.getId(), submission2.getId(), scorecard2.getId(), true,
                    70.0f);
            this.insertScorecards(conn, new Scorecard[] {scorecard1, scorecard2});
            this.insertReviews(conn, new Review[] {screenReview, screenReview2});

            // canEnd method will call canPerform() and perform() methods of the
            // phaseHandler.
            screeningPhase.setPhaseStatus(PhaseStatus.OPEN);

            if (phaseManager.canEnd(screeningPhase)) {
                phaseManager.end(screeningPhase, "1001");
            }
            //check the email manually
        } finally {
            cleanTables();
            closeConnection();
        }
    }
}
