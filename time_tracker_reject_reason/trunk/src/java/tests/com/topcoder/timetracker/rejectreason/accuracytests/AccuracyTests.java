/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Unit test cases.
     * </p>
     *
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DbRejectEmailDAOAccuracyTests.class);
        suite.addTestSuite(DbRejectReasonDAOAccuracyTests.class);
        suite.addTestSuite(RejectEmailAccuracyTests.class);
        suite.addTestSuite(RejectEmailDAOConfigurationExceptionAccuracyTests.class);
        suite.addTestSuite(RejectEmailDAOExceptionAccuracyTests.class);
        suite.addTestSuite(RejectEmailManagerAccuracyTests.class);
        suite.addTestSuite(RejectEmailNotFoundExceptionAccuracyTests.class);
        suite.addTestSuite(RejectEmailSearchBuilderAccuracyTests.class);
        suite.addTestSuite(RejectEmailSessionBeanAccuracyTests.class);
        suite.addTestSuite(RejectReasonAccuracyTests.class);
        suite.addTestSuite(RejectReasonDAOConfigurationExceptionAccuracyTests.class);
        suite.addTestSuite(RejectReasonDAOExceptionAccuracyTests.class);
        suite.addTestSuite(RejectReasonManagerAccuracyTests.class);
        suite.addTestSuite(RejectReasonNotFoundExceptionAccuracyTests.class);
        suite.addTestSuite(RejectReasonSearchBuilderAccuracyTests.class);
        suite.addTestSuite(RejectReasonSessionBeanAccuracyTests.class);

        return suite;
    }

}
