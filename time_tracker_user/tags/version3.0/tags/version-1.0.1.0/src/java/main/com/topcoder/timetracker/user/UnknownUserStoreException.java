/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception is thrown by <code>UserManager</code> if the User being imported references
 * an unknown user store name (i.e., it's not managed by the UserStoreManager).
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class UnknownUserStoreException extends BaseException {

    /**
     * <p>
     * Constructs a new <code>UnknownUserStoreException</code> with the given message providing the
     * details of the error occurred.
     * </p>
     *
     * @param message the details of the error that occurred.
     */
    public UnknownUserStoreException(String message) {
        super(message);
    }


    /**
     * <p>
     * Constructs a new <code>UnknownUserStoreException</code>, with the given message and cause.
     * </p>
     *
     * @param message the details of the error that occurred.
     * @param cause the original cause of this exception (e.g., SQLException)
     */
    public UnknownUserStoreException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>
     * Constructs a new <code>UnknownUserStoreException</code> with the given cause.
     * </p>
     *
     * @param cause the original cause of this exception (e.g., SQLException)
     */
    public UnknownUserStoreException(Throwable cause) {
        super(cause);
    }
}