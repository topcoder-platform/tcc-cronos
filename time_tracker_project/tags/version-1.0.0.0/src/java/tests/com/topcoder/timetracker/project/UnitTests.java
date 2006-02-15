/*
 * UnitTests.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

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
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ClientTest.suite());
        suite.addTest(ClientUtilityTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DemoTest.suite());
        suite.addTest(InformixTimeTrackerProjectPersistenceTest.suite());
        suite.addTest(InsufficientDataExceptionTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());
        suite.addTest(ProjectManagerTest.suite());
        suite.addTest(ProjectPersistenceManagerTest.suite());
        suite.addTest(ProjectTest.suite());
        suite.addTest(ProjectUtilityTest.suite());
        suite.addTest(ProjectWorkerTest.suite());

        return suite;
    }
}
