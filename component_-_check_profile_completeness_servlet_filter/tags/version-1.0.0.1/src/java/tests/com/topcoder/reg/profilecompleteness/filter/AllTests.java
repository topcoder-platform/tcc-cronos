package com.topcoder.reg.profilecompleteness.filter;

import com.topcoder.reg.profilecompleteness.filter.accuracytests.AccuracyTests;
import com.topcoder.reg.profilecompleteness.filter.failuretests.FailureTests;
import com.topcoder.reg.profilecompleteness.filter.stresstests.StressTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>Aggregates all tests cases.</p>
     * @return suite that aggregates all test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // unit tests
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

