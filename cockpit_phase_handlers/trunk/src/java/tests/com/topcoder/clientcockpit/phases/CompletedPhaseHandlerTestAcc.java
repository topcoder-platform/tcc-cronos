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
import com.topcoder.service.studio.submission.Submission;

/**
 * <p>
 * Accuracy tests for <code>CompletedPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompletedPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>CompletedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Ctor1() throws Exception {
        assertNotNull("CompletedPhaseHandler should be created.",
            new CompletedPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>CompletedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Ctor2() throws Exception {
        assertNotNull("CompletedPhaseHandler should be created.",
            new CompletedPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>CompletedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Ctor3() throws Exception {
        assertNotNull("CompletedPhaseHandler should be created.",
            new CompletedPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link CompletedPhaseHandler#CompletedPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>CompletedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCompletedPhaseHandler_Ctor4() throws Exception {
        assertNotNull("CompletedPhaseHandler should be created.",
            new CompletedPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Completed phase can not be performed.",
            this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Completed phase can not be performed.",
            this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.endDate has been reached and the minimum number of submissions has been reached,
     * and client has chosen a winner,
     * the Completed phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest =
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR), null);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);

        assertTrue("The Completed phase can start.", this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.endDate has not been reached, the Completed phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_1() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest =
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                    new Date(System.currentTimeMillis() + ONE_HOUR),
                    new Date(System.currentTimeMillis() + ONE_HOUR), null);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);

        assertFalse("The Completed phase can not start.", this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The minimum number of submissions has not been reached, the Completed phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_2() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest =
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR), null);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        @SuppressWarnings("unchecked")
        Set < ContestResult > results = new HashSet();
        results.add(new ContestResult());
        contest.setResults(results);

        assertFalse("The Completed phase can not start.", this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The client has not picked a winner, the Completed phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_3() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest =
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR),
                    new Date(System.currentTimeMillis() - ONE_HOUR), null);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The Completed phase can not start.", this.getCompletedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Completed phase can always end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.COMPLETED);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(null, null, null, null));

        assertTrue("The Completed phase can end.", this.getCompletedPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Completed phase starts, start email should be sent,
     * contest status should be updated to "Completed".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.COMPLETED);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link CompletedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Completed phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.COMPLETED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.COMPLETED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getCompletedPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.COMPLETED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
