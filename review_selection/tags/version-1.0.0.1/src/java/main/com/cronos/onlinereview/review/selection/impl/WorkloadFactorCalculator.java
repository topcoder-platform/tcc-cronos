/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * This interface is used by DefaultReviewApplicationProcessor to calculate workload factor based on the concurrent
 * number of review. This component provides the DefaultWorkloadFactorCalculator as an implementation of this
 * interface.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation of this interface should be thread-safe.
 * </p>
 *
 * @author Wendell, xianwenchen
 * @version 1.0
 */
public interface WorkloadFactorCalculator {
    /**
     * Calculate the workload factor using given concurrent review number.
     *
     * @param concurrentReviewNumber the number of concurrent review used to calculate workload factor
     * @return The workload factor.
     * @throws IllegalArgumentException is given conccurrentReviewNumber is negative.
     * @throws IllegalStateException if any property not configured properly.
     */
    public double calculateWorkloadFactor(int concurrentReviewNumber);

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     * @throws IllegalArgumentException if config is null
     * @throws WorkloadFactorCalculatorConfigurationException if some error occurred when initializing an instance
     *             using the given configuration
     */
    public void configure(ConfigurationObject config);
}
