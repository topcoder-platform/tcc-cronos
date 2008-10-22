/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import com.topcoder.service.studio.submission.accuracytests.AccuracyTests;
import com.topcoder.service.studio.submission.failuretests.FailureTests;
import com.topcoder.service.studio.submission.stresstests.StressTests;

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
     * <p>
     * Aggregates all tests.
     * </p>
     * 
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(UnitTests.suite());

        suite.addTest(FailureTests.suite());

        suite.addTest(AccuracyTests.suite());

        suite.addTest(StressTests.suite());

        return suite;
    }

}
