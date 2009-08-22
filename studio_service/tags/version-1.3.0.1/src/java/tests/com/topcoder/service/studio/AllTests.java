/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.accuracytests.AccuracyTests;
import com.topcoder.service.studio.failuretests.FailureTests;
import com.topcoder.service.studio.stresstests.StressTests;


/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
