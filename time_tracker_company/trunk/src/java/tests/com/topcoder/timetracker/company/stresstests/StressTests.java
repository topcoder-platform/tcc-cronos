/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress cases.
 * </p>
 *
 * @author Achilles_2005
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
        suite.addTestSuite(LocalCompanyManagerDelegateStressTest.class);
        return suite;
    }
}
