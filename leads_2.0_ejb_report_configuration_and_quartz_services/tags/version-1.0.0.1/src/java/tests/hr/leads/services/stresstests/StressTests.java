/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author elkhawajah
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Test suite for stress tests.
     * 
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(SystemConfigurationPropertyServiceBeanStressUnitTest.class);
        return suite;
    }
}
