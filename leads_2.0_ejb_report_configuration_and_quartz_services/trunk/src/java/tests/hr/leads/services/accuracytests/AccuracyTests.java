/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all accuracy test cases.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(BaseReportConfigurationServiceBeanAccuracyTest.class);
        suite.addTestSuite(ReportServiceBeanAccuracyTests.class);
        suite.addTestSuite(ReportSortingComparatorAccuracyTests.class);
        suite.addTestSuite(SystemConfigurationPropertyServiceBeanAccuracyTests.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleDaemonExceptionAccuracyTest.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleJobAccuracyTests.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleJobExecutionExceptionAccuracyTest.class);

        return suite;
    }
}
