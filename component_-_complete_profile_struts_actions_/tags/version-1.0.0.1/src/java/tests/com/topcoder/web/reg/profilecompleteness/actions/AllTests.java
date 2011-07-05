/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.web.reg.profilecompleteness.actions;

import com.topcoder.web.reg.profilecompleteness.actions.accuracytests.AccuracyTests;
import com.topcoder.web.reg.profilecompleteness.actions.failuretests.FailureTests;
import com.topcoder.web.reg.profilecompleteness.actions.stresstests.StressTests;

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

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }
}