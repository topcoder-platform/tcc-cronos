/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.onlinereview.autoscreening.tool.ScreeningTool.ScreeningJob;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.scheduler.Schedulable;

/**
 * The unit test cases for class ScreeningJob.
 * @author urtks
 * @version 1.0
 */
public class ScreeningJobTest extends BaseTestCase {

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE = "screener.xml";

    /**
     * Represents the path of the object factory config file.
     */
    protected static final String OBJECT_FACTORY_CONFIG_FILE = "object_factory.xml";

    /**
     * Represents the config namespace of the screener.
     */
    protected static final String SCREENER_NAMESPACE = "com.cronos.onlinereview.autoscreening.tool.Screener";

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningJobTest.class);
    }

    /**
     * Sets up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager.getInstance().add(OBJECT_FACTORY_CONFIG_FILE);
        ConfigManager.getInstance().add(SCREENER_CONFIG_FILE);
    }

    /**
     * Clean up the test environment.
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>ScreeningJob()</code>.
     * </p>
     * <p>
     * usage should be printed.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyCtor1() throws Exception {
        ScreeningJob job = new ScreeningJob();

        assertNotNull("check instance", job);
        assertTrue("check inheritance", job instanceof Runnable);
        assertTrue("check inheritance", job instanceof Schedulable);
        assertEquals("check status", "Screening", job.getStatus());
        assertEquals("check isDone", false, job.isDone());
    }

    /**
     * <p>
     * Failure test of the method <code>run()</code>.
     * </p>
     * <p>
     * screener instance has not been created. ScreeningExcpetion is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureRun1() throws Exception {

        try {
            new ScreeningJob().run();
            fail("ScreeningException should be thrown.");
        } catch (ScreeningException e) {
            assertEquals("check message",
                "The screener instance for this screening job has not been created.", e
                    .getMessage());
        }
    }
}