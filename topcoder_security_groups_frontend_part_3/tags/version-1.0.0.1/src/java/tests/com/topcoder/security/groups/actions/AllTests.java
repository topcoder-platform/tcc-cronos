/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.security.groups.actions.accuracytests.AccuracyTests;
import com.topcoder.security.groups.actions.failuretests.FailureUnitTests;
import com.topcoder.security.groups.actions.stresstests.StressTests;
/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(FailureUnitTests.suite());
        return suite;
    }
}
