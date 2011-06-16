/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AuditDAOHibernateFailureTest.suite());
        suite.addTest(UserBeanFailureTest.suite());
        suite.addTest(UserDAOHibernateFailureTest.suite());
        suite.addTest(UserFailureTest.suite());
        suite.addTest(UserProfileFailureTest.suite());

        return suite;
    }

}
