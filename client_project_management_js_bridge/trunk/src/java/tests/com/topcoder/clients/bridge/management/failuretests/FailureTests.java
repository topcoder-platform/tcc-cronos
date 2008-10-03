/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.bridge.management.failuretests;

import com.topcoder.clients.bridge.management.ClientProjectManagementAjaxBridgeServletFailureTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * @return all Failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientProjectManagementAjaxBridgeServletFailureTest.class);

        return suite;
    }
}
