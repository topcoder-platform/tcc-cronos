/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author stylecheck
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * This test case aggregates all accuracy test cases.
     *
     * @return suite containing all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AdminLoginActionTest.class);
        suite.addTestSuite(ClientLoginActionConfigTest.class);
        suite.addTestSuite(ClientLoginActionTest.class);
        suite.addTestSuite(LoginActionConfigTest.class);
        suite.addTestSuite(LoginActionTest.class);
        suite.addTestSuite(ManagerLoginActionTest.class);
        return suite;
    }


}
