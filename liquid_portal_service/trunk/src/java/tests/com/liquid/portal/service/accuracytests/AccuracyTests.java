/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Returns all accuracy tests.
     *
     * @return all accuracy tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(CompetitionDataAccuracyTest.class);
        suite.addTestSuite(CreateCompetitonResultAccuracyTest.class);
        suite.addTestSuite(ProvisionUserResultAccuracyTest.class);
        suite.addTestSuite(RegisterUserResultAccuracyTest.class);
        suite.addTestSuite(ResultAccuracyTest.class);
        suite.addTestSuite(WarningAccuracyTest.class);

        return suite;
    }

}
