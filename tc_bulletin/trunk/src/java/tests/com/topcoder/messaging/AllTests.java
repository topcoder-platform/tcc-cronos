/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

import com.topcoder.messaging.accuracytests.AccuracyTests;
import com.topcoder.messaging.persistence.failuretests.FailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all the test cases.
 * </p>
 *
 * @author yqw
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * Returns all Unit test cases.
     * </p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());

        return suite;
    }
}
