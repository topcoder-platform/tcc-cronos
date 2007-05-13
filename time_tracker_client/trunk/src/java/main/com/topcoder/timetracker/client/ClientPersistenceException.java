/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

/**
 * <p>
 * This exception will be thrown by the ClientUtiltiy and the implementations of ClientDAO when they encounter database
 * exceptions.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientPersistenceException extends ClientUtilityException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 71339456560210958L;

	/**
     * <p>
     * Constructs the exception with given message.
     * </p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public ClientPersistenceException(String message) {
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
    public ClientPersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
