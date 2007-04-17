/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DbRejectEmailDAOTest.suite());
        suite.addTest(DbRejectReasonDAOTest.suite());
        suite.addTest(RejectEmailManagerTest.suite());
        suite.addTest(RejectEmailSearchBuilderTest.suite());
        suite.addTest(RejectEmailTest.suite());
        suite.addTest(RejectReasonManagerTest.suite());
        suite.addTest(RejectReasonSearchBuilderTest.suite());
        suite.addTest(RejectReasonTest.suite());
        return suite;
    }

}
