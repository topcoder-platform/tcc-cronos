/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import com.topcoder.service.prerequisite.document.accuracytests.AccuracyTests;
import com.topcoder.service.prerequisite.document.failuretests.FailureTests;
import com.topcoder.service.prerequisite.document.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author 80x86
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        //suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }

}
