/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

/**
 * <p>
 * This exception will be thrown by the ClientUtility if it encounters any exception when try to
 * retrieve/delete/update/create the contact/address/project property from contact/address manager or project utility.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class PropertyOperationException extends ClientUtilityException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -6284196142621394198L;

	/**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public PropertyOperationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs the exception with given message and cause.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public PropertyOperationException(String message, Exception cause) {
        super(message, cause);
    }
}
