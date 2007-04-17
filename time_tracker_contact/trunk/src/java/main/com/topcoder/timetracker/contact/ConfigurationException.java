/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>
 * This exception will be thrown by the Manager and DAO implementations when they encounter a configuration error.
 * This exception will be exposed to the caller of constructor of <code>ContactManager</code>/<code>AddressManager
 * </code> and <code>ContactDAO</code>/<code>AddressDAO</code> implementations.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationException extends ContactException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -5860157333140606362L;

    /**
     * <p>Constructs the exception with given message.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>Constructs the exception with given message and cause.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public ConfigurationException(String message, Exception cause) {
        super(message, cause);
    }
}
