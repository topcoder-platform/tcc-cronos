/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.tc.accuracytests.AccuracyTests;
import com.topcoder.web.tc.failuretests.FailureTests;
import com.topcoder.web.tc.stresstests.StressTests;


/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all test cases.
     * </p>
     * @return a test suite containing all test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // helper

        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
