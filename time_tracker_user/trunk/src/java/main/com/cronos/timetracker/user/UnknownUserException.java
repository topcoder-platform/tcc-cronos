/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by <code>UserManager</code> and <code>UserStore</code> implementations
 * if the given username doesn't exist within internal storage or the underlying persistent user store.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UnknownUserException extends BaseException {

    /**
     * <p>
     * Constructs a new <code>UnknownUserException</code> with the given message providing the
     * details of the error occurred.
     * </p>
     *
     * @param message the details of the error that occurred.
     */
    public UnknownUserException(String message) {
        super(message);
    }


    /**
     * <p>
     * Constructs a new <code>UnknownUserException</code>, with the given message and cause.
     * </p>
     *
     * @param message the details of the error that occurred.
     * @param cause the original cause of this exception (e.g., SQLException)
     */
    public UnknownUserException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>
     * Constructs new <code>UnknownUserException</code> with the given cause.
     * </p>
     *
     * @param cause the original cause of this exception (e.g., SQLException)
     */
    public UnknownUserException(Throwable cause) {
        super(cause);
    }
}