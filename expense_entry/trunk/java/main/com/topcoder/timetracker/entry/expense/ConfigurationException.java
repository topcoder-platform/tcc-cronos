/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * Defines a custom exception which is thrown by the manager and DAO implementations if anything goes wrong in the
 * process of loading the configuration file or if the information is missing or corrupt.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ConfigurationException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>ConfigurationException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
