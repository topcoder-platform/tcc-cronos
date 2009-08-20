/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.accuracytests.AccuracyTests;
import com.topcoder.service.studio.contest.failuretests.FailureTests;
import com.topcoder.service.studio.contest.stresstests.StressTests;


/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
