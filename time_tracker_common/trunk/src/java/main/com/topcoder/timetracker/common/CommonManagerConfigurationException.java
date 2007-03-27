/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 *  This exception extends the <code>CommonMangementException</code>, and it is thrown if the configured value is
 *  invalid, or any required configuration value is missing. It is also used to wrap the exceptions from
 *  Config Manager and IDGenerator components.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is immutable since its super class <code>CommonManagementException</code> is immutable,
 *  so this class is thread-safe.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class CommonManagerConfigurationException extends CommonManagementException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 4964336138135845467L;

    /**
     * <p>
     * Constructor with error message.
     * </p>
     *
     * @param message the error message
     */
    public CommonManagerConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor with error cause.
     * </p>
     *
     * @param cause the cause of this exception
     */
    public CommonManagerConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Constructor with error message and inner cause.
     * </p>
     *
     * @param message the error message.
     * @param cause the cause of this exception.
     */
    public CommonManagerConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
