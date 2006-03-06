/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ConfigurationException.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown to indicate any problems in the component configuration, such as
 * missing a mandatory configuration property or failure to create a validator/formatter entity via reflection,
 * The exception will  wrap all exceptions thrown by ConfigManager or reflection API.
 * </p>
 *
 * @author nicka81, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationException extends BaseException {

    /**
     * <p>
     * Creates an exception with the given parameter.
     * </p>
     *
     * @param details the exception details
     */
    public ConfigurationException(String details) {
        super(details);
    }

    /**
     * <p>
     * Creates an exception with the given parameters.
     * </p>
     *
     * @param details        the exception details
     * @param innerException the inner exception
     */
    public ConfigurationException(String details, Exception innerException) {
        super(details, innerException);
    }
}