/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.contest.coo.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author yuanyeyuanye
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Add stress test cases to test suite.
     * </p>
     *
     * @return instance of <code>Test</code>.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CooGenerateStressTests.class);
        return suite;
    }
}
