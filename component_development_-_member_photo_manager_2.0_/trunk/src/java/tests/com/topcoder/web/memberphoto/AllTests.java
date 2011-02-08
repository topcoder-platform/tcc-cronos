/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.memberphoto.accuracytests.AccuracyTests;
import com.topcoder.web.memberphoto.failuretests.FailureTests;
import com.topcoder.web.memberphoto.stresstests.StressTests;

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
     * Returns the aggregated test suite.
     *
     * @return the aggregated test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());

        return suite;
    }
}