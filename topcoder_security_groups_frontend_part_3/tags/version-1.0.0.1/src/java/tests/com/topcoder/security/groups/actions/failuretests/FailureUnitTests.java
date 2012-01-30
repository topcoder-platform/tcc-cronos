/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure unit test cases.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class FailureUnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(SendInvitationActionFailureUnitTests.suite());
        suite.addTest(CreateCustomerAdminActionFailureUnitTests.suite());
        suite.addTest(AccessAuditingInfoActionFailureUnitTests.suite());

        return suite;
    }
}
