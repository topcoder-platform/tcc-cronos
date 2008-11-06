/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.jira.managers.DefaultJiraManagerUnitTests;
import com.topcoder.jira.managers.JiraManagerConfigurationExceptionUnitTests;
import com.topcoder.jira.managers.entities.JiraProjectUnitTests;
import com.topcoder.jira.managers.entities.JiraVersionUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(JiraConnectionExceptionUnitTests.class);
        suite.addTestSuite(JiraManagerExceptionUnitTests.class);
        suite.addTestSuite(JiraNotAuthorizedExceptionUnitTests.class);
        suite.addTestSuite(JiraProjectValidationExceptionUnitTests.class);
        suite.addTestSuite(JiraSecurityTokenExpiredExceptionUnitTests.class);
        suite.addTestSuite(JiraConnectionExceptionUnitTests.class);
        suite.addTestSuite(JiraManagerConfigurationExceptionUnitTests.class);

        suite.addTestSuite(JiraProjectUnitTests.class);
        suite.addTestSuite(JiraVersionUnitTests.class);

        suite.addTestSuite(JiraProjectCreationResultUnitTests.class);
        suite.addTestSuite(JiraProjectRetrievalResultUnitTests.class);

        suite.addTestSuite(ComponentTypeUnitTests.class);
        suite.addTestSuite(JiraProjectCreationActionUnitTests.class);
        suite.addTestSuite(JiraProjectRetrievalResultActionUnitTests.class);

        suite.addTestSuite(DefaultJiraManagerUnitTests.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }
}
