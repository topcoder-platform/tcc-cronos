/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from operations from LiquidPortalService.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This exception is not thread safe because parent exception is not thread safe. The
 * application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalServiceException extends Exception {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -543148596996024920L;

    private int errorCode;

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public LiquidPortalServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public LiquidPortalServiceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public LiquidPortalServiceException(String message, Throwable cause) {
        super(message, cause);
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
    public LiquidPortalServiceException(Throwable cause) {
        super(cause);
    }

    public LiquidPortalServiceException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }


    public LiquidPortalServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    
    public int getErrorCode()
    {
        return this.errorCode;
    }
}
