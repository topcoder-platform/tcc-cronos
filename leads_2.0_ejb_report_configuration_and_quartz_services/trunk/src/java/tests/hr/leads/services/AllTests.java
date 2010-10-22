/**
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

package hr.leads.services;

import hr.leads.services.accuracytests.AccuracyTests;
import hr.leads.services.failuretests.FailureTests;
import hr.leads.services.stresstests.StressTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Creates test suite.
     * </p>
     *
     * @return test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        // accuracy tests
        suite.addTest(AccuracyTests.suite());

        // failure tests
        suite.addTest(FailureTests.suite());

        // stress tests
        suite.addTest(StressTests.suite());

        return suite;
    }
}