/**
 *
 * Copyright (c) 2012, TopCoder, Inc. All rights reserved
 */
package com.topcoder.security.groups.actions.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AcceptRejectGroupInvitationActionTest.suite());
        suite.addTest(ApproveRejectPendingUserActionTest.suite());
        suite.addTest(ViewInvitationStatusActionTest.suite());
        suite.addTest(ViewPendingApprovalUserActionTest.suite());
        return suite;
    }

}
