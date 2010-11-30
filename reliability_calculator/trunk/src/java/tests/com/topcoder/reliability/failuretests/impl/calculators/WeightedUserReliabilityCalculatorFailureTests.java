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
import com.topcoder.reliability.impl.calculators.WeightedUserReliabilityCalculator;

/**
 * Failure tests for WeightedUserReliabilityCalculator class.
 * 
 * @author Yeung
 * @version 1.0
 */
public class WeightedUserReliabilityCalculatorFailureTests {
    /**
     * The WeightedUserReliabilityCalculatorTester instance to test against.
     */
    private WeightedUserReliabilityCalculatorTester instance;

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
        config = FailureTestHelper.getConfig("WeightedUserReliabilityCalculator");

        instance = new WeightedUserReliabilityCalculatorTester();
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
     * Tests the method configure with zero weight. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithZeroWeight() throws Exception {
        config.setPropertyValues("weights", new String[] { "0.5", "0.6", "0" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with negative weight. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNegativeWeight() throws Exception {
        config.setPropertyValues("weights", new String[] { "0.5", "0.6", "-1" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with unknown weight. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithUnknownWeight() throws Exception {
        config.setPropertyValues("weights", new String[] { "0.5", "0.6", "unknown" });

        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'weights'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingWeights() throws Exception {
        config.removeProperty("weights");

        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'weights'. Expect ReliabilityCalculatorConfigurationException.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyWeights() throws Exception {
        config.setPropertyValues("weights", new String[] {});

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
        new WeightedUserReliabilityCalculatorTester().callCalculateReliabilityAfterProjects(list);
    }

    /**
     * This tester class exposes the protected part in WeightedUserReliabilityCalculator class.
     * 
     * @author Yeung
     * @version 1.0
     */
    private class WeightedUserReliabilityCalculatorTester extends WeightedUserReliabilityCalculator {

        /**
         * Initializes a new instance of WeightedUserReliabilityCalculatorTester class.
         */
        public WeightedUserReliabilityCalculatorTester() {
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
