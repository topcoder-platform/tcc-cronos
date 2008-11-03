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
 * Accuracy tests for <code>CancelledPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CancelledPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link CancelledPhaseHandler#CancelledPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>CancelledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCancelledPhaseHandler_Ctor1() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.",
            new CancelledPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link CancelledPhaseHandler#CancelledPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>CancelledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCancelledPhaseHandler_Ctor2() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.",
            new CancelledPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link CancelledPhaseHandler#CancelledPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>CancelledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCancelledPhaseHandler_Ctor3() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.",
            new CancelledPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link CancelledPhaseHandler#CancelledPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>CancelledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCancelledPhaseHandler_Ctor4() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.",
            new CancelledPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Cancelled phase can not be performed.",
            this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Cancelled phase can not be performed.",
            this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "Cancelled" phase can always end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.CANCELLED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Cancelled phase can end.", this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The "Cancelled" phase can always start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.CANCELLED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Cancelled phase can start.", this.getCancelledPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Cancelled phase starts, start email should be sent, contest status should be updated to "Cancelled".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.CANCELLED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getCancelledPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.CANCELLED);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link CancelledPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Cancelled phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.CANCELLED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.CANCELLED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getCancelledPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.CANCELLED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
