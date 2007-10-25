/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.team.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.registration.team.service.accuracytests.impl.OperationResultImplUnitTests;
import com.topcoder.registration.team.service.accuracytests.impl.ResourcePositionImplUnitTests;
import com.topcoder.registration.team.service.accuracytests.impl.TeamServicesImplUnitTests;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author 80x86
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //suite.addTest(XXX.suite());
        suite.addTestSuite(TeamServiceConfigurationExceptionUnitTests.class);
        suite.addTestSuite(TeamServicesExceptionUnitTests.class);
        suite.addTestSuite(UnknownEntityExceptionUnitTests.class);
        suite.addTestSuite(OperationResultImplUnitTests.class);
        suite.addTestSuite(ResourcePositionImplUnitTests.class);
        suite.addTestSuite(TeamServicesImplUnitTests.class);
        return suite;
    }

}
