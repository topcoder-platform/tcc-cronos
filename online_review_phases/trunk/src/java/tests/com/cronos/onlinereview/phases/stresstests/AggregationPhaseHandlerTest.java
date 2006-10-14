/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.stresstests;

import java.util.Date;

import com.cronos.onlinereview.phases.AggregationPhaseHandler;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;


/**
 * <p>Stress tests for <code>AggregationPhaseHandler</code>.</p>
 * <p>
 * Since this handler is immutable, so it's naturally thread safe.
 * Here just do benchmark tests.
 * </p>
 *
 * @author assistant
 * @version 1.0
 */
public class AggregationPhaseHandlerTest extends StressBaseTest {

    /**
     * Represents the handler to test.
     */
    private AggregationPhaseHandler handler;

    /**
     * Represents the phase used in this case.
     */
    private Phase phase;

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        handler = new AggregationPhaseHandler("com.cronos.onlinereview.phases.OtherHandler");
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(DEFAULT_ID);
        phase = new Phase(project, DEFAULT_ID);

        // set a phase type to the phase
        PhaseType type = new PhaseType(DEFAULT_ID, "Aggregation");
        phase.setPhaseType(type);
        // set a phase status to the phase
        PhaseStatus status = new PhaseStatus(DEFAULT_ID, "Scheduled");
        phase.setPhaseStatus(status);
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for canPerform(com.topcoder.project.phases.Phase).
     * @throws Exception to JUnit
     */
    public void testCanPerform_FirstLevel() throws Exception {
        startRecord("canPerform", FIRST_LEVEL);
        for (int i = 0; i < FIRST_LEVEL; i++) {
            handler.canPerform(phase);
        }
        endRecord("canPerform", FIRST_LEVEL);
    }

    /**
     * Test method for canPerform(com.topcoder.project.phases.Phase).
     * @throws Exception to JUnit
     */
    public void testCanPerform_SecondLevel() throws Exception {
        startRecord("canPerform", SECOND_LEVEL);
        for (int i = 0; i < SECOND_LEVEL; i++) {
            handler.canPerform(phase);
        }
        endRecord("canPerform", SECOND_LEVEL);
    }

    /**
     * Test method for canPerform(com.topcoder.project.phases.Phase).
     * @throws Exception to JUnit
     */
    public void testCanPerform_ThirdLevel() throws Exception {
        startRecord("canPerform", THIRD_LEVEL);
        for (int i = 0; i < THIRD_LEVEL; i++) {
            handler.canPerform(phase);
        }
        endRecord("canPerform", THIRD_LEVEL);
    }

}
