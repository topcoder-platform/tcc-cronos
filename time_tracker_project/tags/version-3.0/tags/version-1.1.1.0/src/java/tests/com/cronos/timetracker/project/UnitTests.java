/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import com.cronos.timetracker.project.persistence.BatchOperationExceptionTest;
import com.cronos.timetracker.project.persistence.DatabaseSearchUtilityTest;
import com.cronos.timetracker.project.persistence.InformixTimeTrackerProjectPersistenceTest;
import com.cronos.timetracker.project.persistence.PersistenceExceptionTest;
import com.cronos.timetracker.project.searchfilters.BinaryOperationFilterTest;
import com.cronos.timetracker.project.searchfilters.BinaryOperationTest;
import com.cronos.timetracker.project.searchfilters.CompareOperationTest;
import com.cronos.timetracker.project.searchfilters.MultiValueFilterTest;
import com.cronos.timetracker.project.searchfilters.NotFilterTest;
import com.cronos.timetracker.project.searchfilters.ValueFilterTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author colau
 * @author TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return a TestSuite for this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // add tests for version 1.0
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

        // add tests for version 1.1
        suite.addTest(BatchOperationExceptionTest.suite());
        suite.addTest(DatabaseSearchUtilityTest.suite());
        suite.addTest(BinaryOperationFilterTest.suite());
        suite.addTest(BinaryOperationTest.suite());
        suite.addTest(CompareOperationTest.suite());
        suite.addTest(MultiValueFilterTest.suite());
        suite.addTest(NotFilterTest.suite());
        suite.addTest(ValueFilterTest.suite());

        return suite;
    }
}
