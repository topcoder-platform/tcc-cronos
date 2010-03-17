/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 * @author moon.river
 * @version 1.1
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
     * Return all stress test cases.
     * @return all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ProjectServiceBeanTestsV11.suite());
        return suite;
    }

}
