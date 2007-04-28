/*
 * <p>Copyright (c) 2005, TopCoder, Inc. All rights reserved</p>
 */
package com.topcoder.timetracker.project.accuracytests;

import com.topcoder.timetracker.project.accuracytests.persistence.DatabaseSearchUtilityAccuracytest;
import com.topcoder.timetracker.project.accuracytests.persistence.InformixTimeTrackerProjectPersistenceTest;
import com.topcoder.timetracker.project.accuracytests.searchflters.BinaryOperationAccuracytest;
import com.topcoder.timetracker.project.accuracytests.searchflters.BinaryOperationFilterAccuracytest;
import com.topcoder.timetracker.project.accuracytests.searchflters.CompareOperationAccuracytest;
import com.topcoder.timetracker.project.accuracytests.searchflters.MultiValueFilterAccuracytest;
import com.topcoder.timetracker.project.accuracytests.searchflters.NotFilterAccuracytest;
import com.topcoder.timetracker.project.accuracytests.searchflters.ValueFilterAccuracytest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author FireIce
 * @version 1.1
 * 
 * @since 1.0
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

        // persistence
        suite.addTest(DatabaseSearchUtilityAccuracytest.suite());
        // search package since 1.1
        suite.addTest(BinaryOperationAccuracytest.suite());
        suite.addTest(CompareOperationAccuracytest.suite());
        suite.addTest(NotFilterAccuracytest.suite());
        suite.addTest(BinaryOperationFilterAccuracytest.suite());
        suite.addTest(ValueFilterAccuracytest.suite());
        suite.addTest(MultiValueFilterAccuracytest.suite());
        return suite;
    }
}
