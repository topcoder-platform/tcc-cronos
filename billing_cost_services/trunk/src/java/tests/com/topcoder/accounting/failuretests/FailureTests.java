/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BillingCostDataServiceImplFailureTests.suite());
        suite.addTest(BillingCostAuditServiceImplFailureTests.suite());
        suite.addTest(LookupServiceImplFailureTests.suite());
        suite.addTest(BaseServiceFailureTests.suite());


        return suite;
    }

}
