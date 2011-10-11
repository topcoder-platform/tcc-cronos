/*
 * Copyright (C) 2009-2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.accuracytests;

import com.cronos.onlinereview.phases.SubmissionPhaseHandler;

import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * Accuracy tests for V1.2 <code>SubmissionPhaseHandler</code>.
 * <p>
 * Version 1.6.2 (Online Review Phases) Change notes:
 * <ol>
 * <li>remove unused imports.</li>
 * </ol>
 * </p>
 *
 * @author assistant, TMALBONPH
 * @version 1.6.2
 * @since 1.2
 */
public class SubmissionPhaseHandlerTestV12 extends BaseTestCase {

    /** Instance to test. */
    private SubmissionPhaseHandler instance;

    /**
     * Sets up the environment.
     *
     * @throws java.lang.Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        instance = new SubmissionPhaseHandler();
    }

    /**
     * Cleans up the environment.
     *
     * @throws java.lang.Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Test method for {@link com.cronos.onlinereview.phases.SubmissionPhaseHandler
     * #perform(com.topcoder.project.phases.Phase, java.lang.String)}.
     *
     * @throws Exception to JUnit
     */
    public void testPerform_2() throws Exception {
        Project project = setupProjectResourcesNotification("Submission", true);
        project.getAllPhases()[1].setPhaseStatus(PhaseStatus.OPEN);

        instance.perform(project.getAllPhases()[1], "1001");

        // the subject should be Phase End: Online Review Phases
        // no submission
        // manager/observer should receive this email
    }

    /**
     * Test method for {@link com.cronos.onlinereview.phases.SubmissionPhaseHandler
     * #perform(com.topcoder.project.phases.Phase, java.lang.String)}.
     *
     * @throws Exception to JUnit
     */
    public void testPerform_3() throws Exception {
        Project project = setupProjectResourcesNotification("Submission", true);

        // test phase start
        project.getAllPhases()[1].setPhaseStatus(PhaseStatus.SCHEDULED);

        instance.perform(project.getAllPhases()[1], "1001");

        // the subject should be Phase Start: Online Review Phases
        // manager/observer should receive this email
    }
}
