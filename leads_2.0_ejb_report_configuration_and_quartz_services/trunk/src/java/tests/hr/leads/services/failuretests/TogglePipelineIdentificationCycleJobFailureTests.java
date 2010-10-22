/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import hr.leads.services.jobs.TogglePipelineIdentificationCycleJob;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for TogglePipelineIdentificationCycleJob.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleJobFailureTests extends TestCase {
    /**
     * <p>
     * The TogglePipelineIdentificationCycleJob instance for testing.
     * </p>
     */
    private TogglePipelineIdentificationCycleJob instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TogglePipelineIdentificationCycleJob();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TogglePipelineIdentificationCycleJobFailureTests.class);
    }

    /**
     * <p>
     * Tests TogglePipelineIdentificationCycleJob#execute(JobExecutionContext) for failure.
     * It tests the case that when context is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            instance.execute(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}