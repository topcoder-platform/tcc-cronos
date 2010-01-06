/**
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.phases;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.phases.accuracytests.AccuracyTests;
import com.cronos.onlinereview.phases.failuretests.FailureTests;
import com.cronos.onlinereview.phases.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
        suite.addTest(UnitTests.suite());

        // accuracy
        suite.addTest(AccuracyTests.suite());
                
        // stress
        suite.addTest(StressTests.suite());

        suite.addTest(FailureTests.suite());

        return suite;
    }

}
