/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * This interface should be extended by interfaces and implemented by classes that can be configured from
 * Configuration API object. It's assumed that configure() method defined in this interface will be called just once
 * for each instance.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0
 */
public interface Configurable {
    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @throws IllegalArgumentException
     *             if config is <code>null</code>.
     * @throws ReliabilityCalculatorConfigurationException
     *             if some error occurred when initializing an instance using the given configuration.
     */
    public void configure(ConfigurationObject config);
}
