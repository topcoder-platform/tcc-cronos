/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is the top-level exception thrown by the liquid portal
 * service. It extends Exception.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class LiquidPortalServicingException extends Exception {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -4320918748108288924L;

    /**
     * <p>
     * Creates a new exception instance with no error message and no cause of
     * error.
     * </p>
     */
    public LiquidPortalServicingException() {
    }

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public LiquidPortalServicingException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new exception instance with no error message and the given
     * cause of error.
     * </p>
     *
     * @param cause
     *            cause of error
     */
    public LiquidPortalServicingException(Throwable cause) {
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
    public LiquidPortalServicingException(String message, Throwable cause) {
        super(message, cause);
    }
}
