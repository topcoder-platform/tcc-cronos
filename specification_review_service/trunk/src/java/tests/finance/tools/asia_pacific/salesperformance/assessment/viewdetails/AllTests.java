/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.failuretests.FailureTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.accuracytests.AccuracyTests;
import finance.tools.asia_pacific.salesperformance.assessment.viewdetails.stresstests.StressTests;


/**
 * This test case aggregates all test cases.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AllTests extends TestCase {
    /**
     * Test all the tests of this component.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //failure tests
        suite.addTest(FailureTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        //unit tests
        suite.addTest(UnitTests.suite());

        return suite;
    }

}
