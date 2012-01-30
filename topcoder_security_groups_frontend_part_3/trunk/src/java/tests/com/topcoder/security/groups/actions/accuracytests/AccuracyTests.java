/**
 *
 * Copyright (c) 2012, TopCoder, Inc. All rights reserved
 */
package com.topcoder.security.groups.actions.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AccessAuditingInfoActionTest.suite());
        suite.addTest(CreateCustomerAdminActionTest.suite());
        suite.addTest(SendInvitationActionTest.suite());
        return suite;
    }

}
