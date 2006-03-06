/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.apps.screening.applications.specification;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.apps.screening.applications.specification.accuracytests.AccuracyTests;
import com.topcoder.apps.screening.applications.specification.failuretests.FailureTests;
import com.topcoder.apps.screening.applications.specification.stresstests.StressSuite;


/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());
        
        //failure tests
        suite.addTest(FailureTests.suite());

        //stress tests        
        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        return suite;
    }
}
