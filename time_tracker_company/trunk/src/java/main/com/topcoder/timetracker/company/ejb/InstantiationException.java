/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception signals an error during the instantiation of the LocalCompanyManagerDelegate. This error could be due
 * to the provided namespace being not recognized by ConfigManager, or the required jndi property not being available.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class InstantiationException extends BaseException {
    /**
     * <p>
     * Creates a new exception instance with this error message.
     * </p>
     *
     * @param msg error message
     */
    public InstantiationException(String msg) {
        super(msg);
    }

    /**
     * <p>
     * Creates a new exception instance with this error message and cause of error.
     * </p>
     *
     * @param msg error message
     * @param cause cause of error.
     */
    public InstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
