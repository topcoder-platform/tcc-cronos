/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBeanTests;
import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClientTests;
import com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegateTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all Unit test cases.
     * </p>
     *
     * @return test cases suit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConfluenceManagementServiceAuthenticationFailedExceptionTests.class);
        suite.addTestSuite(ConfluenceManagementServiceConfigurationExceptionTests.class);
        suite.addTestSuite(ConfluenceManagementServiceConnectionExceptionTests.class);
        suite.addTestSuite(ConfluenceManagementServiceExceptionTests.class);
        suite.addTestSuite(ConfluenceManagementServiceNotAuthorizedExceptionTests.class);
        suite.addTestSuite(HelperTests.class);

        suite.addTestSuite(ConfluenceManagementServiceAnnotationTests.class);
        suite.addTestSuite(ConfluenceManagementServiceLocalAnnotationTests.class);
        suite.addTestSuite(ConfluenceManagementServiceRemoteAnnotationTests.class);
        suite.addTestSuite(ConfluenceManagementServiceBeanTests.class);
        suite.addTestSuite(ConfluenceManagerWebServiceDelegateTests.class);

        suite.addTestSuite(ConfluenceManagementServiceClientTests.class);

        suite.addTestSuite(Demo.class);
        return suite;
    }
}