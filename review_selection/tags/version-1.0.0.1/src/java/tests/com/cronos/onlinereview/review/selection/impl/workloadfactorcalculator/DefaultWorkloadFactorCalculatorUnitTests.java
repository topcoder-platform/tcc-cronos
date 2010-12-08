/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

/**
 * Unit test cases for class DefaultWorkloadFactorCalculator.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class DefaultWorkloadFactorCalculatorUnitTests extends TestCase {
    /**
     * Tests constructor: DefaultWorkloadFactorCalculator(ReviewApplication, double) with valid parameters. The review
     * application and coefficient value can be retrieved correctly later. No exception is expected.
     */
    public void testCtor() {
        new DefaultWorkloadFactorCalculator();
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithValidConfig() throws Exception {
        DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
            "valid_config");
        calculator.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with null config. IllegalArgumentException should be thrown.
     */
    public void testConfigureWithNullConfig() {
        try {
            DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
            calculator.configure(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. WorkloadFactorCalculatorConfigurationException should
     * be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig1() throws Exception {
        try {
            DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
                "invalid_config1");
            calculator.configure(config);
            fail("Expect WorkloadFactorCalculatorConfigurationException.");
        } catch (WorkloadFactorCalculatorConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. WorkloadFactorCalculatorConfigurationException should
     * be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig2() throws Exception {
        try {
            DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
                "invalid_config2");
            calculator.configure(config);
            fail("Expect WorkloadFactorCalculatorConfigurationException.");
        } catch (WorkloadFactorCalculatorConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. WorkloadFactorCalculatorConfigurationException should
     * be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig3() throws Exception {
        try {
            DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
                "invalid_config3");
            calculator.configure(config);
            fail("Expect WorkloadFactorCalculatorConfigurationException.");
        } catch (WorkloadFactorCalculatorConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests calculateWorkloadFactor(int) for accuracy. In the config, the workloadFactorBase is set to 0.9, and the
     * reviewNumberMultiplier is set to 0.01; when calling calculateWorkloadFactor with 3 as the value of
     * concurrentReviewNumber, the result should be 0.9 - 0.01 * 3 = 0.87.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCalculateWorkloadFactorForAccuracy1() throws Exception {
        DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
            "valid_config");
        calculator.configure(config);

        // 0.9 - 0.01 * 3 = 0.87
        assertEquals("The result of calculateWorkloadFactor is incorrect.", 0.87, calculator
            .calculateWorkloadFactor(3), 0.0000001);
    }

    /**
     * Tests calculateWorkloadFactor(int) for accuracy. The config is empty, so the workloadFactorBase is set to the
     * default 1.0, and the reviewNumberMultiplier is set to the default 0.05; when calling calculateWorkloadFactor
     * with 10 as the value of concurrentReviewNumber, the result should be 1.0 - 0.05 * 10 = 0.5.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCalculateWorkloadFactorForAccuracy2() throws Exception {
        DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
            "empty_config");
        calculator.configure(config);

        // 1.0 - 0.05 * 10 = 0.5
        assertEquals("Secondary reviewer is not properly initialized.", 0.5, calculator.calculateWorkloadFactor(10),
            0.0000001);
    }

    /**
     * Tests calculateWorkloadFactor(int) for accuracy. In the config, the workloadFactorBase is set to 0.2, and the
     * reviewNumberMultiplier is set to 0.9; when calling calculateWorkloadFactor with 1 as the value of
     * concurrentReviewNumber, since 0.2 - 0.9 * 1 < 0, the result should be 0.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCalculateWorkloadFactorForAccuracy3() throws Exception {
        DefaultWorkloadFactorCalculator calculator = new DefaultWorkloadFactorCalculator();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("workload_factor_calculator_config").getChild(
            "large_multiplier_config");
        calculator.configure(config);
        // 0.2 - 0.9 * 1 < 0
        assertEquals("Secondary reviewer is not properly initialized.", 0, calculator.calculateWorkloadFactor(1),
            0.0000001);
    }
}
