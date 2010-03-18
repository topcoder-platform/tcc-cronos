/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

/**
 * <p>
 * This exception signals an issue if the configuration fails for any reason
 * during initialization. It extends RuntimeException. It is used by all classes
 * that perform initialization via configuration.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalServiceConfigurationException extends RuntimeException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 5529565014067347747L;

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of
     * error.
     * </p>
     */
    public LiquidPortalServiceConfigurationException() {
    }

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public LiquidPortalServiceConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and
     * the given cause of error.
     * </p>
     *
     * @param cause
     *          case of error
     */
    public LiquidPortalServiceConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Creates a new exception instance with an error message and the given
     * cause of error.
     * </p>
     *
     * @param message
     *            error message
     * @param cause
     *            cause of error
     */
    public LiquidPortalServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
