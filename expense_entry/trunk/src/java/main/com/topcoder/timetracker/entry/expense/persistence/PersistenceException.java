/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.util.errorhandling.BaseException;

/**
 * <p>
 * Defines a custom exception which is thrown to wrap any persistence implementation specific exception. These
 * exceptions are thrown by the persistence interfaces and implementations, EJBs, and the manager interfaces and
 * implementations. Since they are implementation specific, there needs to be a common way to report them to the user,
 * and that is what this exception is used for. This exception is originally thrown in the persistence
 * implementations. The business logic (EJB) and delegate layer (the manager classes) will forward them to the user.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class PersistenceException extends BaseException {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -7381193438907411750L;

	/**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message.
     * </p>
     *
     * @param message a detail error message describing the error.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Creates a new instance of <code>PersistenceException</code> class with a detail error message and the original
     * exception causing the error.
     * </p>
     *
     * @param message a detail error message describing the error.
     * @param cause an exception representing the cause of the error.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
