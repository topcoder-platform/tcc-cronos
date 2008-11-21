/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.bridge.management;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.clients.bridge.management.accuracytests.AccuracyTests;
import com.topcoder.clients.bridge.management.failuretests.FailureTests;

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
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        return suite;
    }

}
