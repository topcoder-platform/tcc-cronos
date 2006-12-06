/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

/**
 * <p>
 * This exception is thrown when errors occur while setting or retrieving the persistent values through one of the
 * {@link ExtensionPersistence} implementations. This could be due to IO or security errors, as the code is run on the
 * client side. The constructors in this exception should wrap those of the base class.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionPersistenceException extends FirefoxClientException {
    /**
     * <p>
     * Creates a new instance of <code>FirefoxExtensionPersistenceException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public FirefoxExtensionPersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>FirefoxExtensionPersistenceException</code> class with a detail error message
     * and the original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public FirefoxExtensionPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
