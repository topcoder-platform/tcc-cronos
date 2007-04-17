/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;


/**
 * This exception is thrown from RejectReasonSessionBean when any of needed environment variables to create an instance
 * of RejecReasonDAO can't be found or with an illegal value.
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonDAOConfigurationException extends RejectReasonDAOException {
    /**
     * <p>
     * Creates a new instance of <code>RejectReasonDAOConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public RejectReasonDAOConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>RejectReasonDAOConfigurationException</code> class with a detail error message
     * and the original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public RejectReasonDAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
