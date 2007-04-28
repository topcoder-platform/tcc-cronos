/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.project;

import com.cronos.timetracker.project.accuracytests.AccuracyTests;
import com.cronos.timetracker.project.failuretests.FailureTests;
import com.cronos.timetracker.project.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // accuracy tests
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(UnitTests.suite());

        return suite;
    }

}
