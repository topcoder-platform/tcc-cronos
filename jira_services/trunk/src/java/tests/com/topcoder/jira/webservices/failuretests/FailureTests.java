/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.jira.webservices.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * @return Failure test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JiraManagementServiceBeanFailureTest.class);
        suite.addTestSuite(JiraManagementServiceClientFailureTest.class);
        suite.addTestSuite(JiraManagerWebServiceDelegateFailureTest.class);

        return suite;
    }

}
