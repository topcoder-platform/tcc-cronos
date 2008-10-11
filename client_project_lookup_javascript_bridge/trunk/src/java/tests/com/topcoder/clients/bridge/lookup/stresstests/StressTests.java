/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.bridge.lookup.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregate all stress test cases.
     * @return the aggregated tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ClientProjectLookupServiceBridgeServletStressTest.class);
        return suite;
    }
}
