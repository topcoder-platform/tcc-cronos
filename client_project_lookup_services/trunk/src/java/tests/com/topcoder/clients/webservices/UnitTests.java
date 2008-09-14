/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.webservices;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.webservices.beans.ClientStatusServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.ClientStatusServiceBeanTest;
import com.topcoder.clients.webservices.beans.LookupServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.LookupServiceBeanTest;
import com.topcoder.clients.webservices.beans.ProjectStatusServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.ProjectStatusServiceBeanTest;
import com.topcoder.clients.webservices.webserviceclients.ClientStatusServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.ClientStatusServiceClientTest;
import com.topcoder.clients.webservices.webserviceclients.LookupServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.LookupServiceClientTest;
import com.topcoder.clients.webservices.webserviceclients.ProjectStatusServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.ProjectStatusServiceClientTest;

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
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Beans
        suite.addTestSuite(ClientStatusServiceBeanTest.class);
        suite.addTestSuite(ProjectStatusServiceBeanTest.class);
        suite.addTestSuite(LookupServiceBeanTest.class);

        // Clients
        suite.addTestSuite(ClientStatusServiceClientTest.class);
        suite.addTestSuite(LookupServiceClientTest.class);
        suite.addTestSuite(ProjectStatusServiceClientTest.class);

        // Exceptions
        suite.addTestSuite(ClientStatusServiceExceptionTest.class);
        suite.addTestSuite(LookupServiceExceptionTest.class);
        suite.addTestSuite(ProjectStatusServiceExceptionTest.class);
        suite.addTestSuite(ClientStatusServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(LookupServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(ProjectStatusServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(ClientStatusServiceClientCreationExceptionTest.class);
        suite.addTestSuite(ProjectStatusServiceClientCreationExceptionTest.class);
        suite.addTestSuite(LookupServiceClientCreationExceptionTest.class);

        suite.addTestSuite(UtilTest.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
