/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import java.util.Date;

import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for DefaultEmailMessageGenerator.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DefaultEmailMessageGeneratorFailureTests extends TestCase {
    /**
     * <p>
     * The DefaultEmailMessageGenerator instance for testing.
     * </p>
     */
    private DefaultEmailMessageGenerator instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new DefaultEmailMessageGenerator();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
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
        return new TestSuite(DefaultEmailMessageGeneratorFailureTests.class);
    }

    /**
     * <p>
     * Tests DefaultEmailMessageGenerator#generateMessage(Template,Phase) for failure.
     * It tests the case that when template is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateMessage_NullTemplate() throws Exception {
        try {
            instance.generateMessage(null, this.createPhase(null, null));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DefaultEmailMessageGenerator#generateMessage(Template,Phase) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateMessage_NullPhase() throws Exception {
        try {
            instance.generateMessage(new MockTemplate(), null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
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