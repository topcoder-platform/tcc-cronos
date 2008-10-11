/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.bridge.lookup.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Aggregates the test cases.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ClientProjectLookupServiceBridgeServletFailureTest.class);
        return suite;
    }
}
