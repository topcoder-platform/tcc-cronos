/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests.impl.calculators;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.failuretests.FailureTestHelper;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.UserReliabilityCalculationException;
import com.topcoder.reliability.impl.calculators.UniformUserReliabilityCalculator;

/**
 * Failure tests for UniformUserReliabilityCalculator class.
 * 
 * @author Yeung
 * @version 1.0
 */
public class UniformUserReliabilityCalculatorFailureTests {
    /**
     * The UniformUserReliabilityCalculatorTester instance to test against.
     */
    private UniformUserReliabilityCalculatorTester instance;

    /**
     * The configuration object for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up the test environment.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = FailureTestHelper.getConfig("UniformUserReliabilityCalculator");

        instance = new UniformUserReliabilityCalculatorTester();
        instance.configure(config);
    }

    /**
     * Tests the method configure with null config. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigureWithNullConfig() throws Exception {
        instance.configure(null);
    }

    /**
     * Tests the method configure with non-string 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNonStringLoggerName() throws Exception {
        config.setPropertyValue("loggerName", 1);

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyLoggerName() throws Exception {
        config.setPropertyValue("loggerName", " \t \n ");

        instance.configure(config);
    }
    
    /**
     * Tests the method configure with zero 'historyLength'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithZeroHistoryLength() throws Exception {
        config.setPropertyValue("historyLength", "0");

        instance.configure(config);
    }
    
    /**
     * Tests the method configure with negative 'historyLength'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNegativeHistoryLength() throws Exception {
        config.setPropertyValue("historyLength", "-1");

        instance.configure(config);
    }
    
    /**
     * Tests the method configure with unknown 'historyLength'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithUnknownHistoryLength() throws Exception {
        config.setPropertyValue("historyLength", "unknown");

        instance.configure(config);
    }
    
    /**
     * Tests the method configure with missing 'historyLength'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingHistoryLength() throws Exception {
        config.removeProperty("historyLength");

        instance.configure(config);
    }

    /**
     * Tests the method calculate with null reliabilityData. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullReliabilityData() throws Exception {
        instance.callCalculateReliabilityAfterProjects(null);
    }

    /**
     * Tests the method calculate with null UserProjectReliabilityData. Expect IllegalArgumentException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithNullUserProjectReliabilityData() throws Exception {
        List<UserProjectReliabilityData> list = new ArrayList<UserProjectReliabilityData>();
        list.add(new UserProjectReliabilityData());
        list.add(new UserProjectReliabilityData());
        list.add(null);
        instance.callCalculateReliabilityAfterProjects(list);
    }

    /**
     * Tests the method calculate without configured. Expect IllegalStateException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testCalculateWithoutConfigured() throws Exception {
        List<UserProjectReliabilityData> list = new ArrayList<UserProjectReliabilityData>();
        list.add(new UserProjectReliabilityData());
        list.add(new UserProjectReliabilityData());
        new UniformUserReliabilityCalculatorTester().callCalculateReliabilityAfterProjects(list);
    }

    /**
     * This tester class exposes the protected part in UniformUserReliabilityCalculator class.
     * 
     * @author Yeung
     * @version 1.0
     */
    private class UniformUserReliabilityCalculatorTester extends UniformUserReliabilityCalculator {

        /**
         * Initializes a new instance of UniformUserReliabilityCalculatorTester class.
         */
        public UniformUserReliabilityCalculatorTester() {
        }

        /**
         * Calls the calculateReliabilityAfterProjects method of super class.
         * 
         * @param reliabilityData
         *            The reliability data.
         */
        public void callCalculateReliabilityAfterProjects(List<UserProjectReliabilityData> reliabilityData)
                throws UserReliabilityCalculationException {
            super.calculateReliabilityAfterProjects(reliabilityData);
        }
    }
}
