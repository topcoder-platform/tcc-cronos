/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validators;

import com.topcoder.registration.validators.accuracytests.AccuracyTests;
import com.topcoder.registration.validators.failuretests.FailureTests;
import com.topcoder.registration.validators.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test suite aggregates all test cases for this component.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * Returns the Test containing all the test cases for this component.
     *
     * @return The Test containing all the test cases for this component.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(UnitTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(StressTests.suite());

        return suite;
    }
}
