/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author morehappiness
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaultJiraManagerAccuracyTests.class);
        suite.addTestSuite(JiraProjectAccuracyTests.class);
        suite.addTestSuite(JiraProjectCreationResultAccuracyTests.class);
        suite.addTestSuite(JiraProjectRetrievalResultAccuracyTests.class);
        suite.addTestSuite(JiraVersionAccuracyTests.class);

        return suite;
    }
}
