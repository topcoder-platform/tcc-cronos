/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp;

import com.topcoder.utilities.cp.services.impl.DirectProjectCPConfigServiceImplFailureTests;
import com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImplFailureTests;
import com.topcoder.utilities.cp.services.impl.ProjectContestCPConfigServiceImplFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * <p>
     * All failure test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(DirectProjectCPConfigServiceImplFailureTests.suite());
        suite.addTest(MemberContributionPointsServiceImplFailureTests.suite());
        suite.addTest(ProjectContestCPConfigServiceImplFailureTests.suite());
        return suite;
    }
}