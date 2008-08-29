/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author mayday
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all Stress test cases and returns a testSuite.
     *</p>
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DAOClientManagerStressTest.class);
        suite.addTestSuite(DAOCompanyManagerStressTest.class);
        suite.addTestSuite(DAOProjectManagerStressTest.class);
        return suite;
    }
}
