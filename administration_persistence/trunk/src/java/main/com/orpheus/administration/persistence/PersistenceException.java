/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.AdministrationException;

/**
 * A specialization of <code>AdministrationException</code> used by the <i>Administration Persistence</i> component
 * to indicate an error while accessing the data store or any other checked exception that prevents a data-store
 * related operation from succeeding.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class PersistenceException extends AdministrationException {
    /**
     * Constructs a new <code>PersistenceException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>PersistenceException</code> with the specified message and cause.
     *
     * @param message a description of the reason for this exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
