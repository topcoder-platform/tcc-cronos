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
 * Accuracy tests for <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class InsufficientSubmissionsRerunPossiblePhaseHandlerTestAcc extends
    BaseTestCase {

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * InsufficientSubmissionsRerunPossiblePhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsRerunPossiblePhaseHandler_Ctor1() throws Exception {
        assertNotNull("InsufficientSubmissionsRerunPossiblePhaseHandler should be created.",
            new InsufficientSubmissionsRerunPossiblePhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * InsufficientSubmissionsRerunPossiblePhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsRerunPossiblePhaseHandler_Ctor2() throws Exception {
        assertNotNull("InsufficientSubmissionsRerunPossiblePhaseHandler should be created.",
            new InsufficientSubmissionsRerunPossiblePhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * InsufficientSubmissionsRerunPossiblePhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsRerunPossiblePhaseHandler_Ctor3() throws Exception {
        assertNotNull("InsufficientSubmissionsRerunPossiblePhaseHandler should be created.",
            new InsufficientSubmissionsRerunPossiblePhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * InsufficientSubmissionsRerunPossiblePhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>InsufficientSubmissionsRerunPossiblePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInsufficientSubmissionsRerunPossiblePhaseHandler_Ctor4() throws Exception {
        assertNotNull("InsufficientSubmissionsRerunPossiblePhaseHandler should be created.",
            new InsufficientSubmissionsRerunPossiblePhaseHandler(TestHelper.ANOTHER_NAMESPACE,
                TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler
     * #canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Insufficient Submissions - ReRun Possible phase can not be performed.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler
     * #canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Insufficient Submissions - ReRun Possible phase can not be performed.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest has ended (Contest.endDate has been reached),  the minimum number of submissions has
     * not been reached, and the contest is not a rerun(Contest.status.description is not "Extended"),
     * the Insufficient Submissions - ReRun Possible phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null, CockpitContestStatus.ACTIVE);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertTrue("The Insufficient Submissions - ReRun Possible phase can start.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The minimum number of submissions has been met,
     * the Insufficient Submissions - ReRun Possible phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_1() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null, CockpitContestStatus.ACTIVE);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The Insufficient Submissions - ReRun Possible phase can not start.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest end date has not been reached,
     * the Insufficient Submissions - ReRun Possible phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_2() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + 2 * ONE_HOUR), null, CockpitContestStatus.ACTIVE);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The Insufficient Submissions - ReRun Possible phase can not start.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest is a re run,
     * the Insufficient Submissions - ReRun Possible phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_3() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null, CockpitContestStatus.EXTENDED);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The Insufficient Submissions - ReRun Possible phase can not start.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#
     * canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Insufficient Submissions - ReRun Possible phase always can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(null, null, null, null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertTrue("The Insufficient Submissions - ReRun Possible phase can end.",
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Insufficient Submissions - ReRun Possible phase starts, start email should be sent,
     * contest status should be updated to "Insufficient Submissions - ReRun Possible".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(null, null, null,
                CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link InsufficientSubmissionsRerunPossiblePhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Insufficient Submissions - ReRun Possible phase ends, end email should be sent,
     * contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN,
            CockpitPhaseType.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        Contest contest = this.createContest(null, null, null,
            CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getInsufficientSubmissionsRerunPossiblePhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.INSUFFICIENT_SUBMISSIONS_RE_RUN_POSSIBLE);
        } finally {
            this.stopSMTPServer();
        }
    }
}
