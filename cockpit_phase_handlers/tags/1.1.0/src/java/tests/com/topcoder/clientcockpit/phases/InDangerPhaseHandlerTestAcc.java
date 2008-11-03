/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.clientcockpit.phases.TestHelper.CockpitContestStatus;
import com.topcoder.clientcockpit.phases.TestHelper.CockpitPhaseType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.submission.ContestResult;

/**
 * <p>
 * Accuracy tests for <code>InDangerPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InDangerPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link InDangerPhaseHandler#InDangerPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>InDangerPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInDangerPhaseHandler_Ctor1() throws Exception {
        assertNotNull("InDangerPhaseHandler should be created.",
            new InDangerPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link InDangerPhaseHandler#InDangerPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InDangerPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInDangerPhaseHandler_Ctor2() throws Exception {
        assertNotNull("InDangerPhaseHandler should be created.",
            new InDangerPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link InDangerPhaseHandler#InDangerPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>InDangerPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInDangerPhaseHandler_Ctor3() throws Exception {
        assertNotNull("InDangerPhaseHandler should be created.",
            new InDangerPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link InDangerPhaseHandler#InDangerPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InDangerPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInDangerPhaseHandler_Ctor4() throws Exception {
        assertNotNull("InDangerPhaseHandler should be created.",
            new InDangerPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The InDanger phase can not be performed.",
            this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The InDanger phase can not be performed.",
            this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest has less than 24 hours before the client is supposed to pick a winner,
     * the InDanger phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertTrue("The InDanger phase can start.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest has more than 24 hours before the client is supposed to pick a winner,
     * the InDanger phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_1() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        final int moreThan24 = 25;
        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + moreThan24 * ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertFalse("The InDanger phase can not start.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Client has already picked a winner,
     * the InDanger phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_2() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);

        assertFalse("The InDanger phase can not start.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline has been reached without a winner chosen,
     * the InDanger phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd_1() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertTrue("The InDanger phase can end.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The client has chosen a winner (Contest.results is not empty),
     * the InDanger phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd_2() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);

        assertTrue("The InDanger phase can end.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline has not been reached, client has not chosen a winner,
     * and time remains for picking a winner is less than eight hours, the InDanger phase can not end,
     * and an eight hours remainder email should be sent.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd_1() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + 2 * ONE_HOUR), null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            assertFalse("The InDanger phase can not end.", this.getInDangerPhaseHandler().canPerform(phase));

            //Assert eight hours email is sent
            this.assertEightHoursEmailSent();
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline has not been reached, client has not chosen a winner,
     * and time remains for picking a winner is less than one hour, the InDanger phase can not end,
     * and an one hour reminder email should be sent.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd_2() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR / 2), null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            assertFalse("The InDanger phase can not end.", this.getInDangerPhaseHandler().canPerform(phase));

            //Assert one hour email is sent
            this.assertOneHourEmailSent();
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline has not been reached, client has not chosen a winner,
     * and time remains for picking a winner is more than eight hours, the InDanger phase can not end,
     * and NO reminder email should be sent.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd_3() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        final int moreThan8 = 9;
        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR * moreThan8),
                new Date(System.currentTimeMillis() + ONE_HOUR * moreThan8), null);

        this.populateAttributes(phase, contest);

        assertFalse("The InDanger phase can not end.", this.getInDangerPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InDanger phase starts, only start email should be sent since more than eight hours remains for picking
     * a winner,
     * contest status should be updated to "InDanger".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start_onlyStartEmail() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        final int moreThan8 = 9;
        Contest contest = this.createContest(null, null, new Date(System.currentTimeMillis() + ONE_HOUR * moreThan8),
            null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInDangerPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.IN_DANGER);
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InDanger phase starts, start email should be sent, one hour email is also sent since less than one hour
     * remains for picking a winner,
     * contest status should be updated to "InDanger".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start_startEmailAndOneHourEmail() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(null, null, new Date(System.currentTimeMillis() + ONE_HOUR / 2), null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInDangerPhaseHandler().perform(phase, "operator");

            //Assert start email and one hour email are sent
            this.assertStartEmailAndOneHourSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.IN_DANGER);
        } finally {
            this.stopSMTPServer();
        }
    }


    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InDanger phase starts, start email should be sent, eight hours email is also sent since less than eight hours
     * remains for picking a winner,
     * contest status should be updated to "InDanger".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start_startEmailAndEightHoursEmail() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(null, null, new Date(System.currentTimeMillis() + ONE_HOUR * 2), null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInDangerPhaseHandler().perform(phase, "operator");

            //Assert start email and eight hours email are sent
            this.assertStartEmailAndEightHoursSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.IN_DANGER);
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Test method {@link InDangerPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InDanger phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.IN_DANGER);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.IN_DANGER);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInDangerPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.IN_DANGER);
        } finally {
            this.stopSMTPServer();
        }
    }
}
