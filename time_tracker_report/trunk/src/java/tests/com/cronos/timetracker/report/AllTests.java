/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.report;

import com.cronos.timetracker.report.accuracytests.AccuracyTests;
import com.cronos.timetracker.report.failuretests.FailureTests;
import com.cronos.timetracker.report.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


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
        //suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());
        return suite;
    }
}
