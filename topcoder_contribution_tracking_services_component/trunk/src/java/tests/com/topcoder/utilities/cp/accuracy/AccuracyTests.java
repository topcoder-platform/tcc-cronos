/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.accuracy;


import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * <p>
 * This test case aggregates all accuracy unit test cases.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
@SuiteClasses({DirectProjectCPConfigServiceImplUnitTests.class, ProjectContestCPConfigServiceImplUnitTests.class,
    MemberContributionPointsServiceImplUnitTests.class })
@RunWith(Suite.class)
public class AccuracyTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AccuracyTests.class);
    }
}
