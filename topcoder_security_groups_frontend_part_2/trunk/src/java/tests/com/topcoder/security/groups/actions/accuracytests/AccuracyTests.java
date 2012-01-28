/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import com.topcoder.security.groups.actions.GroupInvitationAwareBaseActionAccurracyTest;
import com.topcoder.security.groups.actions.GroupInvitationSearchBaseActionAccuracyTest;
import com.topcoder.security.groups.actions.ViewInvitationStatusActionAccuracyTest;
import com.topcoder.security.groups.actions.ViewPendingApprovalUserActionAccuracyTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AcceptRejectGroupInvitationActionAccuracyTest.suite());
        suite.addTest(ApproveRejectPendingUserActionAccuracyTest.suite());
        suite.addTest(ViewPendingApprovalUserActionAccuracyTest.suite());
        suite.addTest(ViewInvitationStatusActionAccuracyTest.suite());
        suite.addTest(GroupInvitationSearchBaseActionAccuracyTest.suite());
        suite.addTest(GroupInvitationAwareBaseActionAccurracyTest.suite());
        return suite;
    }
}
