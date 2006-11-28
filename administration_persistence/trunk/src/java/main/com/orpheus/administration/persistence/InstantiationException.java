/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.orpheus.administration.AdministrationException;

/**
 * Specialization of <code>AdministrationException</code> used by configurable classes to indicate that a required
 * object cannot be instantiated via the object factory, or that the configuration namespace or a required property
 * is missing or invalid.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 * @see OrpheusMessageDataStore
 * @see LocalOrpheusMessageDataStore
 * @see RemoteOrpheusMessageDataStore
 * @see OrpheusMessengerPlugin
 * @see LocalOrpheusMessengerPlugin
 * @see RemoteOrpheusMessengerPlugin
 * @see com.orpheus.administration.persistence.impl.SQLServerAdminDataDAO
 * @see com.orpheus.administration.persistence.impl.SQLServerMessageDAO
 * @see DAOFactory
 */

public class InstantiationException extends AdministrationException {
    /**
     * Constructs a new <code>InstantiationException</code> with the specified message.
     *
     * @param message a description of the reason for this exception
     */
    public InstantiationException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>InstantiationException</code> with the specified message and cause.
     *
     * @param message a description of the reason for this exception
     * @param cause the lower-level exception that caused this exception to be thrown
     */
    public InstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
