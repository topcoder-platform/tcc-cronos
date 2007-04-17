/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>
 * This exception will be thrown by the Manager classes, EJB entities, and DAO classes when they encounter
 * database exceptions. This exception will be exposed to the caller of database related methods.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class PersistenceException extends ContactException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -1859188209707290777L;

    /**
     * <p>Constructs the exception with given message.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * <p>Constructs the exception with given message and cause.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
