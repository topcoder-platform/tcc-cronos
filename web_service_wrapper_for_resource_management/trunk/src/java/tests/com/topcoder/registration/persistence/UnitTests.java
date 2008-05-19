/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.registration.persistence.client.ResourceManagerServiceClientAccTests;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientCreationExceptionUnitTests;
import com.topcoder.registration.persistence.client.ResourceManagerServiceClientExpTests;
import com.topcoder.registration.persistence.converter.RegistrationEntitiesToResourceConverterImplUnitTests;
import com.topcoder.registration.persistence.ejbservice.ResourceManagerBeanInitializationExceptionUnitTests;
import com.topcoder.registration.persistence.ejbservice.ResourceManagerServiceBeanUnitTests;

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
     * Return all Unit test cases.
     * </p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(WebServiceWrapperForResourceManagementExceptionUnitTests.class);
        suite.addTestSuite(ConfigurationExceptionUnitTests.class);
        suite.addTestSuite(RegistrationEntitiesToResourceConversionExceptionUnitTests.class);
        suite.addTestSuite(ResourceManagementExceptionUnitTests.class);
        suite.addTestSuite(ResourceManagerBeanInitializationExceptionUnitTests.class);
        suite.addTestSuite(ResourceManagerServiceClientCreationExceptionUnitTests.class);

        suite.addTestSuite(CopiedResourceUnitTests.class);
        suite.addTestSuite(GetResourceResponseUnitTests.class);
        suite.addTestSuite(UpdateResourceRequestUnitTests.class);
        suite.addTestSuite(UpdateResourcesRequestUnitTests.class);
        suite.addTestSuite(ResourceManagementFaultUnitTests.class);

        suite.addTestSuite(HelperUnitTests.class);

        suite.addTestSuite(RegistrationEntitiesToResourceConverterImplUnitTests.class);

        suite.addTestSuite(ResourceManagerServiceBeanUnitTests.class);

        suite.addTestSuite(ResourceManagerRegistrationPersistenceUnitTests.class);

        suite.addTestSuite(ResourceManagerServiceClientAccTests.class);
        suite.addTestSuite(ResourceManagerServiceClientExpTests.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }

}
