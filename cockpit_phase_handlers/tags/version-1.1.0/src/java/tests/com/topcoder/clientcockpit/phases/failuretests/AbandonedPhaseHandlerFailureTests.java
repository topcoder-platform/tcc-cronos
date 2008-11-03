/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import java.util.Date;

import com.topcoder.clientcockpit.phases.AbandonedPhaseHandler;
import com.topcoder.clientcockpit.phases.failuretests.TestHelper.CockpitPhaseType;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.management.phase.PhaseHandlingException;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.service.studio.contest.ContestManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for AbandonedPhaseHandler.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AbandonedPhaseHandlerFailureTests extends TestCase {
    /**
     * <p>
     * The AbandonedPhaseHandler instance for testing.
     * </p>
     */
    private AbandonedPhaseHandler instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial",
            "com.topcoder.clientcockpit.phases.failuretests.MockInitialContextFactory");
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);

        instance = new AbandonedPhaseHandler();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        System.clearProperty("java.naming.factory.initial");
        TestHelper.clearConfig();
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AbandonedPhaseHandlerFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullNamespace() throws Exception {
        try {
            new AbandonedPhaseHandler(null, new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_EmptyNamespace() throws Exception {
        try {
            new AbandonedPhaseHandler(" ", new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullBean() throws Exception {
        try {
            new AbandonedPhaseHandler("com.topcoder.clientcockpit.phases", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullNamespace() throws Exception {
        try {
            new AbandonedPhaseHandler((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyNamespace() throws Exception {
        try {
            new AbandonedPhaseHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor AbandonedPhaseHandler#AbandonedPhaseHandler(ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullBean() throws Exception {
        try {
            new AbandonedPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#canPerform(Phase) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCanPerform_NullPhase() throws Exception {
        try {
            instance.canPerform(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#canPerform(Phase) for failure.
     * </p>
     *
     * <p>
     * The phase's status is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullPhaseStatus() throws Exception {
        Phase phase = this.createPhase(null, CockpitPhaseType.ABANDONED);

        try {
            instance.canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#canPerform(Phase) for failure.
     * </p>
     *
     * <p>
     * The phase's type is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullPhaseType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, null);

        try {
            instance.canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#canPerform(Phase) for failure.
     * </p>
     *
     * <p>
     * The "contest" attribute value is null,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_NullContestAttr() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        try {
            instance.canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#canPerform(Phase) for failure.
     * </p>
     *
     * <p>
     * The "contest" attribute value is not type of <code>Contest</code>,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCanPerform_ContestAttr_WrongType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        phase.getProject().setAttribute("contest", "WrongType");
        try {
            instance.canPerform(phase);
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#perform(Phase,String) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testPerform_NullPhase() throws Exception {
        try {
            instance.perform(null, "Abandoned");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#perform(Phase,String) for failure.
     * </p>
     *
     * <p>
     * The phase's status is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullPhaseStatus() throws Exception {
        Phase phase = this.createPhase(null, CockpitPhaseType.ABANDONED);

        try {
            instance.perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#perform(Phase,String) for failure.
     * </p>
     *
     * <p>
     * The phase's type is null, <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullPhaseType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, null);

        try {
            instance.perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#perform(Phase,String) for failure.
     * </p>
     *
     * <p>
     * The "contest" attribute value is null,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_NullContestAttr() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        try {
            instance.perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Tests AbandonedPhaseHandler#perform(Phase,String) for failure.
     * </p>
     *
     * <p>
     * The "contest" attribute value is not type of <code>Contest</code>,
     * <code>PhaseHandlingException</code> expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testPerform_ContestAttr_WrongType() throws Exception {
        Phase phase = this.createPhase(PhaseStatus.SCHEDULED, CockpitPhaseType.ABANDONED);

        phase.getProject().setAttribute("contest", "WrongType");
        try {
            instance.perform(phase, "operator");
            fail("PhaseHandlingException is expected.");
        } catch (PhaseHandlingException e) {
            //pass
        }
    }

    /**
     * <p>
     * Create a <code>Phase</code>.
     * </p>
     *
     * @param phaseStatus phase status.
     * @param phaseType phase type.
     *
     * @return instance of <code>Phase</code> created.
     */
    private Phase createPhase(PhaseStatus phaseStatus, PhaseType phaseType) {
        Workdays workdays = new DefaultWorkdays();
        Project project = new Project(new Date(), workdays);
        Phase phase = new Phase(project, System.currentTimeMillis());
        project.addPhase(phase);

        if (phaseStatus != null) {
            phase.setPhaseStatus(phaseStatus);
        }
        if (phaseType != null) {
            phase.setPhaseType(phaseType);
        }

        return phase;
    }
}