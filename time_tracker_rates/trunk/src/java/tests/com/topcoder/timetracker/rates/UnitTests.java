/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.rates;

import com.topcoder.timetracker.rates.ejb.RateEjbTest;
import com.topcoder.timetracker.rates.persistence.InformixRatePersistenceTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Creates a new test suite for unit tests.
     *
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(RateConfigurationExceptionTest.class);
        suite.addTestSuite(RateManagerExceptionTest.class);
        suite.addTestSuite(RatePersistenceExceptionTest.class);
        suite.addTestSuite(ParameterCheckTest.class);
        suite.addTestSuite(ConfigHelperTest.class);

        suite.addTestSuite(RateTest.class);
        suite.addTestSuite(InformixRatePersistenceTest.class);
        suite.addTestSuite(RateEjbTest.class);
        suite.addTestSuite(RateManagerTest.class);

        return suite;
    }
}
