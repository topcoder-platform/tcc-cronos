/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.client;

import com.topcoder.timetracker.client.accuracytests.AccuracyTests;
import com.topcoder.timetracker.client.failuretests.FailureTests;
import com.topcoder.timetracker.client.stresstests.StressTests;
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

        //failure tests
        suite.addTest(FailureTests.suite());

        //unit tests
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }

}
