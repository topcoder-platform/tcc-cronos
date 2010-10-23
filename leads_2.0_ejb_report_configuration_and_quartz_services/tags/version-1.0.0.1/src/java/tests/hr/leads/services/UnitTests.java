/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services;

import hr.leads.services.ejb.BaseReportConfigurationServiceBeanTest;
import hr.leads.services.ejb.ReportHelperTest;
import hr.leads.services.ejb.ReportServiceBeanTest;
import hr.leads.services.ejb.ReportSortingComparatorTest;
import hr.leads.services.ejb.SystemConfigurationPropertyServiceBeanTest;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemonExceptionTest;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleDaemonTest;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleJobExecutionExceptionTest;
import hr.leads.services.jobs.TogglePipelineIdentificationCycleJobTest;
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
     * <p>
     * Tests all the suites of the all classes.
     * </p>
     *
     * @return the test of all suites.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package: hr.leads.services.ejb
        suite.addTestSuite(ReportServiceBeanTest.class);
        suite.addTestSuite(SystemConfigurationPropertyServiceBeanTest.class);
        suite.addTestSuite(ReportSortingComparatorTest.class);
        suite.addTestSuite(BaseReportConfigurationServiceBeanTest.class);
        suite.addTestSuite(ReportHelperTest.class);

        // package: hr.leads.services.jobs
        suite.addTestSuite(TogglePipelineIdentificationCycleDaemonExceptionTest.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleDaemonTest.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleJobExecutionExceptionTest.class);
        suite.addTestSuite(TogglePipelineIdentificationCycleJobTest.class);

        // demo:
        suite.addTestSuite(DemoTest.class);
        return suite;
    }

}
