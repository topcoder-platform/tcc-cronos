/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception may be thrown by <code>UserManager</code>,<code>UserStoreManagerImpl</code>,
 * <code>DbUserStore</code>, or <code>UserPersistenceImpl</code> if any error occurs while reading
 * configuration parameters, or instantiating and initializing inner fields. Usually this
 * exception will wrap the original exception which caused the operation to fail.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ConfigurationException extends BaseException {

    /**
     * <p>
     * Constructs a new <code>ConfigurationException</code> with the given message providing the
     * details of the error that occurred.
     * </p>
     *
     * @param message the details of the error that occurred.
     */
    public ConfigurationException(String message) {
        super(message);
    }


    /**
     * <p>
     * Constructs a new <code>ConfigurationException</code>, with the given message and cause.
     * </p>
     *
     * @param message the details of the error that occurred.
     * @param cause the original cause of this exception, e.g, ConfigManagerException.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * <p>
     * Constructs a new <code>ConfigurationException</code> with the given cause.
     * </p>
     *
     * @param cause the original cause of this exception, e.g, ConfigManagerException.
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}