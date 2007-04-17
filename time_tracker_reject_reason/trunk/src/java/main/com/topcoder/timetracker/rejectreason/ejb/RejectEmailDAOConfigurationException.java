/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;


/**
 * <p>
 * This exception is thrown from RejectEmailSessionBean when any of needed environment variables to create an instance
 * of RejectEmailDAO can't be found or with an illegal value.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailDAOConfigurationException extends RejectEmailDAOException {
    /**
     * <p>
     * Creates a new instance of <code>RejectEmailDAOConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectEmailDAOConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectEmailDAOConfigurationException</code> class with a detail error message
     * and the original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectEmailDAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
