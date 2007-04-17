/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.rejectreason.accuracytests.AccuracyTests;
import com.topcoder.timetracker.rejectreason.failuretests.FailureTests;
import com.topcoder.timetracker.rejectreason.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Aggregates all test cases.
     *
     * @return the test cases aggregate all test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // stress tests
        suite.addTest(StressTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // unit tests
        suite.addTest(UnitTests.suite());

        return suite;
    }
}
