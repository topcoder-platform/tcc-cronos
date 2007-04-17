/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;


/**
 * <p>
 * This exception will be thrown by the Manager classes, EJB entities, and DAO classes when the property of the
 * properties of given <code>Address</code>/<code>Contact</code> is invalid. This exception will be exposed to
 * the caller of addContact/Address(s), updateContact/Address(s), associate/deassociate method.
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
public class InvalidPropertyException extends ContactException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -1812570027734146689L;

    /**
     * <p>Constructs the exception with given message.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public InvalidPropertyException(String message) {
        super(message);
    }

    /**
     * <p>Constructs the exception with given message and cause.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public InvalidPropertyException(String message, Exception cause) {
        super(message, cause);
    }
}
