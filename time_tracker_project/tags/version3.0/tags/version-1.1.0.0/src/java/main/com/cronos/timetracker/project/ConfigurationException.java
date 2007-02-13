/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception is thrown by the ProjectPersistenceManager if anything goes wrong in the process of loading the
 * configuration file or if the information is missing or corrupted.
 * </p>
 *
 * @author DanLazar, colau
 * @version 1.1
 *
 * @since 1.0
 */
public class ConfigurationException extends BaseException {
    /**
     * <p>
     * Creates a new instance of this custom exception with the given message. The message can be null, however.
     * </p>
     *
     * @param message the message describing the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this custom exception with the given message and cause. The message and cause can be
     * null, however.
     * </p>
     *
     * @param message the message describing the exception
     * @param cause the cause of the exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
