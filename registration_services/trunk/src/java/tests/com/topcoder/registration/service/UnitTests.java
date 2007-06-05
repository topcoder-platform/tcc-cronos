/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.topcoder.registration.service.impl.RegistrationInfoImplTest;
import com.topcoder.registration.service.impl.RegistrationPositionImplTest;
import com.topcoder.registration.service.impl.RegistrationResultImplTest;
import com.topcoder.registration.service.impl.RegistrationServicesImplTest;
import com.topcoder.registration.service.impl.RemovalResultImplTest;
import com.topcoder.registration.service.impl.UtilTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     * @return a test suite will be returned
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(CustomResourcePropertiesTest.class);
        suite.addTestSuite(InvalidRoleExceptionTest.class);
        suite.addTestSuite(RegistrationServiceConfigurationExceptionTest.class);
        suite.addTestSuite(RegistrationServiceExceptionTest.class);
        suite.addTestSuite(RegistrationValidationExceptionTest.class);
        suite.addTestSuite(RegistrationInfoImplTest.class);
        suite.addTestSuite(RegistrationPositionImplTest.class);
        suite.addTestSuite(RegistrationResultImplTest.class);
        suite.addTestSuite(RegistrationServicesImplTest.class);
        suite.addTestSuite(RemovalResultImplTest.class);
        suite.addTestSuite(UtilTest.class);
        suite.addTestSuite(DemoTest.class);

        return suite;
    }

}
