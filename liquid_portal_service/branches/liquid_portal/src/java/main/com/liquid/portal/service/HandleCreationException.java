/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import javax.ejb.ApplicationException;

/**
 * <p>
 * This exception is thrown by the service if it cannot create the given handle.
 * It extends LiquidPortalServiceException.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@ApplicationException(rollback = true)
public class HandleCreationException extends LiquidPortalServiceException {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 5998923398486247086L;

    /**
     * <p>
     * Represents the handle that was not created.
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
    public HandleCreationException(String handle, String message) {
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
    public HandleCreationException(String handle, String message, Throwable cause) {
        super(message, cause);
        this.handle = handle;
    }

    /**
     * <p>
     * Gets the value of handle.
     * </p>
     *
     * @return the value of handle
     */
    public String getHandle() {
        return handle;
    }
}
