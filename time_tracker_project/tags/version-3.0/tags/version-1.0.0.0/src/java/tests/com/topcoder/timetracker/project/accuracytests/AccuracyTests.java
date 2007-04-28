/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(ClientTest.suite());
        suite.addTest(ProjectTest.suite());
        suite.addTest(ProjectManagerTest.suite());
        suite.addTest(ProjectWorkerTest.suite());
        suite.addTest(InsufficientDataExceptionTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(PersistenceExceptionTest.suite());
        suite.addTest(ProjectPersistenceManagerTest.suite());
        suite.addTest(InformixTimeTrackerProjectPersistenceTest.suite());

        return suite;
    }
}
