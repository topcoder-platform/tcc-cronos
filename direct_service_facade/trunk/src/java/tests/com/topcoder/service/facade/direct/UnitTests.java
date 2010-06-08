/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct;

import com.topcoder.service.facade.direct.ejb.Demo;
import com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBeanTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all Unit test cases.
     * </p>
     * 
     * @return the test suite containing all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestNotFoundExceptionTest.class);
        suite.addTestSuite(DirectServiceFacadeConfigurationExceptionTest.class);
        suite.addTestSuite(DirectServiceFacadeExceptionTest.class);
        suite.addTestSuite(EmailSendingExceptionTest.class);
        suite.addTestSuite(ProjectNotFoundExceptionTest.class);

        suite.addTestSuite(ContestPlanTest.class);
        suite.addTestSuite(ContestPrizeTest.class);
        suite.addTestSuite(ContestReceiptDataTest.class);
        suite.addTestSuite(ContestScheduleExtensionTest.class);
        suite.addTestSuite(ContestScheduleTest.class);
        suite.addTestSuite(ProjectBudgetTest.class);
        suite.addTestSuite(SpecReviewStateTest.class);

        suite.addTestSuite(DirectServiceFacadeBeanTest.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }
}
