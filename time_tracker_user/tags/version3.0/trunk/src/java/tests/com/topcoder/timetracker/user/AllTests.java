/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.accuracytests.AccuracyTests;
import com.topcoder.timetracker.user.failuretests.FailureTests;

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


        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        suite.addTest(UnitTests.suite());
//
//        //failure tests
        suite.addTest(FailureTests.suite());
//
//        //stress tests
//        suite.addTest(StressTests.suite());

        return suite;
    }

}
