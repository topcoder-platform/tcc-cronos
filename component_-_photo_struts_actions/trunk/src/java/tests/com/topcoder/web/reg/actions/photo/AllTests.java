/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import com.topcoder.web.reg.actions.photo.accuracytests.AccuracyTests;
import com.topcoder.web.reg.actions.photo.failuretests.FailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author mumujava
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return All test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        return suite;
    }

}
