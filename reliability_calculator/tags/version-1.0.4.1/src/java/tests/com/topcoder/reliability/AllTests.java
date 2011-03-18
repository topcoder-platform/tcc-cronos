/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.reliability.accuracytests.AccuracyTests;
import com.topcoder.reliability.failuretests.FailureTests;
import com.topcoder.reliability.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author Yeung
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Aggregates all test cases.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
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
