/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Base class for stress test cases.
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
class StressTestCase extends TestCase {

    /**
     * <p>
     * Represents the test loop count of the stress tests.
     * </p>
     */
    protected static final int LOOP = 100;

    /**
     * <p>
     * Represents the address of email to be sent used for stress test.
     * </p>
     */
    protected final static String EMAIL_ADDRESS = "stresstest@topcoder.com";

    /**
     * <p>
     * Represents the "ResourceEmails" tag for stress test.
     * </p>
     */
    protected final static String RESOURCE_EMAILS = "ResourceEmails";

    /**
     * <p>
     * Represents the "contest" tag for stress test.
     * </p>
     */
    protected final static String CONTEST = "contest";

    /**
     * <p>
     * Represents the Date instance used for setting phase attribute "date" for stress test.
     * </p>
     */
    protected final static Date DATE = new Date();

    /**
     * <p>
     * Represents the String instance used for setting phase attribute "operator" for stress test.
     * </p>
     */
    protected final static String OPERATOR = "littleken";

    /**
     * <p>
     * Represents the Long instance used for setting phase attribute "cost" for stress test.
     * </p>
     */
    protected final static Long COST = 500L;

    /**
     * <p>
     * Represents the start time of the test.
     * </p>
     */
    private long startTime = 0;

    /**
     * <p>
     * Represents the stop time of the test.
     * </p>
     */
    private long stopTime = 0;

    /**
     * <p>
     * Represents whether the test is running or not.
     * </p>
     */
    private boolean running = false;

    /**
     * <p>
     * Set up the test case. Initialize the config manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void setUp() throws Exception {
        super.setUp();

        StressHelper.setUpConfig();
    }

    /**
     * <p>
     * Tear down the test case. Clear the config manager.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void tearDown() throws Exception {
        StressHelper.clearConfig();

        super.tearDown();
    }

    /**
     * <p>
     * Start the stop watch.
     * </p>
     *
     * @throws IllegalStateException
     *             if the test is already running.
     */
    protected void start() {
        if (running) {
            throw new IllegalStateException("The test is running, stop it first.");
        } else {
            running = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * <p>
     * Stop the stop watch.
     * </p>
     *
     * @throws IllegalStateException
     *             if the test is not running.
     */
    protected void stop() {
        if (!running) {
            throw new IllegalStateException("The test is not running, start it first.");
        } else {
            running = false;
            stopTime = System.currentTimeMillis();
        }
    }

    /**
     * <p>
     * Get the test duration.
     * </p>
     *
     * @return the test duration in milliseconds.
     */
    protected long getMilliseconds() {
        return (stopTime - startTime);
    }

    /**
     * <p>
     * Start SMTP server for stress tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void startServer() throws Exception {
        StressHelper.getTestSMTPServer().start();
    }

    /**
     * <p>
     * Stop SMTP server for stress tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void stopServer() throws Exception {
        StressHelper.getTestSMTPServer().stop();
    }
}
