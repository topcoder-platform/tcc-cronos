/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import com.topcoder.controlpanel.clientassociations.accuracytests.AccuracyTests;
import com.topcoder.controlpanel.clientassociations.failuretests.FailureTests;
import com.topcoder.controlpanel.clientassociations.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(FailureTests.suite());

        // suite.addTest(AccuracyTests.suite());

        suite.addTest(StressTests.suite());

        // unit tests
        suite.addTest(UnitTests.suite());

        return suite;
    }

}
