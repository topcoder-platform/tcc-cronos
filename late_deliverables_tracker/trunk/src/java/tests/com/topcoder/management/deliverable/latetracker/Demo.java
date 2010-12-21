/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl;
import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl;
import com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtility;

import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * Tests the demo of this component.
 *
 * @author saarixx, myxgyy, sparemax
 * @version 1.1
 */
public class Demo extends BaseTestCase {
    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();
        setupPhases(new long[] {112L}, new long[] {4L}, new long[] {2L}, true);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the API usage of this component.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAPIUsage() throws Exception {
        // Prepare configuration for LateDeliverablesRetriever
        ConfigurationObject lateDeliverablesRetrieverConfig = getConfigurationObject(
            "config/LateDeliverablesRetrieverImpl.xml", LateDeliverablesRetrieverImpl.class.getName());

        // Prepare configuration for LateDeliverableProcessor
        ConfigurationObject lateDeliverableProcessorConfig = getConfigurationObject(
            "config/LateDeliverableProcessorImpl.xml", LateDeliverableProcessorImpl.class.getName());

        // Create an instance of LateDeliverablesRetrieverImpl and configure it
        LateDeliverablesRetriever lateDeliverablesRetriever = new LateDeliverablesRetrieverImpl();
        lateDeliverablesRetriever.configure(lateDeliverablesRetrieverConfig);

        // Create an instance of LateDeliverableProcessorImpl and configure it
        LateDeliverableProcessor lateDeliverableProcessor = new LateDeliverableProcessorImpl();
        lateDeliverableProcessor.configure(lateDeliverableProcessorConfig);

        // Get logger
        Log log = LogFactory.getLog("my_logger");

        // Create LateDeliverablesTracker
        LateDeliverablesTracker lateDeliverablesTracker = new LateDeliverablesTracker(
            lateDeliverablesRetriever, lateDeliverableProcessor, log);
        // Track for late deliverables
        lateDeliverablesTracker.execute();
    }

    /**
     * Tests the command line usage of this component.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCommandLineUsage() throws Exception {
        // This command line can be used to print out the usage string
        LateDeliverablesTrackingUtility.main(new String[] {"-help"});
        // If configuration for the utility is stored in the default namespace
        // of the default configuration file, then the application can be
        // executed without additional arguments
        runMain(new String[0]);
        // To use the custom configuration file the user can provide "-c" switch
        // The user can specify custom import files utility configuration file name and
        // namespace
        // The user can specify the interval between late deliverable checks
        // in the command line (in this example deliverables will be checked every 5
        // minutes)
        runMain(new String[] {"-c=test_files/config/custom_config.properties", "-ns=custom_namespace",
            "-interval=300"});
    }
}