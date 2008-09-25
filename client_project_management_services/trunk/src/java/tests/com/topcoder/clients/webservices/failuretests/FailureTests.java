/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Returns all test cases.
     *
     * @return all test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientServiceBeanFailureTest.class);
        suite.addTestSuite(CompanyServiceBeanFailureTest.class);
        suite.addTestSuite(ProjectServiceBeanFailureTest.class);

        suite.addTestSuite(ClientServiceClientFailureTest.class);
        suite.addTestSuite(CompanyServiceClientFailureTest.class);
        suite.addTestSuite(ProjectServiceClientFailureTest.class);

        suite.addTestSuite(JPAUserMappingRetrieverFailureTest.class);
        return suite;
    }
}
