/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all Failure test cases.
     * </p>
     *
     * @return all the aggregated failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestFilterFactoryFailureTest.class);
        suite.addTestSuite(DigitalRunContestManagerBeanFailureTest.class);
        suite.addTestSuite(BaseAuditContestInterceptorFailureTests.class);
        suite.addTestSuite(BaseHibernateDAOFailureTests.class);
        suite.addTestSuite(HibernateContestTrackDAOFailureTests.class);
        suite.addTestSuite(HibernateTrackContestResultCalculatorDAOFailureTests.class);
        suite.addTestSuite(HibernateTrackContestTypeDAOFailureTests.class);

        return suite;
    }
}
