/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions;

import com.topcoder.direct.actions.accuracytests.AccuracyTests;
import com.topcoder.direct.actions.failuretests.FailureTests;
import com.topcoder.direct.actions.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
     * The test suite.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(AccuracyTests.suite());

        return suite;
    }

}
