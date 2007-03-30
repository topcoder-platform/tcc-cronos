/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author mittu
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Aggregates all failure test suites.
     *
     * @return aggregation of all failure test suites.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AdminLoginActionFailureTest.class);
        suite.addTestSuite(ClientLoginActionExecuteFailureTest.class);
        suite.addTestSuite(ClientLoginActionFailureTest.class);
        suite.addTestSuite(LoginActionFailureTest.class);
        suite.addTestSuite(ManagerLoginActionFailureTest.class);
        return suite;
    }

}
