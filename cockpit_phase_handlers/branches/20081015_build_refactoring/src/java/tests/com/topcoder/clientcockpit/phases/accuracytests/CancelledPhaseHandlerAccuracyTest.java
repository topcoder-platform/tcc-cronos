/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.accuracytests;

import java.util.Date;

import com.topcoder.clientcockpit.phases.CancelledPhaseHandler;
import com.topcoder.clientcockpit.phases.accuracytests.TestHelper.CockpitContestStatus;
import com.topcoder.clientcockpit.phases.accuracytests.TestHelper.CockpitPhaseType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.service.studio.contest.Contest;

/**
 * <p>
 * Accuracy tests for the <code>CancelledPhaseHandler</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CancelledPhaseHandlerAccuracyTest extends BaseTestCase {

    /**
     * <p>
     * Accuracy test for the constructor <code>CancelledPhaseHandler()</code>, expects the instance is created
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor1Accuracy() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.", new CancelledPhaseHandler());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CancelledPhaseHandler(ContestManager)</code>, expects the instance
     * is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor2Accuracy() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.", new CancelledPhaseHandler(
                TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CancelledPhaseHandler(String)</code>, expects the instance is
     * created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor3Accuracy() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.", new CancelledPhaseHandler(
                TestHelper.ANOTHER_NAMESPACE));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CancelledPhaseHandler(String, ContestManager)</code>, expects the
     * instance is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCtor4Accuracy() throws Exception {
        assertNotNull("CancelledPhaseHandler should be created.", new CancelledPhaseHandler(
                TestHelper.ANOTHER_NAMESPACE, TestHelper.CONTEST_MANAGER));
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method with the phase type does not match, false should
     * be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCanPerformAccuracy1() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.NO_SUCH_PHASE);
        phase.getProject().setAttribute("contest", this.createContest(null, null, null, null));

        assertFalse("False should be returned.", this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method with the phase has already been closed, false
     * should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCanPerformAccuracy2() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.CLOSED, CockpitPhaseType.CANCELLED);

        // Set contest attribute
        phase.getProject().setAttribute("contest", this.createContest(null, null, null, null));

        assertFalse("False should be returned.", this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCanPerformAccuracy3() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.CANCELLED);
        phase.getProject().setAttribute(
                "contest",
                this.createContest(new Date(System.currentTimeMillis() - 2 * ONE_HOUR), new Date(System
                        .currentTimeMillis()
                        + ONE_HOUR), new Date(System.currentTimeMillis() - ONE_HOUR), null));

        assertTrue("True should be returned.", this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCanPerformAccuracy4() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.CANCELLED);
        phase.getProject().setAttribute("contest", this.createContest(null, null, null, null));

        assertTrue("True should be returned.", this.getCancelledPhaseHandler().canPerform(phase));
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method with the Abandoned phase starts, start email
     * should be sent, contest status should be updated to "Abandoned".
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformAccuracy1() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.CANCELLED);
        Contest contest = this.createContest(null, null, null, null);
        this.populateAttributes(phase, contest);
        this.mockContestManager(false);

        this.startSMTPServer();
        try {
            this.getCancelledPhaseHandler().perform(phase, "operator");
            this.assertStartEmailSent();
            assertEquals("Contest status should be updated", contest.getStatus(), CockpitContestStatus.CANCELLED);
        } finally {
            this.stopSMTPServer();
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>canPerform(Phase)</code> method with the Abandoned phase ends, end email should
     * be sent, contest status should NOT be updated.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testPerformAccuracy2() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.OPEN, CockpitPhaseType.CANCELLED);
        Contest contest = this.createContest(null, null, null, CockpitContestStatus.CANCELLED);
        this.populateAttributes(phase, contest);
        this.mockContestManager(false);

        this.startSMTPServer();
        try {
            this.getCancelledPhaseHandler().perform(phase, "operator");
            this.assertEndEmailSent();
            assertEquals("Contest status should not be changed", contest.getStatus(), CockpitContestStatus.CANCELLED);
        } finally {
            this.stopSMTPServer();
        }
    }
}
