/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
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
        suite.addTest(AcceptRejectGroupInvitationActionTest.suite());
        suite.addTest(ApproveRejectPendingUserActionTest.suite());
        suite.addTest(GroupInvitationAwareBaseActionTest.suite());
        suite.addTest(ViewInvitationStatusActionTest.suite());
        suite.addTest(ViewPendingApprovalUserActionTest.suite());
        suite.addTest(HelperUtilityTest.suite());
        return suite;
    }
}
