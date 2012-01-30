package com.topcoder.security.groups.actions;
/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AccessAuditingInfoActionTest.suite());
        suite.addTest(ClientsPrepopulatingBaseActionTest.suite());
        suite.addTest(CreateCustomerAdminActionTest.suite());
        suite.addTest(SendInvitationActionTest.suite());
        suite.addTest(HelperUtiliyTest.suite());
        return suite;
    }
}
