/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.service.project.impl.ProjectServiceBeanUnitTests;
import com.topcoder.service.project.persistence.JPAProjectPersistenceUnitTests;

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
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AuthorizationFailedFaultUnitTests.suite());
        suite.addTest(ConfigurationExceptionUnitTests.suite());
        suite.addTest(IllegalArgumentFaultUnitTests.suite());
        suite.addTest(PersistenceExceptionUnitTests.suite());
        suite.addTest(PersistenceFaultUnitTests.suite());
        suite.addTest(ProjectHasCompetitionsFaultUnitTests.suite());
        suite.addTest(ProjectNotFoundExceptionUnitTests.suite());
        suite.addTest(ProjectNotFoundFaultUnitTests.suite());
        suite.addTest(ProjectServiceExceptionUnitTests.suite());
        suite.addTest(ProjectServiceFaultUnitTests.suite());
        suite.addTest(UserNotFoundFaultUnitTests.suite());
        suite.addTest(CompetitionUnitTests.suite());
        suite.addTest(ProjectDataUnitTests.suite());
        suite.addTest(ProjectUnitTests.suite());

        suite.addTest(JPAProjectPersistenceUnitTests.suite());
        suite.addTest(ProjectServiceBeanUnitTests.suite());

        // suite.addTestSuite(Demo.class);
        return suite;
    }

}
