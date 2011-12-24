/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.subscribers;

import com.topcoder.configuration.ConfigurationObject;

/**
 * <p>
 * This interface represents a configurable payment event subscriber. It simply extends {@code PaymentEventSubscriber}
 * and defines {@code configure()} method that should be used for configuring subscriber instances with use of
 * <i>Configuration API</i> component.
 * </p>
 *
 * <strong>Thread Safety:</strong> Implementations of this interface are required to be thread safe when
 * {@code configure()} method is called just once right after instantiation and entities passed to them are used by
 * the caller in thread safe manner.
 *
 * @author saarixx, KennyAlive
 * @version 1.0
 */
public interface ConfigurablePaymentEventSubscriber extends PaymentEventSubscriber {
    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *            if configuration is {@code null} (it is not thrown by implementations that don't use any
     *             configuration parameters)
     * @throws com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException
     *            if some error occurred when initializing an instance using the given configuration
     */
    public void configure(ConfigurationObject configuration);
}
