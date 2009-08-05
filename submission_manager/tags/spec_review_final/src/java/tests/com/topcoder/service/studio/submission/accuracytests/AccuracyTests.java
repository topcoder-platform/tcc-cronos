/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

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
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(EntityExistsExceptionAccuracyTests.class);
        suite.addTestSuite(EntityNotFoundExceptionAccuracyTest.class);
        suite.addTestSuite(SubmissionManagementConfigurationExceptionAccuracyTest.class);
        suite.addTestSuite(SubmissionManagementExceptionAccuracyTest.class);
        suite.addTestSuite(SubmissionManagerBeanAccuracyTest.class);

        return suite;
    }

}
