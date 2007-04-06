/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.timetracker.notification.accuracytests.AccuracyTests;
//import com.topcoder.timetracker.notification.failuretests.FailureTests;
import com.topcoder.timetracker.notification.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author kzhu
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //failure tests
        //suite.addTest(FailureTests.suite());

        //stress tests
        //suite.addTest(StressTests.suite());

        return suite;
    }
}
