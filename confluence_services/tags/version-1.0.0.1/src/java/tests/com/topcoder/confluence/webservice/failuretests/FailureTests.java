/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBeanFailureTests;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all the failure test cases.
     * </p>
     *
     * @return failure test cases suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ConfluenceManagementServiceBeanFailureTests.class);
        suite.addTestSuite(ConfluenceManagementServiceClientFailureTests.class);
        suite.addTestSuite(ConfluenceManagerWebServiceDelegateFailureTests.class);
        return suite;
    }
}