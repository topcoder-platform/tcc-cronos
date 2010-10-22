/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(SystemConfigurationPropertyServiceBeanFailureTests.suite());
        suite.addTest(ReportSortingComparatorFailureTests.suite());
        suite.addTest(ReportServiceBeanFailureTests.suite());
        suite.addTest(TogglePipelineIdentificationCycleJobFailureTests.suite());
        suite.addTest(TogglePipelineIdentificationCycleDaemonFailureTests.suite());

        return suite;
    }

}
