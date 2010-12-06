/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is thrown by the service if the user does not have permission
 * to perform the action. It extends LiquidPortalServiceException.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class ActionNotPermittedException extends LiquidPortalServiceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -4828726127513490239L;

    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param message
     *            error message
     */
    public ActionNotPermittedException(String message) {
        super(message);
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
    public ActionNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
