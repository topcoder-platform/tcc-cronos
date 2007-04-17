/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress cases.
 * </p>
 *
 * @author restarter
 * @version 3.2
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all Stress cases.
     * </p>
     *
     * @return the stress test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DbRejectEmailDAOStressTest.class);
        suite.addTestSuite(DbRejectReasonDAOStressTest.class);
        return suite;
    }
}
