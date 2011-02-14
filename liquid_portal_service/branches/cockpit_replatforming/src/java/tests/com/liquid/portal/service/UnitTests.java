/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return test cases suit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ActionNotPermittedExceptionTests.class);
        suite.addTestSuite(CompetitionDataTests.class);
        suite.addTestSuite(ContestNotFoundExceptionTests.class);
        suite.addTestSuite(CreateCompetitonResultTests.class);
        suite.addTestSuite(HandleCreationExceptionTests.class);
        suite.addTestSuite(HandleNotFoundExceptionTests.class);
        suite.addTestSuite(InvalidHandleExceptionTests.class);
        suite.addTestSuite(LiquidPortalIllegalArgumentExceptionTests.class);
        suite.addTestSuite(LiquidPortalServiceConfigurationExceptionTests.class);
        suite.addTestSuite(LiquidPortalServicingExceptionTests.class);
        suite.addTestSuite(ProvisionUserResultTests.class);
        suite.addTestSuite(RegisterUserResultTests.class);
        suite.addTestSuite(ResultTests.class);
        suite.addTestSuite(WarningTests.class);
        suite.addTestSuite(Demo.class);
        return suite;
    }

}
