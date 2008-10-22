/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.phase.clientcockpit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(CockpitConfigurationExceptionTest.suite());
        suite.addTest(CockpitPhaseManagementExceptionTest.suite());
        suite.addTest(CockpitPhaseManagerTest.suite());
        suite.addTest(CockpitPhaseManagerFailureTest.suite());

        suite.addTest(Demo.suite());

        return suite;
    }
}
