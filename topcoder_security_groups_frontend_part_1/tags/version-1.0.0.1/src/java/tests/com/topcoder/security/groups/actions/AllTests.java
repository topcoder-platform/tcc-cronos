/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.security.groups.actions;

import com.topcoder.security.groups.actions.failuretests.FailureTests;
import com.topcoder.security.groups.actions.stress.StressUnitTests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Creates test suite.
     * </p>
     *
     * @return test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // stress tests
        suite.addTest(new JUnit4TestAdapter(StressUnitTests.class));

        return suite;
    }
}