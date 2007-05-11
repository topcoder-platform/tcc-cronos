/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * This exception will be thrown by any persistence plugin implementing the <code>AuditPersistence</code>, whenever
 * there are any problems in persisting information.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditPersistenceException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 3093072502568039738L;

	/**
     * <p>
     * Creates a new instance of <code>AuditPersistenceException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public AuditPersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>AuditPersistenceException</code> class with a detail error message and the
     * original exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public AuditPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
