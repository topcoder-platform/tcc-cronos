/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service;

import com.topcoder.registration.team.service.impl.OperationResultImplTest;
import com.topcoder.registration.team.service.impl.ResourcePositionImplTest;
import com.topcoder.registration.team.service.impl.TeamServicesImplTest;
import com.topcoder.registration.team.service.impl.UtilTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * @return a test suite will be returned
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TeamServiceConfigurationExceptionTest.class);
        suite.addTestSuite(TeamServicesExceptionTest.class);
        suite.addTestSuite(UnknownEntityExceptionTest.class);
        suite.addTestSuite(OperationResultImplTest.class);
        suite.addTestSuite(ResourcePositionImplTest.class);
        suite.addTestSuite(UtilTest.class);
        suite.addTestSuite(TeamServicesImplTest.class);

        return suite;
    }

}
