/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Gathers all unit tests together and return.
     *
     * @return all tests in one suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ContestCategoryDataTest.class);
        suite.addTestSuite(ContestDataTest.class);
        suite.addTestSuite(ContestPayloadTest.class);
        suite.addTestSuite(ContestStatusDataTest.class);
        suite.addTestSuite(StudioServiceBeanTest.class);
        suite.addTestSuite(ContestCategoryDataTest.class);
        return suite;
    }
}
