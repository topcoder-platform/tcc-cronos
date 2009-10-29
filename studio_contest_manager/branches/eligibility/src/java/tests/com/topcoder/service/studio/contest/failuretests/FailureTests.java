/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author liuliquan
 * @version 1.3
 */
public class FailureTests extends TestCase {
    /**
     * All failure tests for this failure review.
     *
     * @return the suite aggregated all failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ContestManagerBeanFailureTest.suite());
        suite.addTestSuite(ContestManagerBean2FailureTest.class);
        suite.addTestSuite(ContestManagerBean3FailureTest.class);
        suite.addTestSuite(ContestFilterFactoryFailureTest.class);
        suite.addTestSuite(FilterToSqlConverterFailureTest.class);
        suite.addTest(SocketDocumentContentManagerFailureTest.suite());
        suite.addTest(SocketDocumentContentServerFailureTest.suite());

        return suite;
    }
}
