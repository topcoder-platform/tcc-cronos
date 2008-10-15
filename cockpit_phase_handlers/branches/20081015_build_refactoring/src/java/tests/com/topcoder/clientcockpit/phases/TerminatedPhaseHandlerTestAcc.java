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
 * Accuracy tests for <code>TerminatedPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TerminatedPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link TerminatedPhaseHandler#TerminatedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>TerminatedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTerminatedPhaseHandler_Ctor1() throws Exception {
        assertNotNull("TerminatedPhaseHandler should be created.",
            new TerminatedPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link TerminatedPhaseHandler#TerminatedPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>TerminatedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTerminatedPhaseHandler_Ctor2() throws Exception {
        assertNotNull("TerminatedPhaseHandler should be created.",
            new TerminatedPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link TerminatedPhaseHandler#TerminatedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>TerminatedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTerminatedPhaseHandler_Ctor3() throws Exception {
        assertNotNull("TerminatedPhaseHandler should be created.",
            new TerminatedPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link TerminatedPhaseHandler#TerminatedPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>TerminatedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testTerminatedPhaseHandler_Ctor4() throws Exception {
        assertNotNull("TerminatedPhaseHandler should be created.",
            new TerminatedPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Terminated phase can not be performed.",
            this.getTerminatedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Terminated phase can not be performed.",
            this.getTerminatedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "Terminated" phase can always end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.TERMINATED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Terminated phase can end.", this.getTerminatedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "Terminated" phase can always start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.TERMINATED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Terminated phase can start.", this.getTerminatedPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Terminated phase starts, start email should be sent, contest status should be updated to "Terminated".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.TERMINATED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getTerminatedPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.TERMINATED);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link TerminatedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Terminated phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.TERMINATED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.TERMINATED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getTerminatedPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.TERMINATED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
