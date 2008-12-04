/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.confluence.webservice.accuracytests.AccuracyTests;
import com.topcoder.confluence.webservice.failuretests.FailureTests;
import com.topcoder.confluence.webservice.stresstests.ConfluenceManagementServiceStressTest;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AllTests extends TestCase {

    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return test cases suit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // unit tests
        suite.addTest(UnitTests.suite());

        suite.addTest(FailureTests.suite());

        suite.addTest(AccuracyTests.suite());

        suite.addTestSuite(ConfluenceManagementServiceStressTest.class);
        return suite;
    }

}