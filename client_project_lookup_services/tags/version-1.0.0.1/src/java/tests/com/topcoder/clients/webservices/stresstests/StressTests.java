/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Aggregates the test cases.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientStatusServiceBeanStressTest.class);
        suite.addTestSuite(ProjectStatusServiceBeanStressTest.class);
        suite.addTestSuite(LookupServiceBeanStressTest.class);
        return suite;
    }
}
