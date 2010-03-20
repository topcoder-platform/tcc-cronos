/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is thrown by the service if it cannot find the given handle.
 * It extends LiquidPortalServiceException.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class HandleNotFoundException extends LiquidPortalServiceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = -7984257737163608401L;

    /**
     * <p>
     * Represents the handle that was not found.
     * </p>
     * <p>
     * It is set in the constructor it is retrieved in the getter.
     * </p>
     */
    private final String handle;

    /**
     * <p>
     * Creates a new exception instance with the handle and error message.
     * </p>
     *
     * @param handle
     *            the handle
     * @param message
     *            error message
     */
    public HandleNotFoundException(String handle, String message) {
        super(message);
        this.handle = handle;
    }

    /**
     * <p>
     * Creates a new exception instance with handle and error message and the
     * given cause of error.
     * </p>
     *
     * @param handle
     *            the handle
     * @param message
     *            error message
     * @param cause
     *            cause of error
     */
    public HandleNotFoundException(String handle, String message, Throwable cause) {
        super(message, cause);
        this.handle = handle;
    }

    /**
     * <p>
     * Gets the value of the handle.
     * </p>
     *
     * @return the value of the handle
     */
    public String getHandle() {
        return handle;
    }
}
