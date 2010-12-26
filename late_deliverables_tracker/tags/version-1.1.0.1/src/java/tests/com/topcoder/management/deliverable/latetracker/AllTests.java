/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.deliverable.latetracker.failuretests.FailureTests;
import com.topcoder.management.deliverable.latetracker.accuracytests.AccuracyTests;
import com.topcoder.management.deliverable.latetracker.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>Creates test suite for all tests.</p>
     *
     * @return test suite for all tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(FailureTests.suite());
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());
        return suite;
    }

}
