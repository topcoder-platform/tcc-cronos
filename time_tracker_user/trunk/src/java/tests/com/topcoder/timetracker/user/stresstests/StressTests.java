/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Aggregation of all the stress tests.
 * @author assistant
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Aggregates all stress tests.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DbCompanyDAOStressTest.class);
        suite.addTestSuite(DbRejectEmailDAOStressTest.class);
        suite.addTestSuite(DbRejectReasonDAOStressTest.class);
        suite.addTestSuite(DbUserDAOStressTest.class);
        return suite;
    }

}
