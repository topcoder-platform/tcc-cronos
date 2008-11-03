/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import com.topcoder.clientcockpit.phases.TestHelper.CockpitContestStatus;
import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;


/**
 * <p>
 * Accuracy tests for <code>DraftPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DraftPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link DraftPhaseHandler#DraftPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>DraftPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDraftPhaseHandler_Ctor1() throws Exception {
        assertNotNull("DraftPhaseHandler should be created.",
            new DraftPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link DraftPhaseHandler#DraftPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>DraftPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDraftPhaseHandler_Ctor2() throws Exception {
        assertNotNull("DraftPhaseHandler should be created.",
            new DraftPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link DraftPhaseHandler#DraftPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>DraftPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDraftPhaseHandler_Ctor3() throws Exception {
        assertNotNull("DraftPhaseHandler should be created.",
            new DraftPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link DraftPhaseHandler#DraftPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>DraftPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDraftPhaseHandler_Ctor4() throws Exception {
        assertNotNull("DraftPhaseHandler should be created.",
            new DraftPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The phase type does not match, the phase can not be performed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_PhaseTypeNotMatch() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.NO_SUCH_PHASE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertFalse("The Draft phase can not be performed.",
            this.getDraftPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The phase has already been closed, the phase can not be performed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_PhaseAlreadyClosed() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.CLOSED, CockpitPhaseType.NO_SUCH_PHASE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertFalse("The Draft phase can not be performed.",
            this.getDraftPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The project has a contest attribute, the Draft phase can be performed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_WithContest() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.DRAFT);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Draft phase should be perform-able.", this.getDraftPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The project does not have a contest attribute, the Draft phase can not be performed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_WithoutContest() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.DRAFT);

        assertFalse("The Draft phase should not be perform-able.", this.getDraftPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Draft phase starts, start email should be sent, contest status should be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.DRAFT);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getDraftPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated as Draft
            assertEquals("Contest status should be updated as Draft", contest.getStatus(),
                CockpitContestStatus.DRAFT);
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Test method {@link DraftPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Draft phase ends, end email should be sent.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.DRAFT);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.DRAFT);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getDraftPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.DRAFT);
        } finally {
            this.stopSMTPServer();
        }
    }
}
