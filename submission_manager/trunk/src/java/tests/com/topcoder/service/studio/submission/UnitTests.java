/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(EntityExistsExceptionUnitTests.suite());
        suite.addTest(EntityNotFoundExceptionUnitTests.suite());
        suite.addTest(SubmissionManagementConfigurationExceptionUnitTests.suite());
        suite.addTest(SubmissionManagementExceptionUnitTests.suite());
        suite.addTest(InconsistentContestsExceptionUnitTests.suite());
        suite.addTest(NumberOfSubmissionsExceededExceptionUnitTests.suite());
        suite.addTest(SubmissionManagerBeanUnitTests.suite());
        suite.addTest(Demo.suite());
        return suite;
    }
}
