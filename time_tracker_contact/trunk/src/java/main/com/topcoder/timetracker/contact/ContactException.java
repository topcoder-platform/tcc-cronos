/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.util.errorhandling.BaseException;


/**
 * <p>
 * This exception will be the base exception for all exceptions thrown by the <code>ContactManager</code> and
 * <code>AddressManager</code>. This exception can be used by the application to simply their exception
 * processing by catching a single exception regardless of the actual subclass.
 * <p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This exception is thread safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ContactException extends BaseException {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -5366851977654405823L;

    /**
     * <p>Constructs the exception with given message.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     */
    public ContactException(String message) {
        super(message);
    }

    /**
     * <p>Constructs the exception with given message and cause.</p>
     *
     * @param message a possible null, possible empty(trim'd) error message
     * @param cause a possibly null cause exception
     */
    public ContactException(String message, Exception cause) {
        super(message, cause);
    }
}
