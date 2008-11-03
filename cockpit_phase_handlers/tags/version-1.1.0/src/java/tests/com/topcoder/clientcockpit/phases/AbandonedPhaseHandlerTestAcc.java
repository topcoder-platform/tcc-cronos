/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.Date;

import com.topcoder.clientcockpit.phases.TestHelper.CockpitContestStatus;
import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;

/**
 * <p>
 * Accuracy tests for <code>AbandonedPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbandonedPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link AbandonedPhaseHandler#AbandonedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>AbandonedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAbandonedPhaseHandler_Ctor1() throws Exception {
        assertNotNull("AbandonedPhaseHandler should be created.",
            new AbandonedPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link AbandonedPhaseHandler#AbandonedPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>AbandonedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAbandonedPhaseHandler_Ctor2() throws Exception {
        assertNotNull("AbandonedPhaseHandler should be created.",
            new AbandonedPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link AbandonedPhaseHandler#AbandonedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>AbandonedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAbandonedPhaseHandler_Ctor3() throws Exception {
        assertNotNull("AbandonedPhaseHandler should be created.",
            new AbandonedPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link AbandonedPhaseHandler#AbandonedPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>AbandonedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAbandonedPhaseHandler_Ctor4() throws Exception {
        assertNotNull("AbandonedPhaseHandler should be created.",
            new AbandonedPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Abandoned phase can not be performed.",
            this.getAbandonedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Abandoned phase can not be performed.",
            this.getAbandonedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.winnerAnnouncementDeadline is reached, the Abandoned phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null));

        assertTrue("The Abandoned phase can start.", this.getAbandonedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.winnerAnnouncementDeadline is not reached, the Abandoned phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null));

        assertFalse("The Abandoned phase can not start.", this.getAbandonedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Abandoned phase can always end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.ABANDONED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Abandoned phase can end.", this.getAbandonedPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Abandoned phase starts, start email should be sent, contest status should be updated to "Abandoned".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getAbandonedPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.ABANDONED);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link AbandonedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Abandoned phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.ABANDONED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.ABANDONED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getAbandonedPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.ABANDONED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
