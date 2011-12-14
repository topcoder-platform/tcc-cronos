/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.utilities.cp.entities.DirectProjectCPConfigUnitTests;
import com.topcoder.utilities.cp.entities.MemberContributionPointsUnitTests;
import com.topcoder.utilities.cp.entities.ProjectContestCPConfigUnitTests;
import com.topcoder.utilities.cp.services.ContributionServiceEntityNotFoundExceptionUnitTests;
import com.topcoder.utilities.cp.services.ContributionServiceExceptionUnitTests;
import com.topcoder.utilities.cp.services.ContributionServiceInitializationExceptionUnitTests;
import com.topcoder.utilities.cp.services.impl.BaseServiceUnitTests;
import com.topcoder.utilities.cp.services.impl.DirectProjectCPConfigServiceImplUnitTests;
import com.topcoder.utilities.cp.services.impl.MemberContributionPointsServiceImplUnitTests;
import com.topcoder.utilities.cp.services.impl.ProjectContestCPConfigServiceImplUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());

        suite.addTest(DirectProjectCPConfigServiceImplUnitTests.suite());
        suite.addTest(ProjectContestCPConfigServiceImplUnitTests.suite());
        suite.addTest(MemberContributionPointsServiceImplUnitTests.suite());
        suite.addTest(BaseServiceUnitTests.suite());

        suite.addTest(DirectProjectCPConfigUnitTests.suite());
        suite.addTest(MemberContributionPointsUnitTests.suite());
        suite.addTest(ProjectContestCPConfigUnitTests.suite());

        // Exceptions
        suite.addTest(ContributionServiceInitializationExceptionUnitTests.suite());
        suite.addTest(ContributionServiceExceptionUnitTests.suite());
        suite.addTest(ContributionServiceEntityNotFoundExceptionUnitTests.suite());

        return suite;
    }

}
