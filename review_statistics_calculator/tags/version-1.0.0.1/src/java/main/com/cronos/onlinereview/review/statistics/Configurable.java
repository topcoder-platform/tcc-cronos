/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics;

import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * This interface must be implemented by all classes that support initialization using Configuration API component.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface are not required to be thread safe. It's assumed that
 * configure() method will be called just once right after instantiation, before calling any business methods.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface Configurable {
    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param config the configuration object
     *
     * @throws IllegalArgumentException if config is null
     * @throws StatisticsCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration
     */
    public void configure(ConfigurationObject config);
}
