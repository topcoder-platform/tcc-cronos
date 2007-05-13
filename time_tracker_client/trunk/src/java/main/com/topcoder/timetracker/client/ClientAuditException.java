/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

/**
 * <p>
 * This exception will be thrown by the ClientUtility if it encounters exception when audit.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientAuditException extends ClientUtilityException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -7434632463915221694L;

	/**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public ClientAuditException(String message) {
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
    public ClientAuditException(String message, Exception cause) {
        super(message, cause);
    }
}
