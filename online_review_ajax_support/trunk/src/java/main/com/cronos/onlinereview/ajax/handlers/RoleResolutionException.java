/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.ajax.handlers;
import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Represents an exception used to wrap any checked exception thrown by the ResourceManager class.
 * <br><br>
 * This exception is thrown by  many methods in the CommonHandler class.
 * </p>
 *
 * @author topgear, TCSDEVELOPER
 * @version 1.0
 */
public class RoleResolutionException extends BaseException {

    /**
     * <p>
     * Creates a new instance of this exception class.
     * </p>
     */
    public RoleResolutionException() {
        // do nothing
    }

    /**
     * <p>
     * Creates a new instance of this exception class with a descriptive message.
     * </p>
     *
     * @param message a descriptive message about the exception
     */
    public RoleResolutionException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of this exception class with the cause of the exception.
     * </p>
     *
     * @param cause the cause of this exception
     */
    public RoleResolutionException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Creates a new instance of this exception class with a descriptive message, and the cause of the exception.
     * </p>
     *
     * @param message a descriptive message about the exception
     * @param cause the cause of this exception
     */
    public RoleResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
