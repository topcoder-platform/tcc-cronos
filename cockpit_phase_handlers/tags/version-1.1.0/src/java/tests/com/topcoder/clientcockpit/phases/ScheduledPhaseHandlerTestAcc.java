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
 * Accuracy tests for <code>ScheduledPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScheduledPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link ScheduledPhaseHandler#ScheduledPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>ScheduledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduledPhaseHandler_Ctor1() throws Exception {
        assertNotNull("ScheduledPhaseHandler should be created.",
            new ScheduledPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link ScheduledPhaseHandler#ScheduledPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ScheduledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduledPhaseHandler_Ctor2() throws Exception {
        assertNotNull("ScheduledPhaseHandler should be created.",
            new ScheduledPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link ScheduledPhaseHandler#ScheduledPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>ScheduledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduledPhaseHandler_Ctor3() throws Exception {
        assertNotNull("ScheduledPhaseHandler should be created.",
            new ScheduledPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link ScheduledPhaseHandler#ScheduledPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ScheduledPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testScheduledPhaseHandler_Ctor4() throws Exception {
        assertNotNull("ScheduledPhaseHandler should be created.",
            new ScheduledPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Scheduled phase can not be performed.",
            this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Scheduled phase can not be performed.",
            this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest status is "Draft", the Scheduled phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.SCHEDULED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(), new Date(), null, CockpitContestStatus.DRAFT));

        assertTrue("The Scheduled phase can start.", this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest status is not "Draft", the Scheduled phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.SCHEDULED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(), new Date(), null, CockpitContestStatus.COMPLETED));

        assertFalse("The Scheduled phase can not start.", this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.startDate has been reached and Contest.endDate has not been reached, the Scheduled phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.SCHEDULED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null, CockpitContestStatus.SCHEDULED));

        assertTrue("The Scheduled phase can end.", this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.startDate has not been reached , the Scheduled phase can not end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd_1() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.SCHEDULED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + 2 * ONE_HOUR), null, CockpitContestStatus.SCHEDULED));

        assertFalse("The Scheduled phase can not end.", this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.endDate has been reached, the Scheduled phase can not end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd_2() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.SCHEDULED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null, CockpitContestStatus.SCHEDULED));

        assertFalse("The Scheduled phase can not end.", this.getScheduledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Scheduled phase starts, start email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.SCHEDULED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getScheduledPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is not changed
            assertNull("Contest status should not be changed", contest.getStatus());
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link ScheduledPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Scheduled phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.SCHEDULED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.SCHEDULED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getScheduledPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                CockpitContestStatus.SCHEDULED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
