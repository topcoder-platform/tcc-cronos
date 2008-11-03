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
 * Accuracy tests for <code>ActivePhaseHandler</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActivePhaseHandlerTestAcc extends BaseTestCase {

    /**
     * <p>
     * Test constructor {@link ActivePhaseHandler#ActivePhaseHandler()}.
     * </p>
     *
     * <p>
     * The instance of <code>ActivePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testActivePhaseHandler_Ctor1() throws Exception {
        assertNotNull("ActivePhaseHandler should be created.",
            new ActivePhaseHandler());
    }

    /**
     * <p>
     * Test constructor {@link ActivePhaseHandler#ActivePhaseHandler(
     * com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ActivePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testActivePhaseHandler_Ctor2() throws Exception {
        assertNotNull("ActivePhaseHandler should be created.",
            new ActivePhaseHandler(TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test constructor {@link ActivePhaseHandler#ActivePhaseHandler(String)}.
     * </p>
     *
     * <p>
     * The instance of <code>ActivePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testActivePhaseHandler_Ctor3() throws Exception {
        assertNotNull("ActivePhaseHandler should be created.",
            new ActivePhaseHandler(TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Test constructor {@link ActivePhaseHandler#ActivePhaseHandler(
     * String, com.topcoder.service.studio.contest.ContestManager)}.
     * </p>
     *
     * <p>
     * The instance of <code>ActivePhaseHandler</code> should be created.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testActivePhaseHandler_Ctor4() throws Exception {
        assertNotNull("ActivePhaseHandler should be created.",
            new ActivePhaseHandler(TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Active phase can not be performed.",
            this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
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

        assertFalse("The Active phase can not be performed.",
            this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest status is "Scheduled", the contest start date has been reached, the Active phase can start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanStart() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ACTIVE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - ONE_HOUR), new Date(), null,
                CockpitContestStatus.SCHEDULED));

        assertTrue("The Active phase can start.", this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest status is not "Scheduled", the Active phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_1() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ACTIVE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - ONE_HOUR), new Date(), null,
                CockpitContestStatus.CANCELLED));

        assertFalse("The Active phase can not start.", this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest start date has not been reached, the Active phase can not start.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotStart_2() throws Exception {
        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ACTIVE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + 2 * ONE_HOUR), null,
                CockpitContestStatus.CANCELLED));

        assertFalse("The Active phase can not start.", this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest end date has been reached, the Active phase can end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.ACTIVE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR),
                new Date(System.currentTimeMillis() - ONE_HOUR), null,
                CockpitContestStatus.ACTIVE));

        assertTrue("The Active phase can end.", this.getActivePhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Test method {@link ActivePhaseHandler#canPerform(com.topcoder.project.phases.Phase)}.
     * </p>
     *
     * <p>
     * The contest end date has not been reached, the Active phase can not end.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_CanNotEnd() throws Exception {
        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.ACTIVE);

        //Set contest attribute
        phase.getProject().setAttribute(TestHelper.PROJECT_ATTR_CONTEST,
            this.createContest(new Date(System.currentTimeMillis() + ONE_HOUR),
                new Date(System.currentTimeMillis() + 2 * ONE_HOUR), null,
                CockpitContestStatus.ACTIVE));

        assertFalse("The Active phase can not end.", this.getActivePhaseHandler().canPerform(phase));
    }


    /**
     * <p>
     * Test method {@link ActivePhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Active phase starts, start email should be sent, contest status should be updated to "Active".
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_Start() throws Exception {

        //PhaseStatus.SCHEDULED means the phase is about to start
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ACTIVE);

        Contest contest = this.createContest(null, null, null, null);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getActivePhaseHandler().perform(phase, "operator");

            //Assert start email is sent
            this.assertStartEmailSent();

            //Assert the contest status is updated
            assertEquals("Contest status should be updated", contest.getStatus(),
                CockpitContestStatus.ACTIVE);
        } finally {
            this.stopSMTPServer();
        }
    }
    /**
     * <p>
     * Test method {@link ActivePhaseHandler#perform(Phase, String)}.
     * </p>
     *
     * <p>
     * The Active phase ends, end email should be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_End() throws Exception {

        //PhaseStatus.OPEN means the phase is about to end
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.ACTIVE);

        Contest contest = this.createContest(null, null, null, CockpitContestStatus.ACTIVE);

        this.populateAttributes(phase, contest);

        //Mock ContestManager.getAllContestStatuses()
        this.mockContestManager(false);

        this.startSMTPServer();

        try {
            this.getActivePhaseHandler().perform(phase, "operator");

            //Assert end email is sent
            this.assertEndEmailSent();

            //Assert the contest status is not changed
            assertEquals("Contest status should not be changed", contest.getStatus(),
                    CockpitContestStatus.ACTIVE);
        } finally {
            this.stopSMTPServer();
        }
    }
}
