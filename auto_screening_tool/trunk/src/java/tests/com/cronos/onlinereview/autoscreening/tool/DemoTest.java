/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManager;

/**
 * This TestCase demonstrates the usage of this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends BaseTestCase {

    /**
     * Represents the path of the screener config file.
     */
    protected static final String SCREENER_CONFIG_FILE = "screener.xml";

    /**
     * Represents the path of the object factory config file.
     */
    protected static final String OBJECT_FACTORY_CONFIG_FILE = "object_factory.xml";

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
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
     * Demos how to use Screener class.
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testScreener() throws Exception {

        // Create the Screener instance.
        Screener screener = new Screener(23, "com.cronos.onlinereview.autoscreening.tool.Screener");

        // First rollback the screening tasks of screener id 23 with status
        // 'screening' back to 'pending'.
        screener.initialize();

        try {
            // Second, call screen() to select a task and perform screening.
            screener.screen();
        } catch (ScreeningException e) {
            // may throw ScreeningException.
            assertTrue("check message", e.getMessage().startsWith(
                "Unable to find the screening logics "
                    + "for the selected screening task with taskId ["));
        }
    }
}