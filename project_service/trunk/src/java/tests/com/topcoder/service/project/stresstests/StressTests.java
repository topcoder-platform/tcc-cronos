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
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class StressTests extends TestCase {

    /**
     * Number for the stress tests, loop TIMES times for each test.
     */
    public static final int TIMES = 10;

    /**
     * Represents double value of one thousand.
     */
    public static final double ONE_THOUSAND = 1000;

    /**
     * Runs all stress tests.
     *
     * @return instance of Test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(ProjectServiceBeanStressTests.class);
        return suite;
    }
}
