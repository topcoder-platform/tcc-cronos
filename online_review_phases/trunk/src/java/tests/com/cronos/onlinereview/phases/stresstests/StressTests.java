/**
 *
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.phases.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(OnlineReviewStressTests.class);
        return suite;
    }
}
