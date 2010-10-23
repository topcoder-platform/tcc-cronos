/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemon;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemonException;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for TogglePipelineIdentificationCycleDaemon.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class TogglePipelineIdentificationCycleDaemonFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(TogglePipelineIdentificationCycleDaemonFailureTests.class);
    }

    /**
     * <p>
     * Tests TogglePipelineIdentificationCycleDaemon#main(String[]) for failure.
     * It tests the case that when length of args is more than one and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMain_InvalidArgs() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"value1", "value2"});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TogglePipelineIdentificationCycleDaemon#main(String[]) for failure.
     * It tests the case that when args contains null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMain_NullInArgs() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TogglePipelineIdentificationCycleDaemon#main(String[]) for failure.
     * It tests the case that when args contains empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMain_EmptyInArgs() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TogglePipelineIdentificationCycleDaemon#main(String[]) for failure.
     * Expects TogglePipelineIdentificationCycleDaemonException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testMain_TogglePipelineIdentificationCycleDaemonException() throws Exception {
        try {
            TogglePipelineIdentificationCycleDaemon.main(new String[] {"NoExist"});
            fail("TogglePipelineIdentificationCycleDaemonException expected.");
        } catch (TogglePipelineIdentificationCycleDaemonException e) {
            //good
        }
    }
}