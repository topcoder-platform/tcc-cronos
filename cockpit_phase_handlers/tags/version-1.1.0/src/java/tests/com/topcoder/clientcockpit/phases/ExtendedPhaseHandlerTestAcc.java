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
 * Accuracy tests for <code>ExtendedPhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ExtendedPhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link ExtendedPhaseHandler#ExtendedPhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>ExtendedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExtendedPhaseHandler_Ctor1() throws Exception {
        assertNotNull("ExtendedPhaseHandler should be created.",
            new ExtendedPhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link ExtendedPhaseHandler#ExtendedPhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ExtendedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExtendedPhaseHandler_Ctor2() throws Exception {
        assertNotNull("ExtendedPhaseHandler should be created.",
            new ExtendedPhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link ExtendedPhaseHandler#ExtendedPhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>ExtendedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExtendedPhaseHandler_Ctor3() throws Exception {
        assertNotNull("ExtendedPhaseHandler should be created.",
            new ExtendedPhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link ExtendedPhaseHandler#ExtendedPhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ExtendedPhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testExtendedPhaseHandler_Ctor4() throws Exception {
        assertNotNull("ExtendedPhaseHandler should be created.",
            new ExtendedPhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Extended phase can not be performed.",
            this.getExtendedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Extended phase can not be performed.",
            this.getExtendedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest fails to get the required number of submissions,
     * the Extended phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null, null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 2
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 2);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertTrue("The Extended phase can start.", this.getExtendedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest has got the required number of submissions,
     * the Extended phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null, null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        //Set minimum number of submissions to 1
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_MINIMUM_SUBMISSIONS, 1);

        @SuppressWarnings("unchecked")
        Set < Submission > submissions = new HashSet();
        submissions.add(new Submission());
        contest.setSubmissions(submissions);

        assertFalse("The Extended phase can not start.", this.getExtendedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.endDate has been reached,
     * the Extended phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null, null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);

        assertTrue("The Extended phase can end.", this.getExtendedPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The Contest.endDate has not been reached,
     * the Extended phase can not end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() + ONE_HOUR), null, null);
        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST, contest);


        assertFalse("The Extended phase can not end.", this.getExtendedPhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Extended phase starts, start email should be sent,
     * contest status should be updated to "Extended".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getExtendedPhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.EXTENDED);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link ExtendedPhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Extended phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.EXTENDED);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.EXTENDED);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getExtendedPhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.EXTENDED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
