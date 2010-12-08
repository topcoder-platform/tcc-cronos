/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator;

import com.cronos.onlinereview.review.selection.Helper;
import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculator;
import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculatorConfigurationException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class implements the WorkloadFactorCalculator interface. It uses the formula "workloadFactorBase -
 * reviewNumberMultiplier * concurrentReviewNumber" to calculate workload factor.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and thread-safe as long as the configure() method will be called just once
 * right after instantiation.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public class DefaultWorkloadFactorCalculator implements WorkloadFactorCalculator {
    /**
     * <p>
     * Represents the default workload factor base.
     * </p>
     */
    public static final double DEFAULT_WORKLOAD_FACTOR_BASE = 1.0;

    /**
     * <p>
     * Represents the default review number multiplier.
     * </p>
     */
    public static final double DEFAULT_REVIEW_NUMBER_MULTIPLIER = 0.05;

    /**
     * <p>
     * Represents the workload factor base used to calculate workload factor.
     * </p>
     *
     * <p>
     * Initial value equals to DEFAULT_WORKLOAD_FACTOR_BASE, also can be initialized in the configure() method. Valid
     * value should be between 0(inclusive), 1(inclusive) after initialized. Can not be changed after initialized.
     * </p>
     *
     * <p>
     * Used by the calculateWorkloadFactor() method to calculate workload factor.
     * </p>
     */
    private double workloadFactorBase = DEFAULT_WORKLOAD_FACTOR_BASE;

    /**
     * <p>
     * Represents the review number multiplier used to calculate workload factor.
     * </p>
     *
     * <p>
     * Initial value equals to DEFAULT_REVIEW_NUMBER_MULTIPLIER, also can be initialized in the configure() method.
     * Valid value should be between 0(inclusive), 1(inclusive) after initialized. Can not be changed after
     * initialized.
     * </p>
     *
     * <p>
     * Used by the calculateWorkloadFactor() method to calculate workload factor.
     * </p>
     */
    private double reviewNumberMultiplier = DEFAULT_REVIEW_NUMBER_MULTIPLIER;

    /**
     * Default no-argument constructor. Constructs a new DefaultWorkloadFactorCalculator instance.
     */
    public DefaultWorkloadFactorCalculator() {
    }

    /**
     * Calculate the workload factor using given concurrent review number.
     *
     * @param concurrentReviewNumber the number of concurrent review used to calculate workload factor
     * @throws IllegalArgumentException is given concurrentReviewNumber is negative.
     * @throws IllegalStateException if any property not configured properly.
     * @return The workload factor.
     */
    public double calculateWorkloadFactor(int concurrentReviewNumber) {
        if (workloadFactorBase < 0 || workloadFactorBase > 1) {
            throw new IllegalStateException(
                "The value of workloadFactorBase is invalid. It must be between 0(inclusive), 1(inclusive).");
        }
        if (reviewNumberMultiplier < 0 || reviewNumberMultiplier > 1) {
            throw new IllegalStateException(
                "The value of reviewNumberMultiplier is invalid. It must be between 0(inclusive), 1(inclusive).");
        }

        if (concurrentReviewNumber < 0) {
            throw new IllegalArgumentException("The concurrentReviewNumber is negative");
        }
        double result = workloadFactorBase - concurrentReviewNumber * reviewNumberMultiplier;
        return result > 0 ? result : 0;
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     * @throws IllegalArgumentException if config is null
     * @throws WorkloadFactorCalculatorConfigurationException if some error occurred when initializing an instance
     *             using the given configuration
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");

        ObjectFactory objectFactory = createObjectFactory(config);
        workloadFactorBase = getDoubleProperty(config, objectFactory, "workloadFactorBase",
            DEFAULT_WORKLOAD_FACTOR_BASE);
        if (workloadFactorBase < 0 || workloadFactorBase > 1) {
            throw new WorkloadFactorCalculatorConfigurationException(
                "The value of workloadFactorBase config is invalid. It must be between 0(inclusive), 1(inclusive).");
        }

        reviewNumberMultiplier = getDoubleProperty(config, objectFactory, "reviewNumberMultiplier",
            DEFAULT_REVIEW_NUMBER_MULTIPLIER);
        if (reviewNumberMultiplier < 0 || reviewNumberMultiplier > 1) {
            throw new WorkloadFactorCalculatorConfigurationException(
                "The value of reviewNumberMultiplier config is invalid."
                    + " It must be between 0(inclusive), 1(inclusive).");
        }
    }

    /**
     * Create object factory from given config.
     *
     * @param config the configuration object
     * @return the object factory.
     * @throws WorkloadFactorCalculatorConfigurationException if error occurred when creating the object factory.
     */
    private static ObjectFactory createObjectFactory(ConfigurationObject config) {
        ConfigurationObjectSpecificationFactory cosf;
        try {
            cosf = new ConfigurationObjectSpecificationFactory(config);
        } catch (IllegalReferenceException e) {
            throw new WorkloadFactorCalculatorConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory config.", e);
        } catch (SpecificationConfigurationException e) {
            throw new WorkloadFactorCalculatorConfigurationException(
                "Error occurred when creating ConfigurationObjectSpecificationFactory config.", e);
        }
        return new ObjectFactory(cosf);
    }

    /**
     * Create a double property via objectFactory.
     *
     * @param config the config object
     * @param objectFactory the object factory.
     * @param propertyName the name of the property.
     * @param defaultValue the default value of the property
     * @return the created double value.
     * @throws WorkloadFactorCalculatorConfigurationException if error occurred when creating the property.
     */
    private Double getDoubleProperty(ConfigurationObject config, ObjectFactory objectFactory, String propertyName,
        Double defaultValue) {
        Double propertyValue;
        try {
            if (config.containsChild(propertyName)) {
                propertyValue = (Double) objectFactory.createObject(propertyName);
            } else {
                propertyValue = defaultValue;
            }
        } catch (InvalidClassSpecificationException e) {
            throw new WorkloadFactorCalculatorConfigurationException("Error occurred when creating object with key "
                + propertyName + ".", e);
        } catch (ClassCastException e) {
            throw new WorkloadFactorCalculatorConfigurationException("Failed to cast created object " + propertyName
                + " to Double.", e);
        } catch (ConfigurationAccessException e) {
            throw new WorkloadFactorCalculatorConfigurationException("Error occurred when accessing property ["
                + propertyName + "] from given config.", e);
        }
        return propertyValue;
    }
}
