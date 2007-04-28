/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * Aggregate the stress tests.
     *
     * @return Test instance.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(InformixTimeTrackerProjectPersistence1_1StressTest.class);
        return suite;
    }
}
