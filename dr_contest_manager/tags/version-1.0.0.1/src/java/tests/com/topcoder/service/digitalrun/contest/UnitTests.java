/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.digitalrun.contest.audit.interceptors.BaseAuditContestInterceptorUnitTests;
import com.topcoder.service.digitalrun.contest.persistence.BaseHibernateDAOUnitTests;
import com.topcoder.service.digitalrun.contest.persistence.HibernateContestTrackDAOUnitTests;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestResultCalculatorDAOUnitTests;
import com.topcoder.service.digitalrun.contest.persistence.HibernateTrackContestTypeDAOUnitTests;

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
     * @return All Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(HelperUnitTests.class);

        suite.addTestSuite(ContestManagerConfigurationExceptionUnitTests.class);
        suite.addTestSuite(DigitalRunContestManagerExceptionUnitTests.class);
        suite.addTestSuite(PersistenceExceptionUnitTests.class);
        suite.addTestSuite(EntityExistsExceptionUnitTests.class);
        suite.addTestSuite(EntityNotFoundExceptionUnitTests.class);

        suite.addTestSuite(BaseAuditContestInterceptorUnitTests.class);

        suite.addTestSuite(BaseHibernateDAOUnitTests.class);
        suite.addTestSuite(HibernateContestTrackDAOUnitTests.class);
        suite.addTestSuite(HibernateTrackContestTypeDAOUnitTests.class);
        suite.addTestSuite(HibernateTrackContestResultCalculatorDAOUnitTests.class);

        suite.addTestSuite(ContestFilterFactoryUnitTests.class);

        suite.addTestSuite(DigitalRunContestManagerBeanUnitTests.class);

        suite.addTestSuite(DigitalRunContestManagerBeanTestAcc.class);
        suite.addTestSuite(DigitalRunContestManagerBeanTestExp.class);

        suite.addTestSuite(Demo.class);

        suite.addTestSuite(SaveTest.class);

        return suite;
    }

}
