/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

/**
 * <p>
 * This exception is thrown when the configuration values read for the {@link FirefoxExtensionHelper} class are either
 * missing or erroneous. The constructors in this exception should wrap those of the base class.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author Ghostar, TCSDEVELOPER
 * @version 1.0
 */
public class FirefoxExtensionConfigurationException extends FirefoxClientException {
    /**
     * <p>
     * Creates a new instance of <code>FirefoxExtensionConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public FirefoxExtensionConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>FirefoxExtensionConfigurationException</code> class with a detail error message
     * and the original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public FirefoxExtensionConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
