/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.webservices;

import com.topcoder.clients.webservices.beans.AuthorizationFailedExceptionTest;
import com.topcoder.clients.webservices.beans.ClientServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.ClientServiceBeanTest;
import com.topcoder.clients.webservices.beans.CompanyServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.CompanyServiceBeanTest;
import com.topcoder.clients.webservices.beans.ProjectServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.beans.ProjectServiceBeanTest;
import com.topcoder.clients.webservices.beans.ServiceBeanConfigurationExceptionTest;
import com.topcoder.clients.webservices.usermapping.EntityNotFoundExceptionTest;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalExceptionTest;
import com.topcoder.clients.webservices.usermapping.UserNotFoundExceptionTest;
import com.topcoder.clients.webservices.usermapping.impl.JPAUserMappingRetrieverTest;
import com.topcoder.clients.webservices.usermapping.impl.UserClientMappingTest;
import com.topcoder.clients.webservices.usermapping.impl.UserProjectMappingTest;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.ClientServiceClientTest;
import com.topcoder.clients.webservices.webserviceclients.ClientUtilsTest;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.CompanyServiceClientTest;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClientCreationExceptionTest;
import com.topcoder.clients.webservices.webserviceclients.ProjectServiceClientTest;
import com.topcoder.clients.webservices.webserviceclients.ServiceClientCreationExceptionTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Demo
        suite.addTestSuite(Demo.class);

        // com.topcoder.clients.webservices
        suite.addTestSuite(ClientProjectManagementServicesExceptionTest.class);
        suite.addTestSuite(ClientServiceExceptionTest.class);
        suite.addTestSuite(CompanyServiceExceptionTest.class);
        suite.addTestSuite(ProjectServiceExceptionTest.class);

        // com.topcoder.clients.webservices.bean
        suite.addTestSuite(ClientServiceBeanTest.class);
        suite.addTestSuite(CompanyServiceBeanTest.class);
        suite.addTestSuite(ProjectServiceBeanTest.class);
        suite.addTestSuite(AuthorizationFailedExceptionTest.class);
        suite.addTestSuite(ClientServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(CompanyServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(ProjectServiceBeanConfigurationExceptionTest.class);
        suite.addTestSuite(ServiceBeanConfigurationExceptionTest.class);

        // com.topcoder.clients.webservices.usermapping
        suite.addTestSuite(EntityNotFoundExceptionTest.class);
        suite.addTestSuite(UserMappingRetrievalExceptionTest.class);
        suite.addTestSuite(UserNotFoundExceptionTest.class);

        // com.topcoder.clients.webservices.usermapping.impl
        suite.addTestSuite(UserClientMappingTest.class);
        suite.addTestSuite(UserProjectMappingTest.class);
        suite.addTestSuite(JPAUserMappingRetrieverTest.class);

        // com.topcoder.clients.webservices.webserviceclients
        suite.addTestSuite(ClientUtilsTest.class);
        suite.addTestSuite(ClientServiceClientTest.class);
        suite.addTestSuite(CompanyServiceClientTest.class);
        suite.addTestSuite(ProjectServiceClientTest.class);
        suite.addTestSuite(ClientServiceClientCreationExceptionTest.class);
        suite.addTestSuite(CompanyServiceClientCreationExceptionTest.class);
        suite.addTestSuite(ProjectServiceClientCreationExceptionTest.class);
        suite.addTestSuite(ServiceClientCreationExceptionTest.class);

        return suite;
    }

}
