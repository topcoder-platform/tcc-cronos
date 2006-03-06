/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SubmissionValidatorFactory.java
 */
package com.topcoder.apps.screening.applications.specification;

/**
 * <p>
 * This interface defines an abstract factory for submission validators and output formatters. The factory
 * defines two methods: a method to create a set of validators to use and a method to create a formatter
 * to use.
 * </p>
 *
 * <p>
 * The component provides the default factory implementation (DefaultSubmissionValidatorFactory)
 * which instantiates validators and formatters based on the information loaded from the static component
 * configuration.
 * </p>
 *
 * <p>
 * Thread-Safety: all implementations of this interface are expected to be thread-safe.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public interface SubmissionValidatorFactory {
    /**
     * <p>
     * This is a declaration of the factory method to instantiate the validators. The behavior of this method is
     * implementation dependent. All implementations are required to return a non-empty array with no null
     * elements.
     * </p>
     *
     * @return the set of validators to  use (as a static array)
     * @throws ConfigurationException if the factory configuration can't be retrieved or the configuration is wrong
     */
    public SubmissionValidator[] createValidators() throws ConfigurationException;

    /**
     * <p>
     * This is a declaration of the factory method to instantiate the formatter. The behavior of this method is
     * implementation dependent.
     * </p>
     *
     * @return the formatter to use
     * @throws ConfigurationException if the factory configuration can't be retrieved or the configuration is wrong
     */
    public ValidationOutputFormatter createFormatter() throws ConfigurationException;
}