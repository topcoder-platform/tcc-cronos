/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Stress tests for Project Service.
 *
 * @author 80x86
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * Int value for the stress tests, loop TIMES times for each test.
     */
    public static final int TIMES = 10;

    /**
     * Runs all stress tests.
     *
     * @return instance of Test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        //suite.addTestSuite(JPAProjectPersistenceUnitTests.class);
        suite.addTestSuite(ProjectServiceBeanUnitTests.class);
        return suite;
    }
}
