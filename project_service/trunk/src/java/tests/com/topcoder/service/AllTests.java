/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service;


import com.topcoder.service.project.accuracytests.AccuracyTests;
import com.topcoder.service.project.failuretests.FailureTests;
import com.topcoder.service.project.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AccuracyTests.suite());
        suite.addTest(UnitTests.suite());
        suite.addTest(StressTests.suite());
        suite.addTest(FailureTests.suite());

        return suite;
    }
}
