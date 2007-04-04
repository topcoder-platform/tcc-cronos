/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception will be thrown by classes within this component whenever there are problems in initialization from
 * configuration, such as missing or invalid configuration parameters.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditConfigurationException extends BaseException {
    /**
     * <p>
     * Creates a new instance of <code>AuditConfigurationException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public AuditConfigurationException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>AuditConfigurationException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public AuditConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
