/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author KSD
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestManagerBean2FailureTest.class);
        suite.addTestSuite(ContestFilterFactoryFailureTest.class);
        suite.addTestSuite(FilterToSqlConverterFailureTest.class);

        // old version
        suite.addTest(ContestManagerBeanFailureTest.suite());
        suite.addTest(SocketDocumentContentManagerFailureTest.suite());
        suite.addTest(SocketDocumentContentServerFailureTest.suite());
        
        return suite;
    }
}
