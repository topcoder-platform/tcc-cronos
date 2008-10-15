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
import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Accuracy tests for <code>InsufficientSubmissionsPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InsufficientSubmissionsPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsPhaseHandler#InsufficientSubmissionsPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsPhaseHandler_Ctor1() throws Exception {
        assertNotNull("InsufficientSubmissionsPhaseHandler should be created.",
            new InsufficientSubmissionsPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsPhaseHandler#InsufficientSubmissionsPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsPhaseHandler_Ctor2() throws Exception {
        assertNotNull("InsufficientSubmissionsPhaseHandler should be created.",
            new InsufficientSubmissionsPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsPhaseHandler#InsufficientSubmissionsPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsPhaseHandler_Ctor3() throws Exception {
        assertNotNull("InsufficientSubmissionsPhaseHandler should be created.",
            new InsufficientSubmissionsPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsPhaseHandler#InsufficientSubmissionsPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsPhaseHandler_Ctor4() throws Exception {
        assertNotNull("InsufficientSubmissionsPhaseHandler should be created.",
            new InsufficientSubmissionsPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The InsufficientSubmissions phase can not be performed.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The InsufficientSubmissions phase can not be performed.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The minimum number of submissions has not been met and the contest has been rerun
     * (check if contest.status.name is "Rerun"),
     * the InsufficientSubmissions phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null,
                    CockpitContestStatus.RERUN);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertTrue("The InsufficientSubmissions phase can start.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The minimum number of submissions has been met,
     * the InsufficientSubmissions phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_1() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null,
                    CockpitContestStatus.RERUN);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The InsufficientSubmissions phase can not start.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest is not a re run,
     * the InsufficientSubmissions phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_2() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null,
                    CockpitContestStatus.ABANDONED);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The InsufficientSubmissions phase can not start.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline is reached,
     * the InsufficientSubmissions phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertTrue("The InsufficientSubmissions phase can end.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * Contest.winnerAnnouncementDeadline is not reached,
     * the InsufficientSubmissions phase can not end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);


        assertFalse("The InsufficientSubmissions phase can not end.",
            this.getInsufficientSubmissionsPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InsufficientSubmissions phase starts, start email should be sent,
     * contest status should be updated to "InsufficientSubmissions".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInsufficientSubmissionsPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.INSUFFICIENT_SUBMISSIONS);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link InsufficientSubmissionsPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The InsufficientSubmissions phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.INSUFFICIENT_SUBMISSIONS);

        Contest contest = this.createContest(null, null, null,
            CockpitContestStatus.INSUFFICIENT_SUBMISSIONS);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInsufficientSubmissionsPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.INSUFFICIENT_SUBMISSIONS);
        } finally {
            this.stopSMTPServer();
        }
    }
}
