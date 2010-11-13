/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import com.topcoder.management.deliverable.latetracker.processors.EmailSendingExceptionTests;
import com.topcoder.management.deliverable.latetracker.processors.EmailSendingUtilityTests;
import com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImplTests;
import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImplTests;
import com.topcoder.management.deliverable.latetracker.utility.LateDeliverablesTrackingUtilityTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Adds all test suites to the unit test suite and returns the unit test suite.
     *
     * @return the unit test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(Demo.class);

        suite.addTestSuite(LateDeliverablesTrackingJobRunnerTests.class);
        suite.addTestSuite(LateDeliverablesTrackerTests.class);
        suite.addTestSuite(LateDeliverableTests.class);
        suite.addTestSuite(LateDeliverablesProcessingExceptionTests.class);
        suite.addTestSuite(LateDeliverablesRetrievalExceptionTests.class);
        suite.addTestSuite(LateDeliverablesTrackerConfigurationExceptionTests.class);
        suite.addTestSuite(LateDeliverablesTrackingExceptionTests.class);
        suite.addTestSuite(HelperTests.class);

        suite.addTestSuite(LateDeliverablesRetrieverImplTests.class);

        suite.addTestSuite(EmailSendingExceptionTests.class);
        suite.addTestSuite(EmailSendingUtilityTests.class);
        suite.addTestSuite(LateDeliverableProcessorImplTests.class);

        suite.addTestSuite(LateDeliverablesTrackingUtilityTests.class);

        return suite;
    }
}
