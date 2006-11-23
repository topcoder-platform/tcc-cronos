/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence;

import com.orpheus.auction.persistence.accuracytests.AccuracyTests;
import com.orpheus.auction.persistence.failuretests.FailureTests;
import com.orpheus.auction.persistence.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // failure tests.
        suite.addTest(FailureTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // unit tests
        suite.addTest(UnitTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }

}
