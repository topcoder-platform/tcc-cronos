/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.entry.fixedbilling.accuracytests.AccuracyTests;
import com.topcoder.timetracker.entry.fixedbilling.failuretests.FailureTests;
import com.topcoder.timetracker.entry.fixedbilling.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


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
     * Creates the test cases suite.
     *
     * @return the test case suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //Because of the conflict setting and test cases, only can run one test suite one time.
        //unit tests
        suite.addTest(UnitTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }
}
