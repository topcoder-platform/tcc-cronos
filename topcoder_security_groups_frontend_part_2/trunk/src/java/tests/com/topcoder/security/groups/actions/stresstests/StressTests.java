/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test case aggregates all Unit test cases.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Represents the times that the tests needs to run.
     */
    static final int TIMES = 1000;
    /**
     * Creates a test suite for the stress tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AcceptRejectGroupInvitationActionStressTest.suite());
        suite.addTest(ApproveRejectPendingUserActionStressTest.suite());
        suite.addTestSuite(ViewInvitationStatusActionStressTest.class);
        suite.addTestSuite(ViewPendingApprovalUserActionStressTest.class);
        return suite;
    }
}
