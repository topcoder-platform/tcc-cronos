/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.phases.template.accuracytests.AccuracyTests;
import com.topcoder.project.phases.template.failuretests.FailureTests;
import com.topcoder.project.phases.template.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     * @return the aggregated test cases
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