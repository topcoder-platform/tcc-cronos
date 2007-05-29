/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.project.service.impl.ProjectServicesImplTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     * @return A test suite will be returned
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConfigurationExceptionTest.class);
        suite.addTestSuite(DemoTest.class);
        suite.addTestSuite(FullProjectDataTest.class);
        suite.addTestSuite(ProjectServicesExceptionTest.class);
        suite.addTestSuite(UtilTest.class);
        suite.addTestSuite(ProjectServicesImplTest.class);

        return suite;
    }

}
