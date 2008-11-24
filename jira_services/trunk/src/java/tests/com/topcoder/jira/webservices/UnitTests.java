/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.jira.webservices.bean.JiraManagementServiceBeanTest;
import com.topcoder.jira.webservices.client.JiraManagementServiceClientTest;
import com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegateTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all unit tests together.
     *
     * @return all unit tests in one suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(Demo.suite());
        suite.addTest(JiraManagerWebServiceDelegateTest.suite());
        suite.addTest(JiraManagementServiceBeanTest.suite());
        suite.addTest(UtilTest.suite());
        suite.addTest(JiraServiceExceptionTest.suite());
        suite.addTest(JiraServiceConfigurationExceptionTest.suite());
        suite.addTest(JiraServiceConnectionExceptionTest.suite());
        suite.addTest(JiraServiceNotAuthorizedExceptionTest.suite());
        suite.addTest(JiraServiceProjectValidationExceptionTest.suite());
        suite.addTest(JiraServiceSecurityTokenExpiredExceptionTest.suite());
        suite.addTest(JiraManagementServiceClientTest.suite());
        return suite;
    }
}
