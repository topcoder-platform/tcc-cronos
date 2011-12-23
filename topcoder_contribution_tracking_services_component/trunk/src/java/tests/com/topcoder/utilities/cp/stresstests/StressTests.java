/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Creates TestSuite that aggregates all Stress tests.
     * </p>
     *
     * @return TestSuite that aggregates all Stress tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DirectProjectCPConfigServiceImplStressTest.class);
        suite.addTestSuite(MemberContributionPointsServiceImplStressTest.class);
        suite.addTestSuite(ProjectContestCPConfigServiceImplStressTest.class);
        return suite;
    }
}
