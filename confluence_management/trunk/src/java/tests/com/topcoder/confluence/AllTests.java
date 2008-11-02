/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.confluence.accuracytests.AccuracyTests;
import com.topcoder.confluence.failuretests.FailureTests;
import com.topcoder.confluence.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * The all tests suite.
     *
     * @return the suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());

        suite.addTest(FailureTests.suite());

        suite.addTest(AccuracyTests.suite());

        suite.addTest(StressTests.suite());
        return suite;
    }

}
