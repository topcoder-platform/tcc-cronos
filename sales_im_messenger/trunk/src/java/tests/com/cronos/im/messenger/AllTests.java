/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.messenger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.im.messenger.accuracytests.AccuracyTests;
import com.cronos.im.messenger.failuretests.FailureTests;
import com.cronos.im.messenger.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     * 
     * @return all tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // failure tests
        suite.addTest(FailureTests.suite());

        // unit tests
        suite.addTest(UnitTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }

}
